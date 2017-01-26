package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class scoremanager {
	
	private score[] numbers=new score[10];//保存0-9十个数的数组
	public static int[] texIDS=new int[10];
	public static int  goals=0;//保存玩家的实际分数
	private float x_translate;
	private float y_translate;
	private float z_translate;
	private float dx;
	private float width;
	
	public scoremanager(float width,float height,float xpos,float ypos,float zpos,float dx)
	{
		
		for(int i=0;i<10;i++)
		{
			numbers[i]=new score(width,height,texIDS[i]);
		}
		this.x_translate=xpos;
		this.y_translate=ypos;
		this.z_translate=zpos;
		this.dx=dx;
		this.width=width;
		
	}
	
	public void drawself(GL10 gl)
	{
		String score_string=goals+"";
		int str_len=score_string.length()-1;
		for(int i=score_string.length()-1;i>=0;i--)
		{
			char c=score_string.charAt(i);
			gl.glPushMatrix();
			gl.glTranslatef(x_translate-(width+dx)*(str_len-i), y_translate, z_translate);
			numbers[c-'0'].drawself(gl);
			gl.glPopMatrix();
			
		}
	}
	

}
