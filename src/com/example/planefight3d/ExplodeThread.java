package com.example.planefight3d;

public class ExplodeThread extends Thread{
	
	public void run()
	{
		while(GameActivity.is_game_over==false)
		{
			
			for(int i=0;i<ExplodeManager.explode_list.size();i++)
			{
				Explode tmp=ExplodeManager.explode_list.get(i);
				if(tmp.is_ani_over()==true)
				{
					ExplodeManager.explode_list.remove(tmp);
					--i;
					player.isvisable=true;
				}
				else
				{
					tmp.set_frame_add();
				}
			}
			
			
			try
			{
				sleep(200);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}

}
