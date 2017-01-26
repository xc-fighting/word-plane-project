package com.example.planefight3d;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



/******************************************************************************************************/
//�Ժ��Ǻ�sd���򽻵���ȫ������������Ȩ��!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEM"/> 
//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
/*******************************************************************************************************/



public class DBAdapter{
	
	public SQLiteDatabase database;
	
	private Context context;
	
	private final String db_path=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/planefight3d";
	
	private final String db_name="mydict.db";
	
	private final String db_table="Words";
	
	
	public DBAdapter(Context _context)
	{
		this.context=_context;
	}
	
	
	
	public void close_db()
	{
		this.database.close();
	}
	
	
	
	public void open_db()
	{
		try
		{
			String db_filename=db_path+"/"+db_name;
			File dir=new File(db_path);
			
			//���sd���в��������·���ͽ��д���
			if(dir.exists()==false)
			{
				dir.mkdir();
			}
			
			//������·������û�����ݿ⣬����п���
			if(new File(db_filename).exists()==false)
			{
				InputStream is=context.getResources().openRawResource(R.raw.mydict);
				FileOutputStream fos=new FileOutputStream(db_filename);
				//����8mb�Ŀռ仺��
				byte[] buffer=new byte[8192];
				int count=0;
				while((count=is.read(buffer))>0)
				{
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				
			}
			this.database=SQLiteDatabase.openOrCreateDatabase(db_filename, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public ShengCi[] ConvertToWords(Cursor cursor)
	{
		int count=cursor.getCount();
		if(count==0||cursor.moveToFirst()==false)
		{
			return null;
		}
		ShengCi[] word_group=new ShengCi[count];
		for(int i=0;i<count;i++)
		{
			String content=cursor.getString(cursor.getColumnIndex("word"));
			String explain=cursor.getString(cursor.getColumnIndex("explain"));
			word_group[i]=new ShengCi(content,explain);
			cursor.moveToNext();
		}
		return word_group;
	}
	
	
	public ShengCi[] query_infos(String word)
	{
		Cursor cursor=database.rawQuery("select word,explain from "+db_table+" where word ='"+word+"'", null);		
		return this.ConvertToWords(cursor);
	}
	
	public String[] query_explain(String word)
	{
		Cursor cursor=database.rawQuery("select explain from "+db_table+" where word='"+word+"'",null);
		return this.converttostrings(cursor);
	}
	public String[] converttostrings(Cursor cursor)
	{
		int count=cursor.getCount();
		if(count==0||cursor.moveToFirst()==false)
		{
			return null;
		}
		String[] word_group=new String[count];
		for(int i=0;i<count;i++)
		{
			
			String explain=cursor.getString(cursor.getColumnIndex("explain"));
			word_group[i]=explain;
			cursor.moveToNext();
		}
		return word_group;
	}
	
	
	
	

}
