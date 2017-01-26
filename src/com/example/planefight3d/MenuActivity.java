package com.example.planefight3d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.content.pm.ActivityInfo;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MenuActivity extends Activity{
	
	
	public boolean repeat=true;
	public Thread menu_thread;
	public MenuView myview;
	
	public static float width;
	public static float height;
	
	private HTTPConnector conn;
	
	
	private Button btn_login;
	private Button btn_reg;	
	private Button btn_test;
	private Button reg;
	
	private EditText user;
	private EditText pwd;	
	private EditText link;
	
	private EditText reg_username;
	private EditText reg_password;
	private EditText reg_pwd_confirm;
	
	
	
	
	
	private Dialog dlg;
	

	
	
	private class registerThread extends Thread
	{
		public void run()
		{
			conn=new HTTPConnector(HTTPConnector.ip);
			int what=conn.register_user(HTTPConnector.username, HTTPConnector.password);
			internet_handler.sendEmptyMessage(what);
			
		}
	}
	
	private class ConnectThread extends Thread
	{
		
		
		public void run()
		{
			
				conn=new HTTPConnector(HTTPConnector.ip);
				int what=conn.linktest();
				internet_handler.sendEmptyMessage(what);
			    HTTPConnector.istest=true;
		}
	}
	
	
	private class loginThread extends Thread
	{
		public void run()
		{
			conn=new HTTPConnector(HTTPConnector.ip);
			int what=conn.login_server(HTTPConnector.username,HTTPConnector.password);
			internet_handler.sendEmptyMessage(what);
			
		}
	}
	
	
	
	
	
	private class press implements OnClickListener
	{
	  
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
				case R.id.loginbtn:
				{
					if(HTTPConnector.islogin==false)
					{
						HTTPConnector.username=user.getText().toString();
						HTTPConnector.password=pwd.getText().toString();
						new loginThread().start();
					}
					else if(HTTPConnector.islogin==true)
					{
						Toast.makeText(MenuActivity.this,"您已经登录上系统",Toast.LENGTH_SHORT).show();
					}
				}break;
				case R.id.linkbtn:
				{
					HTTPConnector.ip=link.getText().toString();
					new ConnectThread().start();
				}break;
				case R.id.regbtn:
				{
				  if(HTTPConnector.islogin==false)
				  {
				   dlg.dismiss();
				   register_dialog();
				  }
				  else
				  {
					  Toast.makeText(MenuActivity.this,"已经登入系统，无法进行注册",Toast.LENGTH_SHORT).show();
				  }
				   
				}break;
				case R.id.register:
				{
					HTTPConnector.username=reg_username.getText().toString();
					HTTPConnector.password=reg_password.getText().toString();
					String str=reg_pwd_confirm.getText().toString();
					if(HTTPConnector.password.equals(str)==false)
					{
						reg_password.setText("");
						reg_pwd_confirm.setText("");
						Toast.makeText(MenuActivity.this,"两次输入的密码不同请重新输入",Toast.LENGTH_SHORT).show();
					}
					else
					{
						new registerThread().start();
					}
							
						
				}break;
			}
			
		}
		
	}
	public void register_dialog()
	{
		dlg=new Dialog(MenuActivity.this);
		Window window=dlg.getWindow();
		window.setContentView(R.layout.register);
		reg=(Button)window.findViewById(R.id.register);
		reg_username=(EditText)window.findViewById(R.id.editreg1);
		reg_password=(EditText)window.findViewById(R.id.editreg2);
	    reg_pwd_confirm=(EditText)window.findViewById(R.id.editreg3);
	    reg.setOnClickListener(new press());
		dlg.show();
	}
	
	public void test_link()
	{
		 dlg=new Dialog(MenuActivity.this);
						
			
			Window window=dlg.getWindow();
			window.setContentView(R.layout.linktest);
		
			btn_test=(Button)window.findViewById(R.id.linkbtn);
			
			
			link=(EditText)window.findViewById(R.id.ipinput);
			
			
			
			
			btn_test.setOnClickListener(new press());
				dlg.show();
	}
	
	
	
	
	public void login_register_dialog()
	{
		
		 
		dlg=new Dialog(MenuActivity.this);		
		
		Window window=dlg.getWindow();
		window.setContentView(R.layout.loginreg);
	
		btn_login=(Button)window.findViewById(R.id.loginbtn);
		btn_reg=(Button)window.findViewById(R.id.regbtn);
		
		user=(EditText)window.findViewById(R.id.loginuser);
		pwd=(EditText)window.findViewById(R.id.loginpwd);
		
		
		
		btn_login.setOnClickListener(new press());
		btn_reg.setOnClickListener(new press());
		dlg.show();
		
	}
	
	
	
	public void set_banner()
	{
		if(HTTPConnector.islogin==true)
		{
			myview.tips="用户:"+HTTPConnector.username+",您已登入";
		}
	}
	
	
	
	
	public  void get_screen_info()
	{
		WindowManager manager=getWindowManager();
		Display display=manager.getDefaultDisplay();
		DisplayMetrics metrics=new DisplayMetrics();
		display.getMetrics(metrics);
		width=metrics.widthPixels;
		height=metrics.heightPixels;
	}
	
	
	public float[][] touch_area=new float [8][4]; 
	
	
	public Handler internet_handler=new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
					case -3:
					{
						Toast.makeText(MenuActivity.this,"该用户已经存在，请更换用户名",Toast.LENGTH_SHORT).show();
						reg_username.setText("");
						reg_password.setText("");
						reg_pwd_confirm.setText("");
					}break;
					case -2:
					{
						Toast.makeText(MenuActivity.this,"出现异常,请重新设置ip",Toast.LENGTH_SHORT).show();
						dlg.dismiss();
					}break;
					case -1:
					{
						Toast.makeText(MenuActivity.this,"网络出错,请重新设置ip",Toast.LENGTH_SHORT).show();
						dlg.dismiss();
					}break;
					case 0:
					{
						Toast.makeText(MenuActivity.this,"登录失败,请检查用户名密码",Toast.LENGTH_SHORT).show();
						user.setText("");
						pwd.setText("");
					}break;
					case 1:
					{
						Toast.makeText(MenuActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
						dlg.dismiss();
						myview.tips="用户:"+HTTPConnector.username+",您已登入";
						HTTPConnector.islogin=true;
						
					}break;
					case 2:
					{
						Toast.makeText(MenuActivity.this,"连接测试成功",Toast.LENGTH_SHORT).show();				
						dlg.dismiss();
					}break;
					case 3:
					{
						Toast.makeText(MenuActivity.this,"用户注册成功，已登入系统",Toast.LENGTH_SHORT).show();
						dlg.dismiss();						
						myview.tips="用户:"+HTTPConnector.username+",您已登入";
						HTTPConnector.islogin=true;
					}break;
			}
		}
	};
	
	@SuppressLint("HandlerLeak")
	public Handler handler=new Handler()
	{
		public void handleMessage(Message msg)
		{
						
			switch(msg.what)
			{
				case 0:
				{
					Intent intent=new Intent(MenuActivity.this,BeiciActivity.class);
					MenuActivity.this.startActivity(intent);
					MenuActivity.this.finish();
				}break;
				case 1:
				{
					
					Intent intent=new Intent(MenuActivity.this,GameActivity.class);
					MenuActivity.this.startActivity(intent);
					MenuActivity.this.finish();
					
				}break;
				case 2:
				{
					
					
					if(HTTPConnector.islogin==false)
					{
						Toast.makeText(MenuActivity.this,"请先进行登录",Toast.LENGTH_SHORT).show();
					}
					else
					{
						Intent intent=new Intent(MenuActivity.this,PaihangActivity.class);
						MenuActivity.this.startActivity(intent);
						MenuActivity.this.finish();
					}
					
				}break;
				case 3:
				{
					
					Intent intent=new Intent(MenuActivity.this,AboutGameActivity.class);
					MenuActivity.this.startActivity(intent);
					MenuActivity.this.finish();
				}break;
				case 4:
				{
					
					if(HTTPConnector.islogin==false)
					{
						Toast.makeText(MenuActivity.this,"请先进行登录",Toast.LENGTH_SHORT).show();
					}
					else
					{
						Intent intent=new Intent(MenuActivity.this,LianxiActivity.class);
						MenuActivity.this.startActivity(intent);
						Toast.makeText(MenuActivity.this,HTTPConnector.ip+":"+HTTPConnector.username+":"+HTTPConnector.password,Toast.LENGTH_SHORT).show();
						MenuActivity.this.finish();
					}
				}break;
				case 5:
				{
					
					Intent intent=new Intent(MenuActivity.this,ShengciActivity.class);
					MenuActivity.this.startActivity(intent);
					MenuActivity.this.finish();
				}break;
				case 6:
				{
					Intent intent=new Intent(MenuActivity.this,NiujinCiDianActivity.class);
					MenuActivity.this.startActivity(intent);
					MenuActivity.this.finish();
					
				}break;
				case 7:
				{
					if(HTTPConnector.islogin==true)
					{
						Intent intent=new Intent(MenuActivity.this,KaoKaoNiActivity.class);
						MenuActivity.this.startActivity(intent);
						MenuActivity.this.finish();
					}
					else
					{
						Toast.makeText(MenuActivity.this,"请先进行登录",Toast.LENGTH_LONG).show();
					}
				}break;
				case 8:
				{
					
					if(HTTPConnector.istest==true)
						//Toast.makeText(MenuActivity.this,"登录",Toast.LENGTH_SHORT).show();
						MenuActivity.this.login_register_dialog();
						else Toast.makeText(MenuActivity.this,"请先设置ip",Toast.LENGTH_SHORT).show();
				}break;
				case 9:
				{
					MenuActivity.this.test_link();
				}break;
			}
		}
	};
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
	  super.onCreate(savedInstanceState);
	  
	  this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    requestWindowFeature(Window.FEATURE_NO_TITLE); 		//设置为全屏
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	  
	  myview=new MenuView(this);
	  setContentView(myview);
	  this.set_banner();
	}
	
}	
	