package com.example.planefight3d;

import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public class TextSpeaker {
	
	
	private TextToSpeech speaker;
	
	private String text;
	
	public TextSpeaker(Context context)
	{
	
		this.speaker=new TextToSpeech(context,new SpeechListener());
	}
	
	public void set_text(String content)
	{
		this.text=content;
	}
	
	public void speak_voice()
	{
		this.speaker.speak(this.text,TextToSpeech.QUEUE_FLUSH,null);
	}
	
	public void Destroy()
	{
		this.speaker.stop();
		this.speaker.shutdown();
		this.speaker=null;
	}
	
	
	private class SpeechListener implements OnInitListener
	{

		@Override
		public void onInit(int status) {
			// TODO Auto-generated method stub
			if(status==TextToSpeech.SUCCESS)
			{
				int result=speaker.setLanguage(Locale.ENGLISH);
				if(result==TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED)
				{
					Log.e("lanageTag", "not use");
				}
			}
			
		}
		
	}
	

}
