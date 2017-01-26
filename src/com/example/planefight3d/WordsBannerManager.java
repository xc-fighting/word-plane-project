package com.example.planefight3d;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class WordsBannerManager {
	
	public static int[] texture_id_list=new int [26];	
	public static boolean is_blank_visible=false;
	public static boolean is_word_show=false;
	
	private static char[] blank_ch;//�հ׵��ַ�
	private static int[] index;//��ȥ��ĸ��λ��
	private static WordsBanner[][] alpha_list;
	
	private float width;//ͼƬ�Ŀ��
	private float dx;//ͼƬ֮��ļ��
	private float xpos;//xλ��
	private float ypos;//yλ��
	private float zpos;//zλ��
	
	private int wenhao_tex;	//�ʺŵ�����id
	
	
	
	
	//private String word_value;//�����ַ�����ֵ
	
	
	
	
	
	
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
	
	//ÿ��ͨ��gamerule �������õ��ʵ�ֵ
	public void set_word_value(String value)
	{
		this.word_value=value;
	}
*/	
	
	public void init_all_alpha_list()
	{
		
		is_blank_visible=false;//����ȥ��������Ϊ���ɼ�
		
		
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
	
	
	
	
	
	
	
	
	
	
	//���ôʻ��ִ�
/*	public void set_new_word()
	{
		is_blank_visible=false;//����ȥ��������Ϊ���ɼ�
		is_word_show=true;
		
		int len=this.word_value.length();//��ȡ�ַ����ĳ���
		
		Random seed=new Random();//�������������
		
		this.index=seed.nextInt(len);//��¼ʹ���ʺŵ���ĸλ��
		
		blank_ch=this.word_value.charAt(this.index);//��¼��ȥ����ĸ��ֵ
		
		alpha_list=new WordsBanner[len];//���õ��ʳ��ȵ�����
		
		
		for(int i=0;i<len;i++)//��ʼ��������ĸ������
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
	
	public static char get_blank_alpha()//��ȡ�ڿմʻ��������ж�
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
