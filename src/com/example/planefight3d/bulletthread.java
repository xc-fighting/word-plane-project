package com.example.planefight3d;

public class bulletthread extends Thread{
	
	
	public void run()
	{
		while(GameActivity.is_game_over==false)
		{
			for(int i=0;i<bulletmanager.player_bullets.size();i++)
			{
				bullet b=bulletmanager.player_bullets.get(i);
				b.settrranslate();
				if(this.test_collide_enemy(b)||this.test_over_boundary(b)||this.test_collide_words(b)||this.test_collide_enemyPlanes(b))
					{
					
					   
					   bulletmanager.player_bullets.remove(b);
					   i--;
					 
					}
			}
			for(int j=0;j<bulletmanager.enemy_bullets.size();j++)
			{
				bullet b=bulletmanager.enemy_bullets.get(j);
				b.settrranslate();
				if(this.test_over_boundary(b))
					{
					   bulletmanager.enemy_bullets.remove(b);
					   j--;
					}
			}
			try
			{
				sleep(1000/60);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	//碰撞判断相关方法
	
	//判断子弹是否越界，包括敌人子弹和自己子弹的判断
	public boolean test_over_boundary(bullet b)
	{
		if(b.getz()>=0||b.getz()<=-30)return true;
		else return false;
	}
	
	//判断是否和陨石敌人战机发生了碰撞
	public boolean test_collide_enemyPlanes(bullet b)
	{
		for(int i=0;i<EnemyPlaneManager.enemyplanes.size();i++)
		{
			enemyplane tmp=EnemyPlaneManager.enemyplanes.get(i);
			boolean flag=tmp.iscollide(b.getxyzr(),b.getxyzr()[3]);
			if(flag==true)
			{
				
				Explode ex=new Explode(0.2f,0.2f,tmp.get_position()[0],tmp.get_position()[1],tmp.get_position()[2]);
				ExplodeManager.explode_list.add(ex);
				
				
				EnemyPlaneManager.enemyplanes.remove(tmp);
				scoremanager.goals++;
				return true;
			}
		}
		return false;
	}
	//判断是否和陨石敌人发生了碰撞
	public boolean test_collide_enemy(bullet b)
	{
		
		for(int i=0;i<enemymanager.enemy_group.size();i++)
		{
			enemy temp_enemy=enemymanager.enemy_group.get(i);
			boolean flag=temp_enemy.isdestroyed(b.getxyzr(),b.getxyzr()[3]);
			if(flag==true)
				{
				//float width,float height,float x,float y,float z
				  Explode tmp=new Explode(0.2f,0.2f,temp_enemy.getXYZR()[0],temp_enemy.getXYZR()[1],temp_enemy.getXYZR()[3]);
				  ExplodeManager.explode_list.add(tmp);
				  
				  enemymanager.enemy_group.remove(temp_enemy);
				  scoremanager.goals++;
				  
				  return true;
				}
			
		}
		return false;
	}
	//判断是否打中了单词字母方块
	public boolean test_collide_words(bullet b)
	{
		for(int i=0;i<WordCubeManager.cube_list.size();i++)
		{
			TexturedCube cb=WordCubeManager.cube_list.get(i);
			//获取正方体的位置信息
			float[] infos=new float[4];
			infos=cb.getxyzl();
			float cx=infos[0];
			float cy=infos[1];
			float cz=infos[2];
			float c_len=infos[3];
			//获取球形子弹的位置信息
			infos=b.getxyzr();
			float bx=infos[0];
			float by=infos[1];
			float bz=infos[2];
			float b_radius=infos[3];
			//判断是否发生了碰撞
			//AABB算法进行试验,首先定义包围盒边界,这里忽略了正方体的旋转，进行下简化和近似操作
			float cx_min,cx_max,cy_min,cy_max,cz_min,cz_max;
			float bx_min,bx_max,by_min,by_max,bz_min,bz_max;
			//分别进行计算
			//首先是正方体的相关变量
			cx_min=cx-c_len*0.5f;
			cy_min=cy-c_len*0.5f;
			cz_min=cz-c_len*0.5f;
			
			cx_max=cx+c_len*0.5f;
			cy_max=cy+c_len*0.5f;
			cz_max=cz+c_len*0.5f;
			//其次是球的相关变量
			bx_min=bx-b_radius*0.5f;
			by_min=by-b_radius*0.5f;
			bz_min=bz-b_radius*0.5f;
			
			bx_max=bx+b_radius*0.5f;
			by_max=by+b_radius*0.5f;
			bz_max=bz+b_radius*0.5f;
			
			//正式开始进行碰撞的判断
			float[]over=new float[3];
			over=this.cal_over_all(cx_min, cy_min, cz_min, cx_max, cy_max, cz_max, bx_min, by_min, bz_min, bx_max, by_max, bz_max);
			if(over[0]>0.05f&&over[1]>0.05f&&over[2]>0.05f)
			  {
				// WordCubeManager.cube_list.remove(cb);
				
				 char tmp=cb.get_cube_alpha();
				 if(tmp==WordsBannerManager.get_blank_alpha())
				   {
					   WordsBannerManager.is_blank_visible=false;
					   WordsBannerManager.is_word_show=false;
					   GameRule.is_generate_new_daoju=true;
					   GameRule.word_index++;
					
				   }
				 else
				 {
					 WordsBannerManager.is_blank_visible=false;
					 WordsBannerManager.is_word_show=false;
					 GameRule.is_generate_new_daoju=false;
					 GameRule.word_index++;
				 }
				  WordCubeManager.cube_list.removeAll(WordCubeManager.cube_list);
				  return true;
			  }			
		}
		return false;
	}
	
	
	
	//进行aabb包围盒判断的相关方法
	public float cal_over_one(float amax,float amin,float bmax,float bmin)
	{
		float minMax=0f;//两个较大值中小的那个
		float maxMin=0f;//两个较小值中大的那个
		if(amax<bmax)//如果用z值进行举例相当于a在b的前边（右手坐标系下面）
		{
			minMax=amax;
			maxMin=bmin;
		}
		else
		{
			minMax=bmax;
			maxMin=amin;
		}
		if(minMax>maxMin)return minMax-maxMin;//说明有交集，返回相交部分长度
		else return 0;//否则表示没有交集
	}
	
	public float[] cal_over_all(float cx_min,float cy_min,float cz_min,float cx_max,float cy_max,float cz_max,
			float bx_min,float by_min,float bz_min,float bx_max,float by_max,float bz_max)
	{
		float xover=this.cal_over_one(cx_max,cx_min,bx_max,bx_min);
		float yover=this.cal_over_one(cy_max,cy_min,by_max,by_min);
		float zover=this.cal_over_one(cz_max, cz_min, bz_max, bz_min);
		return new float[] {xover,yover,zover};
	}
	


}
