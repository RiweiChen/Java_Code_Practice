package com.crw.Itest.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.crw.DB.MyDBOpenHelper;
import com.crw.Itest.R;

public class Activity_error_ask_show extends ListActivity //implements OnItemClickListener ,OnItemLongClickListener
{
	List<Map<String, Object>> list;//��ű���
	ArrayList<Integer> idList;
	MyDBOpenHelper myDBOpenHelper;
	SQLiteDatabase db;
	Cursor c;
	String uid;
	String currentDB;
	SharedPreferences userdata;
	SimpleAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		//��û��������ݿ���Ϣ��
		userdata=getSharedPreferences("userdata", MODE_WORLD_READABLE);
		currentDB=userdata.getString("currentDB", "");//���õ�ǰ���ݿ⡣
		uid ="'"+userdata.getString("username","")+"'";
		myDBOpenHelper=new MyDBOpenHelper(this,currentDB); // ����dbopenHelper;   	
		db = myDBOpenHelper.getWritableDatabase();//ȡ�����ݿ�	
		
		list = new ArrayList<Map<String, Object>>();
		idList=new ArrayList<Integer>();
		//��������
		setAdapter();
		ListView lv=getListView();
		registerForContextMenu(lv);
  }
	//���ð�
	public void setAdapter()
	{
		adapter = new SimpleAdapter(this,getData(),R.layout.collect_fillblank, 
				new String[]{"qid","qtitle","qanswer","qimportance"}, 
				new int[]{R.id.qid,R.id.qtitle,R.id.qanswer,R.id.qimportance}); 
				setListAdapter(adapter); 
	}
	//���������Ĳ˵�
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.setHeaderTitle("��ѡ����Ҫ�Ĳ�����");
		menu.add(0, 0, 0, "ɾ��");
		menu.add(0, 1, 1, "�鿴");
		menu.add(0, 2, 2, "����");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	//���������Ĳ˵�ѡ�����¼�
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        //��ȡ��ǰ��ѡ��Ĳ˵������Ϣ  
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	int qid=idList.get((int) info.id);
        switch(item.getItemId()){
        case 0://ɾ��
        	db.execSQL("delete from e_ask where uid="+uid+" and qid="+qid);
        	Toast.makeText(Activity_error_ask_show.this, "�ɹ�ɾ����", Toast.LENGTH_SHORT);
        	setAdapter();
            break;
        case 1://�鿴��
        	show(qid);
        	Toast.makeText(Activity_error_ask_show.this, "�鿴��", Toast.LENGTH_SHORT);
            break;    
        case 2://���ġ�
        	alter(qid);
        	Toast.makeText(Activity_error_ask_show.this, "�޸ĳɹ���", Toast.LENGTH_SHORT);
        	setAdapter();
            break;
        }
        return true;
    }
    
    //���ݰ󶨡�
	private List<Map<String, Object>> getData() 
	{ 
    	c = db.rawQuery("select q_ask.qid,qtitle,qanswer,comment from q_ask ,e_ask where q_ask.qid=e_ask.qid and uid="+uid,null);
		//������ݡ�
    	list.clear();
		idList.clear();
		while(c.moveToNext())
		{
			int i=c.getInt(c.getColumnIndex("q_ask.qid"));
			String title=c.getString(c.getColumnIndex("qtitle"));
			String answer=c.getString(c.getColumnIndex("qanswer"));
			String comment=c.getString(c.getColumnIndex("comment"));
			idList.add(i);//��id����������
			Map map = new HashMap(); 
			map.put("qid","��ţ�"+i);
			map.put("qtitle", "��Ŀ"+":"+title); 
			map.put("qanswer","��:"+answer); 
			map.put("qimportance","����ʼ�:"+comment); 
			list.add(map);
		}
		return list; 
	}
	//�޸ġ�
	public void alter(int id ) 
	{
		final int qid=id;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Activity_error_ask_show.this);
		builder.setTitle("����");
		builder.setMessage("�������µıʼ�!");
		final EditText edtEditText = new EditText(getApplicationContext());
		builder.setView(edtEditText);

		builder.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() 
				{

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub					
						String newnote = edtEditText.getText().toString();
						db.execSQL("update e_ask set comment='"+newnote+"' where uid="+uid+" and qid="+qid);
						return;
					}
				});
		builder.setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub

						return;
					}
				});
		builder.show();
	}
	public void  show(int qid) 
	{
		   LayoutInflater inflater = getLayoutInflater();
		   View layout = inflater.inflate(R.layout.search_ask,(ViewGroup) findViewById(R.id.search_ask));
		   c=db.rawQuery("select * from q_ask where qid="+qid, null);
		   c.moveToFirst();
			int id=c.getInt(c.getColumnIndex("qid"));
			String title=c.getString(c.getColumnIndex("qtitle"));
			String answer=c.getString(c.getColumnIndex("qanswer"));
			int difficulty=c.getInt(c.getColumnIndex("qdifficulty"));
			String explain=c.getString(c.getColumnIndex("qexplain"));
			String source=c.getString(c.getColumnIndex("qsource"));
			String mainpoint=c.getString(c.getColumnIndex("qmainpoint"));
			
		   final TextView qtitle=(TextView)layout.findViewById(R.id.qtitle);
		   final TextView qanswer=(TextView)layout.findViewById(R.id.qanswer);
		   final TextView qdifficulty=(TextView)layout.findViewById(R.id.qdifficulty);
		   final TextView qexplain=(TextView)layout.findViewById(R.id.qexplain);
		   final TextView qsource=(TextView)layout.findViewById(R.id.qsource);
		   final TextView qmainpoint=(TextView)layout.findViewById(R.id.qmainpoint);
		   
		   qtitle.setText("��Ŀ��"+id+"."+title);
		   qanswer.setText("��"+answer);
		   qdifficulty.setText("�Ѷȣ�"+difficulty+"");
		   qexplain.setText("���ͣ�"+explain);
		   qsource.setText("��Դ��"+source);
		   qmainpoint.setText("Ҫ�㣺"+mainpoint);
		   

		   new AlertDialog.Builder(Activity_error_ask_show.this)
		   	 .setTitle("��Ŀ���飡").setView(layout)		   	 
		     .setPositiveButton("ȷ��", null)
		     .setNegativeButton("ȡ��", null).show();

	}

}
