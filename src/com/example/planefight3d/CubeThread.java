package com.example.planefight3d;

public class CubeThread extends Thread{

	
	public void run()
	{
		while(GameActivity.is_game_over==false)
		{
			for(int i=0;i<WordCubeManager.cube_list.size();i++)
			{
				TexturedCube tmp=WordCubeManager.cube_list.get(i);
				tmp.set_tanslate();
				if(this.test_over_boundary(tmp))
				{	
					WordCubeManager.cube_list.remove(tmp);
				//便于进行显示
				scoremanager.goals++;
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
	
	public boolean test_over_boundary(TexturedCube tmp)
	{
		float[] info=new float[4];
		info=tmp.getxyzl();
		if(info[2]>=-2f)return true;
		else return false;
	}
	
	
	
}
