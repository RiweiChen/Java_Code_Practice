package com.crw.Itest.question;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Activity_collect_brower extends TabActivity
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
	addThreeTab(); 

	} 
	 
	public void addOneTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_collect_brower.this, Activity_collect_choice_show.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("ѡ����"); 
	spec.setIndicator("ѡ����", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 
	 
	public void addTwoTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_collect_brower.this, Activity_collect_fillblank_show.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("�����"); 
	spec.setIndicator("�����", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 
	public void addThreeTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_collect_brower.this, Activity_collect_ask_show.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("�ʴ���"); 
	spec.setIndicator("�ʴ���", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 

}
