package com.mgs.controller.util.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseDTO implements Serializable {


    private static final long serialVersionUID = 758101114390428453L;
    /**
     * 服务器文件名
     * 如果上传时有传入服务器文件名，则用传入的文件名
     * 如果上传时没有传入服务器文件名，则系统生成
     */
    private String realFileName;

    /**
     * 文件URL
     */
    private String fileUrl;

    /**
     * 文件真实路径
     */
    private String filePath;

    /**
     * 文件原始名称
     */
    private String originalFilename;
}
