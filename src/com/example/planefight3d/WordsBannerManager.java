package com.example.planefight3d;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class WordsBannerManager {
	
	public static int[] texture_id_list=new int [26];	
	public static boolean is_blank_visible=false;
	public static boolean is_word_show=false;
	
	private static char[] blank_ch;//空白的字符
	private static int[] index;//扣去字母的位置
	private static WordsBanner[][] alpha_list;
	
	private float width;//图片的宽度
	private float dx;//图片之间的间隔
	private float xpos;//x位置
	private float ypos;//y位置
	private float zpos;//z位置
	
	private int wenhao_tex;	//问号的纹理id
	
	
	
	
	//private String word_value;//单词字符串的值
	
	
	
	
	
	
	public WordsBannerManager(float width,int wenhao,float dx,float x,float y,float z)
	{
		this.width=width;
		this.dx=dx;
		
		texture_id_list=WordCubeManager.alpha_ids;	
		
		this.wenhao_tex=wenhao;
		
		this.xpos=x;
		this.ypos=y;
		this.zpos=z;
		
		this.init_all_alpha_list();
		
	}
	
/*	public void set_words(String value)
	{
		this.set_word_value(value);
		this.set_new_word();
	}
	
	//每次通过gamerule 调用设置单词的值
	public void set_word_value(String value)
	{
		this.word_value=value;
	}
*/	
	
	public void init_all_alpha_list()
	{
		
		is_blank_visible=false;//将扣去区域设置为不可见
		
		
		int arr_size=constant.words.length;
		alpha_list=new WordsBanner[arr_size][];
		blank_ch=new char[arr_size];
		index=new int [arr_size];
		for(int i=0;i<arr_size;i++)
		{
			alpha_list[i]=new WordsBanner[constant.words[i].length()];
			
			Random seed=new Random();
		    index[i]=seed.nextInt(constant.words[i].length());
			blank_ch[i]=constant.words[i].charAt(index[i]);
			
			for(int j=0;j<constant.words[i].length();j++)
			{
				if(j==index[i])
				{
					alpha_list[i][j]=new WordsBanner(this.wenhao_tex,width);
				}
				else
				{
					char ch=constant.words[i].charAt(j);
					alpha_list[i][j]=new WordsBanner(WordsBannerManager.texture_id_list[ch-'a'],width);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	//设置词汇字串
/*	public void set_new_word()
	{
		is_blank_visible=false;//将扣去区域设置为不可见
		is_word_show=true;
		
		int len=this.word_value.length();//获取字符串的长度
		
		Random seed=new Random();//产生随机数种子
		
		this.index=seed.nextInt(len);//记录使用问号的字母位置
		
		blank_ch=this.word_value.charAt(this.index);//记录扣去的字母的值
		
		alpha_list=new WordsBanner[len];//设置单词长度的数组
		
		
		for(int i=0;i<len;i++)//初始化单词字母的序列
		{
			if(i==this.index)
			{
				alpha_list[this.index]=new WordsBanner(this.wenhao_tex,width);
			}
			else
			{
				char ch=this.word_value.charAt(i);
				alpha_list[i]=new WordsBanner(WordsBannerManager.texture_id_list[ch-'a'],this.width);
			}
		}
	}*/
	
	public static char get_blank_alpha()//获取挖空词汇来进行判断
	{
		return blank_ch[GameRule.word_index];
	}
	
	public void drawself(GL10 gl)
	{
		if(WordsBannerManager.is_word_show==true)
		{
		//	this.is_blank_show();
			gl.glPushMatrix();
			gl.glTranslatef(xpos, ypos, zpos);
			for(int i=0;i<alpha_list[GameRule.word_index].length;i++)
			{
				gl.glPushMatrix();
				gl.glTranslatef((dx+width)*i,0,0);
				alpha_list[GameRule.word_index][i].drawself(gl);
				gl.glPopMatrix();
			}
			gl.glPopMatrix();
		}
	}
	
	public  void is_blank_show()
	{
		if(WordsBannerManager.is_blank_visible==true)
		{
			int tex=WordsBannerManager.texture_id_list[blank_ch[GameRule.word_index]-'a'];
			alpha_list[GameRule.word_index][index[GameRule.word_index]]=new WordsBanner(tex,width);
		}
	}

}
