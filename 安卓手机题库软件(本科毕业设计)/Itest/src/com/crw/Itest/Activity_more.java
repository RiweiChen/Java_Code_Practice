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
		//获得基本的数据库信息。
		userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		currentDB=userdata.getString("currentDB", "");//设置当前数据库。
		uid =userdata.getString("username","");
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // 创建dbopenHelper;   	
		db = myDBOpenHelper.getWritableDatabase();//取得数据库	
		
		//删除错题。
		Button deleteError=(Button)findViewById(R.id.deleteError);
		deleteError.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("清空错题集");
				builder.setMessage("清空错题后，当前用户的所有错题将丢失，是否确定清空？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) 
							{
								// TODO Auto-generated method stub
								db.execSQL("delete from e_choice");
								db.execSQL("delete from e_fillblank");
								db.execSQL("delete from e_ask");
								Toast.makeText(Activity_more.this, "已经清空错题库", Toast.LENGTH_SHORT).show();
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
		});
		
		//删除收藏。
		Button deleteCollect=(Button)findViewById(R.id.deleteCollect);
		deleteCollect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("清空收藏夹");
				builder.setMessage("清空收藏夹后，当前用户的所有收藏将丢失，是否确定清空？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) 
							{
								// TODO Auto-generated method stub
								db.execSQL("delete from f_choice");
								db.execSQL("delete from f_fillblank");
								db.execSQL("delete from f_ask");
								Toast.makeText(Activity_more.this, "已经清空收藏夹", Toast.LENGTH_SHORT).show();
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
		});
		//联系。
		Button contact=(Button)findViewById(R.id.contact);
		contact.setOnClickListener(new OnClickListener() 
		{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Activity_more.this);
			builder.setTitle("联系开发者");
			builder.setMessage("如果你有什么意见或建议在作者的\n"+"新浪微博：http://weibo.com/chenriwei2\n"+"留言!");
			builder.setPositiveButton("确定",
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
		//分享
		Button share=(Button)findViewById(R.id.share);
		share.setOnClickListener(new OnClickListener() 
		{
					
		@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Activity_more.this);
			builder.setTitle("题库分享");
			builder.setMessage("您可以发送邮件到\n"+"chenriwei@outlook.com\n"+"来分享题库!");
			builder.setPositiveButton("确定",
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
				builder.setTitle("关于");
				builder.setMessage("本应用是是毕业设计的课题\n"+"有许多不足之处警情谅解\n"
									+"最后感谢指导老师程烨的悉心教导");
				builder.setPositiveButton("确定",
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
				builder.setTitle("退出？");
				builder.setMessage("是否确定退出");
				builder.setPositiveButton("确定",
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
		});
		
		
		
		Button changeUser=(Button)findViewById(R.id.changeUser);
		changeUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("设置当前用户名");
				builder.setMessage("请输入新的用户名或切换用户!");

				final EditText edtEditText = new EditText(Activity_more.this);
				builder.setView(edtEditText);
				SharedPreferences userdata = getSharedPreferences(
						"userdata", 0);
				String uid = userdata.getString("username", "");
				edtEditText.setText(uid);
				builder.setPositiveButton("确定",
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

								Toast.makeText(Activity_more.this, "设置成功！",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								intent.setClass(Activity_more.this, MainActivity.class);
								startActivity(intent);
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
		});
		
		Button changeLevel=(Button)findViewById(R.id.changeLevel);
		changeLevel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Activity_more.this);
				builder.setTitle("请设置选择在模拟题的难度！");
				builder.setSingleChoiceItems(new String[] {"难度值1---最简单","难度值2---比较基础","难度值3---难度中等","难度值4---中等偏难","难度值5---极其的难"}, 0, 

					    new DialogInterface.OnClickListener()

					    {
					        public void onClick(DialogInterface dialog, int which) 
					        {
					        	System.out.println("难度值："+which);
					        	SharedPreferences userdata = getSharedPreferences(
										"userdata", 0);
					        	userdata.edit().putInt("level", which+1)
								.commit();
					            //dialog.dismiss();
					        }

					    }

					);
				builder.setPositiveButton("确定",new DialogInterface.OnClickListener() 
				{
							@Override
							public void onClick(DialogInterface dialog,int which) 
							{
								// TODO Auto-generated method stub
								Toast.makeText(Activity_more.this, "设置成功！",Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								intent.setClass(Activity_more.this, MainActivity.class);
								startActivity(intent);
							}
						});
				builder.setNegativeButton("取消",new DialogInterface.OnClickListener() 
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
