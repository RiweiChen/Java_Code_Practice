package com.crw.Itest.search;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class Activity_questionsearch extends TabActivity
{	
	private TabHost m_tabHost; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) { 
	super.onCreate(savedInstanceState); 
	//setContentView(R.layout.tabs);
	SharedPreferences userdata = getSharedPreferences(
			"userdata", 0);
	String lastKeyword = userdata.getString("lastKeyword", "");
	setTitle("������Ĺؼ����ǣ�"+lastKeyword);
	//getTabHost���ص�TabHost����װ��tabs 
	m_tabHost = getTabHost(); 
	 
	//add tabs,����������Ӿ����Tabs,����Tab������Ӧ��Activity 
	addOneTab(); 
	addTwoTab(); 
	addThreeTab(); 

	} 
	 
	public void addOneTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_questionsearch.this, Activity_search_choice.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("ѡ����"); 
	spec.setIndicator("ѡ����", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 
	 
	public void addTwoTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_questionsearch.this, Activity_search_fillblank.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("�����"); 
	spec.setIndicator("�����", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 
	public void addThreeTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_questionsearch.this, Activity_search_ask.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("�ʴ���"); 
	spec.setIndicator("�ʴ���", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 

}
