package com.example.planefight3d;

public class Questions {
   
	public String content;
	public String[] questions;
	public int answer;
	
	public Questions(String content,String ques1,String ques2,String ques3,String ques4,int ans)
	{
		this.content=content;
		questions=new String[4];
		questions[0]=ques1;
		questions[1]=ques2;
		questions[2]=ques3;
		questions[3]=ques4;
		this.answer=ans;
	}
	
	
}
