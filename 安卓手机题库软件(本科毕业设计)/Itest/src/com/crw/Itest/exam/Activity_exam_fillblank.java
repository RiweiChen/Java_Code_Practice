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
	
	//�����ؼ��¼���
	private TextView qid, qtitle,msg;
	private EditText qyouanswer;
	private Button preview,next,adderror,addcollect,submit;
	private  int id;
	private String title,answer;
	
	private Cursor c1;
	private Cursor c2;
	private Cursor c3;
	private Cursor c4;
	private Cursor c5;//������ݿ�����Ÿ����ѶȵĲ�ѯ�ṹ��
	private Cursor c;
	private ArrayList<Integer> qids;
	private int total=50;
	private int doRight=0;
	private int doWrong=0;//�����¼
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
		currentDB=userdata.getString("currentDB", "");//���õ�ǰ���ݿ⡣
		uid ="'"+userdata.getString("username","")+"'";
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // ����dbopenHelper;   	
		db = myDBOpenHelper.getWritableDatabase();//ȡ�����ݿ�	
		//��ȡ�Ѷ�ֵ��
		level= userdata.getInt("level",4);
		qids=new ArrayList<Integer>();
		setCursor();
		getView();
		//������ľ�Ѷȡ�
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
		//�����
		int k=0;
		Random random=new Random();
		int count1=c1.getCount();
		System.out.println("�������Ŀ1��"+count1);
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
		System.out.println("�������Ŀ2��"+count2);
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
		System.out.println("�������Ŀ3��"+count3);
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
		System.out.println("�������Ŀ4��"+count4);
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
		System.out.println("�������Ŀ5��"+count5);
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
		//���޷�����ģ����ʱ��
		if (qids.size()==0) 
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Activity_exam_fillblank.this);
			builder.setTitle("����");
			builder.setMessage("�Բ��𣬸��ݵ�ǰ�Ѷ������޷�����ģ���⣬����������Ѷ�ֵ!");	
			builder.setPositiveButton("ȷ��",
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
			builder.setNegativeButton("ȡ��",null);
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
			System.out.println("qid����Ϊ��"+qids.get(i));
		}
		
	}
	//ͳһ���ҿؼ���id�󶨡�
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
		
		//ȡ������ڱ����ϡ�
		public void getResult(Cursor c)
		{
			id=c.getInt(c.getColumnIndex("qid"));
			title=c.getString(c.getColumnIndex("qtitle"));
			answer=c.getString(c.getColumnIndex("qanswer"));
		}
		//��ʾ����ڿؼ��ϡ�
		public void  setView() 
		{
			msg.setText("��ǰ���ȣ���"+(index+1)+"/"+qids.size()+"��");
			qtitle.setText(id+"."+title);
			qyouanswer.setText("");
		}
		
		String noteString;
		//���õ���¼�����
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
					builder.setTitle("��ӱʼ�")  ;
					builder.setMessage("������ʼ�����!"); 
					final EditText edtEditText=new EditText(Activity_exam_fillblank.this);
					builder.setView(edtEditText);
					builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							noteString=edtEditText.getText().toString();
							SharedPreferences userdata=getSharedPreferences("userdata", 0);
							String uid =userdata.getString("username","");
							db.execSQL("insert into f_fillblank values(?,?,?)", new String[]{id+"",uid,noteString});
							Toast.makeText(Activity_exam_fillblank.this,"����ղسɹ���",Toast.LENGTH_SHORT).show();
						}
					});  
					builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						
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
						//û����ʱ��
						Toast.makeText(Activity_exam_fillblank.this,"�Բ����㻹δ����𰸣�",Toast.LENGTH_SHORT).show();
						break;
					}
					else
					{
						if(qyouanswer.getText().toString().equals(answer))
						{
							//���ʱ��
							Toast.makeText(Activity_exam_fillblank.this,"��ϲ�����ˣ�",Toast.LENGTH_SHORT).show();
					
						}
						else
						{
							//���ʱ��
							Toast.makeText(Activity_exam_fillblank.this,"������˼�������ˣ����ͣ�",Toast.LENGTH_SHORT).show();
							//�Զ��������
							Date now = new Date(); 
							DateFormat df = DateFormat.getDateTimeInstance(); 
						    String data = df.format(now); 
						    //��ȡ�û�id��
						    SharedPreferences userdata=getSharedPreferences("userdata", 0);
						    String uid =userdata.getString("username","");
						    //��ȡ�Լ��Ĵ𰸡�
						    String youanswer=qyouanswer.getText().toString();
						    db.execSQL("insert into e_fillblank values(?,?,?,?,?,?)", 
									new String[]{id+"",uid,youanswer,data,noteString,"1"});
							Toast.makeText(Activity_exam_fillblank.this,"��Ӵ���ɹ���",
									Toast.LENGTH_SHORT).show();
						 
						}
							
					}
					
					break;
				}
				case R.id.submit://����
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
		System.out.println("��ʾ���"+doRight);
		AlertDialog.Builder builder =new AlertDialog.Builder(Activity_exam_fillblank.this);   
		builder.setTitle("ģ��������")  ;
		if(doRight==50)
		{
			builder.setMessage("��ϲ�㣬������ˣ����֣��㻹�Ǻ�������!"); 
		}
		else if(doRight>=45)
		{
			builder.setMessage("��ϲ�㣬�ɼ�����!"+(doRight*2)+"��");
		}
		else if(doRight>=40)
		{
			builder.setMessage("��ϲ�㣬�ɼ�����!"+(doRight*2)+"��");
		}
		else if(doRight>=35)
		{
			builder.setMessage("��ϲ�㣬�ɼ��е�!"+(doRight*2)+"��");
		}
		else if(doRight>=30)
		{
			builder.setMessage("��ϲ�㣬�ɼ�����!"+(doRight*2)+"��");
		}
		else
		{
			builder.setMessage("������˼���ɼ�������!"+(doRight*2)+"�֣�ͬѧ������Ŭ��ѽ��");
		}
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
