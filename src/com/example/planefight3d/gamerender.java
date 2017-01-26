package com.example.planefight3d;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class gamerender implements Renderer{
	
	
	private Resources resource;
	private gameview myview;
	private GameRule rule;
 
    private TextureRect frame;
    private drawsky colum;
    private drawland lands;
    private player player;
    private drawcircle firebutton;
    private Timer timer;

   
   
  
   //线程类
   private bulletthread thread;
   private playerthread pthread;
   private enemythread ethread;
   private CubeThread cthread;
   private ExplodeThread exthread;
   private EnemyPlaneThread plane_thread;
   private TimerThread timer_thread;
   
   
   //manager类
   private scoremanager score_manager;
   private lifemanager life_manager;
   private playermanager player_manager;
   private DaojuManager daoju_manager;
   private WordsBannerManager banner_manager;
   private ButtonForControlManager button_manager;
   
   
   //保存纹理信息的变量
   private int textureID;
    
    
    
   
   public WordsBannerManager get_word_banner_manager()
   {
	   return this.banner_manager;
   }
   
   
   
   
   
    	
	public gamerender(gameview mv)
	{
		this.myview=mv;
		this.resource=myview.getResources();
	}

	
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		GLU.gluLookAt(gl,0,0,0f,0,0,-1,0,1,0);
		
		//画玩家
	     this.player_manager.drawself(gl);
		
		
		//画大地天空
		gl.glPushMatrix();
	    gl.glTranslatef(0f,0f,-10f);
		colum.drawself(gl);
		gl.glTranslatef(0f,-8f,-2f);
		lands.drawself(gl);
		gl.glPopMatrix();	
		
			
		
		//游戏界面的标题头
		gl.glEnable(GL10.GL_BLEND);						//开启混合
       	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);//设置混合因子
		life_manager.life_decrease();
		life_manager.drawself(gl);		
		score_manager.drawself(gl);
		timer.drawself(gl);
		gl.glDisable(GL10.GL_BLEND);//关闭混合模式		
		
		
		//绘制所有的爆炸效果
		ExplodeManager.drawself(gl);
		
		
		//画开火按钮
		firebutton.drawself(gl);
		
		
		//绘制所有的子弹
		bulletmanager.drawself(gl);
		
		//绘制所有的陨石敌人
		enemymanager.drawself(gl);
		
		//绘制所有的敌机
		EnemyPlaneManager.drawself(gl);	
		
		//绘制所有的单词块
		WordCubeManager.drawself(gl);
		
		//画边框 
		gl.glPushMatrix();
		gl.glTranslatef(0,-constant.edge+0.11f,-2.0001f);
		frame.drawself(gl);
		gl.glPopMatrix();
		
		
		//画道具
		GameRule.generate_random_daoju();
		this.daoju_manager.set_touch_area_of_all();
		this.daoju_manager.drawself(gl);
		
		gl.glEnable(GL10.GL_BLEND);						//开启混合
       	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);//设置混合因子
		//画控制按钮
		this.button_manager.drawself(gl);
		gl.glDisable(GL10.GL_BLEND);//关闭混合模式	

		
		//画单词
		this.banner_manager.drawself(gl);		
		
	}

	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		
		constant.ratio=(float)width/height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();		
		gl.glFrustumf(-1,1,-1/constant.ratio,1/constant.ratio,2,100);
		gl.glViewport(0,0,width,height);
		
		
		
	}

	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		//关闭抗抖动
		gl.glDisable(GL10.GL_DITHER);
		//设置平滑模式
		gl.glShadeModel(GL10.GL_SMOOTH);
		//将背景色改为黑色
		gl.glClearColor(0f,0f,0f,1f);
		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
		//设置开启深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);		
		

		
		
