package com.crw.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper 
{

    public MyDBOpenHelper(Context context,String DBname) 
    {
    	//打开或新建已有的数据库。
        //super(context, "questionbank_java.s3db",null,1);
    	//数据库重名是不行的，会导致程序无法运行，即使两个数据库的后缀名不同。
        super(context, DBname,null,1);
    }
  //数据库第一次创建时调用， 

    @Override

    public  void onCreate(SQLiteDatabase db) 
    {
       //System.out.println("数据库创建");
    	//因为数据库都是在外部创建，所以不需要再程序里面建立。 
    }

    //onUpGrade是通过检查数据库版本是否改变，如果改变了则执行该方法，否则不执行

    @Override

    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
       // TODO Auto-generated method stub
    	//不做处理。
    }

 

}

 



