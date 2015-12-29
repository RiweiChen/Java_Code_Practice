package com.crw.Itest.question;

import android.R.string;

public class QuestionBase 
{
	private int qid;//题号
	private string qtitle;//题目
	private string qanswer;//答案
	private int qdifficulty;//难度
	private string qexplain;//解释
	private string qsource;//题目来源
	private string qmainpoint;//要点
	
	public QuestionBase()
	{
	
	}
	//构造函数
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
