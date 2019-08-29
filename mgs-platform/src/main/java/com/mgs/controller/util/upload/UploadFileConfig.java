package com.mgs.controller.util.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ftp")
@Data
public class UploadFileConfig {

    /**
     * 服务地址
     */
    private String addr;

    /**
     * 端口
     */
    private String port;

    /**
     * 服务器登录名
     */
    private String userName;

    /**
     * 用户登录秘密
     */
    private String passWord;

    /**
     * 文件目录前缀
     */
    private String fileDirRefix;

    /**
     * 文件url前缀
     */
    private String fileUrlRefix;

    /**
     * 订单文件目录
     */
    private String orderFileDir;
}
