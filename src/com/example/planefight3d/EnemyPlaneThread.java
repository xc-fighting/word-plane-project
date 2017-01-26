package com.example.planefight3d;

public class EnemyPlaneThread extends Thread{
	
	private int texid;
	public EnemyPlaneThread(int id)
	{
		this.texid=id;
	}
	

	public void run()
	{
		while(GameActivity.is_game_over==false)
		{
			for(int i=0;i<EnemyPlaneManager.enemyplanes.size();i++)
			{
				enemyplane tmp=EnemyPlaneManager.enemyplanes.get(i);
				if(tmp.is_over_boundary()||tmp.is_collide_with_player(playermanager.single_player))
				{
					if(tmp.is_collide_with_player(playermanager.single_player))
					{
						player.isvisable=false;
						playerthread.is_life_decrease=true;
						
						ExplodeManager.explode_list.add(new Explode(0.5f,0.5f,player.xpos,player.ypos,-4f));
					}
					EnemyPlaneManager.enemyplanes.remove(tmp);
				
					--i;
				}
				else
				{
					tmp.set_translate();
					tmp.fire(texid);
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
