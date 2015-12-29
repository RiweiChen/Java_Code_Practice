package com.crw.DB.Import;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
//多线程实现下载。
public class FileDownloader extends Thread {
	String _urlStr;// 要下载的路径
	String _dirName;// 要保存的目标文件夹

	public FileDownloader(String urlStr, String dirName) {
		_urlStr = urlStr;
		_dirName = dirName;
	}

	@Override
	public void run() {
		// 准备拼接新的文件名（保存在存储卡后的文件名）
		String newFilename = _urlStr.substring(_urlStr.lastIndexOf("/") + 1);
		newFilename = _dirName + "/"+newFilename;
		File file = new File(newFilename);
		// 如果目标文件已经存在，则删除。产生覆盖旧文件的效果
		if (file.exists()) {
			file.delete();
		}
		try {
			// 构造URL
			URL url = new URL(_urlStr);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			System.out.println("长度 :" + contentLength);
			System.out.println("文件目录："+_dirName);
			System.out.println("文件名："+newFilename);
			// 输入流
			InputStream is = con.getInputStream();

			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(newFilename);
		
			
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();

		} catch (Exception e) 
		{
			e.printStackTrace();
		}

	}
}
