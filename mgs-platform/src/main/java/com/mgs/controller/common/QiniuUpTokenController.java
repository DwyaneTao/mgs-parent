package com.mgs.controller.common;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.dto.DeleteFileRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.storage.Configuration;
import com.qiniu.common.Zone;
/**
 * @author py
 * @date 2019/7/30 10:31
 **/
@RestController
@Slf4j
@RequestMapping(value = "/upload")
public class QiniuUpTokenController {
    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "4UgnSE4vpzLfABDxGQoS4V6emcT2T79KJDSEkp9t";
    private static final String SECRET_KEY = "wkBBKRVSSqr1FpzHABtxhqo_tSuD54HC4b7pRmG5";

    // 要上传的空间
    private static final String bucketname = "mgsfile";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    /**
     * 获得文件token
     */
    @RequestMapping(value = "/getQiniuUpToken",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response getQiniuUpToken() {
        Response response = new Response();
        try {
            response.setResult(1);
            response.setModel(auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1)));
            log.info("getQiniuUpToken result:"+response.getResult());
        } catch (Exception e) {
            log.error("getQiniuUpToken 接口异常",e);
            response=new com.mgs.common.Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除文件
     */
    @RequestMapping(value = "/deleteFile",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteFile(@RequestBody DeleteFileRequestDTO request) {
        Response response = new Response();
        try {
            Configuration cfg = new Configuration(Zone.zone0());
            BucketManager bucketManager = new BucketManager(auth, cfg);
            bucketManager.delete(bucketname,request.getKey());
            response.setResult(1);
            log.info("deleteFile result:"+response.getResult());
        } catch (Exception e) {
            log.error("deleteFile 接口异常",e);
            response=new com.mgs.common.Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }



}
