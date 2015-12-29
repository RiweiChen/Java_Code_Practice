package com.crw.DB.qbselect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.crw.Itest.MainActivity;
import com.crw.Itest.R;

public class Activity_qbselect extends Activity //implements OnItemClickListener  
{
	private List<String> items = null;
	private String path;
	private ListView listview;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylist);
		listview = (ListView) findViewById(R.id.list);
		path="/data/data/com.crw.Itest/databases";
		File file = new File(path);
		fill(file.listFiles());// �г���·���µ������ļ�
	
		registerForContextMenu(listview);
		
	}

	private void fill(File[] files) 
	{
		items = new ArrayList<String>();
		if (files == null) {
			return;
		}

		/* ȡ���ļ�������ArrayList */
		for (File file : files) {
			items.add(file.getName());
		}

		/* ��ArrayList����ArrayAdapter */
		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		listview.setAdapter(fileList);
	}
/*
	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) 
	{
		File file = new File(path + File.separator + items.get(position));
		if (file.isDirectory()) {
			fill(file.listFiles());
		}
		
		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		String currentDB=userdata.getString("currentDB", "");//���õ�ǰ���ݿ⡣
		userdata.edit()
        .putString("currentDB",items.get(position))//���õ�ǰ���ݿ⡣
        .commit();
		Toast.makeText(Activity_qbselect.this,"����л��ɹ���",Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
		intent.setClass(Activity_qbselect.this, MainActivity.class);
		startActivity(intent);
	}*/
	
	//���������Ĳ˵�
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.setHeaderTitle("��ѡ����Ҫ�Ĳ�����");
		menu.add(0, 0, 0, "ʹ�ø����");
		menu.add(0, 1, 1, "ɾ�������");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	//���������Ĳ˵�ѡ�����¼�
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        //��ȡ��ǰ��ѡ��Ĳ˵������Ϣ  
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	int position=(int) info.id;
    	File file = new File(path + File.separator + items.get(position));
        switch(item.getItemId()){
        case 0://�л�  
    		if (file.isDirectory()) {
    			fill(file.listFiles());
    		}    		
    		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
    		String currentDB=userdata.getString("currentDB", "");//���õ�ǰ���ݿ⡣
    		userdata.edit()
            .putString("currentDB",items.get(position))//���õ�ǰ���ݿ⡣
            .commit();
    		Toast.makeText(Activity_qbselect.this,"����л��ɹ���",Toast.LENGTH_LONG).show();
    		Intent intent = new Intent();
    		intent.setClass(Activity_qbselect.this, MainActivity.class);
    		startActivity(intent);
            break;
        case 1://ɾ����
        	file.delete();
        	Toast.makeText(getApplicationContext(), "�Ѿ�ɾ����ɣ�", Toast.LENGTH_SHORT).show();
            break;    

        }
        return true;
    }
    
}

