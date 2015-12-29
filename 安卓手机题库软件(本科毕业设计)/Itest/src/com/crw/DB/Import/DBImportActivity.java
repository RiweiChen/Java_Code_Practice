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
		
		//�̡߳�
		private Handler handler=null; 
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_dbimport);
			//�������������.
			if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
			}
			listview=(ListView)findViewById(R.id.list);
			//���̴߳���
			handler=new Handler();  
			new Thread(new Runnable()
			{
                public void run(){
                        while(true)
                        {
                			ftp=new Ftp("chenriwei.mfbz.tk", 21, "u140431346", "889029", "/public_html/DB", "/data/data/com.crw.Itest/databases");
                			//�����ļ�����
                			filelist= ftp.getFileList();
                			handler.post(runnableUi);   
                			//�б�����

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
		
		//���������Ĳ˵�
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			menu.setHeaderTitle("��ѡ����Ҫ�Ĳ�����");
			menu.add(0, 0, 0, "����");
			menu.add(0, 1, 1, "�鿴");
			super.onCreateContextMenu(menu, v, menuInfo);
		}
		//���������Ĳ˵�ѡ�����¼�
	    @Override
	    public boolean onContextItemSelected(MenuItem item)
	    {
	        //��ȡ��ǰ��ѡ��Ĳ˵������Ϣ  
	    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        switch(item.getItemId()){
	        case 0://ɾ��  
	        	Toast.makeText(DBImportActivity.this, "�Ѿ�������ɣ�", Toast.LENGTH_SHORT).show();
	        	ftp.downFile(filelist.get((int) info.id));
	            break;
	        case 1://�鿴��
	        	Toast.makeText(DBImportActivity.this, "�鿴��", Toast.LENGTH_SHORT).show();
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
		// ����Runnable������runnable�и��½���  
		Runnable   runnableUi=new  Runnable(){  
		@Override  
		public void run() 
		{  
		 //���½���  
			setAdapter();
			registerForContextMenu(listview);	

		}  
	};  


}

