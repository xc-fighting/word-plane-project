package com.example.planefight3d;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.content.res.Resources;

public class loadmodels {
   public  ArrayList<Float> init_vertexs_from_obj;
   public ArrayList<Float> final_vertexs_from_obj;
   public ArrayList<Float> init_texture_from_obj;
   public ArrayList<Float> final_texture_form_obj;
   //为对象提供AABB包围盒信息
   public float xmin,xmax;
   public float ymin,ymax;
   public float zmin,zmax;
    public FloatBuffer vertexs_obj;
    public FloatBuffer texture_obj;
    public int vcounts;
    public loadmodels()
    {
    	this.init_vertexs_from_obj=new ArrayList<Float>();
    	this.init_texture_from_obj=new ArrayList<Float>();
    	this.final_texture_form_obj=new ArrayList<Float>();
    	this.final_vertexs_from_obj=new ArrayList<Float>();
    }
    public void loadfromfile(String filename,Resources res)
    {
    	try
    	{
    		InputStream in=res.getAssets().open(filename);
    		InputStreamReader reader=new InputStreamReader(in);
    		BufferedReader br=new BufferedReader(reader);
    		String tmp=null;
    		while((tmp=br.readLine())!=null)//读一行数据
    		{
    			String[] tmps=tmp.split("[ ]+");//通过空格进行划分
    			if(tmps[0].trim().equals("v"))
    			{
    				this.init_vertexs_from_obj.add(Float.parseFloat(tmps[1]));
    				this.init_vertexs_from_obj.add(Float.parseFloat(tmps[2]));
    				this.init_vertexs_from_obj.add(Float.parseFloat(tmps[3]));
    			}
    			else if(tmps[0].trim().equals("vt"))
    			{
    				this.init_texture_from_obj.add(Float.parseFloat(tmps[1]));
    				this.init_texture_from_obj.add(Float.parseFloat(tmps[2]));
    			}
    			else if(tmps[0].trim().equals("f"))
    			{
    				int index;
    				//加载第一个点的信息
    				index=Integer.parseInt(tmps[1].split("/")[0])-1;
    				float x0=this.init_vertexs_from_obj.get(3*index);
    				float y0=this.init_vertexs_from_obj.get(3*index+1);
    				float z0=this.init_vertexs_from_obj.get(3*index+2);
    				this.final_vertexs_from_obj.add(x0);
    				this.final_vertexs_from_obj.add(y0);
    				this.final_vertexs_from_obj.add(z0);
    				
    				//加载第二个点的信息
    				index=Integer.parseInt(tmps[2].split("/")[0])-1;
    				float x1=this.init_vertexs_from_obj.get(3*index);
    				float y1=this.init_vertexs_from_obj.get(3*index+1);
    				float z1=this.init_vertexs_from_obj.get(3*index+2);
    				this.final_vertexs_from_obj.add(x1);
    				this.final_vertexs_from_obj.add(y1);
    				this.final_vertexs_from_obj.add(z1);
    				
    				//加载第三个点的信息
    				index=Integer.parseInt(tmps[3].split("/")[0])-1;
    				float x2=this.init_vertexs_from_obj.get(3*index);
    				float y2=this.init_vertexs_from_obj.get(3*index+1);
    				float z2=this.init_vertexs_from_obj.get(3*index+2);
    				this.final_vertexs_from_obj.add(x2);
    				this.final_vertexs_from_obj.add(y2);
    				this.final_vertexs_from_obj.add(z2);
    				
    				//加载纹理信息
    						
    				int texindex;//每个三角形的三个纹理索引
    				
    				texindex=Integer.parseInt(tmps[1].split("/")[1])-1;
    				float s0=this.init_texture_from_obj.get(2*texindex);
    				float t0=this.init_texture_from_obj.get(2*texindex+1);
    				this.final_texture_form_obj.add(s0);
    				this.final_texture_form_obj.add(t0);
    				
    				texindex=Integer.parseInt(tmps[2].split("/")[1])-1;
    				float s1=this.init_texture_from_obj.get(2*texindex);
    				float t1=this.init_texture_from_obj.get(2*texindex+1);
    				this.final_texture_form_obj.add(s1);
    				this.final_texture_form_obj.add(t1);
    				
    				texindex=Integer.parseInt(tmps[3].split("/")[1])-1;
    				float s2=this.init_texture_from_obj.get(2*texindex);
    				float t2=this.init_texture_from_obj.get(2*texindex+1);
    				this.final_texture_form_obj.add(s2);
    				this.final_texture_form_obj.add(t2);
    				
    				
    			}
    			
    		}
    		this.vertexs_obj=bufferutil.list2buffer(final_vertexs_from_obj);
    		this.texture_obj=bufferutil.list2buffer(final_texture_form_obj);
    		this.vcounts=this.final_vertexs_from_obj.size()/3;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public void get_AABBInfo()
    {
    	this.xmin=Float.POSITIVE_INFINITY;
    	this.xmax=Float.NEGATIVE_INFINITY;
    	this.ymin=Float.POSITIVE_INFINITY;
    	this.ymax=Float.NEGATIVE_INFINITY;
    	this.zmin=Float.POSITIVE_INFINITY;
    	this.zmax=Float.NEGATIVE_INFINITY;
    	for(int i=0;i<this.final_vertexs_from_obj.size()/3;i++)
    	{
    		if(this.final_vertexs_from_obj.get(3*i)<this.xmin)
    		{
    			this.xmin=this.final_vertexs_from_obj.get(3*i);
    		}
    		if(this.final_vertexs_from_obj.get(3*i)>this.xmax)
    		{
    			this.xmax=this.final_vertexs_from_obj.get(3*i);
    		}
    		if(this.final_vertexs_from_obj.get(3*i+1)<this.ymin)
    		{
    			this.ymin=this.final_vertexs_from_obj.get(3*i+1);
    		}
    		if(this.final_vertexs_from_obj.get(3*i+1)>this.ymax)
    		{
    			this.ymax=this.final_vertexs_from_obj.get(3*i+1);
    		}
    		if(this.final_vertexs_from_obj.get(3*i+2)<this.zmin)
    		{
    			this.zmin=this.final_vertexs_from_obj.get(3*i+2);
    		}
    		if(this.final_vertexs_from_obj.get(3*i+2)>this.zmax)
    		{
    			this.zmax=this.final_vertexs_from_obj.get(3*i+2);
    		}  		
    	}
    }
    
    
    
}
