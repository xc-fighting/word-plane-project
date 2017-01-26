package com.example.planefight3d;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;


import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.TextView;


public class Word extends Activity {

	
	private TextView word;
	private TextView explain;
	private TextView tip;
	private Button play_sound;
	private Button shuxi;
	private Button bushuxi;
	private Button back;
	
	private int index=0;
    private boolean flag;
    private TextSpeaker speaker;
    
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word);
      
		shuxi = (Button) this.findViewById(R.id.shuxi);		// Ïœ§				
		bushuxi = (Button) this.findViewById(R.id.bushuxi);		//≤ª Ïœ§						
		back= (Button) this.findViewById(R.id.back);		//∑µªÿ
		play_sound=(Button)this.findViewById(R.id.put);   //≤•∑≈∞¥≈•
		shuxi.setOnClickListener(new press());
		bushuxi.setOnClickListener(new press());
		back.setOnClickListener(new press());
		play_sound.setOnClickListener(new press());
		
		word=(TextView)this.findViewById(R.id.word);
		explain=(TextView)this.findViewById(R.id.explanation);
		tip=(TextView)this.findViewById(R.id.TextView01);
		
		flag=BeiciActivity.reader.flag;
		this.init_the_texts();
		
		speaker=new TextSpeaker(this);

		
      }
	
	public void onDestroy()
	{
		super.onDestroy();
		BeiciActivity.reader.wirte_Shengci();
		//BeiciActivity.reader.write_index_of_siliuji();
		BeiciActivity.reader=null;
		speaker.Destroy();
	}
	
	public void init_the_texts()
	{
		if(index<10)
		{
			if(flag==true)
			{
				word.setText(ShengCi.siji_word_list.get(index).get_content());
				explain.setText(ShengCi.siji_word_list.get(index).get_explain());
				tip.setText((index+1)+"/10");
			}
			else
			{
				word.setText(ShengCi.liuji_word_list.get(index).get_content());
				explain.setText(ShengCi.liuji_word_list.get(index).get_explain());
				tip.setText((index+1)+"/10");
			}
			++index;
		}
	}
	
	private class press implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
				case R.id.shuxi:
				{
					Word.this.init_the_texts();
				}break;
				case R.id.bushuxi:
				{
					String str=word.getText().toString();
					ShengCi.mosheng_word_list.add(str);
					Word.this.init_the_texts();
					
				}break;
				case R.id.back:
				{
					
					Intent intent=new Intent(Word.this,BeiciActivity.class);
					Word.this.startActivity(intent);
					Word.this.finish();
				}break;
				case R.id.put:
				{
					String str=word.getText().toString();
					speaker.set_text(str);
					speaker.speak_voice();
				}break;
			}
			
		}
		
	}
	
	
	
}