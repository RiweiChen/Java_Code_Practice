 package com.crw.Itest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crw.DB.Import.DBImportActivity;
import com.crw.DB.qbselect.Activity_qbselect;
import com.crw.Itest.classify.Activity_category_mainpoint;
import com.crw.Itest.classify.Activity_qcategory;
import com.crw.Itest.exam.Activity_exam_brower;
import com.crw.Itest.exercise.Activity_exercise_brower;
import com.crw.Itest.question.Activity_collect_brower;
import com.crw.Itest.question.Activity_error_brower;
import com.crw.Itest.question.Activity_question_brower;
import com.crw.Itest.search.Activity_questionsearch;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		//�汾����������Ľ��.
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		SharedPreferences userdata = getSharedPreferences("userdata",
				MODE_WORLD_READABLE);
		
		
		
		//�ж����ݿ����������ݣ���������Ĭ�����ݿ⡣
		String DATABASE_PATH="/data/data/com.crw.Itest/databases";
		//�ļ��������ú�׺��
		String DATABASE_FILENAME = "questionbank_java";
		String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
		try {
			File dir = new File(DATABASE_PATH);	
			if (!dir.exists()) // ����ļ��в����ڴ����ļ���
				dir.mkdir();
			
			if (!(new File(databaseFilename)).exists()) 
			{ // ����ļ������ڴ����ļ�
				Context context=MainActivity.this;
				InputStream is = context.getResources().openRawResource(
						R.raw.questionbank_java);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) 
				{
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				System.out.println("��һ�����ݿ��Ѿ����롣");
				//�������Ĭ��ֵ��
				userdata.edit().putString("username", "Ĭ���û�")
							   .putString("currentDB","questionbank_java")
						       .commit();
				Toast.makeText(MainActivity.this,"��һ�����ݿ��Ѿ����룡",Toast.LENGTH_LONG).show();
			}
			//ͷ��������ʾ��
			TextView top = (TextView) findViewById(R.id.top);

			String username = userdata.getString("username", "");
			String currentDB=userdata.getString("currentDB", "");
			top.setText( "��ǰ�û���" + username+"\n"+"��ǰ���:"+currentDB);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// ��һ����
		LinearLayout button = (LinearLayout) this.findViewById(R.id.center1);
		button.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				/* �˴���Intent��ʵ��Activity��Activity֮�����ת */
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,
						Activity_question_brower.class);
				// Intent intent=new Intent(IntentTest.this,MyActivity.class);
				startActivity(intent);
			}
		});
		// �ڶ�����
		LinearLayout button2 = (LinearLayout) this.findViewById(R.id.center2);
		button2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/* �˴���Intent��ʵ��Activity��Activity֮�����ת */
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Activity_qcategory.class);
				startActivity(intent);
			}
		});

		// ��������
		LinearLayout button3 = (LinearLayout) this.findViewById(R.id.center3);
		button3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/* �˴���Intent��ʵ��Activity��Activity֮�����ת */
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,
						Activity_exercise_brower.class);
				startActivity(intent);
			}
		});

		// ���ĸ���
		LinearLayout button4 = (LinearLayout) this.findViewById(R.id.center4);
		button4.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/* �˴���Intent��ʵ��Activity��Activity֮�����ת */
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,
						Activity_category_mainpoint.class);
				startActivity(intent);
			}
		});

		// �ڶ���Ŀ��
		// �û�����
		LinearLayout setuser = (LinearLayout) this.findViewById(R.id.setuser);
		setuser.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) 
			{
				//
/*				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("���õ�ǰ�û���");
				builder.setMessage("�������µ��û������л��û�!");

				final EditText edtEditText = new EditText(MainActivity.this);
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

								Toast.makeText(MainActivity.this, "���óɹ���",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								intent.setClass(MainActivity.this, MainActivity.class);
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
				builder.show();*/
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Activity_exam_brower.class);
				startActivity(intent);
		
				
			}
		});

		// �ҵĴ��⡣
		LinearLayout error = (LinearLayout) this.findViewById(R.id.error);
		error.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/* �˴���Intent��ʵ��Activity��Activity֮�����ת */
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Activity_error_brower.class);
				startActivity(intent);
			}
		});

		// �ҵ��ղء�
		LinearLayout collect = (LinearLayout) this.findViewById(R.id.collect);
		collect.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/* �˴���Intent��ʵ��Activity��Activity֮�����ת */
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,
						Activity_collect_brower.class);
				startActivity(intent);
			}
		});

		// �ײ�������
		//������
		RadioButton search = (RadioButton) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				//
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("�������");
				builder.setMessage("�����������ؼ���!");
				final EditText edtEditText = new EditText(MainActivity.this);
				builder.setView(edtEditText);
		
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub					
								String keyword = edtEditText.getText()
										.toString();
								Bundle data=new Bundle();
								data.putString("keyword", keyword);
								SharedPreferences userdata=getSharedPreferences("userdata", 0);
								//ע�ᡣ
								userdata.edit()
								.putString("lastKeyword",keyword)
				                .commit();
								
								Intent intent = new Intent();
								intent.putExtras(data);
								intent.setClass(MainActivity.this,Activity_questionsearch.class);
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
		//��ҳ��ˢ��
		RadioButton homepage = (RadioButton) findViewById(R.id.homepage);
		homepage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		//������ء�
		RadioButton dbdownload = (RadioButton) findViewById(R.id.dbdownload);
		dbdownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DBImportActivity.class);
				startActivity(intent);
			}
		});
		//����л���
		RadioButton dbswitch = (RadioButton) findViewById(R.id.dbswitch);
		dbswitch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Activity_qbselect.class);
				startActivity(intent);
			}
		});
		//����ѡ�
		RadioButton more = (RadioButton) findViewById(R.id.more);
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Activity_more.class);
				startActivity(intent);
			}
		});

	}

	// ��ʾ�û�����
	public void showUserName() {
		SharedPreferences userdata = getSharedPreferences("userdata",
				MODE_WORLD_READABLE);
		String username = userdata.getString("username", "");
		TextView viewUsername = (TextView) findViewById(R.id.username);
		viewUsername.setText("��ӭ�û���" + username);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
