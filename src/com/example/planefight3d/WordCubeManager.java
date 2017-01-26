package com.example.planefight3d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class WordCubeManager {
	
	public static int[] alpha_ids=new int [26];
	public static ArrayList<TexturedCube> cube_list=new ArrayList<TexturedCube>();
	
	public static void drawself(GL10 gl)
	{
		for(int i=0;i<cube_list.size();i++)
		{
			TexturedCube tmp=cube_list.get(i);
			tmp.drawself(gl);
		}
	}
	
	//根据给定的字母产生相应的单词块,gamerule调用
	public static void generate_words(char ch,float vz,float x,float y,float z)
	{
		//float vx,float vy,float vz,float x,float y,float z,int texid,float len
		int texid=alpha_ids[ch-'a'];
		TexturedCube tmp=new TexturedCube(0,0,vz,x,y,z,texid,0.2f);
		tmp.set_cube_alpha(ch);
		cube_list.add(tmp);
	}

}
