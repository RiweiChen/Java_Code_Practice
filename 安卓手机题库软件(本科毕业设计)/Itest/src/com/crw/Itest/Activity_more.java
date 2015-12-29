package com.crw.Itest;

import com.crw.DB.MyDBOpenHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_more extends Activity
{
	MyDBOpenHelper myDBOpenHelper;
	SQLiteDatabase db;
	Cursor c;
	String uid;
	String currentDB;
	SharedPreferences userdata;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_more);
		//��û��������ݿ���Ϣ��
		userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		currentDB=userdata.getString("currentDB", "");//���õ�ǰ���ݿ⡣
		uid =userdata.getString("username","");
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // ����dbopenHelper;   	
		db = myDBOpenHelper.getWritableDatabase();//ȡ�����ݿ�	
		
		//ɾ�����⡣
		Button deleteError=(Button)findViewById(R.id.deleteError);
		deleteError.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("��մ��⼯");
				builder.setMessage("��մ���󣬵�ǰ�û������д��⽫��ʧ���Ƿ�ȷ����գ�");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) 
							{
								// TODO Auto-generated method stub
								db.execSQL("delete from e_choice");
								db.execSQL("delete from e_fillblank");
								db.execSQL("delete from e_ask");
								Toast.makeText(Activity_more.this, "�Ѿ���մ����", Toast.LENGTH_SHORT).show();
								return;
							}
						});
				builder.setNegativeButton("ȡ��",
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
		});
		
		//ɾ���ղء�
		Button deleteCollect=(Button)findViewById(R.id.deleteCollect);
		deleteCollect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("����ղؼ�");
				builder.setMessage("����ղؼк󣬵�ǰ�û��������ղؽ���ʧ���Ƿ�ȷ����գ�");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) 
							{
								// TODO Auto-generated method stub
								db.execSQL("delete from f_choice");
								db.execSQL("delete from f_fillblank");
								db.execSQL("delete from f_ask");
								Toast.makeText(Activity_more.this, "�Ѿ�����ղؼ�", Toast.LENGTH_SHORT).show();
								return;
							}
						});
				builder.setNegativeButton("ȡ��",
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
		});
		//��ϵ��
		Button contact=(Button)findViewById(R.id.contact);
		contact.setOnClickListener(new OnClickListener() 
		{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Activity_more.this);
			builder.setTitle("��ϵ������");
			builder.setMessage("�������ʲô������������ߵ�\n"+"����΢����http://weibo.com/chenriwei2\n"+"����!");
			builder.setPositiveButton("ȷ��",
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
		});
		//����
		Button share=(Button)findViewById(R.id.share);
		share.setOnClickListener(new OnClickListener() 
		{
					
		@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Activity_more.this);
			builder.setTitle("������");
			builder.setMessage("�����Է����ʼ���\n"+"chenriwei@outlook.com\n"+"���������!");
			builder.setPositiveButton("ȷ��",
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
		});
		
		Button about =(Button)findViewById(R.id.about);
		about.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("����");
				builder.setMessage("��Ӧ�����Ǳ�ҵ��ƵĿ���\n"+"����಻��֮�������½�\n"
									+"����лָ����ʦ���ǵ�Ϥ�Ľ̵�");
				builder.setPositiveButton("ȷ��",
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
		});
		
		Button quit =(Button)findViewById(R.id.quit);
		quit.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("�˳���");
				builder.setMessage("�Ƿ�ȷ���˳�");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) 
							{
								// TODO Auto-generated method stub
								//android.os.Process.killProcess(android.os.Process.myPid()) ; 
								/*Context context =getApplicationContext();
								ActivityManager activityManager =  (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
								activityManager.restartPackage("com.crw.Itest");*/
								 System.exit(0);
								return;
							}
						});
				builder.setNegativeButton("ȡ��",
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
		});
		
		
		
		Button changeUser=(Button)findViewById(R.id.changeUser);
		changeUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("���õ�ǰ�û���");
				builder.setMessage("�������µ��û������л��û�!");

				final EditText edtEditText = new EditText(Activity_more.this);
				builder.setView(edtEditText);
				SharedPreferences userdata = getSharedPreferences(
						"userdata", 0);
				String uid = userdata.getString("username", "");
				edtEditText.setText(uid);
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								SharedPreferences userdata = getSharedPreferences(
										"userdata", 0);
								String username = edtEditText.getText()
										.toString();
								userdata.edit().putString("username", username)
										.commit();

								Toast.makeText(Activity_more.this, "���óɹ���",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								intent.setClass(Activity_more.this, MainActivity.class);
								startActivity(intent);
							}
						});
				builder.setNegativeButton("ȡ��",
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
		});
		
		Button changeLevel=(Button)findViewById(R.id.changeLevel);
		changeLevel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("������ѡ����ģ������Ѷȣ�");
				builder.setSingleChoiceItems(new String[] {"�Ѷ�ֵ1---���","�Ѷ�ֵ2---�Ƚϻ���","�Ѷ�ֵ3---�Ѷ��е�","�Ѷ�ֵ4---�е�ƫ��","�Ѷ�ֵ5---�������"}, 0, 

					    new DialogInterface.OnClickListener()

					    {
					        public void onClick(DialogInterface dialog, int which) 
					        {
					        	System.out.println("�Ѷ�ֵ��"+which);
					        	SharedPreferences userdata = getSharedPreferences(
										"userdata", 0);
					        	userdata.edit().putInt("level", which+1)
								.commit();
					            //dialog.dismiss();
					        }

					    }

					);
				builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() 
				{
							@Override
							public void onClick(DialogInterface dialog,int which) 
							{
								// TODO Auto-generated method stub
								Toast.makeText(Activity_more.this, "���óɹ���",Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								intent.setClass(Activity_more.this, MainActivity.class);
								startActivity(intent);
							}
						});
				builder.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() 
				{

							@Override
							public void onClick(DialogInterface dialog,int which) {
								// TODO Auto-generated method stub
								return;
							}
						});
				builder.show();
			}
		});
	}
	
}
