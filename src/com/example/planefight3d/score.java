package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class score {
      private TextureRect score_rect;//显示分数的矩行框
      private float width;
      private float height;
      private int texid;
      public score(float width,float height,int texid)
      {
    	  this.width=width;
    	  this.height=height;
    	  this.texid=texid;
    	  this.score_rect=new TextureRect(this.texid,this.width,this.height);
      }
      public void drawself(GL10 gl)
      {
    	  gl.glPushMatrix();
    	  this.score_rect.drawself(gl);
    	  gl.glPopMatrix();
      }
      
}
