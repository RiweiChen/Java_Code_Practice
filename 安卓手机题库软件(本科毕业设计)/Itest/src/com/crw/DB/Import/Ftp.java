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
	 * ͨ��ftp�ϴ��ļ� 
	 * @param url ftp��������ַ �磺 192.168.1.110 
	 * @param port �˿��� �� 21 
	 * @param username  ��¼�� 
	 * @param password   ���� 
	 * @param remotePath  �ϵ�ftp�������Ĵ���·�� 
	 * @param fileNamePath  Ҫ�ϴ����ļ�·�� 
	 * @param fileName      Ҫ�ϴ����ļ��� 
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
	     if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// �����¼�ɹ�  
	         ftpClient.makeDirectory(remotePath);  
	         // �����ϴ�Ŀ¼  
	         ftpClient.changeWorkingDirectory(remotePath);  
	         ftpClient.setBufferSize(1024);  
	         ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
	         ftpClient.setControlEncoding("UTF-8");  
	         ftpClient.enterLocalPassiveMode();  
	         fis = new FileInputStream(localPath + fileName);  
	         ftpClient.storeFile(fileName, fis);  
	         
	            
	         returnMessage = "1";   //�ϴ��ɹ�        
	     } else {// �����¼ʧ��   
	         returnMessage = "0";  
	         }  
	                
	   
	 } catch (IOException e) {  
	     e.printStackTrace();  
	     throw new RuntimeException("FTP�ͻ��˳���", e);  
	 } finally {  
	     //IOUtils.closeQuietly(fis);  
	 try {  
	     ftpClient.disconnect();  
	 } catch (IOException e) {  
	        e.printStackTrace();  
	        throw new RuntimeException("�ر�FTP���ӷ����쳣��", e);  
	    }  
	 }  
	 return returnMessage;  
	}  

	/**
	 * Description: ��FTP�����������ļ�
	 * @Version1.0 Jul 27, 2008 5:32:36 PM by �޺챣��cuihongbao@d-heaven.com������
	 * @param url FTP������hostname
	 * @param port FTP�������˿�
	 * @param username FTP��¼�˺�
	 * @param password FTP��¼����
	 * @param remotePath FTP�������ϵ����·��
	 * @param fileName Ҫ���ص��ļ���
	 * @param localPath ���غ󱣴浽���ص�·��
	 * @return
	 */
	public boolean downFile(String fileName)
	{
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			//�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
			ftp.login(username, password);//��¼
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) 
			{
				ftp.disconnect();
				System.out.println("���ӷ�����ʧ��");
				return success;
			}
			ftp.makeDirectory(remotePath);  
			ftp.changeWorkingDirectory(remotePath);//ת�Ƶ�FTP������Ŀ¼
			
	         ftp.setBufferSize(1024);  
	         ftp.setFileType(ftp.BINARY_FILE_TYPE);
	         ftp.setControlEncoding("UTF-8");  
	         ftp.enterLocalPassiveMode();  

			FTPFile[] fs = ftp.listFiles();
			System.out.println("Ŀ¼���ļ�������"+fs.length);
			for(FTPFile ff:fs)
			{
				System.out.println("�ļ���Ϊ��"+ff.getName());
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
//��ȡ����ļ��С�
	public List<String> getFileList() 
	{
		List<String> fileList= new ArrayList<String>();
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			//�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
			ftp.login(username, password);//��¼
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) 
			{
				ftp.disconnect();
				System.out.println("���ӷ�����ʧ��");
			}
			ftp.makeDirectory(remotePath);  
			ftp.changeWorkingDirectory(remotePath);//ת�Ƶ�FTP������Ŀ¼
			
	         ftp.setBufferSize(1024);  
	         ftp.setFileType(ftp.BINARY_FILE_TYPE);
	         ftp.setControlEncoding("UTF-8");  
	         ftp.enterLocalPassiveMode();  

			FTPFile[] fs = ftp.listFiles();
			System.out.println("Ŀ¼���ļ�������"+(fs.length-2));
			for(FTPFile ff:fs)
			{
				//System.out.println("�ļ���Ϊ��"+ff.getName());
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
