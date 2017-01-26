package com.example.planefight3d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class LianxiActivity extends Activity implements Runnable{

	private  EditText t1;
	private  Button b1;
	private Button b2;
	private Button b3;
	private  HTTPConnector conn;
	private String content=null;
	private boolean flag=false;
	private String liuyan_content;
	private Button liuyan_btn;
	private TextView content_edit;
	private TextView time_edit;
	private Dialog dlg;
	
	public Handler handler=new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case -4:
				{
					Toast.makeText(LianxiActivity.this,msg.what+"",Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(LianxiActivity.this,MenuActivity.class);
					LianxiActivity.this.startActivity(intent);
					LianxiActivity.this.finish();
				}break;
				case -2:
				{
					Toast.makeText(LianxiActivity.this,msg.what+"",Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(LianxiActivity.this,MenuActivity.class);
					LianxiActivity.this.startActivity(intent);
					LianxiActivity.this.finish();
				}break;
				case -1:
				{
					Toast.makeText(LianxiActivity.this,msg.what+"",Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(LianxiActivity.this,MenuActivity.class);
					LianxiActivity.this.startActivity(intent);
					LianxiActivity.this.finish();
				}break;
				case 4:
				{
					Toast.makeText(LianxiActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(LianxiActivity.this,MenuActivity.class);
					LianxiActivity.this.startActivity(intent);
					LianxiActivity.this.finish();
				}break;
				case 5:
				{
					liuyan_dialog();
				}break;
				case 6:
				{
					Toast.makeText(LianxiActivity.this,"留言内容不能为空",Toast.LENGTH_SHORT).show();
				}break;
			}
		}
	};
	
	
	
	
	public void liuyan_dialog()
	{
		dlg=new Dialog(LianxiActivity.this);
		Window window=dlg.getWindow();
		window.setContentView(R.layout.liuyanget);
		liuyan_btn=(Button)window.findViewById(R.id.liuyanqueren);
		content_edit=(TextView)window.findViewById(R.id.liuyanneirong);
		time_edit=(TextView)window.findViewById(R.id.liuyanshijian);		
		if(liuyan_content.contains("-"))
		{
		String[] str=liuyan_content.split("-");
	
		content_edit.setText(str[0]);
		
		time_edit.setText(str[1]);
		}
		else
		{
			content_edit.setText(liuyan_content);
		}
		liuyan_btn.setOnClickListener(new press());
		dlg.show();
		
	}
	
	
	
	
	
	
	
	
	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.lianxiwomen);
	        
	        t1 = (EditText) findViewById(R.id.input);   //获取文本框内容
	        
	        b1=(Button)findViewById(R.id.send);   //发送按钮
	        b1.setOnClickListener(new press());
	        
	        b2=(Button)findViewById(R.id.chakan);
	        b2.setOnClickListener(new press());
	        
	        b3=(Button)findViewById(R.id.liuyanback);
	        b3.setOnClickListener(new press());
 
             }
	 private class press implements OnClickListener
	 {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.send:
			{
				flag=false;
				content=t1.getText().toString();
				if(content.equals("")==false)
				new Thread(LianxiActivity.this).start();
				else handler.sendEmptyMessage(6);
			}break;
			case R.id.chakan:
			{
				flag=true;
				new Thread(LianxiActivity.this).start();
			}break;
			case R.id.liuyanqueren:
			{
				dlg.dismiss();
			}break;
			case R.id.liuyanback:
			{
				Intent intent=new Intent(LianxiActivity.this,MenuActivity.class);
				LianxiActivity.this.startActivity(intent);
				LianxiActivity.this.finish();
			}break;
			}
			
		}
		 
	 }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(flag==false)
		{
		conn=new HTTPConnector(HTTPConnector.ip);
		int what=conn.connect_us(HTTPConnector.username,this.content,GetTime.get_time());
		handler.sendEmptyMessage(what);
		}
		if(flag==true)
		{
			conn=new HTTPConnector(HTTPConnector.ip);
			liuyan_content=conn.get_liuyan(HTTPConnector.username);
			int what=5;
			handler.sendEmptyMessage(what);
		}
	}
}
