package com.example.planefight3d;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;



public class HTTPConnector {
	
	private static String ip_addr;
	private static String http_path;
	
	private HttpPost post_request;
	private HttpResponse response;
	
	private String str_http_entity;
	
	
	public static String ip;
	public static boolean islogin=false;
	public static boolean istest=false;
	public static String username;
	public static String password;	
	
	
	
	
	
	public HTTPConnector(String ip)
	{
		ip_addr=ip;
		http_path="http://"+ip_addr+":8080/";
	}
	
	public boolean upload_shengci()
	{
		String addr=http_path+"zendworks/wordsplane_proj/mobile/"+"upload_shengci.php";
		String end = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "******";
	    try
	    {
	      URL url = new URL(addr);
	      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	      // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
	      // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
	      httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
	      // 允许输入输出流
	      httpURLConnection.setDoInput(true);
	      httpURLConnection.setDoOutput(true);
	      httpURLConnection.setUseCaches(false);
	      // 使用POST方法
	      httpURLConnection.setRequestMethod("POST");
	      httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
	      httpURLConnection.setRequestProperty("Charset", "UTF-8");
	      httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
	 
	      DataOutputStream dos = new DataOutputStream(
	          httpURLConnection.getOutputStream());
	      dos.writeBytes(twoHyphens + boundary + end);
	      dos.writeBytes("Content-Disposition: form-data; name=\"files\"; filename=\""
	          + TextReader.full_path_of_shengci.substring(TextReader.full_path_of_shengci.lastIndexOf("/") + 1)
	          + "\""
	          + end);
	      dos.writeBytes(end);
	 
	      FileInputStream fis = new FileInputStream(TextReader.full_path_of_shengci);
	      byte[] buffer = new byte[8192]; // 8k
	      int count = 0;
	      // 读取文件
	      while ((count = fis.read(buffer)) != -1)
	      {
	        dos.write(buffer, 0, count);
	      }
	      fis.close();
	      
	      dos.write(("username="+username).getBytes());
	      dos.writeBytes(end);
	      
	     
	      dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
	     
	      dos.flush();
	 
	      InputStream is = httpURLConnection.getInputStream();
	      InputStreamReader isr = new InputStreamReader(is, "utf-8");
	      BufferedReader br = new BufferedReader(isr);
	      String result = br.readLine();
	      dos.close();
	      is.close();
	      if(result.equals("success"))return true;
	      else return false;

	     
	 
	    } catch (Exception e)
	    {
	      e.printStackTrace();
	     return false;
	    }
	  
	}
	
	public void set_wordnum(PaihangActivity paihang)
	{
		String addr=http_path+"zendworks/wordsplane_proj/mobile/"+"getwordnum.php";
		this.post_request=new HttpPost(addr);
		
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("get_wordnum",HTTPConnector.username));
				