/****************************************************************************************************************************/		
		//初始化开火按钮的触碰区域
		constant.edge=(float)GameActivity.height/(float)GameActivity.width;
		constant.fire_button_area[0]=(1-constant.button_radius)*GameActivity.width;//right
		constant.fire_button_area[1]=GameActivity.width;//right
		constant.fire_button_area[2]=(0.5f-constant.button_radius/(2*constant.edge))*GameActivity.height;
		constant.fire_button_area[3]=GameActivity.height*(0.5f+constant.button_radius/(2*constant.edge));
		
		
		//初始化天空
		textureID=this.InitTexture(R.drawable.skyball, gl);
		colum=new drawsky(textureID,30,30,50,20);		
		
		
		//初始化大地
		textureID=this.InitTexture(R.drawable.grass, gl);
		lands=new drawland(constant.rows,constant.cols,textureID,50,5,constant.YArray);	
		lands.initAll();		
		
		
		//初始化玩家的同时设置子弹的id
		textureID=this.InitTexture(R.drawable.feijione, gl);
		player=new player("feiji11.obj",resource,textureID);
		this.myview.set_listen_obj(player);		
		
		//初始化玩家管理器的信息
		this.player_manager=new playermanager(this.player);
		
		//初始化生命值管理器
		textureID=this.InitTexture(R.drawable.life, gl);
		this.life_manager=new lifemanager(3,textureID,constant.icon_width,constant.icon_width,-1f+constant.icon_width/2,constant.edge-constant.icon_width/2,-2.0001f);
		
		
		//初始化开火按钮
		textureID=this.InitTexture(R.drawable.button2, gl);
		this.firebutton=new drawcircle(textureID,20,constant.button_radius);
		this.firebutton.initAll();
		float[] pos={1-constant.button_radius,0,-2.0001f};
		this.firebutton.setposXYZ(pos);
		
		
		//绘制方向按钮
		this.init_dir_button(gl);
		this.button_manager=new ButtonForControlManager(0.1f,-2.0001f);
	    this.button_manager.set_touch_area_of_all();
		
	    
		//初始化分数显示
		this.init_score_textures(gl);
		this.score_manager=new scoremanager(constant.icon_width,constant.icon_width,1-constant.icon_width/2,constant.edge-constant.icon_width/2,-2.0001f,0.02f);
		
		
		//初始化边框
		textureID=this.InitTexture(R.drawable.biankuang, gl);
		this.frame=new TextureRect(textureID,2,0.02f);
		
		
		//设置道具显示的相关参数
		//初始化所有道具纹理
		this.init_daoju_texs(gl);
		//设置道具管理器
		this.daoju_manager=new DaojuManager(0.01f,-2.0001f);
		//在view中设置道具
		this.myview.set_daoju_touch(daoju_manager);
		
		
		//初始化计时器的参数
		textureID=this.InitTexture(R.drawable.miao, gl);
		this.timer=new Timer(textureID,120,0.1f,0f,constant.edge-0.05f,-2.0001f,0.05f);
		
		
		//初始化所有字母的纹理信息
		this.init_alpha_texture_id(gl);
		
		//初始化单词显示的参数
		textureID=this.InitTexture(R.drawable.wenhao, gl);
		//float width,int wenhao,float dx,float x,float y,float z
		this.banner_manager=new WordsBannerManager(0.1f,textureID,0.05f,0,constant.edge-0.1f-0.05f,-2.0001f);
		
	
		
		
		
		//初始化所有爆炸纹理
		this.init_explode_texs(gl);
		
		//初始化子弹纹理
		constant.bullet_tex=this.InitTexture(R.drawable.bullettexture, gl);
		
		//初始化飞机纹理信息
		constant.feiji_tex=this.InitTexture(R.drawable.feidietex, gl);
		
		//初始化陨石敌人纹理信息
		constant.yunshi_tex=this.InitTexture(R.drawable.yanshi, gl);
		
		
/******************************************************************************************************************************/		
		
	//gamerule 的相关初始化工作	
		
		
	//	EnemyPlaneManager.enemyplanes.add(new enemyplane(constant.feidie_filename,resource,constant.feiji_tex,constant.scale_factor,0,0,-50f,0f,0f,0.1f));
		
		//String filename,Resources res,int id,float scaled,float xpos,float ypos,float zpos,float vx,float vy,float vz
		
		
		
		
		
	//	WordCubeManager.generate_words('n',0.1f,0,0,-20);
		
		
//		this.banner_manager.set_words("banana");
		
		this.rule=new GameRule(this.resource,this);
	//	this.rule.init_banner();
		
	
/*************************************************************************************************************************/		
		
		
		this.plane_thread=new EnemyPlaneThread(constant.bullet_tex);
		
		this.timer_thread=new TimerThread(timer);
		
		this.cthread=new CubeThread();
				
		this.thread=new bulletthread();
		
		pthread=new playerthread(player);
		
		ethread=new enemythread();
		
		exthread=new ExplodeThread();
		
		
		pthread.start();		
		ethread.start();		
		exthread.start();
		cthread.start();
		timer_thread.start();
		plane_thread.start();
		thread.start();
		
		
		rule.start();
		
		
