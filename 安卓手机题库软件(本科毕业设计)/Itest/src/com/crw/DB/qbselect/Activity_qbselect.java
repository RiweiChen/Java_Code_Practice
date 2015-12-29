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
		fill(file.listFiles());// 列出该路径下的所有文件
	
		registerForContextMenu(listview);
		
	}

	private void fill(File[] files) 
	{
		items = new ArrayList<String>();
		if (files == null) {
			return;
		}

		/* 取得文件名放入ArrayList */
		for (File file : files) {
			items.add(file.getName());
		}

		/* 将ArrayList放入ArrayAdapter */
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
		String currentDB=userdata.getString("currentDB", "");//设置当前数据库。
		userdata.edit()
        .putString("currentDB",items.get(position))//设置当前数据库。
        .commit();
		Toast.makeText(Activity_qbselect.this,"题库切换成功！",Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
		intent.setClass(Activity_qbselect.this, MainActivity.class);
		startActivity(intent);
	}*/
	
	//创建上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.setHeaderTitle("请选择需要的操作！");
		menu.add(0, 0, 0, "使用该题库");
		menu.add(0, 1, 1, "删除该题库");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	//设置上下文菜单选择处理事件
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        //获取当前被选择的菜单项的信息  
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	int position=(int) info.id;
    	File file = new File(path + File.separator + items.get(position));
        switch(item.getItemId()){
        case 0://切换  
    		if (file.isDirectory()) {
    			fill(file.listFiles());
    		}    		
    		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
    		String currentDB=userdata.getString("currentDB", "");//设置当前数据库。
    		userdata.edit()
            .putString("currentDB",items.get(position))//设置当前数据库。
            .commit();
    		Toast.makeText(Activity_qbselect.this,"题库切换成功！",Toast.LENGTH_LONG).show();
    		Intent intent = new Intent();
    		intent.setClass(Activity_qbselect.this, MainActivity.class);
    		startActivity(intent);
            break;
        case 1://删除。
        	file.delete();
        	Toast.makeText(getApplicationContext(), "已经删除完成！", Toast.LENGTH_SHORT).show();
            break;    

        }
        return true;
    }
    
}

