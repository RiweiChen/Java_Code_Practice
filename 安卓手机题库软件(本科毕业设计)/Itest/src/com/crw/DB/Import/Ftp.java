package com.crw.DB.Import;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Ftp {
	/** 
	 * 通过ftp上传文件 
	 * @param url ftp服务器地址 如： 192.168.1.110 
	 * @param port 端口如 ： 21 
	 * @param username  登录名 
	 * @param password   密码 
	 * @param remotePath  上到ftp服务器的磁盘路径 
	 * @param fileNamePath  要上传的文件路径 
	 * @param fileName      要上传的文件名 
	 * @return 
	 */ 
	public String url;
	public int port;
	public String username;
	public String password; 
	public String remotePath;
	public String localPath;
	public String fileName;
	public Ftp(String url, int port,String username, String password, String remotePath,String localPath) 
	{
		setUrl(url);
		setPort(port);
		setUsername(username);
		setPassword(password);
		setRemotePath(remotePath);
		setFileNamePath(localPath);
		
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getFileNamePath() {
		return localPath;
	}

	public void setFileNamePath(String fileNamePath) {
		this.localPath = fileNamePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String ftpUpload(String fileName) 
	{
	 setFileName(fileName);	
	 FTPClient ftpClient = new FTPClient();  
	 FileInputStream fis = null;  
	 String returnMessage = "0";  
	 try {  
	     ftpClient.connect(url, port);  
	     boolean loginResult = ftpClient.login(username, password);  
	     int returnCode = ftpClient.getReplyCode();  
	     if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登录成功  
	         ftpClient.makeDirectory(remotePath);  
	         // 设置上传目录  
	         ftpClient.changeWorkingDirectory(remotePath);  
	         ftpClient.setBufferSize(1024);  
	         ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
	         ftpClient.setControlEncoding("UTF-8");  
	         ftpClient.enterLocalPassiveMode();  
	         fis = new FileInputStream(localPath + fileName);  
	         ftpClient.storeFile(fileName, fis);  
	         
	            
	         returnMessage = "1";   //上传成功        
	     } else {// 如果登录失败   
	         returnMessage = "0";  
	         }  
	                
	   
	 } catch (IOException e) {  
	     e.printStackTrace();  
	     throw new RuntimeException("FTP客户端出错！", e);  
	 } finally {  
	     //IOUtils.closeQuietly(fis);  
	 try {  
	     ftpClient.disconnect();  
	 } catch (IOException e) {  
	        e.printStackTrace();  
	        throw new RuntimeException("关闭FTP连接发生异常！", e);  
	    }  
	 }  
	 return returnMessage;  
	}  

	/**
	 * Description: 从FTP服务器下载文件
	 * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return
	 */
	public boolean downFile(String fileName)
	{
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) 
			{
				ftp.disconnect();
				System.out.println("连接服务器失败");
				return success;
			}
			ftp.makeDirectory(remotePath);  
			ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
			
	         ftp.setBufferSize(1024);  
	         ftp.setFileType(ftp.BINARY_FILE_TYPE);
	         ftp.setControlEncoding("UTF-8");  
	         ftp.enterLocalPassiveMode();  

			FTPFile[] fs = ftp.listFiles();
			System.out.println("目录下文件个数："+fs.length);
			for(FTPFile ff:fs)
			{
				System.out.println("文件名为："+ff.getName());
				if(ff.getName().equals(fileName))
				{
					File localFile = new File(localPath+"/"+ff.getName());
					OutputStream is = new FileOutputStream(localFile); 
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			ftp.logout();
			success = true;
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
		return success;
	}
//获取题库文件夹。
	public List<String> getFileList() 
	{
		List<String> fileList= new ArrayList<String>();
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) 
			{
				ftp.disconnect();
				System.out.println("连接服务器失败");
			}
			ftp.makeDirectory(remotePath);  
			ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
			
	         ftp.setBufferSize(1024);  
	         ftp.setFileType(ftp.BINARY_FILE_TYPE);
	         ftp.setControlEncoding("UTF-8");  
	         ftp.enterLocalPassiveMode();  

			FTPFile[] fs = ftp.listFiles();
			System.out.println("目录下文件个数："+(fs.length-2));
			for(FTPFile ff:fs)
			{
				//System.out.println("文件名为："+ff.getName());
				if((ff.getName().equals("."))||(ff.getName().equals(".."))) 
				{
					;
				}
				else
					fileList.add(ff.getName());
			}
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
		return fileList;
	}
}
