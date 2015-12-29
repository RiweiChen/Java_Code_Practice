package com.crw.Itest.question;

import android.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.crw.Itest.MainActivity;

public class Activity_question_brower extends TabActivity 
{
	private TabHost m_tabHost; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) { 
	super.onCreate(savedInstanceState); 
	//setContentView(R.layout.tabs);
	 
	//getTabHost返回的TabHost用于装载tabs 
	m_tabHost = getTabHost(); 
	 
	//add tabs,这里用于添加具体的Tabs,并用Tab触发相应的Activity 
	addOneTab(); 
	addTwoTab(); 
	addThreeTab(); 

	} 
	 
	public void addOneTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_question_brower.this, Activity_questionshow.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("选择题"); 
	spec.setIndicator("选择题", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 
	 
	public void addTwoTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_question_brower.this, Activity_question_fillblank_show.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("填空题"); 
	spec.setIndicator("填空题", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 
	public void addThreeTab(){ 
	Intent intent = new Intent(); 
	intent.setClass(Activity_question_brower.this, Activity_question_ask_show.class); 
	 
	TabSpec spec = m_tabHost.newTabSpec("问答题"); 
	spec.setIndicator("问答题", null); 
	spec.setContent(intent); 
	m_tabHost.addTab(spec); 
	} 

}
