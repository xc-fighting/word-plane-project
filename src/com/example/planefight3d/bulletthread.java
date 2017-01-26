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
	
	
	
	
	
	
	//��ײ�ж���ط���
	
	//�ж��ӵ��Ƿ�Խ�磬���������ӵ����Լ��ӵ����ж�
	public boolean test_over_boundary(bullet b)
	{
		if(b.getz()>=0||b.getz()<=-30)return true;
		else return false;
	}
	
	//�ж��Ƿ����ʯ����ս����������ײ
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
	//�ж��Ƿ����ʯ���˷�������ײ
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
	//�ж��Ƿ�����˵�����ĸ����
	public boolean test_collide_words(bullet b)
	{
		for(int i=0;i<WordCubeManager.cube_list.size();i++)
		{
			TexturedCube cb=WordCubeManager.cube_list.get(i);
			//��ȡ�������λ����Ϣ
			float[] infos=new float[4];
			infos=cb.getxyzl();
			float cx=infos[0];
			float cy=infos[1];
			float cz=infos[2];
			float c_len=infos[3];
			//��ȡ�����ӵ���λ����Ϣ
			infos=b.getxyzr();
			float bx=infos[0];
			float by=infos[1];
			float bz=infos[2];
			float b_radius=infos[3];
			//�ж��Ƿ�������ײ
			//AABB�㷨��������,���ȶ����Χ�б߽�,������������������ת�������¼򻯺ͽ��Ʋ���
			float cx_min,cx_max,cy_min,cy_max,cz_min,cz_max;
			float bx_min,bx_max,by_min,by_max,bz_min,bz_max;
			//�ֱ���м���
			//���������������ر���
			cx_min=cx-c_len*0.5f;
			cy_min=cy-c_len*0.5f;
			cz_min=cz-c_len*0.5f;
			
			cx_max=cx+c_len*0.5f;
			cy_max=cy+c_len*0.5f;
			cz_max=cz+c_len*0.5f;
			//����������ر���
			bx_min=bx-b_radius*0.5f;
			by_min=by-b_radius*0.5f;
			bz_min=bz-b_radius*0.5f;
			
			bx_max=bx+b_radius*0.5f;
			by_max=by+b_radius*0.5f;
			bz_max=bz+b_radius*0.5f;
			
			//��ʽ��ʼ������ײ���ж�
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
	
	
	
	//����aabb��Χ���жϵ���ط���
	public float cal_over_one(float amax,float amin,float bmax,float bmin)
	{
		float minMax=0f;//�����ϴ�ֵ��С���Ǹ�
		float maxMin=0f;//������Сֵ�д���Ǹ�
		if(amax<bmax)//�����zֵ���о����൱��a��b��ǰ�ߣ���������ϵ���棩
		{
			minMax=amax;
			maxMin=bmin;
		}
		else
		{
			minMax=bmax;
			maxMin=amin;
		}
		if(minMax>maxMin)return minMax-maxMin;//˵���н����������ཻ���ֳ���
		else return 0;//�����ʾû�н���
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
