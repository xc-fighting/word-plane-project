package com.example.planefight3d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class KaoKaoNiActivity extends Activity implements Runnable{
	
	RadioGroup group;
	RadioButton btn1,btn2,btn3,btn4;
	Button back;
	TextView index;
	TextView content;
	
	private String[] alphas={"A","B","C","D"};
	private int[] buttonIDS={R.id.button1kaokao,R.id.button2kaokao,R.id.button3kaokao,R.id.button4kaokao};
	private int answerID;
	
	private int cur_id=0;
	private int length=0;
	
	private Questions[] groups;
	
	
	public Handler handler=new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(msg.what==0)
			{
				content.setText(groups[cur_id].content);
				btn1.setText(groups[cur_id].questions[0]);btn1.setChecked(false);
				btn2.setText(groups[cur_id].questions[1]);btn2.setChecked(false);
				btn3.setText(groups[cur_id].questions[2]);btn3.setChecked(false);
				btn4.setText(groups[cur_id].questions[3]);btn4.setChecked(false);
				index.setText((cur_id+1)+"/"+length);
				answerID=groups[cur_id].answer-1;
			}
			if(msg.what==-1)
			{
				Toast.makeText(KaoKaoNiActivity.this,"尚无老师给你添加题目测试",Toast.LENGTH_LONG).show();
				Intent intent=new Intent(KaoKaoNiActivity.this,MenuActivity.class);
				KaoKaoNiActivity.this.startActivity(intent);
				KaoKaoNiActivity.this.finish();
			}
		}
	};
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kaokaoni);
		
		group=(RadioGroup)this.findViewById(R.id.radioGroup1);
		btn1=(RadioButton)this.findViewById(R.id.button1kaokao);
		btn2=(RadioButton)this.findViewById(R.id.button2kaokao);
		btn3=(RadioButton)this.findViewById(R.id.button3kaokao);
		btn4=(RadioButton)this.findViewById(R.id.button4kaokao);
		
		content=(TextView)this.findViewById(R.id.kaokaocontent);
		index=(TextView)this.findViewById(R.id.textkaokao);
		
		back=(Button)this.findViewById(R.id.kaokaoback);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(KaoKaoNiActivity.this,MenuActivity.class);
				KaoKaoNiActivity.this.startActivity(intent);
				KaoKaoNiActivity.this.finish();
				
			}
			
		});
		
		group.setOnCheckedChangeListener(new listener());
			
		new Thread(this).start();
		
	}
	
	
	private class listener implements RadioGroup.OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(RadioGroup group, int id) {
			// TODO Auto-generated method stub
			if(buttonIDS[answerID]==id)
			{
				Toast.makeText(KaoKaoNiActivity.this,"恭喜你回答正确",Toast.LENGTH_LONG).show();
				if(cur_id<length-1)
				cur_id++;
				handler.sendEmptyMessage(0);
			}
			else
			{
				Toast.makeText(KaoKaoNiActivity.this,"答错了，正确答案是"+alphas[answerID], Toast.LENGTH_LONG).show();
			}
			
		}
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		HTTPConnector conn=new HTTPConnector(HTTPConnector.ip);
		groups=conn.get_questions();
		if(groups!=null)
		{
			 length=groups.length;
			 handler.sendEmptyMessage(0);
		}
		else
		{
			handler.sendEmptyMessage(-1);
		}
		
	}

}
