package com.crw.DB.Import;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.crw.Itest.R;

public class DBImportActivity extends Activity
{
		/*String DATABASE_PATH ="/data/data/com.crw.Itest/databases";
		String DATABASE_FILENAME = "questionbank_java";
		String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;*/
		
		Ftp ftp;
		ListView listview;
		List<String> filelist;
		ArrayAdapter<String> adfileList;
		
		//线程。
		private Handler handler=null; 
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_dbimport);
			//控制网络的问题.
			if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
			}
			listview=(ListView)findViewById(R.id.list);
			//多线程处理。
			handler=new Handler();  
			new Thread(new Runnable()
			{
                public void run(){
                        while(true)
                        {
                			ftp=new Ftp("chenriwei.mfbz.tk", 21, "u140431346", "889029", "/public_html/DB", "/data/data/com.crw.Itest/databases");
                			//保存文件名。
                			filelist= ftp.getFileList();
                			handler.post(runnableUi);   
                			//列表适配

                        }
                }
			}).start();
			//

		}
		public void setAdapter() 
		{
			adfileList = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, filelist);
			listview.setAdapter(adfileList);
		}
		
		//创建上下文菜单
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			menu.setHeaderTitle("请选择需要的操作！");
			menu.add(0, 0, 0, "下载");
			menu.add(0, 1, 1, "查看");
			super.onCreateContextMenu(menu, v, menuInfo);
		}
		//设置上下文菜单选择处理事件
	    @Override
	    public boolean onContextItemSelected(MenuItem item)
	    {
	        //获取当前被选择的菜单项的信息  
	    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        switch(item.getItemId()){
	        case 0://删除  
	        	Toast.makeText(DBImportActivity.this, "已经下载完成！", Toast.LENGTH_SHORT).show();
	        	ftp.downFile(filelist.get((int) info.id));
	            break;
	        case 1://查看。
	        	Toast.makeText(DBImportActivity.this, "查看！", Toast.LENGTH_SHORT).show();
	            break;    

	        }
	        return true;
	    }
	    

		@Override
		public boolean onCreateOptionsMenu(Menu menu) 
		{
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		//
		// 构建Runnable对象，在runnable中更新界面  
		Runnable   runnableUi=new  Runnable(){  
		@Override  
		public void run() 
		{  
		 //更新界面  
			setAdapter();
			registerForContextMenu(listview);	

		}  
	};  


}

