package com.example.planefight3d;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;  
import android.content.Intent;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.Button;
import android.widget.ImageView;  
import android.widget.ListView;  
import android.widget.TextView;  



@SuppressLint("HandlerLeak")
public class PaihangActivity extends Activity{

       
	   private ListView listView;  
	   public String[] usernames;
	   public String[] scores;
	   public String[] times;
	   public String[] wordnums;
	   
	   private Button refresh;
	   private Button back;
	   
	   private HTTPConnector conn;
	   public String content; 
	   
	   public Handler handler=new Handler()
	   {
		  public void handleMessage(Message msg)
		  {
			  switch(msg.what)
			  {
				
				  case 1:
				  {
					// t.setText(content);
					  listView.setAdapter(new ListViewAdapter(usernames,scores,times,wordnums));
				  }break;
			  }
		  }
	   };
	   
	   
	   private class press implements OnClickListener
	   {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
				case R.id.refresh:
				{
					new get_gradeThread().start();
				}break;
				case R.id.back:
				{
					Intent intent=new Intent(PaihangActivity.this,MenuActivity.class);
					PaihangActivity.this.startActivity(intent);
					PaihangActivity.this.finish();
				}break;
			}
		}
		   
	   }
	    
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.paihang);  
	        this.setTitle("排行榜");  
	        listView=(ListView)this.findViewById(R.id.paihanglistView); 
	        
	        refresh=(Button)this.findViewById(R.id.refresh);
	        back=(Button)this.findViewById(R.id.back);
	        
	        refresh.setOnClickListener(new press());
	        back.setOnClickListener(new press());
	        
	        new  get_gradeThread().start();
	    }  
	      
	    public class ListViewAdapter extends BaseAdapter
	    {  
	        View [] itemViews;  
	          
	        public ListViewAdapter(String [] usernames, String [] scores,String[] times,String[] wordnums)
	        {  
	            itemViews = new View[usernames.length];  
	              
	            for (int i=0; i<itemViews.length; ++i)
	            {  
	                itemViews[i] = makeItemView(usernames[i],scores[i],times[i],wordnums[i]);  
	            }  
	        }  
	          
	        public int getCount()  
	        {  
	            return itemViews.length;  
	        }  
	          
	        public View getItem(int position)  
	        {  
	            return itemViews[position];  
	        }  
	          
	        public long getItemId(int position)
	        {  
	            return position;  
	        }  
	          
	        private View makeItemView(String strTitle, String strText, String strScore,String wordnum)
	        {  
	            LayoutInflater inflater = (LayoutInflater)PaihangActivity.this  
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	              
	            // 使用View的对象itemView与R.layout.item关联  
	            View itemView = inflater.inflate(R.layout.listview_item, null);  
	           
	            // 通过findViewById()方法实例R.layout.item内各组件  
	            TextView title = (TextView)itemView.findViewById(R.id.itemTitle);  
	            title.setText(strTitle);  
	            TextView text = (TextView)itemView.findViewById(R.id.itemText);  
	            text.setText(strText); 
	            TextView score = (TextView)itemView.findViewById(R.id.itemScore);  
	            score.setText(strScore);   
	            TextView word_num=(TextView)itemView.findViewById(R.id.itemwordnum);
	            word_num.setText(wordnum);
	            ImageView image = (ImageView)itemView.findViewById(R.id.itemImage);  
	            image.setImageResource(R.drawable.tiny);  
	              
	            return itemView;  
	        }  
	          
	        public View getView(int position, View convertView, ViewGroup parent) 
	        {  
	            if (convertView == null)  
	                return itemViews[position];  
	            return convertView;  
	        }  
	    }  

	    private class get_gradeThread extends Thread
	    {
	    	public void run()
	    	{
	    		conn=new HTTPConnector(HTTPConnector.ip);
	    		conn.set_paihang(PaihangActivity.this);
	    	
	    		if(PaihangActivity.this.usernames!=null&&PaihangActivity.this.scores!=null&&PaihangActivity.this.times!=null)
	    		{
	    			handler.sendEmptyMessage(1);
	    		}
	    		
	    	}
	    }
	    
}
