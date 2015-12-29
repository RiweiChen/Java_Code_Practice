package com.crw.Itest.exam;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.crw.DB.MyDBOpenHelper;
import com.crw.Itest.Activity_more;
import com.crw.Itest.MainActivity;
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

public class Activity_exam_fillblank extends Activity
{
	private MyDBOpenHelper myDBOpenHelper;
	private SQLiteDatabase db;
	private String currentDB;
	private String uid;
	private SharedPreferences userdata;
	
	//各个控件事件。
	private TextView qid, qtitle,msg;
	private EditText qyouanswer;
	private Button preview,next,adderror,addcollect,submit;
	private  int id;
	private String title,answer;
	
	private Cursor c1;
	private Cursor c2;
	private Cursor c3;
	private Cursor c4;
	private Cursor c5;//五个数据库结果存放各个难度的查询结构。
	private Cursor c;
	private ArrayList<Integer> qids;
	private int total=50;
	private int doRight=0;
	private int doWrong=0;//做题记录
	private int num1=0;
	private int num2=0;
	private int num3=0;
	private int num4=0;
	private int num5=0;
	private int level=4;
	public void setCursor() 
	{
		c1=db.rawQuery("select * from q_fillblank  where qdifficulty = 1 ", null);
		c2=db.rawQuery("select * from q_fillblank  where qdifficulty = 2 ", null);
		c3=db.rawQuery("select * from q_fillblank  where qdifficulty = 3 ", null);
		c4=db.rawQuery("select * from q_fillblank  where qdifficulty = 4 ", null);
		c5=db.rawQuery("select * from q_fillblank  where qdifficulty = 5 ", null);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_exam_fillblank);
		
		userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		currentDB=userdata.getString("currentDB", "");//设置当前数据库。
		uid ="'"+userdata.getString("username","")+"'";
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // 创建dbopenHelper;   	
		db = myDBOpenHelper.getWritableDatabase();//取得数据库	
		//获取难度值。
		level= userdata.getInt("level",4);
		qids=new ArrayList<Integer>();
		setCursor();
		getView();
		//设置题木难度。
		if(level==3)
		{	num1=0;
			num3=30;
			num2=10;
			num4=10;
			num5=0;
		}
		else if (level==4)
		{
			num1=0;
			num2=0;
			num3=10;
			num4=30;
			num5=10;
		}
		else if (level==5) 
		{
			num1=0;
			num2=0;
			num3=0;
			num4=0;
			num5=50;
		}
		else if (level==2) 
		{
			num2=30;
			num1=10;
			num3=10;
			num4=0;
			num5=0;
		}
		else
		{
			num1=50;
			num2=0;
			num3=0;
			num4=0;
			num5=0;
		}
		//随机数
		int k=0;
		Random random=new Random();
		int count1=c1.getCount();
		System.out.println("各组的数目1："+count1);
		for (int i = 0; i < num1&&count1>0; i++) 
		{
			int j=Math.abs(random.nextInt())%count1;
			System.out.println(j);
			c1.moveToPosition(j);
			int q;
			q=c1.getInt(c1.getColumnIndex("qid"));
			qids.add(q);
		}
		int count2=c2.getCount();
		System.out.println("各组的数目2："+count2);
		for (int i = 0; i < num2&&count2>0; i++) 
		{
			int j=Math.abs(random.nextInt())%count2;
			System.out.println(j);
			c2.moveToPosition(j);
			int q;
			q=c2.getInt(c2.getColumnIndex("qid"));
			qids.add(q);
		}
		int count3=c3.getCount();
		System.out.println("各组的数目3："+count3);
		for (int i = 0; i < num3&&count3>0; i++) 
		{
			int j=Math.abs(random.nextInt())%count3;
			System.out.println(j);
			c3.moveToPosition(j);
			System.out.println("c3.getPosition():"+c3.getPosition());
			int q;
			q=c3.getInt(c3.getColumnIndex("qid"));
			qids.add(q);
		}
		int count4=c4.getCount();
		System.out.println("各组的数目4："+count4);
		for (int i = 0; i < num4&&count4>0; i++) 
		{
			int j=Math.abs(random.nextInt())%count4;
			System.out.println(j);
			c4.moveToPosition(j);
			System.out.println("c4.getPosition():"+c4.getPosition());
			int q;
			q=c4.getInt(c4.getColumnIndex("qid"));
			qids.add(q);
		}
		int count5=c5.getCount();
		System.out.println("各组的数目5："+count5);
		for (int i = 0; i < num5&&count5>0; i++) 
		{
			int j=Math.abs(random.nextInt())%count5;
			System.out.println(j);
			c5.moveToPosition(j);
			System.out.println("c5.getPosition():"+c5.getPosition());
			int q;
			q=c5.getInt(c5.getColumnIndex("qid"));
			qids.add(q);
		}
		//当无法生成模拟题时。
		if (qids.size()==0) 
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Activity_exam_fillblank.this);
			builder.setTitle("提醒");
			builder.setMessage("对不起，根据当前难度设置无法生成模拟题，请从新设置难度值!");	
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog,
						int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(Activity_exam_fillblank.this, Activity_more.class);
					startActivity(intent);
				}
			});
			builder.setNegativeButton("取消",null);
			builder.show();
		}
		else 
		{
			index=0;
			c=db.rawQuery("select * from q_fillblank  where qid = "+qids.get(index), null);
			c.moveToFirst();
			getResult(c);
			setView();
		}
		for(int i=0;i<qids.size();i++)
		{
			System.out.println("qid序列为："+qids.get(i));
		}
		
	}
	//统一查找控件的id绑定。
		public void getView() 
		{
			msg=(TextView)findViewById(R.id.msg);
			qtitle=(TextView)findViewById(R.id.qtitle);
			submit=(Button)findViewById(R.id.submit);
			qyouanswer=(EditText)findViewById(R.id.qyouanswer);
			
			preview=(Button)findViewById(R.id.previous);
			next=(Button)findViewById(R.id.next);
			adderror=(Button)findViewById(R.id.adderror);
			addcollect=(Button)findViewById(R.id.addcollect);
			next.setOnClickListener(clickListener);
			preview.setOnClickListener(clickListener);
			addcollect.setOnClickListener(clickListener);
			adderror.setOnClickListener(clickListener);
			submit.setOnClickListener(clickListener);
			
		}
		
		//取结果放在变量上。
		public void getResult(Cursor c)
		{
			id=c.getInt(c.getColumnIndex("qid"));
			title=c.getString(c.getColumnIndex("qtitle"));
			answer=c.getString(c.getColumnIndex("qanswer"));
		}
		//显示结果在控件上。
		public void  setView() 
		{
			msg.setText("当前进度：第"+(index+1)+"/"+qids.size()+"题");
			qtitle.setText(id+"."+title);
			qyouanswer.setText("");
		}
		
		String noteString;
		//设置点击事件处理。
		int index=0;
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
					index=(index+1)%50;
					c=db.rawQuery("select * from q_fillblank  where qid = "+qids.get(index), null);
					c.moveToFirst();
					getResult(c);
					setView();
					break;
				}
					
				case R.id.previous:
				{
					index=(index-1)%50;
					c=db.rawQuery("select * from q_fillblank  where qid = "+qids.get(index), null);
					c.moveToFirst();
					getResult(c);
					setView();
					break;
				}
									
				case R.id.addcollect:
				{
					
					//
					AlertDialog.Builder builder =new AlertDialog.Builder(Activity_exam_fillblank.this);    
					builder.setTitle("添加笔记")  ;
					builder.setMessage("请输入笔记内容!"); 
					final EditText edtEditText=new EditText(Activity_exam_fillblank.this);
					builder.setView(edtEditText);
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							noteString=edtEditText.getText().toString();
							SharedPreferences userdata=getSharedPreferences("userdata", 0);
							String uid =userdata.getString("username","");
							db.execSQL("insert into f_fillblank values(?,?,?)", new String[]{id+"",uid,noteString});
							Toast.makeText(Activity_exam_fillblank.this,"添加收藏成功！",Toast.LENGTH_SHORT).show();
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
					break;
					//
				}
				case R.id.adderror:
				{	
					if(qyouanswer.getText().toString().equals(""))
					{
						//没输入时。
						Toast.makeText(Activity_exam_fillblank.this,"对不起，你还未输入答案！",Toast.LENGTH_SHORT).show();
						break;
					}
					else
					{
						if(qyouanswer.getText().toString().equals(answer))
						{
							//答对时，
							Toast.makeText(Activity_exam_fillblank.this,"恭喜你答对了！",Toast.LENGTH_SHORT).show();
					
						}
						else
						{
							//答错时。
							Toast.makeText(Activity_exam_fillblank.this,"不好意思，你打错了，加油！",Toast.LENGTH_SHORT).show();
							//自动加入错题
							Date now = new Date(); 
							DateFormat df = DateFormat.getDateTimeInstance(); 
						    String data = df.format(now); 
						    //获取用户id。
						    SharedPreferences userdata=getSharedPreferences("userdata", 0);
						    String uid =userdata.getString("username","");
						    //获取自己的答案。
						    String youanswer=qyouanswer.getText().toString();
						    db.execSQL("insert into e_fillblank values(?,?,?,?,?,?)", 
									new String[]{id+"",uid,youanswer,data,noteString,"1"});
							Toast.makeText(Activity_exam_fillblank.this,"添加错题成功！",
									Toast.LENGTH_SHORT).show();
						 
						}
							
					}
					
					break;
				}
				case R.id.submit://交卷
				{
					showResult();
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
	public void showResult()
	{
		System.out.println("宣示结果"+doRight);
		AlertDialog.Builder builder =new AlertDialog.Builder(Activity_exam_fillblank.this);   
		builder.setTitle("模拟试题结果")  ;
		if(doRight==50)
		{
			builder.setMessage("恭喜你，都答对了，满分，你还是很厉害的!"); 
		}
		else if(doRight>=45)
		{
			builder.setMessage("恭喜你，成绩优秀!"+(doRight*2)+"分");
		}
		else if(doRight>=40)
		{
			builder.setMessage("恭喜你，成绩良好!"+(doRight*2)+"分");
		}
		else if(doRight>=35)
		{
			builder.setMessage("恭喜你，成绩中等!"+(doRight*2)+"分");
		}
		else if(doRight>=30)
		{
			builder.setMessage("恭喜你，成绩及格!"+(doRight*2)+"分");
		}
		else
		{
			builder.setMessage("不好意思，成绩不及格!"+(doRight*2)+"分，同学，继续努力呀！");
		}
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			
			}
		});  
		builder.show();
	}
	@Override
	protected void onDestroy() 
	{
	  super.onDestroy();
	  if (myDBOpenHelper  != null) 
	   {
	     myDBOpenHelper.close();
	   }
	 }

}
