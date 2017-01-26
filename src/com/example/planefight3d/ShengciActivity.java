package com.example.planefight3d;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import android.widget.ListView;

import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class ShengciActivity extends Activity
{
	
	
	    private TextReader reader;
	     private DBAdapter db;
	    
	    private String word;
	    private String explain;
	   
	    
	    private Button btn1;
	    private ImageButton btn2;
	    private TextView v1;
	    private TextView v2;
	    private TextSpeaker speaker;
	    
	    private Dialog dlg;
	    
	    
	    private Button shengci_back;
	    private Button shengci_tongbu;
	    
	    private ListView shengci_list;
	    private SimpleAdapter adapter;
	    private List<Map<String,Object>>list_items=new ArrayList<Map<String,Object>>();
	    
	 
	    
	 
	    
	    
	    public Handler handler=new Handler()
	    {
	    	public void handleMessage(Message msg)
	    	{
	    		if(msg.what==0)
	    		dialog();
	    		if(msg.what==1)
	    		{
	    		//	adapter=new SimpleAdapter(ShengciActivity.this,list_items,R.layout.ab,
	            //       		new String[]{"tubiao","content"},new int[]{R.id.img,R.id.info});
	    			adapter.notifyDataSetChanged();
	    			shengci_list.setAdapter(adapter);
	    		}
	    		if(msg.what==2)
	    		{
	    			Toast.makeText(ShengciActivity.this,"生词本内容同步成功",Toast.LENGTH_LONG).show();
	    		}
	    		if(msg.what==3)
	    		{
	    			Toast.makeText(ShengciActivity.this,"请登陆后再进行上传",Toast.LENGTH_LONG).show();
	    		}
	    		if(msg.what==4)
	    		{
	    			Toast.makeText(ShengciActivity.this,"上传失败",Toast.LENGTH_LONG).show();
	    		}
	    	}
	    };
	   
	    
	    public void dialog()
	    {
	    	dlg=new Dialog(ShengciActivity.this);
	    	Window window=dlg.getWindow();
	    	window.setContentView(R.layout.showinfo);
	    	btn1=(Button)window.findViewById(R.id.t4);
	    	btn1.setOnClickListener(new pressbtn());
	    	btn2=(ImageButton)window.findViewById(R.id.t3);
	    	btn2.setOnClickListener(new pressbtn());
	    	v1=(TextView)window.findViewById(R.id.t1);
	    	v1.setText(word);
	    	v2=(TextView)window.findViewById(R.id.t2);
	    	v2.setText(explain);
	    	dlg.show();
	    }
	    
	    
	    public void onDestroy()
	    {
	    	super.onDestroy();
	    	//这里开始清空list同时加入新的生词
	    	reader.wirte_Shengci();
	    	db.close_db();
	    	speaker.Destroy();
	    }
	    
	    private class clicklistener implements OnItemClickListener
	    {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				word=ShengCi.mosheng_word_list.get(position);
				showInfo(position);
				
				
			}
	    	
	    }
    
        public void onCreate(Bundle savedInstanceState) 
        {
            super.onCreate(savedInstanceState);
            
            setContentView(R.layout.shengci);
            
            reader=new TextReader(this,true);
            reader.read_Shengci();
            
            db=new DBAdapter(this);
            db.open_db();
            
            speaker=new TextSpeaker(this);
            
            int size=ShengCi.mosheng_word_list.size();
         
            for(int i=0;i<size;i++)
            {
        
            	Map<String,Object>item=new HashMap<String,Object>();
            	item.put("tubiao",R.drawable.i2);
            	item.put("content",ShengCi.mosheng_word_list.get(i));
            	list_items.add(item);
            }
            
            adapter=new SimpleAdapter(this,list_items,R.layout.ab,
            		new String[]{"tubiao","content"},new int[]{R.id.img,R.id.info});
	        shengci_list=(ListView)this.findViewById(R.id.listshengci);
	        shengci_list.setAdapter(adapter);
            shengci_list.setItemsCanFocus(true);
            shengci_list.setOnItemClickListener(new clicklistener());
            
            
            shengci_back=(Button)this.findViewById(R.id.shengciback);
            shengci_back.setOnClickListener(new pressbtn());
            
            shengci_tongbu=(Button)this.findViewById(R.id.tongbu);
            shengci_tongbu.setOnClickListener(new pressbtn());
       
        }
        
        
	
	 
	    
	    
        public void showInfo(int position)
	    {
	        new AlertDialog.Builder(this)
	        .setTitle("生词本")
	        .setMessage("这个单词你会吗？")	        
	        .setNegativeButton("会", new dialog_press(position))
	        .setNeutralButton("不会", new dialog_press())
	        .show();
	
	    }
        
        
  /*      private class update_view_thread extends Thread
        {
        	
        	private int pos;
        	public update_view_thread(int p)
        	{
        		this.pos=p;
        	}
        	public void run()
        	{
        		ShengCi.mosheng_word_list.remove(pos);
        		list_items.remove(pos);
        		handler.sendEmptyMessage(2);
        	}
        }*/
        
     public class dialog_press implements   DialogInterface.OnClickListener
     {
    	 private int position;
    	 public dialog_press()
    	 {
    		 
    		 
    	 }
    	 public dialog_press(int position)
    	 {
    		 this.position=position;
    	 }

		@Override
		public void onClick(DialogInterface inter, int which) {
			// TODO Auto-generated method stub
			switch(which)
			{
				
				case DialogInterface.BUTTON_NEGATIVE:
				{
					//new update_view_thread(position).start();
					ShengCi.mosheng_word_list.remove(position);
	        		list_items.remove(position);
	        		adapter=new SimpleAdapter(ShengciActivity.this,list_items,R.layout.ab,
		                   		new String[]{"tubiao","content"},new int[]{R.id.img,R.id.info});
		    			//adapter.notifyDataSetChanged();
		    			shengci_list.setAdapter(adapter);
	            	
				}break;
				case DialogInterface.BUTTON_NEUTRAL:
				{
					new get_explain_thread().start();
				}break;
				
			}
			
		}
    	 
     }

    
    private class get_explain_thread extends Thread
    {
    	public void run()
    	{
    		String[] strs=db.query_explain(word);
    		speaker.set_text(word);
    		explain=strs[0];
    		handler.sendEmptyMessage(0);
    	}
    }
    
    
    
    
    public class pressbtn implements OnClickListener
    {
    	
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			    case R.id.shengciback:
			    	Intent intent=new Intent(ShengciActivity.this,MenuActivity.class);
			    	ShengciActivity.this.startActivity(intent);
				    ShengciActivity.this.finish();
				case R.id.t3:
					speaker.speak_voice();
					break;
				case R.id.t4:
					dlg.dismiss();
					break;
				case R.id.tongbu:
					if(HTTPConnector.islogin==true)
					new Thread()
					{
						public void run()
						{
							reader.wirte_Shengci();
							HTTPConnector conn=new HTTPConnector(HTTPConnector.ip);
							boolean flag=conn.upload_shengci();
							if(flag)
							handler.sendEmptyMessage(2);
							else
								handler.sendEmptyMessage(4);
						}
					}.start();
					else
					{
						handler.sendEmptyMessage(3);
					}
					break;
			}
		}
    	
    }
    
    
    
    
    
    
    
}
