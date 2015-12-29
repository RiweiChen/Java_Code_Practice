package com.crw.Itest.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.crw.DB.MyDBOpenHelper;
import com.crw.Itest.R;

public class Activity_error_ask_show extends ListActivity //implements OnItemClickListener ,OnItemLongClickListener
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
	//设置绑定
	public void setAdapter()
	{
		adapter = new SimpleAdapter(this,getData(),R.layout.collect_fillblank, 
				new String[]{"qid","qtitle","qanswer","qimportance"}, 
				new int[]{R.id.qid,R.id.qtitle,R.id.qanswer,R.id.qimportance}); 
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
        	db.execSQL("delete from e_ask where uid="+uid+" and qid="+qid);
        	Toast.makeText(Activity_error_ask_show.this, "成功删除！", Toast.LENGTH_SHORT);
        	setAdapter();
            break;
        case 1://查看。
        	show(qid);
        	Toast.makeText(Activity_error_ask_show.this, "查看！", Toast.LENGTH_SHORT);
            break;    
        case 2://更改。
        	alter(qid);
        	Toast.makeText(Activity_error_ask_show.this, "修改成功！", Toast.LENGTH_SHORT);
        	setAdapter();
            break;
        }
        return true;
    }
    
    //数据绑定。
	private List<Map<String, Object>> getData() 
	{ 
    	c = db.rawQuery("select q_ask.qid,qtitle,qanswer,comment from q_ask ,e_ask where q_ask.qid=e_ask.qid and uid="+uid,null);
		//清空内容。
    	list.clear();
		idList.clear();
		while(c.moveToNext())
		{
			int i=c.getInt(c.getColumnIndex("q_ask.qid"));
			String title=c.getString(c.getColumnIndex("qtitle"));
			String answer=c.getString(c.getColumnIndex("qanswer"));
			String comment=c.getString(c.getColumnIndex("comment"));
			idList.add(i);//把id保存起来。
			Map map = new HashMap(); 
			map.put("qid","题号："+i);
			map.put("qtitle", "题目"+":"+title); 
			map.put("qanswer","答案:"+answer); 
			map.put("qimportance","错题笔记:"+comment); 
			list.add(map);
		}
		return list; 
	}
	//修改。
	public void alter(int id ) 
	{
		final int qid=id;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Activity_error_ask_show.this);
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
						db.execSQL("update e_ask set comment='"+newnote+"' where uid="+uid+" and qid="+qid);
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
		   View layout = inflater.inflate(R.layout.search_ask,(ViewGroup) findViewById(R.id.search_ask));
		   c=db.rawQuery("select * from q_ask where qid="+qid, null);
		   c.moveToFirst();
			int id=c.getInt(c.getColumnIndex("qid"));
			String title=c.getString(c.getColumnIndex("qtitle"));
			String answer=c.getString(c.getColumnIndex("qanswer"));
			int difficulty=c.getInt(c.getColumnIndex("qdifficulty"));
			String explain=c.getString(c.getColumnIndex("qexplain"));
			String source=c.getString(c.getColumnIndex("qsource"));
			String mainpoint=c.getString(c.getColumnIndex("qmainpoint"));
			
		   final TextView qtitle=(TextView)layout.findViewById(R.id.qtitle);
		   final TextView qanswer=(TextView)layout.findViewById(R.id.qanswer);
		   final TextView qdifficulty=(TextView)layout.findViewById(R.id.qdifficulty);
		   final TextView qexplain=(TextView)layout.findViewById(R.id.qexplain);
		   final TextView qsource=(TextView)layout.findViewById(R.id.qsource);
		   final TextView qmainpoint=(TextView)layout.findViewById(R.id.qmainpoint);
		   
		   qtitle.setText("题目："+id+"."+title);
		   qanswer.setText("答案"+answer);
		   qdifficulty.setText("难度："+difficulty+"");
		   qexplain.setText("解释："+explain);
		   qsource.setText("来源："+source);
		   qmainpoint.setText("要点："+mainpoint);
		   

		   new AlertDialog.Builder(Activity_error_ask_show.this)
		   	 .setTitle("题目详情！").setView(layout)		   	 
		     .setPositiveButton("确定", null)
		     .setNegativeButton("取消", null).show();

	}

}
