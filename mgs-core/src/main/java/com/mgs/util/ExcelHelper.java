package com.mgs.util;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 导出excel
 */

public class ExcelHelper<T> {

    private static final Log log = LogFactory.getLog(ExcelHelper.class);

    /**
     * 从本地模板导出Excel
     * @param data
     * @param templateLocation
     * @return
     * @throws Exception
     */
    public static ByteArrayInputStream exportFromLocal(Map data, String templateLocation)throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(templateLocation));

        XLSTransformer transformer = new XLSTransformer();
        transformer.transformWorkbook(workbook, data);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        workbook.write(output);
        output.flush();
        byte[] byteArray = output.toByteArray();
        output.close();
        return new ByteArrayInputStream(byteArray, 0, byteArray.length);
    }

    /**
     * 从远程模板导出Excel
     * @param data
     * @param templateUrl
     * @return
     * @throws Exception
     */
    public static ByteArrayInputStream exportFromRemote(Map data,String templateUrl)throws Exception {
        InputStream is=getTemplateStreamRemote(templateUrl);
        if(is==null){
            log.error("########templateUrl == :"+templateUrl) ;
            throw new RuntimeException("找不到模板文件");
        }
        HSSFWorkbook workbook = new HSSFWorkbook(is);

        XLSTransformer transformer = new XLSTransformer();
        transformer.transformWorkbook(workbook, data);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        workbook.write(output);
        output.flush();
        byte[] byteArray = output.toByteArray();
        output.close();
        return new ByteArrayInputStream(byteArray, 0, byteArray.length);
    }

    /**
     * 远程读取模板数据流
     * @param url
     * @return
     */
    private static InputStream getTemplateStreamRemote(String url){
        URL remoteUrl = null;
        InputStream is =null;
        try{
            remoteUrl = new URL(url);
        }catch(MalformedURLException e){
            log.error("getTemplateStreamRemote",e);
        }
        try{
            HttpURLConnection conn = (HttpURLConnection) remoteUrl.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream(); //得到网络返回的输入流
        }catch(IOException e){
            log.error("getTemplateStreamRemote",e);
        }
        return is;
    }
}
