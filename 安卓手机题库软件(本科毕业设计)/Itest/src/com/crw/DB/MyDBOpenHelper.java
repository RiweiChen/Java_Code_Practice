package com.crw.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper 
{

    public MyDBOpenHelper(Context context,String DBname) 
    {
    	//�򿪻��½����е����ݿ⡣
        //super(context, "questionbank_java.s3db",null,1);
    	//���ݿ������ǲ��еģ��ᵼ�³����޷����У���ʹ�������ݿ�ĺ�׺����ͬ��
        super(context, DBname,null,1);
    }
  //���ݿ��һ�δ���ʱ���ã� 

    @Override

    public  void onCreate(SQLiteDatabase db) 
    {
       //System.out.println("���ݿⴴ��");
    	//��Ϊ���ݿⶼ�����ⲿ���������Բ���Ҫ�ٳ������潨���� 
    }

    //onUpGrade��ͨ��������ݿ�汾�Ƿ�ı䣬����ı�����ִ�и÷���������ִ��

    @Override

    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
       // TODO Auto-generated method stub
    	//��������
    }

 

}

 



