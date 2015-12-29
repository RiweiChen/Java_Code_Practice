package com.crw.Itest.classify;

import java.util.ArrayList;
import java.util.List;

import com.crw.DB.MyDBOpenHelper;
import com.crw.Itest.exercise.Activity_exercise_fillblank_show;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Activity_category_mainpoint_fillblank extends Activity 
{
	private ListView listView;
	List<String> data;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//控件自己建立。
		listView=new ListView(this);
		//数据库有关的操作。
		MyDBOpenHelper myDBOpenHelper;
		SQLiteDatabase db;
		Cursor c;
		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		String currentDB=userdata.getString("currentDB", "");//设置当前数据库。
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // 创建dbopenHelper;   	
		db = myDBOpenHelper.getWritableDatabase();//取得数据库
		c = db.rawQuery("select  distinct qmainpoint from q_fillblank where 1",null);
		//获取数据。
		data = new ArrayList<String>();
		while(c.moveToNext())
		{
			data.add(c.getString(c.getColumnIndex("qmainpoint")));
		}
		//适配器的设置。
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,data));
		setContentView(listView);
		
		//添加点击  
		listView.setOnItemClickListener(new OnItemClickListener() 
		{  
			@Override  
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{  
				//构造条件
				String qsource=data.get(arg2);
				String sql="select * from q_fillblank where qmainpoint="+"'"+data.get(arg2)+"'";
				Intent intent=new Intent(Activity_category_mainpoint_fillblank.this,Activity_exercise_fillblank_show.class);
				Bundle data=new Bundle();
				data.putString("sql", sql);
				data.putString("qsource", qsource);
				intent.putExtras(data);
				startActivity(intent);
				
			}  
		});  

	 


	}
}
