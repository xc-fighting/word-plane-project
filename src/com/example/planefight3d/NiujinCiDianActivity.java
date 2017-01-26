package com.example.planefight3d;




import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.Dialog;


import android.content.Context;

import android.content.Intent;
//import android.content.pm.ActivityInfo;

import android.database.Cursor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;

import android.view.Window;
//import android.view.WindowManager;


import android.widget.AutoCompleteTextView;
import android.widget.Toast;
//import android.widget.Toast;

import android.widget.ImageButton;

import android.widget.TextView;

import android.widget.Button;


@SuppressLint("HandlerLeak")
public class NiujinCiDianActivity extends Activity{

	private Button niujin_back;
	private Button niujin_add;
	private Button niujin_search;
	
	private AutoCompleteTextView edit;
	
	private DBAdapter db;
	
	private TextSpeaker speaker;
	
	private TextReader reader;
	
	private String content;
	private String explain;
	
	
	private Dialog dlg;
	private TextView t;
	private ImageButton btn;
	private Button tuichu;
	
	
	
	public Handler handler=new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case 1:
				{
					set_view();
				}break;
				case 0:
				{
					Toast.makeText(NiujinCiDianActivity.this,"所查词汇不存在！",Toast.LENGTH_LONG).show();
				}break;
				case 2:
				{
					Toast.makeText(NiujinCiDianActivity.this,"该词已经在生词本中",Toast.LENGTH_LONG).show();
				}break;
				case 3:
				{
					Toast.makeText(NiujinCiDianActivity.this,"生词已经添加成功",Toast.LENGTH_LONG).show();
				}break;
			}
		}
	};
	
	
	
	public void set_view()
	{
		 
			dlg=new Dialog(NiujinCiDianActivity.this);		
			
			dlg.setTitle(content);
			
			Window window=dlg.getWindow();
			window.setContentView(R.layout.wordform);
		
			btn=(ImageButton)window.findViewById(R.id.imageButtonplay);
			
			btn.setOnClickListener(new press());
			
			tuichu=(Button)window.findViewById(R.id.tuichu);
			
			tuichu.setOnClickListener(new press());
			
			niujin_add=(Button)window.findViewById(R.id.addniujin);
			niujin_add.setOnClickListener(new press());
			
			t=(TextView)window.findViewById(R.id.textexplain);
			t.setText(explain);
			
			dlg.show();
		
	}
	
	
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		/************************************一加上这个就出错speaker,尚不知原因*******************************************/
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	  //  requestWindowFeature(Window.FEATURE_NO_TITLE); 		//设置为全屏
	  //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	    
	    setContentView(R.layout.niujincidian);
	    
	    
	    
	    db=new DBAdapter(this);
	    db.open_db();
	    
	    niujin_back=(Button)this.findViewById(R.id.backniujin);
	   
	    niujin_search=(Button)this.findViewById(R.id.button1niujin);
	    
	    niujin_back.setOnClickListener(new press());
	  
	    niujin_search.setOnClickListener(new press());
	    
	    edit=(AutoCompleteTextView)this.findViewById(R.id.actvWord);
	    edit.addTextChangedListener(new watcher());
	    edit.setThreshold(1);
	    
	    speaker=new TextSpeaker(this);
	    reader=new TextReader(this,false);
	    reader.read_Shengci();
	   
	    
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		speaker.Destroy();
		db.close_db();
		
	}
	
	
	
	private class watcher implements TextWatcher
	{

	
		public void afterTextChanged(Editable s) 
		{
			String str=s.toString();
			 //  必须将english字段的别名设为_id 
			Cursor cursor = db.database.rawQuery("select word as _id from Words where word like '%"+str+"%'",null);
			Adapter dictionaryAdapter = new Adapter(NiujinCiDianActivity.this,cursor, true);
			edit.setAdapter(dictionaryAdapter);
		
		}

		
		public void beforeTextChanged(CharSequence s, int start, int count,int after)
		{
			
			
		}

		
		public void onTextChanged(CharSequence s, int start, int before,int count) 
		{
           
			
			
			
		}
		

	
	}
	
	private class Adapter extends CursorAdapter
	{
		
		private LayoutInflater layoutInflater;

		public Adapter(Context context, Cursor c, boolean autoRequery)
		{
			
			super(context, c, autoRequery);
			layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		
		
		public CharSequence convertToString(Cursor cursor)
		{
			return cursor == null ? "" : cursor.getString(cursor.getColumnIndex("_id"));
		}

		private void setView(View view, Cursor cursor)
		{
			TextView tvWordItem = (TextView) view;
			tvWordItem.setText(cursor.getString(cursor.getColumnIndex("_id")));
		}

		
		public void bindView(View view, Context context, Cursor cursor)
		{
			setView(view, cursor);
		}

		
		public View newView(Context context, Cursor cursor, ViewGroup parent)
		{
			View view = layoutInflater.inflate(R.layout.word_list_item, null);
			setView(view, cursor);
			return view;
		}

		
		
	}
	
	

	
	private class press implements OnClickListener
	{

		
		public void onClick(View v) {
			switch(v.getId())
			{
				case R.id.backniujin:
				{
					Intent intent=new Intent(NiujinCiDianActivity.this,MenuActivity.class);
					NiujinCiDianActivity.this.startActivity(intent);
					NiujinCiDianActivity.this.finish();
				}break;
				case R.id.button1niujin:
				{
					String str=edit.getText().toString();
					new get_explaination(str).start();
				}break;
				case R.id.addniujin:
				{
					new check_exist().start();
				}break;
				case R.id.imageButtonplay:
				{
					speaker.set_text(content);
					speaker.speak_voice();
				}break;
				case R.id.tuichu:
				{
					dlg.dismiss();
				}break;
			}
			
		}
		
	}
	
	
	private class check_exist extends Thread
	{
		public void run()
		{
			boolean flag=false;
			for(String word:ShengCi.mosheng_word_list)
			{
				if(word.equals(content)==true)flag=true;
				else flag=false;
			}
			if(flag==true)handler.sendEmptyMessage(2);
			else 
			{
				ShengCi.mosheng_word_list.add(content);
				reader.wirte_Shengci();
				handler.sendEmptyMessage(3);
			}
		}
	}
	
	
	private class get_explaination extends Thread
	{
		
		
		public get_explaination(String c)
		{
			content=c;
		}
		
		public void run()
		{
			ShengCi[] list=db.query_infos(content);
			if(list!=null)
			{
			explain=list[0].get_explain();
			handler.sendEmptyMessage(1);
			}
			else
			{
				handler.sendEmptyMessage(0);
			}
		}
	}
	
	
}
