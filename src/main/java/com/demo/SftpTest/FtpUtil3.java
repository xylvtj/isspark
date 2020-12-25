package com.demo.SftpTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp上传下载工具类lb
 */
public class FtpUtil3 {

    /**
     * Description: 向FTP服务器上传文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {  //进不去目录，说明该目录不存在
                        if (!ftp.makeDirectory(tempPath)) { //创建目录
                            //如果创建文件目录失败，则返回
                            System.out.println("创建文件目录"+tempPath+"失败");
                            return result;
                        } else {
                            //目录存在，则直接进入该目录
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //被动模式（可以提高针对不同FTP服务器的兼容性）
            ftp.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
//                if (!ftp.storeFile(filename, input)) {
            if (!ftp.storeFile(new String(filename.getBytes("UTF-8"), "iso-8859-1"), input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            ftp.setControlEncoding("UTF-8");
            //被动模式（可以提高针对不同FTP服务器的兼容性）
            ftp.enterLocalPassiveMode();
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());

                    OutputStream is = new FileOutputStream(localFile);
                    // ftp需使用ISO-8859-1编码格式
                    String realName = new String(ff.getName().getBytes("UTF-8"), "ISO-8859-1");
//                        ftp.retrieveFile(ff.getName(), is);
                    ftp.retrieveFile(realName, is);
                    is.close();
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * 直接将远程文件下载到流中【注意：使用完要记住关闭返回的InputStream流】
     * @param host
     * @param port
     * @param username
     * @param password
     * @param remotePath
     * @param fileName
     * @return 直接将远程文件下载到流中
     */
    public static InputStream downloadFile(String host, int port, String username, String password, String remotePath,
                                           String fileName) {
        InputStream result = null;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            ftp.setControlEncoding("UTF-8");
            //被动模式（可以提高针对不同FTP服务器的兼容性）
            ftp.enterLocalPassiveMode();
            // ftp需使用ISO-8859-1编码格式
            String realName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            result = ftp.retrieveFileStream(realName);
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * Description: 判断指定名称的文件在FTP服务器上是否存在
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @return true：存在   false：不存在/或者方法出现错误
     * @throws Exception
     */
    public static boolean isExistInFTP(String host, int port, String username, String password, String remotePath,
                                       String fileName){
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
//                    return result;
                throw new RuntimeException("FTP连接异常。。。");
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            //这一行一定要有，否则取到的文件名会乱码，无法和参数 fileName 匹配
            ftp.setControlEncoding("UTF-8");
            //被动模式（可以提高针对不同FTP服务器的兼容性）
            ftp.enterLocalPassiveMode();
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                String name = ff.getName();
                if (name.equals(fileName)) {
                    result = true;
                    break;
//                        OutputStream is = new FileOutputStream(localFile);
//                        ftp.retrieveFile(ff.getName(), is);
//                        is.close();
                }
            }
            ftp.logout();
        } catch (Exception e) {
            System.out.println("------------查询FTP上文件是否存在时出现异常，直接返回：不存在------------");
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    //        ftp上传文件测试main函数
    public static void main(String[] args) {
        try {
//                FileInputStream in=new FileInputStream(new File("D:\\Tomcat 5.5\\pictures\\t0176ee418172932841.jpg"));  
//                boolean flag = uploadFile("192.168.111.128", 21, "用户名", "密码", "/www/images","/2017/11/19", "hello.jpg", in);  

//                FileInputStream in=new FileInputStream(new File("D:/副本2.txt"));

            boolean flag = false;
//                boolean flag = uploadFile("10.18.90.13", 21, "root", "root", "/crm/fileUpload/Account/test","", "副本291195.txt", in); 
//                flag =uploadFile("10.12.9.163",21,"aps","aps","/crm/fileUpload/Account/test","","副本291195.txt",in);
//                String name = "zhicai-beijing-00000385-20190624.txt";
            String name = "记录.txt";
//                String name = "asgagh2.txt";
//                flag =downloadFile("10.12.9.163", 21, "apps","apps", "/crm/fileUpload/Account/test", name, "D:/a/b/f");
            InputStream input = downloadFile("10.12.9.163", 21, "apps","apps", "/crm/fileUpload/Account/test", name);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            FileOutputStream out;
            out = new FileOutputStream("D:/a/b/f/1.txt");
            out.write(buffer);
            out.close();
//                        loadFile("10.12.9.163",21,"apps","apps","/crm/fileUpload/Account/test","","副本291195.txt",in);
//                System.out.println(flag);
//                flag =uploadFile("10.12.4.159",2121,"ji","Y349#","/jitiles/excel","","副本291195.txt",in);
            System.out.println(flag);

//                boolean existInFTP = FtpUtil3.isExistInFTP("10.18.90.13",21,"root","root","/fileUpload/Account/test6","副本291195.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}