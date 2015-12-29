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
	//�����ؼ��¼���
	private TextView qtitle;
	private TextView qanswer,qdifficulty,qsource,qmainpoint,qexplain;
	private Button preview,next,adderror,addcollect,banswer;
	//���ݿ���ر�����
	private MyDBOpenHelper myDBOpenHelper;
	private SQLiteDatabase db;
	private Cursor c;
	//����м������
	private int id,difficulty;
	private String title,answer,source,mainpoint,explain;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_exercise_ask);
		getView();//�ؼ�id�󶨡�
		SharedPreferences userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		String currentDB=userdata.getString("currentDB", "");//���õ�ǰ���ݿ⡣
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // ����dbopenHelper;   	
    	db = myDBOpenHelper.getWritableDatabase();//ȡ�����ݿ�
    	//��ѯ���ݿ⡣
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
	//���õ���¼�����
	
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
				//��ʾ�𰸡�
				setanswer();
				break;
			}
			
			case R.id.addcollect:
			{
				
				//
				AlertDialog.Builder builder =new AlertDialog.Builder(Activity_exercise_ask_show.this);    
				builder.setTitle("��ӱʼ�")  ;
				builder.setMessage("������ʼ�����!"); 
				final EditText edtEditText=new EditText(Activity_exercise_ask_show.this);
				builder.setView(edtEditText);
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						noteString=edtEditText.getText().toString();
						SharedPreferences userdata=getSharedPreferences("userdata", 0);
						String uid =userdata.getString("username","");
						db.execSQL("insert into f_ask values(?,?,?)", new String[]{id+"",uid,noteString});
						Toast.makeText(Activity_exercise_ask_show.this,"����ղسɹ���",Toast.LENGTH_SHORT).show();
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
				//

				break;
			}
			case R.id.adderror:
			{	
			    //�û���ӱʼǡ�
				//
				AlertDialog.Builder builder =new AlertDialog.Builder(Activity_exercise_ask_show.this);    
				builder.setTitle("��ӱʼ�")  ;
				builder.setMessage("������ʼ�����!"); 
				final EditText edtEditText=new EditText(Activity_exercise_ask_show.this);
				builder.setView(edtEditText);
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						noteString=edtEditText.getText().toString();
						//��ȡ�¼���
						Date now = new Date(); 
						DateFormat df = DateFormat.getDateTimeInstance(); 
					    String data = df.format(now); 
					    //��ȡ�û�id��
					    SharedPreferences userdata=getSharedPreferences("userdata", 0);
					    String uid =userdata.getString("username","");
					    //��ȡ�Լ��Ĵ𰸡�
					    String youanswer=" ";
					    db.execSQL("insert into e_ask values(?,?,?,?,?,?)", 
								new String[]{id+"",uid,youanswer,data,noteString,"1"});
						Toast.makeText(Activity_exercise_ask_show.this,"��Ӵ���ɹ���",
								Toast.LENGTH_SHORT).show();
						
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
	//ȡ������ڱ����ϡ�
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
	//��ʾ����ڿؼ��ϡ�
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
		qanswer.setText("�𰸣�"+answer);
		qdifficulty.setText("�Ѷȣ�"+difficulty+"");
		qexplain.setText("���ͣ�"+explain);
		qsource.setText("��Ŀ��Դ��"+source);
		qmainpoint.setText("���㣺"+mainpoint);
	}
	//ͳһ���ҿؼ���id�󶨡�
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
