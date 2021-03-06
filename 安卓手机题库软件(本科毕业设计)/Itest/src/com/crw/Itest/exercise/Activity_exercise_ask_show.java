package com.crw.Itest.exercise;

import java.text.DateFormat;
import java.util.Date;

import com.crw.DB.MyDBOpenHelper;
import com.crw.Itest.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_exercise_ask_show extends Activity
{
	//各个控件事件。
	private TextView qtitle;
	private TextView qanswer,qdifficulty,qsource,qmainpoint,qexplain;
	private Button preview,next,adderror,addcollect,banswer;
	//数据库相关变量。
	private MyDBOpenHelper myDBOpenHelper;
	private SQLiteDatabase db;
	private Cursor c;
	//结果中间变量。
	private int id,difficulty;
	private String title,answer,source,mainpoint,explain;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_exercise_ask);
		getView();//控件id绑定。
		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		String currentDB=userdata.getString("currentDB", "");//设置当前数据库。
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // 创建dbopenHelper;   	
    	db = myDBOpenHelper.getWritableDatabase();//取得数据库
    	//查询数据库。
    	String sql="select * from q_ask ";
    	Intent intent=getIntent();
    	if (intent.hasExtra("sql")) {
    		Bundle data=intent.getExtras();
        	sql=data.getString("sql");
        	String qsource =data.getString("qsource");
        	setTitle(qsource);
		}
    	
    	c = db.rawQuery(sql,null);
    	c.moveToFirst();
    	getResult(c);
    	setView();
    	//myDBOpenHelper.close();
    
		
	
	}
	String noteString;
	//设置点击事件处理。
	
	Button.OnClickListener clickListener=new Button.OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			switch (v.getId()) 
			{
			case R.id.next:
			{
				if (c.isLast()) {
					c.moveToFirst();
				}
				else {
					c.moveToNext();
				}
				
				getResult(c);
				setView();
				clearanswer();
				break;
			}
				
			case R.id.previous:
			{
				if(c.isFirst())
					c.moveToLast();
				else
					c.moveToPrevious();
				getResult(c);
				setView();
				clearanswer();
				break;
			}
			
			case R.id.banswer:
			{
				//显示答案。
				setanswer();
				break;
			}
			
			case R.id.addcollect:
			{
				
				//
				AlertDialog.Builder builder =new AlertDialog.Builder(Activity_exercise_ask_show.this);    
				builder.setTitle("添加笔记")  ;
				builder.setMessage("请输入笔记内容!"); 
				final EditText edtEditText=new EditText(Activity_exercise_ask_show.this);
				builder.setView(edtEditText);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						noteString=edtEditText.getText().toString();
						SharedPreferences userdata=getSharedPreferences("userdata", 0);
						String uid =userdata.getString("username","");
						db.execSQL("insert into f_ask values(?,?,?)", new String[]{id+"",uid,noteString});
						Toast.makeText(Activity_exercise_ask_show.this,"添加收藏成功！",Toast.LENGTH_SHORT).show();
					}
				});  
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						return ;
					}
				});  
				builder.show();  
				//

				break;
			}
			case R.id.adderror:
			{	
			    //用户添加笔记。
				//
				AlertDialog.Builder builder =new AlertDialog.Builder(Activity_exercise_ask_show.this);    
				builder.setTitle("添加笔记")  ;
				builder.setMessage("请输入笔记内容!"); 
				final EditText edtEditText=new EditText(Activity_exercise_ask_show.this);
				builder.setView(edtEditText);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						noteString=edtEditText.getText().toString();
						//获取事件。
						Date now = new Date(); 
						DateFormat df = DateFormat.getDateTimeInstance(); 
					    String data = df.format(now); 
					    //获取用户id。
					    SharedPreferences userdata=getSharedPreferences("userdata", 0);
					    String uid =userdata.getString("username","");
					    //获取自己的答案。
					    String youanswer=" ";
					    db.execSQL("insert into e_ask values(?,?,?,?,?,?)", 
								new String[]{id+"",uid,youanswer,data,noteString,"1"});
						Toast.makeText(Activity_exercise_ask_show.this,"添加错题成功！",
								Toast.LENGTH_SHORT).show();
						
					}
				});  
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						return ;
					}
				});  
				builder.show();  
				//	
				
				
				break;
			}
			default:
			{				
				break;
			}
				
			}
		};
	
	};
	//取结果放在变量上。
	public void getResult(Cursor c)
	{
		id=c.getInt(c.getColumnIndex("qid"));
		title=c.getString(c.getColumnIndex("qtitle"));
		answer=c.getString(c.getColumnIndex("qanswer"));
		difficulty=c.getInt(c.getColumnIndex("qdifficulty"));
		explain=c.getString(c.getColumnIndex("qexplain"));
		source=c.getString(c.getColumnIndex("qsource"));
		mainpoint=c.getString(c.getColumnIndex("qmainpoint"));

	}
	//显示结果在控件上。
	public void  setView() 
	{
		qtitle.setText(id+"."+title);
	}
	public void clearanswer() 
	{
		qanswer.setText("");
		qdifficulty.setText("");
		qexplain.setText("");
		qsource.setText("");
		qmainpoint.setText("");
	}
	public void setanswer() 
	{
		qanswer.setText("答案："+answer);
		qdifficulty.setText("难度："+difficulty+"");
		qexplain.setText("解释："+explain);
		qsource.setText("题目来源："+source);
		qmainpoint.setText("考点："+mainpoint);
	}
	//统一查找控件的id绑定。
	public void getView() 
	{
		qtitle=(TextView)findViewById(R.id.qtitle);
		
		qanswer=(TextView)findViewById(R.id.qanswer);
		qdifficulty=(TextView)findViewById(R.id.qdifficulty);
		qsource=(TextView)findViewById(R.id.qsource);
		qmainpoint=(TextView)findViewById(R.id.qmainpoint);
		qexplain=(TextView)findViewById(R.id.qexplain);
		
		preview=(Button)findViewById(R.id.previous);
		next=(Button)findViewById(R.id.next);
		adderror=(Button)findViewById(R.id.adderror);
		addcollect=(Button)findViewById(R.id.addcollect);
		banswer=(Button)findViewById(R.id.banswer);
		
		next.setOnClickListener(clickListener);
		preview.setOnClickListener(clickListener);
		addcollect.setOnClickListener(clickListener);
		adderror.setOnClickListener(clickListener);
		banswer.setOnClickListener(clickListener);

		
	}

}
