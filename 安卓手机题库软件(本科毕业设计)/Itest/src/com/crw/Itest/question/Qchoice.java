package com.crw.Itest.question;

import android.R.string;
import com.crw.Itest.question.QuestionBase;
public class Qchoice extends QuestionBase
{

	private string qoptA;
	private string qoptB;
	private string qoptC;
	private string qoptD;
	
	public Qchoice()
	{
		;
	}
	
	//定义构造函数
	public Qchoice(int id, string title, string answer, int difficulty,
			string explain, string source, string mainpoint,
			string optA,string optB,string optC,string optD) 
	{
		super(id, title, answer, difficulty, explain, source, mainpoint);
		// TODO Auto-generated constructor stub
		qoptA=optA;
		qoptB=optB;
		qoptC=optC;
		qoptD=optD;
	}
	
	public string getQoptA() {
		return qoptA;
	}
	public string getQoptB() {
		return qoptB;
	}
	public string getQoptC() {
		return qoptC;
	}
	public string getQoptD() {
		return qoptD;
	}
	
	//定义选择题题库内容。



}
