package com.example.planefight3d;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;


public class TextReader {
	
	private int lines_siji=0;
	private int lines_liuji=0;
	private int index_siji;
	private int index_liuji;
	
    private Context context;
	private static final String filename="shengci.txt";
	private static final String siji_filename="sijiwords.txt";
	private static final String liuji_filename="liujiwords.txt";
	private static final String index_filename="wordindex.txt";
	
	private static final String filepath=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
			+"/planefight3d";
	
	private static final String fullpath=filepath+"/"+filename;
	private static final String index_fullpath=filepath+"/"+index_filename;
	
	public static final String full_path_of_shengci=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
			+"/planefight3d/"+filename;
	
	public boolean flag=true;//flag为true是为四级词汇，反之为六级词汇
	
public TextReader(Context _context,boolean f)
	{
		this.context=_context;
		this.init_source(fullpath,R.raw.shengci);
		this.init_source(index_fullpath, R.raw.wordindex);
		this.calculate_lines_siji();
		this.calculate_lines_liuji();
	//	this.read_index_of_siliuji();
		this.flag=f;
	}
	
	
	
	
public void init_source(String fullpath,int resid)
	{
		try
		{
			
			File dir=new File(filepath);
			
			//如果sd卡中不存在这个路径就进行创建
			if(dir.exists()==false)
			{
				dir.mkdir();
			}
			
			//如果这个路径下面没有数据库，则进行拷贝
			if(new File(fullpath).exists()==false)
			{
				InputStream is=context.getResources().openRawResource(resid);
				FileOutputStream fos=new FileOutputStream(fullpath);
				//分配8mb的空间缓存
				byte[] buffer=new byte[8192];
				int count=0;
				while((count=is.read(buffer))>0)
				{
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	



public void read_Shengci()
{
	  String full = null;
	try {
		full = this.readFileSdcardFile(fullpath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  String[] groups=full.split("-");
	  ShengCi.mosheng_word_list.removeAll(ShengCi.mosheng_word_list);
	  for(int i=0;i<groups.length;i++)
	  {
		  if(groups[i].equals("")==true)continue;
		  ShengCi.mosheng_word_list.add(groups[i]);
	  }
}





public void wirte_Shengci() 
	{
		String write_str ="";
		int size=ShengCi.mosheng_word_list.size();
		if(size!=0)
		{
			for(int i=0;i<size;i++)
			{
				write_str+=ShengCi.mosheng_word_list.get(i)+"-";
			}
		}
		try {
			this.writeFileSdcardFile(fullpath, write_str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



public void calculate_lines_siji()
	{
		InputStream in = null;
		try {
			in = context.getAssets().open(siji_filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStreamReader reader=new InputStreamReader(in);
		BufferedReader br=new BufferedReader(reader);
		
		try {
			while((br.readLine())!=null)
			{
				this.lines_siji++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	



public void calculate_lines_liuji()
	{
		InputStream in = null;
		try {
			in = context.getAssets().open(liuji_filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStreamReader reader=new InputStreamReader(in);
		BufferedReader br=new BufferedReader(reader);
		
		try {
			while((br.readLine())!=null)
			{
				this.lines_liuji++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	
public void read_siji_words()
	{
		  if(ShengCi.siji_word_list.size()!=0)ShengCi.siji_word_list.removeAll(ShengCi.siji_word_list);
	        this.read_index_of_siliuji();
	        int up_level=this.index_siji+10;
    		InputStream in = null;
			try {
				in = context.getAssets().open(siji_filename);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
    		InputStreamReader reader = null;
			try {
				reader = new InputStreamReader(in,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		BufferedReader br=new BufferedReader(reader);
    		String tmp=null;
    		try {
    			int i=1,j=this.index_siji;
				while((tmp=br.readLine())!=null&&j<=up_level)//读一行数据
				{
					if(i<this.index_siji)i++;
					else
					{
						tmp=tmp.trim();//去除每一行的首尾空格
						String[] splits=tmp.split("/");
						ShengCi temp=new ShengCi(splits[0],splits[2]);
						ShengCi.siji_word_list.add(temp);
						j++;
					}
				}
				this.index_siji=up_level;
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
    		finally
    		{
    			this.write_index_of_siliuji();
    		}
    			   	    	
	}




public void read_liuji_words()
{
	   if(ShengCi.liuji_word_list.size()!=0)ShengCi.liuji_word_list.removeAll(ShengCi.liuji_word_list);
	    this.read_index_of_siliuji();
	    int up_level=this.index_liuji+10;
		InputStream in = null;
		try {
			in =context.getAssets().open(liuji_filename);
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(in,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br=new BufferedReader(reader);
		String tmp=null;
		try {
			int i=1,j=this.index_liuji;
			while((tmp=br.readLine())!=null&&j<=up_level)//读一行数据
			{
				if(i<this.index_liuji)i++;
				else
				{
					tmp=tmp.trim();//去除每一行的首尾空格
					String[] splits=tmp.split("[ ]+");
					ShengCi temp=new ShengCi(splits[0],splits[1]);
					ShengCi.liuji_word_list.add(temp);
					j++;
				}
			}
			this.index_liuji=up_level;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		finally
		{
			this.write_index_of_siliuji();
		}
}




public String readFileSdcardFile(String fileName) throws IOException{   
		  String res="";   
		  try{   
		         FileInputStream fin = new FileInputStream(fileName);   
		  
		         int length = fin.available();   
		  
		         byte [] buffer = new byte[length];   
		         fin.read(buffer);       
		  
		         res = EncodingUtils.getString(buffer, "UTF-8");   
		  
		         fin.close();       
		        }   
		  
		        catch(Exception e){   
		         e.printStackTrace();   
		        }   
		        return res;   
		}  




	//写数据到SD中的文件  
public void writeFileSdcardFile(String fileName,String write_str) throws IOException{   
	 try{   
	  
	       FileOutputStream fout = new FileOutputStream(fileName);   
	       byte [] bytes = write_str.getBytes();   
	  
	       fout.write(bytes);   
	       fout.close();   
	     }  
	  
	      catch(Exception e){   
	        e.printStackTrace();   
	       }   
	   }   
	






public void read_index_of_siliuji()
{
	String result=null;
	try {
		 result=this.readFileSdcardFile(index_fullpath);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	String[] res=result.split("-");
	this.index_siji=Integer.parseInt(res[0]);
	this.index_liuji=Integer.parseInt(res[1]);
}




public void write_index_of_siliuji()
{
	if(this.index_siji>=this.lines_siji)this.index_siji=1;
	if(this.index_liuji>=this.lines_liuji)this.index_liuji=1;
	String str=this.index_siji+"-"+this.index_liuji;
	try {
		this.writeFileSdcardFile(index_fullpath, str);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
}
	





public void read_kaoshi_words()
{
	if(flag==true)
	this.read_siji_words();
	else
		this.read_liuji_words();
	this.write_index_of_siliuji();
	
}







	
	
}
