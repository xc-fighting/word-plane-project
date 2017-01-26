package com.example.planefight3d;

import java.util.ArrayList;



public class ShengCi{
	
	private String content;
	private String explanation;
	
	public static ArrayList<ShengCi>siji_word_list=new ArrayList<ShengCi>();
	public static ArrayList<ShengCi>liuji_word_list=new ArrayList<ShengCi>();
	public static ArrayList<String>mosheng_word_list=new ArrayList<String>();
	
	public ShengCi(String content,String explanation)
	{
		this.content=content;
		this.explanation=explanation;
	}
	
	public String convert_to_string()
	{
		String str="单词内容:"+content+"\n"+"中文解释:"+explanation;
		return str;
	}
	
	public String get_content()
	{
		return this.content;
	}
	
	public String get_explain()
	{
		return this.explanation;
	}

}
