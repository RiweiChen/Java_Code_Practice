package com.crw.Itest.question;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crw.DB.MyDBOpenHelper;
import com.crw.Itest.R;

public class Activity_question_choice_show extends Activity
{
	//各个控件事件。
	private TextView qid,qtitle;
	private RadioGroup qopt;
	private RadioButton qoptA,qoptB,qoptC,qoptD;
	private TextView qanswer,qdifficulty,qsource,qmainpoint,qexplain;
	private Button preview,next,adderror,addcollect;
	//数据库相关变量。
	private MyDBOpenHelper myDBOpenHelper;
	private SQLiteDatabase db;
	private Cursor c;
	//结果中间变量。
	private int id,difficulty;
	private String title,opt,optA,optB,optC,optD,answer,source,mainpoint,explain;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_qchoice);
		getView();//控件id绑定。
		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		String currentDB=userdata.getString("currentDB", "");//设置当前数据库。
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // 创建dbopenHelper;   	
    	db = myDBOpenHelper.getWritableDatabase();//取得数据库
    	//查询数据库。
    	c = db.rawQuery("select * from q_choice ",null);
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
				break;
			}
								
			case R.id.addcollect:
			{
				
				//
				AlertDialog.Builder builder =new AlertDialog.Builder(Activity_question_choice_show.this);    
				builder.setTitle("添加笔记")  ;
				builder.setMessage("请输入笔记内容!"); 
				final EditText edtEditText=new EditText(Activity_question_choice_show.this);
				builder.setView(edtEditText);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						noteString=edtEditText.getText().toString();
						SharedPreferences userdata=getSharedPreferences("userdata", 0);
						String uid =userdata.getString("username","");
						db.execSQL("insert into f_choice values(?,?,?)", new String[]{id+"",uid,noteString});
						Toast.makeText(Activity_question_choice_show.this,"添加收藏成功！",Toast.LENGTH_SHORT).show();
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
				AlertDialog.Builder builder =new AlertDialog.Builder(Activity_question_choice_show.this);    
				builder.setTitle("添加笔记")  ;
				builder.setMessage("请输入笔记内容!"); 
				final EditText edtEditText=new EditText(Activity_question_choice_show.this);
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
					    if(qoptA.isChecked()==true)
					    	youanswer="A";
					    if(qoptB.isChecked()==true)
					    	youanswer="B";
					    if(qoptC.isChecked()==true)
					    	youanswer="C";
					    if(qoptD.isChecked()==true)
					    	youanswer="D";
					    db.execSQL("insert into e_choice values(?,?,?,?,?,?)", 
								new String[]{id+"",uid,youanswer,data,noteString,"1"});
						Toast.makeText(Activity_question_choice_show.this,"添加错题成功！",
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
		optA=c.getString(c.getColumnIndex("qoptA"));
		optB=c.getString(c.getColumnIndex("qoptB"));
		optC=c.getString(c.getColumnIndex("qoptC"));
		optD=c.getString(c.getColumnIndex("qoptD"));
		//Toast.makeText(this,id+"."+title,Toast.LENGTH_SHORT).show();
	}
	//显示结果在控件上。
	public void  setView() 
	{
		qid.setText("选择题");
		qtitle.setText(id+"."+title);
		qoptA.setText("A."+optA);
		qoptB.setText("B."+optB);
		qoptC.setText("C."+optC);
		qoptD.setText("D."+optD);
		qanswer.setText("答案："+answer);
		qdifficulty.setText("难度："+difficulty+"");
		qexplain.setText("解释："+explain);
		qsource.setText("题目来源："+source);
		qmainpoint.setText("考点："+mainpoint);
	}
	//统一查找控件的id绑定。
	public void getView() 
	{
		qid=(TextView)findViewById(R.id.qid);
		qtitle=(TextView)findViewById(R.id.qtitle);
		
		qopt=(RadioGroup)findViewById(R.id.qopt);
		qoptA=(RadioButton)findViewById(R.id.qoptA);
		qoptB=(RadioButton)findViewById(R.id.qoptB);
		qoptC=(RadioButton)findViewById(R.id.qoptC);
		qoptD=(RadioButton)findViewById(R.id.qoptD);
		
		qanswer=(TextView)findViewById(R.id.qanswer);
		qdifficulty=(TextView)findViewById(R.id.qdifficulty);
		qsource=(TextView)findViewById(R.id.qsource);
		qmainpoint=(TextView)findViewById(R.id.qmainpoint);
		qexplain=(TextView)findViewById(R.id.qexplain);
		
		preview=(Button)findViewById(R.id.previous);
		next=(Button)findViewById(R.id.next);
		adderror=(Button)findViewById(R.id.adderror);
		addcollect=(Button)findViewById(R.id.addcollect);
		next.setOnClickListener(clickListener);
		preview.setOnClickListener(clickListener);
		addcollect.setOnClickListener(clickListener);
		adderror.setOnClickListener(clickListener);
		
	}

}
