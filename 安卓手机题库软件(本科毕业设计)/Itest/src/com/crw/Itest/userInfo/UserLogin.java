package com.crw.Itest.userInfo;

import com.crw.Itest.MainActivity;
import com.crw.Itest.R;
import com.crw.Itest.exercise.Activity_exercise_ask_show;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends Activity
{
	private EditText username;
	private Button login;
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);	//调用父类的函数。
		setContentView(R.layout.activity_userlogin);
		getViewID();
		SharedPreferences userdata = getSharedPreferences(
				"userdata", 0);
		String uid = userdata.getString("username", "");
		username.setText(uid);
		login.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				SharedPreferences userdata=getSharedPreferences("userdata", 0);
				//注册。
				userdata.edit()
				.putString("username",username.getText().toString())
                .commit();
				Toast.makeText(UserLogin.this,"登陆成功！",Toast.LENGTH_SHORT).show();
				Intent intent =new Intent();
				intent.setClass(UserLogin.this,MainActivity.class);
				startActivity(intent);
				
			}
		});
		
		
	}
	public void getViewID()
	{
		username=(EditText)findViewById(R.id.username);
		login=(Button)findViewById(R.id.login);
	}
}
