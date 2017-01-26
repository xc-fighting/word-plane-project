package com.example.planefight3d;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class BeiciActivity extends Activity{
	
	
	public static TextReader reader;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
   
		Button button1 = (Button) this.findViewById(R.id.four);		//4¼¶
		button1.setOnClickListener(new View.OnClickListener(){		
			@Override
			public void onClick(View v) {		
				reader=new TextReader(BeiciActivity.this,true);
				reader.read_kaoshi_words();
				reader.read_Shengci();
				Intent intent = new Intent(BeiciActivity.this,Word.class);
				BeiciActivity.this.startActivity(intent);
				BeiciActivity.this.finish();
			}
		});
		
		Button button2 = (Button) this.findViewById(R.id.six);		//6¼¶
		button2.setOnClickListener(new View.OnClickListener(){		
			@Override
			public void onClick(View v) {	
				reader=new TextReader(BeiciActivity.this,false);
				reader.read_kaoshi_words();
				reader.read_Shengci();
				Intent intent = new Intent(BeiciActivity.this,Word.class);
				BeiciActivity.this.startActivity(intent);
				BeiciActivity.this.finish();
			}
		});
		Button button3 = (Button) this.findViewById(R.id.back);		//·µ»Ø
		button3.setOnClickListener(new View.OnClickListener(){		
			@Override
			public void onClick(View v) {						
				
				Intent intent = new Intent(BeiciActivity.this,MenuActivity.class);
				BeiciActivity.this.startActivity(intent);
				BeiciActivity.this.finish();
			}
		});
	}

}
