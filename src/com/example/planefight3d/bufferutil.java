package com.example.planefight3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

//缓存区工具类，负责进行float,byte数组转成相应的缓冲区
public class bufferutil {
	
	public static FloatBuffer array2buffer(float []arr)
	{
		ByteBuffer buf=ByteBuffer.allocateDirect(arr.length*4);
		buf.order(ByteOrder.nativeOrder());
		FloatBuffer fbuf=buf.asFloatBuffer();
		fbuf.put(arr);
		fbuf.position(0);
		return fbuf;
	}
	
	public static FloatBuffer list2buffer(List<Float> arr)
	{
		ByteBuffer buf=ByteBuffer.allocateDirect(arr.size()*4);
		buf.order(ByteOrder.nativeOrder());
		FloatBuffer fbuf=buf.asFloatBuffer();
		for(float f:arr)
		fbuf.put(f);
		fbuf.position(0);
		return fbuf;
	}
	
	public static ByteBuffer byte2buffer(byte[] b)
	{
		ByteBuffer buf=ByteBuffer.allocateDirect(b.length);
		buf.order(ByteOrder.nativeOrder());
		buf.put(b);
		buf.position(0);
		return buf;
		
	}

}