		try
		{
			this.post_request.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
			this.response=new DefaultHttpClient().execute(post_request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				this.str_http_entity=EntityUtils.toString(response.getEntity());
				if(this.str_http_entity!=null)
				{
					paihang.content=this.str_http_entity;
					String[] splits=this.str_http_entity.split("\\|");
					int len=splits.length;
					paihang.wordnums=new String[len];
					paihang.times=new String[len];
					paihang.usernames=new String[len];
					for(int i=0;i<=len-1;i++)
					{
					//	if(splits[len-1].contains("::"))
					//	{
					//	 String[] strs=splits[len-1].split("::");
					//	 paihang.usernames[len-1]=strs[0];
					//	 paihang.scores[len-1]=strs[1];
					//	 paihang.times[len-1]=strs[2];
					//	}
						
							String[] strs=splits[i].split("~");
							 paihang.usernames[i]=strs[0];
							 paihang.times[i]=strs[1];
							 paihang.wordnums[i]=strs[2];
						
					}
			    }
				
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
	
	
	
	public void set_paihang(PaihangActivity paihang)
	{
		String addr=http_path+"zendworks/wordsplane_proj/mobile/"+"getgrade.php";
		this.post_request=new HttpPost(addr);
		
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("username_grade",HTTPConnector.username));
				
		try
		{
			this.post_request.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
			this.response=new DefaultHttpClient().execute(post_request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				this.str_http_entity=EntityUtils.toString(response.getEntity());
				if(this.str_http_entity!=null)
				{
					paihang.content=this.str_http_entity;
					String[] splits=this.str_http_entity.split("\\|");
					int len=splits.length;
					paihang.scores=new String[len];
					paihang.times=new String[len];
					paihang.usernames=new String[len];
					paihang.wordnums=new String[len];
					for(int i=0;i<=len-1;i++)
					{
					//	if(splits[len-1].contains("::"))
					//	{
					//	 String[] strs=splits[len-1].split("::");
					//	 paihang.usernames[len-1]=strs[0];
					//	 paihang.scores[len-1]=strs[1];
					//	 paihang.times[len-1]=strs[2];
					//	}
						
							String[] strs=splits[i].split("~");
							 paihang.usernames[i]=strs[0];
							 paihang.scores[i]=strs[1];
							 paihang.times[i]=strs[2];
						     paihang.wordnums[i]=strs[3];
					}
			    }
				
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
	}
	
	
	public boolean send_grade(String name,int grade,String time)
	{
		String addr=http_path+"zendworks/wordsplane_proj/mobile/"+"insertgrade.php";
		this.post_request=new HttpPost(addr);
		
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("username_grade",name));
		param.add(new BasicNameValuePair("grade",grade+""));
		param.add(new BasicNameValuePair("time_grade",time));
		param.add(new BasicNameValuePair("wordnum",10+""));
		
		try
		{
			this.post_request.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
			this.response=new DefaultHttpClient().execute(post_request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				this.str_http_entity=EntityUtils.toString(response.getEntity());
				if(this.str_http_entity.equals("success!"))return true;
				if(this.str_http_entity.equals("fail!")) return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	public String get_liuyan(String name)
	{
		String addr=http_path+"zendworks/wordsplane_proj/mobile/"+"getliuyan.php";
		this.post_request=new HttpPost(addr);
		
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("getliuyan_name",name));
		
		
		try
		{
			this.post_request.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
			this.response=new DefaultHttpClient().execute(post_request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				this.str_http_entity=EntityUtils.toString(response.getEntity());
				if(this.str_http_entity.equals("get fail"))return "no record exist";
				else return this.str_http_entity;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
	public int connect_us(String username,String content,String time)
	{
		String addr=http_path+"zendworks/wordsplane_proj/mobile/"+"connectus.php";
		this.post_request=new HttpPost(addr);
		
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("user_lianxi",username));
		param.add(new BasicNameValuePair("content_lianxi",content));
		param.add(new BasicNameValuePair("time_lianxi",time));
		
		try
		{
			this.post_request.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
			this.response=new DefaultHttpClient().execute(post_request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				this.str_http_entity=EntityUtils.toString(response.getEntity());
				if(str_http_entity.equals("success"))return 4;
				if(str_http_entity.equals("fail"))return -4;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -2;
		}
		return -1;
	}
	
	public int register_user(String name,String pwd)
	{
		String login_addr=http_path+"zendworks/wordsplane_proj/mobile/"+"register.php";
		this.post_request=new HttpPost(login_addr);
		
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("user_name_reg",name));
		param.add(new BasicNameValuePair("password_reg",pwd));
		
		try
		{
			this.post_request.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
			this.response=new DefaultHttpClient().execute(post_request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				this.str_http_entity=EntityUtils.toString(response.getEntity());
				if(str_http_entity.equals("register success"))return 3;
				if(str_http_entity.equals("register fail"))return -3;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -2;
		}
		return -1;
	}
	
	
	
	
	public int login_server(String username,String password)
	{
		String login_addr=http_path+"zendworks/wordsplane_proj/mobile/"+"login.php";
		this.post_request=new HttpPost(login_addr);
		
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("user_name_login",username));
		param.add(new BasicNameValuePair("password_login",password));
		
		try
		{
			this.post_request.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
			this.response=new DefaultHttpClient().execute(post_request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				this.str_http_entity=EntityUtils.toString(response.getEntity());
				if(str_http_entity.equals("success"))return 1;
				if(str_http_entity.equals("fail"))return 0;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -2;
		}
		return -1;
		
	}
	
	
	public int linktest()
	{
		try
		{
		String login_addr=http_path+"zendworks/wordsplane_proj/"+"index.php";
		this.post_request=new HttpPost(login_addr);
		response =new DefaultHttpClient().execute(post_request);
		int code = response.getStatusLine().getStatusCode();
		if(code==200)return 2;
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -2;
		}
		return -1;
	}
	
	
	
	public Questions[] get_questions()
	{
		String addr=http_path+"zendworks/wordsplane_proj/mobile/"+"getquestions.php";
		this.post_request=new HttpPost(addr);
		
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("stu_name",username));
		
		Questions[] question_group=null;
		
		try
		{
			this.post_request.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
			this.response=new DefaultHttpClient().execute(post_request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				this.str_http_entity=EntityUtils.toString(response.getEntity());
				
				if(this.str_http_entity.equals("fail!")) return null;
				else
				{
					String[] strs=this.str_http_entity.split("-");
					
					int size=strs.length;
					question_group=new Questions[size];
					
					for(int i=0;i<size;i++)
					{
						String[] tmp=strs[i].split("~");
						question_group[i]=new Questions(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4],Integer.parseInt(tmp[5]));
					}
					return question_group;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
			
		}
		return null;
	
		
	}
	
	

}
