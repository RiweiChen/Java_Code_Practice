package com.crw.Itest.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crw.DB.MyDBOpenHelper;
import com.crw.Itest.R;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class Activity_search_ask extends ListActivity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		//������������
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.search_ask, 
		new String[]{"qid","qtitle","qanswer",
			"qdifficulty","qexplain","qsource","qimportance"}, 
		new int[]{R.id.qid,R.id.qtitle,R.id.qanswer,
			R.id.qdifficulty,R.id.qexplain,R.id.qsource,R.id.qimportance}); 
		setListAdapter(adapter); 
	}
	private List<Map<String, Object>> getData() 
	{ 
		//���ݿ���ر�����
		MyDBOpenHelper myDBOpenHelper;
		SQLiteDatabase db;
		Cursor c;
		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		String currentDB=userdata.getString("currentDB", "");//���õ�ǰ���ݿ⡣
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // ����dbopenHelper;   	
    	db = myDBOpenHelper.getWritableDatabase();//ȡ�����ݿ�
    	//��ѯ���ݿ⡣
    	//��ùؼ��֡�
		//SharedPreferences userdata = getSharedPreferences(
		//		"userdata", 0);
		String lastKeyword = userdata.getString("lastKeyword", "");
		String keyword ="'"+"%"+lastKeyword+"%"+"'";
    	c = db.rawQuery("select * from q_ask" +
    			" where qtitle like "+keyword+"or qmainpoint like "+keyword,null);
    	
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
		while(c.moveToNext())
		{
			int i=c.getInt(c.getColumnIndex("qid"));
			String title=c.getString(c.getColumnIndex("qtitle"));
			String answer=c.getString(c.getColumnIndex("qanswer"));
			String difficulty=c.getString(c.getColumnIndex("qdifficulty"));
			String explain=c.getString(c.getColumnIndex("qexplain"));
			String source=c.getString(c.getColumnIndex("qsource"));
			String mainpoint=c.getString(c.getColumnIndex("qmainpoint"));

			Map map = new HashMap(); 
			map.put("qid","��ţ�"+i);
			map.put("qtitle", "��Ŀ"+":"+title); 
			map.put("qanswer","��:"+answer); 
			map.put("qdifficulty","�Ѷ�:"+difficulty); 
			map.put("qexplain","����:"+explain); 
			map.put("qsource","��Ŀ��Դ:"+source); 
			map.put("qmainpoint","����֪ʶ��:"+mainpoint); 
			
			list.add(map);
			
		}
		return list; 
	}

}
