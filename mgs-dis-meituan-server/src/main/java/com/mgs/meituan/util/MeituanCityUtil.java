package com.mgs.meituan.util;

import com.mgs.meituan.dto.city.request.MeituanCityDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

public class MeituanCityUtil {

    public static Map<String, Integer> getCityMap() throws  Exception{
        //换上你的路径就可以用了emm
        File file = new File("cityId.xls");
        FileInputStream in = new FileInputStream(file);
        Workbook workbook =  new HSSFWorkbook(in);

        List<MeituanCityDTO> meituanCityList = new ArrayList<MeituanCityDTO>();
        Sheet sheet = workbook.getSheetAt(0);
        int count = 0;
        MeituanCityDTO meituanCityDTO = new MeituanCityDTO();
        for(Row row: sheet){
            try{
                if(count < 1){
                    count ++;
                    continue;
                }

                int end = row.getLastCellNum();
                StringBuffer stringBuffer = new StringBuffer();
                int flag = 0; //标志，有两个就输出
                for(int i = 0; i < end; i++){
                    Cell cell = row.getCell(i);
                    Object obj = getValue(cell);
                    String str = String.valueOf(obj);

                    ++flag;
                    if(flag % 2 != 0) {
                        meituanCityDTO.setCityCode(Integer.valueOf(str));
                    }
                    if(flag % 2 == 0){

                        /*stringBuffer.append(str);
                        stringBuffer.append("\n");
                         bufferedWriter.write(stringBuffer.toString());*/

                        meituanCityDTO.setCityName(str);
                        meituanCityList.add(meituanCityDTO);
                        meituanCityDTO = new MeituanCityDTO();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Map<String, Integer> cityMap = meituanCityList.stream().collect(Collectors.toMap(MeituanCityDTO::getCityName, t->t.getCityCode(), (s1, s2) -> s2));
        return cityMap;
    }

    private static Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellType()) {
            case CELL_TYPE_BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case CELL_TYPE_ERROR:
                obj = cell.getErrorCellValue();
                break;
            case CELL_TYPE_NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case CELL_TYPE_STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }
}
