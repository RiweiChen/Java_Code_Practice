package com.crw.Itest.question;

import android.R.string;

public class QuestionBase 
{
	private int qid;//���
	private string qtitle;//��Ŀ
	private string qanswer;//��
	private int qdifficulty;//�Ѷ�
	private string qexplain;//����
	private string qsource;//��Ŀ��Դ
	private string qmainpoint;//Ҫ��
	
	public QuestionBase()
	{
	
	}
	//���캯��
	public QuestionBase(int id,string title,string answer,int difficulty
			,string explain,string source,string mainpoint)
	{
		qid=id;
		qtitle=title;
		qanswer=answer;
		qdifficulty=difficulty;
		qexplain=explain;
		qsource=source;
		qmainpoint=mainpoint;
	}

	public int  getqid()
	{
		return qid;
	}
	public string gettitle()
	{
		return qtitle;
	}
	public string getqanswer() 
	{
		return qanswer;
	}
	public int getqdifficulty()
	{
	    return qdifficulty;	
	}
	public string getqexplain() 
	{
		return qexplain;
	}
	public string getqsource() 
	{
		return qsource;
	}
	public string getqmainpoint() 
	{
		return qmainpoint;
	}
}
