package com.crw.Itest.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crw.DB.MyDBOpenHelper;
import com.crw.Itest.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Activity_collect_choice_show extends ListActivity
{
	List<Map<String, Object>> list;//存放变量
	ArrayList<Integer> idList;
	MyDBOpenHelper myDBOpenHelper;
	SQLiteDatabase db;
	Cursor c;
	String uid;
	String currentDB;
	SharedPreferences userdata;
	SimpleAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		//获得基本的数据库信息。
		userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		currentDB=userdata.getString("currentDB", "");//设置当前数据库。
		uid ="'"+userdata.getString("username","")+"'";
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // 创建dbopenHelper;   	
		db = myDBOpenHelper.getWritableDatabase();//取得数据库	
		
		list = new ArrayList<Map<String, Object>>();
		idList=new ArrayList<Integer>();
		//适配器。
		setAdapter();
		ListView lv=getListView();
		registerForContextMenu(lv);
	}
	public void setAdapter() 
	{
		adapter = new SimpleAdapter(this,getData(),R.layout.collect_choice, 
				new String[]{"qid","qtitle","qoptA","qoptB","qoptC","qoptD","qanswer","qimportance"}, 
				new int[]{R.id.qid,R.id.qtitle,R.id.qoptA,R.id.qoptB,R.id.qoptC,R.id.qoptD,R.id.qanswer,R.id.qimportance}); 
		setListAdapter(adapter); 
		
	}
	
	//创建上下文菜单
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			menu.setHeaderTitle("请选择需要的操作！");
			menu.add(0, 0, 0, "删除");
			menu.add(0, 1, 1, "查看");
			menu.add(0, 2, 2, "更改");
			super.onCreateContextMenu(menu, v, menuInfo);
		}
		//设置上下文菜单选择处理事件
	    @Override
	    public boolean onContextItemSelected(MenuItem item)
	    {
	        //获取当前被选择的菜单项的信息  
	    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    	int qid=idList.get((int) info.id);
	        switch(item.getItemId()){
	        case 0://删除
	        	db.execSQL("delete from f_choice where uid="+uid+" and qid="+qid);
	        	Toast.makeText(Activity_collect_choice_show.this, "成功删除！", Toast.LENGTH_SHORT);
	        	setAdapter();
	            break;
	        case 1://查看。
	        	show(qid);
	        	Toast.makeText(Activity_collect_choice_show.this, "查看！", Toast.LENGTH_SHORT);
	            break;    
	        case 2://更改。
	        	alter(qid);
	        	Toast.makeText(Activity_collect_choice_show.this, "修改成功！", Toast.LENGTH_SHORT);
	        	setAdapter();
	            break;
	        }
	        return true;
	    }
	    
	private List<Map<String, Object>> getData() 
	{ 
 

		c = db.rawQuery("select q_choice.qid,qtitle,qoptA,qoptB,qoptC,qoptD,qanswer,importance from q_choice ,f_choice where q_choice.qid=f_choice.qid and uid="+uid,null);
    	list.clear();
		idList.clear();
		while(c.moveToNext())
		{
			int i=c.getInt(c.getColumnIndex("q_fillblank.qid"));
			String title=c.getString(c.getColumnIndex("qtitle"));
			String answer=c.getString(c.getColumnIndex("qanswer"));
			String importance=c.getString(c.getColumnIndex("importance"));
			String optA=c.getString(c.getColumnIndex("qoptA"));
			String optB=c.getString(c.getColumnIndex("qoptB"));
			String optC=c.getString(c.getColumnIndex("qoptC"));
			String optD=c.getString(c.getColumnIndex("qoptD"));
			Map map = new HashMap(); 
			map.put("qid","题号："+i);
			idList.add(i);
			map.put("qtitle", "题目"+":"+title); 
			map.put("qoptA", "A."+optA);
			map.put("qoptB", "B."+optB);
			map.put("qoptC", "C."+optC);
			map.put("qoptD", "D."+optD);
			map.put("qanswer","正确答案:"+answer); 
			map.put("qimportance","备注:"+importance); 
			list.add(map);
			
		}
		return list; 
	}
	
	public void alter(int id ) 
	{
		final int qid=id;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Activity_collect_choice_show.this);
		builder.setTitle("更改");
		builder.setMessage("请输入新的笔记!");
		final EditText edtEditText = new EditText(getApplicationContext());
		builder.setView(edtEditText);

		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub					
						String newnote = edtEditText.getText().toString();
						db.execSQL("update f_choice set importance='"+newnote+"' where uid="+uid+" and qid="+qid);
						return;
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub

						return;
					}
				});
		builder.show();
	}
	public void  show(int qid) 
	{
		   LayoutInflater inflater = getLayoutInflater();
		   View layout = inflater.inflate(R.layout.search_choice,(ViewGroup) findViewById(R.id.search_choice));
		   c=db.rawQuery("select * from q_choice where qid="+qid, null);
		   c.moveToFirst();
			int id=c.getInt(c.getColumnIndex("qid"));
			String title=c.getString(c.getColumnIndex("qtitle"));
			String answer=c.getString(c.getColumnIndex("qanswer"));
			int difficulty=c.getInt(c.getColumnIndex("qdifficulty"));
			String explain=c.getString(c.getColumnIndex("qexplain"));
			String source=c.getString(c.getColumnIndex("qsource"));
			String mainpoint=c.getString(c.getColumnIndex("qmainpoint"));
			
			String optA=c.getString(c.getColumnIndex("qoptA"));
			String optB=c.getString(c.getColumnIndex("qoptB"));
			String optC=c.getString(c.getColumnIndex("qoptC"));
			String optD=c.getString(c.getColumnIndex("qoptD"));
		   final TextView qtitle=(TextView)layout.findViewById(R.id.qtitle);
		   final TextView qanswer=(TextView)layout.findViewById(R.id.qanswer);
		   final TextView qdifficulty=(TextView)layout.findViewById(R.id.qdifficulty);
		   final TextView qexplain=(TextView)layout.findViewById(R.id.qexplain);
		   final TextView qsource=(TextView)layout.findViewById(R.id.qsource);
		   final TextView qmainpoint=(TextView)layout.findViewById(R.id.qmainpoint);
		   
		   final RadioButton qoptA=(RadioButton)layout.findViewById(R.id.qoptA);
		   final RadioButton qoptB=(RadioButton)layout.findViewById(R.id.qoptB);
		   final RadioButton qoptC=(RadioButton)layout.findViewById(R.id.qoptC);
		   final RadioButton qoptD=(RadioButton)layout.findViewById(R.id.qoptD);
		   
		   qtitle.setText("题目："+id+"."+title);
		   qanswer.setText("答案"+answer);
		   qdifficulty.setText("难度："+difficulty+"");
		   qexplain.setText("解释："+explain);
		   qsource.setText("来源："+source);
		   qmainpoint.setText("要点："+mainpoint);
		   
		   qoptA.setText("A."+optA);
		   qoptB.setText("B."+optB);
		   qoptC.setText("C."+optC);
		   qoptD.setText("D."+optD);
		   

		   new AlertDialog.Builder(Activity_collect_choice_show.this)
		   	 .setTitle("题目详情！").setView(layout)
		     .setPositiveButton("确定", null)
		     .setNegativeButton("取消", null).show();

	}


}
