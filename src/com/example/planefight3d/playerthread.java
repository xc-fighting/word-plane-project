package com.example.planefight3d;



public class playerthread extends Thread{
//判断玩家是否被击中的线程
	private player single;
	public static boolean is_life_decrease=false;
	public playerthread(player p)
	{
		this.single=p;
		
	}
	public void run()
	{
		while(GameActivity.is_game_over==false)
		{
			for(int i=0;i<bulletmanager.enemy_bullets.size();i++)
			{
				bullet bomb=bulletmanager.enemy_bullets.get(i);
				float []info=bomb.getxyzr();
				boolean flag=single.iscollide(info, info[3]);
				if(flag==true)
				{
					player.isvisable=false;
					//生命值减少
					is_life_decrease=true;
					bulletmanager.enemy_bullets.remove(bomb);
					ExplodeManager.explode_list.add(new Explode(0.5f,0.5f,player.xpos,player.ypos,-4f));
					--i;
					    break;
				}
			}
			for(int j=0;j<enemymanager.enemy_group.size();j++)
			{
				enemy tmp=enemymanager.enemy_group.get(j);
				float[] position=new float[4];
				position=tmp.getXYZR();
				boolean flag=single.iscollide(position, position[3]);
				if(flag==true)
				{
					player.isvisable=false;
					is_life_decrease=true;
					enemymanager.enemy_group.remove(tmp);
					ExplodeManager.explode_list.add(new Explode(0.5f,0.5f,player.xpos,player.ypos,-4f));
					--j;
					break;
				}
			}
			try
			{
				sleep(30);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	
}
