package com.example.planefight3d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class constant {
	
	
	
	
	public static char[] alpha={'a','b','c','d','e','f','g','h','i','j','k','l','m',
		'n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	public static String[]  words={"banana","tiger","intriger","excellent"};
	
	public static float[][]YArray;
	public static int GAMESTATE_GAMING=0;
	public static int GAMESTATE_PAUSE=1;
	public static int GAMESTATE_FAIL=2;
	public static int GAMESTATE_WIN=3;
	public static int land_highest=3;
	public static int rows;
	public static int cols;
	
	public static float ratio;
	public static float icon_width=0.1f;
	public static float button_radius=0.1f;
	public static int bullet_tex;
	public static float[] fire_button_area=new float[4];
	public static float edge;
	public static int feiji_tex;
	public static String feidie_filename="feidie.obj";
	public static float scale_factor=0.0005f;
	public static int yunshi_tex;
	
	
	
	public static int[] daoju_textures=new int [3];
	public static int[] daoju_pics={R.drawable.bombdaoju,R.drawable.blooddaoju,R.drawable.clock};
	public static int[] explode_pics={R.drawable.explode1,R.drawable.explode2,R.drawable.explode3,R.drawable.explode3,R.drawable.explode4,
		R.drawable.explode5,R.drawable.explode6};
	public static void init_land(Resources res)
	{
		YArray=landform(res);
		rows=YArray.length-1;
		cols=YArray[0].length-1;
	}
	public static  float[][] landform(Resources res)
	{
		Bitmap bt=BitmapFactory.decodeResource(res,R.drawable.map01);
		int cols=bt.getWidth();
		int rows=bt.getHeight();
		float [][]result=new float[rows][cols];
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
			{
				int color=bt.getPixel(j,i);
				int r=Color.red(color);
				int g=Color.green(color);
				int b=Color.blue(color);
				int h=(r+g+b)/3;
				result[i][j]=h*land_highest/255;
				
				
			}
		return result;
	}

}
