package com.crw.DB.Import;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
//���߳�ʵ�����ء�
public class FileDownloader extends Thread {
	String _urlStr;// Ҫ���ص�·��
	String _dirName;// Ҫ�����Ŀ���ļ���

	public FileDownloader(String urlStr, String dirName) {
		_urlStr = urlStr;
		_dirName = dirName;
	}

	@Override
	public void run() {
		// ׼��ƴ���µ��ļ����������ڴ洢������ļ�����
		String newFilename = _urlStr.substring(_urlStr.lastIndexOf("/") + 1);
		newFilename = _dirName + "/"+newFilename;
		File file = new File(newFilename);
		// ���Ŀ���ļ��Ѿ����ڣ���ɾ�����������Ǿ��ļ���Ч��
		if (file.exists()) {
			file.delete();
		}
		try {
			// ����URL
			URL url = new URL(_urlStr);
			// ������
			URLConnection con = url.openConnection();
			// ����ļ��ĳ���
			int contentLength = con.getContentLength();
			System.out.println("���� :" + contentLength);
			System.out.println("�ļ�Ŀ¼��"+_dirName);
			System.out.println("�ļ�����"+newFilename);
			// ������
			InputStream is = con.getInputStream();

			// 1K�����ݻ���
			byte[] bs = new byte[1024];
			// ��ȡ�������ݳ���
			int len;
			// ������ļ���
			OutputStream os = new FileOutputStream(newFilename);
		
			
			// ��ʼ��ȡ
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// ��ϣ��ر���������
			os.close();
			is.close();

		} catch (Exception e) 
		{
			e.printStackTrace();
		}

	}
}
