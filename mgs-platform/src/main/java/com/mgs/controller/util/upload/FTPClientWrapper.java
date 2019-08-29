package com.mgs.controller.util.upload;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPClientWrapper {

    /** FTP客户端实例 */
    private FTPClient ftpClient;

    /**
     * 私有构造器
     */
    private FTPClientWrapper() {
    }

    /**
     * 内部类维护单例，防止并发问题
     *
     */
    private static class SingletonFactory {
        private static FTPClientWrapper instance = new FTPClientWrapper();
    }

    /**
     * 获取实例
     * @return {@link FTPClientWrapper}
     */
    public static FTPClientWrapper getInstance() {
        return SingletonFactory.instance;
    }

    /**
     * 连接文件服务器
     * @param addr 文件服务器地址
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @throws Exception 
     */
    public void connect(String addr, int port, String username, String password) throws Exception {
        ftpClient = new FTPClient();
        // 连接
        ftpClient.connect(addr, port);
        // 登录
        ftpClient.login(username, password);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        // 判断文件服务器是否可用？？
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            ftpClient.disconnect();
        }
    }

    /**
     * 连接文件服务器
     * @param addr 文件服务器地址
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @param workingDirectory 目标连接工作目录
     * @throws Exception 
     */
    public void connect(String addr, int port, String username, String password, String workingDirectory) throws Exception {
        ftpClient = new FTPClient();
        // 连接
        ftpClient.connect(addr, port);
        // 登录
        ftpClient.login(username, password);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        // 判断文件服务器是否可用？？
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            ftpClient.disconnect();
        }
        changeWorkingDirectory(workingDirectory);
    }

    /**
     * 关闭连接，使用完连接之后，一定要关闭连接，否则服务器会抛出 Connection reset by peer的错误
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        ftpClient.disconnect();
    }

    /**
     * 切换工作目录
     * @param directory 目标工作目录
     * @throws IOException
     */
    public void changeWorkingDirectory(String directory) throws IOException {
        // 切换到目标工作目录
        if (!ftpClient.changeWorkingDirectory(directory)) {
            ftpClient.makeDirectory(directory);
            ftpClient.changeWorkingDirectory(directory);
        }
    }

    /** 
     * @param file 上传的文件或文件夹 
     * @throws Exception 
     */
    public void upload(File file) throws Exception {

        if (file == null) {
            throw new RuntimeException("上传文件为空");
        }
        // 是文件，直接上传
        if (!file.isDirectory()) {
            storeFile(new File(file.getPath()));
            return;
        }

        ftpClient.makeDirectory(file.getName());
        ftpClient.changeWorkingDirectory(file.getName());
        // 文件夹，递归上传所有文件
        for (File item : file.listFiles()) {
            if (!item.isDirectory()) {
                storeFile(item);
                continue;
            }
            upload(item);
            ftpClient.changeToParentDirectory();
        }
    }

    public boolean upload(InputStream is,String fileName) throws Exception{
        boolean result = ftpClient.storeFile(fileName, is);
        return result;
    }

    /**
     * 删除文件
     * @param fileName 要删除的文件地址
     * @return true/false
     * @throws Exception
     */
    public boolean delete(String fileName) throws Exception {
        return ftpClient.deleteFile(fileName);
    }

    /**
     * 存储文件
     * @param file {@link File}
     * @throws Exception
     */
    private void storeFile(File file) throws Exception {
        FileInputStream input = new FileInputStream(file);
        ftpClient.storeFile(file.getName(), input);
        input.close();
    }

    /**
     * 下载文件
     * @param ftpFile 文件服务器上的文件地址
     * @param dstFile 输出文件的路径和名称
     * @throws Exception 
     */
    public void downLoad(String ftpFile, String dstFile) throws Exception {
        File file = new File(dstFile);
        FileOutputStream fos = new FileOutputStream(file);
        ftpClient.retrieveFile(ftpFile, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 从文件服务器获取文件流
     * @param ftpFile 文件服务器上的文件地址
     * @return {@link InputStream}
     * @throws IOException
     */
    public InputStream retrieveFileStream(String ftpFile) throws IOException {
        return ftpClient.retrieveFileStream(ftpFile);
    }

    public static void main(String[] args) throws Exception {
        FTPClientWrapper emp = FTPClientWrapper.getInstance();
        String addr = "192.168.0.1";
        int port = 21;
        String username = "ftpuser";
        String password = "ftpuser";
        emp.connect(addr, port, username, password);

        // 上传文件
        emp.changeWorkingDirectory("putTest");
        
        //String filepath = "E://test/test.docx";
        File file = new File("E://test/test.docx");
        //删除文件
        emp.delete("/home/putTest/test.docx");
        
        //上传文件
        emp.upload(file);
        emp.closeConnection();
    }
}
