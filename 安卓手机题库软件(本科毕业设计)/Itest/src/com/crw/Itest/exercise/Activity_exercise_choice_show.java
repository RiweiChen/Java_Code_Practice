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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_exercise_choice_show extends Activity
{
	//各个控件事件。
	private TextView qtitle;
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
		setContentView(R.layout.layout_exercise_choice);
		getView();//控件id绑定。
		try {
			
		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		String currentDB=userdata.getString("currentDB", "");//设置当前数据库。
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // 创建dbopenHelper;   	
    	db = myDBOpenHelper.getWritableDatabase();//取得数据库
    	//查询数据库。
    	String sql="select * from q_choice ";
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
		} catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();

		}
		
	
	}
	String noteString;
	//设置点击事件处理。
	
	Button.OnClickListener clickListener=new Button.OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			try {
		
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
				AlertDialog.Builder builder =new AlertDialog.Builder(Activity_exercise_choice_show.this);    
				builder.setTitle("添加笔记")  ;
				builder.setMessage("请输入笔记内容!"); 
				final EditText edtEditText=new EditText(Activity_exercise_choice_show.this);
				builder.setView(edtEditText);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						noteString=edtEditText.getText().toString();
						SharedPreferences userdata=getSharedPreferences("userdata", 0);
						String uid =userdata.getString("username","");
						db.execSQL("insert into f_choice values(?,?,?)", new String[]{id+"",uid,noteString});
						Toast.makeText(Activity_exercise_choice_show.this,"添加收藏成功！",Toast.LENGTH_SHORT).show();
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
				 String youanswer=" ";
				 if(qoptA.isChecked()==true)
				    	youanswer="A";
				 else if(qoptB.isChecked()==true)
				    	youanswer="B";
				 else if(qoptC.isChecked()==true)
				    	youanswer="C";
				 else if(qoptD.isChecked()==true)
				    	youanswer="D";
				 else {
					 Toast.makeText(Activity_exercise_choice_show.this,"你还没选答案呢！",Toast.LENGTH_SHORT).show();
					 break;
				}
					
				 if (youanswer.equals(answer)) 
				 {
					 Toast.makeText(Activity_exercise_choice_show.this,"恭喜你答对了！",Toast.LENGTH_SHORT).show();
					 qanswer.setText("正确答案为："+answer);
					 qexplain.setText("解释："+explain);					 
					 qmainpoint.setText("考点："+mainpoint);
					 qdifficulty.setText("难度："+difficulty+"");
				}
				 else 
				 {
					 Toast.makeText(Activity_exercise_choice_show.this,"不好意思你答错了！",Toast.LENGTH_SHORT).show();
					 qanswer.setText("正确答案为："+answer);
					 qexplain.setText("解释："+explain);				 
					 qmainpoint.setText("考点："+mainpoint);
					 qdifficulty.setText("难度："+difficulty+"");
					 //需要加入错题。
					 
						Date now = new Date(); 
						DateFormat df = DateFormat.getDateTimeInstance(); 
					    String data = df.format(now); 
					    //获取用户id。
					    SharedPreferences userdata=getSharedPreferences("userdata", 0);
					    String uid =userdata.getString("username","");
					    db.execSQL("insert into e_choice values(?,?,?,?,?,?)", 
								new String[]{id+"",uid,youanswer,data,noteString,"1"});
						Toast.makeText(Activity_exercise_choice_show.this,"自动添加错题成功！",Toast.LENGTH_SHORT).show();
				}
			
			  break;
			}
			default:
			{				
				break;
			}
				
			}
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
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
		qtitle.setText(id+"."+title);
		qoptA.setText("A."+optA);
		qoptB.setText("B."+optB);
		qoptC.setText("C."+optC);
		qoptD.setText("D."+optD);
		qsource.setText("题目来源："+source);
		qanswer.setText("");
		qexplain.setText("");
		qmainpoint.setText("");
		qdifficulty.setText("");
		qoptA.setChecked(false);
		qoptB.setChecked(false);
		qoptC.setChecked(false);
		qoptD.setChecked(false);
	}
	//统一查找控件的id绑定。
	public void getView() 
	{
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myDBOpenHelper  != null) {
        	myDBOpenHelper.close();
        }
    }

}
