package com.example.planefight3d;
//���µ���λ�õ��߳�
public class enemythread extends Thread{
	
	public void run()
	{
		while(GameActivity.is_game_over==false)
		{
			for(int i=0;i<enemymanager.enemy_group.size();i++)
			{
				enemy tmp=enemymanager.enemy_group.get(i);
				tmp.calculate_vxy();
				tmp.set_translate();
				
				
			}
			try
			{
				sleep(100);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	
}
