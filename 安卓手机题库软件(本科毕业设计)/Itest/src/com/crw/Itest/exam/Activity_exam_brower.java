package com.crw.Itest.exam;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Activity_exam_brower extends TabActivity
{
	private TabHost m_tabHost; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) { 
	super.onCreate(savedInstanceState); 
	//setContentView(R.layout.tabs);
	 
	//getTabHost���ص�TabHost����װ��tabs 
	m_tabHost = getTabHost(); 
	 
	//add tabs,����������Ӿ����Tabs,����Tab������Ӧ��Activity 
	addOneTab(); 
	addTwoTab(); 
	//addThreeTab(); 

	} 
	 
	public void addOneTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_exam_brower.this, Activity_exam.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("ѡ����"); 
	spec.setIndicator("ѡ����", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 
	 
	public void addTwoTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_exam_brower.this, Activity_exam_fillblank.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("�����"); 
	spec.setIndicator("�����", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 
/*	public void addThreeTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_qcategory.this, Activity_qcategory_ask.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("�ʴ���"); 
	spec.setIndicator("�ʴ���", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} */
}