/****************************************************************************************************************************/		
		
		
		
	}
	
	
	
	
	
	public int InitTexture(int drawable,GL10 gl)
	{
		int []texture=new int [1];
		gl.glGenTextures(1,texture,0);
		int curid=texture[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D,curid);		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_REPEAT);
		//这个环境设置一定要注意
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
        InputStream is = resource.openRawResource(drawable);
        Bitmap bitmapTmp; 
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle(); 
        
        return curid;
	}
	
	public void init_dir_button(GL10 gl)
	{
		int texid;
		texid=this.InitTexture(R.drawable.left, gl);
		ButtonForControlManager.button_ids[0]=texid;
		texid=this.InitTexture(R.drawable.right, gl);
		ButtonForControlManager.button_ids[1]=texid;
		texid=this.InitTexture(R.drawable.up, gl);
		ButtonForControlManager.button_ids[2]=texid;
		texid=this.InitTexture(R.drawable.down, gl);
		ButtonForControlManager.button_ids[3]=texid;
	}
	
	public void init_score_textures(GL10 gl)
	{
		int texids;
		texids=this.InitTexture(R.drawable.number0, gl);
		scoremanager.texIDS[0]=texids;
		texids=this.InitTexture(R.drawable.number1, gl);
		scoremanager.texIDS[1]=texids;
		texids=this.InitTexture(R.drawable.number2, gl);
		scoremanager.texIDS[2]=texids;
		texids=this.InitTexture(R.drawable.number3, gl);
		scoremanager.texIDS[3]=texids;
		texids=this.InitTexture(R.drawable.number4, gl);
		scoremanager.texIDS[4]=texids;
		texids=this.InitTexture(R.drawable.number5, gl);
		scoremanager.texIDS[5]=texids;
		texids=this.InitTexture(R.drawable.number6, gl);
		scoremanager.texIDS[6]=texids;
		texids=this.InitTexture(R.drawable.number7, gl);
		scoremanager.texIDS[7]=texids;
		texids=this.InitTexture(R.drawable.number8, gl);
		scoremanager.texIDS[8]=texids;
		texids=this.InitTexture(R.drawable.number9, gl);
		scoremanager.texIDS[9]=texids;
	}
	public void init_alpha_texture_id(GL10 gl)
	{
		int texids;
		texids=this.InitTexture(R.drawable.a, gl);
		WordCubeManager.alpha_ids[0]=texids;
		texids=this.InitTexture(R.drawable.b, gl);
		WordCubeManager.alpha_ids[1]=texids;
		texids=this.InitTexture(R.drawable.c, gl);
		WordCubeManager.alpha_ids[2]=texids;
		texids=this.InitTexture(R.drawable.d, gl);
		WordCubeManager.alpha_ids[3]=texids;
		texids=this.InitTexture(R.drawable.e, gl);
		WordCubeManager.alpha_ids[4]=texids;
		texids=this.InitTexture(R.drawable.f, gl);
		WordCubeManager.alpha_ids[5]=texids;
		texids=this.InitTexture(R.drawable.g, gl);
		WordCubeManager.alpha_ids[6]=texids;
		texids=this.InitTexture(R.drawable.h, gl);
		WordCubeManager.alpha_ids[7]=texids;
		texids=this.InitTexture(R.drawable.i, gl);
		WordCubeManager.alpha_ids[8]=texids;
		texids=this.InitTexture(R.drawable.j, gl);
		WordCubeManager.alpha_ids[9]=texids;
		texids=this.InitTexture(R.drawable.k, gl);
		WordCubeManager.alpha_ids[10]=texids;
		texids=this.InitTexture(R.drawable.l, gl);
		WordCubeManager.alpha_ids[11]=texids;
		texids=this.InitTexture(R.drawable.m, gl);
		WordCubeManager.alpha_ids[12]=texids;
		texids=this.InitTexture(R.drawable.n, gl);
		WordCubeManager.alpha_ids[13]=texids;
		texids=this.InitTexture(R.drawable.o, gl);
		WordCubeManager.alpha_ids[14]=texids;
		texids=this.InitTexture(R.drawable.p, gl);
		WordCubeManager.alpha_ids[15]=texids;
		texids=this.InitTexture(R.drawable.q, gl);
		WordCubeManager.alpha_ids[16]=texids;
		texids=this.InitTexture(R.drawable.r, gl);
		WordCubeManager.alpha_ids[17]=texids;
		texids=this.InitTexture(R.drawable.s, gl);
		WordCubeManager.alpha_ids[18]=texids;
		texids=this.InitTexture(R.drawable.t, gl);
		WordCubeManager.alpha_ids[19]=texids;
		texids=this.InitTexture(R.drawable.u, gl);
		WordCubeManager.alpha_ids[20]=texids;
		texids=this.InitTexture(R.drawable.v, gl);
		WordCubeManager.alpha_ids[21]=texids;
		texids=this.InitTexture(R.drawable.w, gl);
		WordCubeManager.alpha_ids[22]=texids;
		texids=this.InitTexture(R.drawable.x, gl);
		WordCubeManager.alpha_ids[23]=texids;
		texids=this.InitTexture(R.drawable.y, gl);
		WordCubeManager.alpha_ids[24]=texids;
		texids=this.InitTexture(R.drawable.z, gl);
		WordCubeManager.alpha_ids[25]=texids;
	}
	
	
	public void init_explode_texs(GL10 gl)
	{
		for(int i=0;i<6;i++)
		{
			Explode.explode_id[i]=this.InitTexture(constant.explode_pics[i], gl);
		}
	}
	
	public void init_daoju_texs(GL10 gl)
	{
		for(int i=0;i<3;i++)
		{
			constant.daoju_textures[i]=this.InitTexture(constant.daoju_pics[i], gl);
		}
	}
	
	
	
	

}
