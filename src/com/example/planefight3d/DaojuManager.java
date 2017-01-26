package com.example.planefight3d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DaojuManager {
	private float dx;
	private float zpos;
    public DaojuManager(float dx,float zpos)
    {
    	this.dx=dx;
    	this.zpos=zpos;
    }
	public static ArrayList<Daoju> DaojuList=new ArrayList<Daoju>();
	public void drawself(GL10 gl)
	{
		
		for(int i=0;i<DaojuList.size();i++)
		{
			gl.glPushMatrix();
			Daoju tmp=DaojuList.get(i);
			gl.glTranslatef(-1+tmp.width/2+(tmp.width+dx)*i,-(1/constant.ratio)+tmp.height/2,zpos);
			tmp.drawself(gl);
			gl.glPopMatrix();
		}
		
	}
	public  void set_touch_area_of_all()
	{
		for(int i=0;i<DaojuList.size();i++)
		{
			Daoju tmp=DaojuList.get(i);
			tmp.touch_areas[0]=GameActivity.width*i*(tmp.width+dx)/2;//left
			tmp.touch_areas[1]=GameActivity.width*(i*(tmp.width+dx)+tmp.width)/2;//right
			tmp.touch_areas[2]=GameActivity.height*(1-tmp.height/(2*constant.edge));//top
			tmp.touch_areas[3]=GameActivity.height;//bottom
			
		}
	}
	
	public void listen_touch(float x,float y)
	{
		for(int i=0;i<DaojuList.size();i++)
		{
			Daoju tmp=DaojuList.get(i);
			if((x>=tmp.touch_areas[0]&&x<=tmp.touch_areas[1])&&(y>=tmp.touch_areas[2]&&y<=tmp.touch_areas[3]))
			{
				switch(tmp.type)
				{
					case 0:
					{
						//bomb道具则清空
						EnemyPlaneManager.enemyplanes.removeAll(EnemyPlaneManager.enemyplanes);
						enemymanager.enemy_group.removeAll(enemymanager.enemy_group);
					}break;
					case 1:
					{
						//生命道具加生命
						
						lifemanager.life_add_action();
						
					}break;
					case 2:
					{
						//时间加10s
						Timer.time_value+=10;
					}break;
				}
				DaojuList.remove(tmp);
			}
				
		}
	}
	
	
	
}
