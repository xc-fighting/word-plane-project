package com.example.planefight3d;

public class TimerThread extends Thread{
	
	private Timer timer_entity;
	
	public static int step=0;
	
	public TimerThread(Timer timer)
	{
		this.timer_entity=timer;
	}
	public void run()
	{
		while(timer_entity.get_time()>0&&GameActivity.is_game_over==false)
		{
			
			
			if(Timer.time_value==GameRule.init_time-1-step*GameRule.dt)
			{
				WordsBannerManager.is_word_show=true;
				
				step++;
				GameRule.gen_new_words_cube();
			}
			
			timer_entity.time_decrease();
			
			
			
			try
			{
				sleep(1000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(timer_entity.get_time()<=0)
		{
			Timer.time_value=0;
		}
	}

}
