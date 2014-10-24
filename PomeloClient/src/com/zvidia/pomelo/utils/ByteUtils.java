package com.zvidia.pomelo.utils;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: jiangzm
 * Date: 13-8-4
 * Time: 涓嬪崍1:48
 * To change this template use File | Settings | File Templates.
 */
public class ByteUtils {


    //瀛楃鍒板瓧鑺傝浆鎹�   
	public static byte[] charToBytes(char charValue) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putChar(charValue);
        return buffer.array();
    }

    //瀛楄妭鍒板瓧绗﹁浆鎹�    
	public static char bytesToChar(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getChar();
    }

    //鏁存暟鍒板瓧鑺傛暟缁勭殑杞崲
    public static byte[] intToBytes(int intValue) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(intValue);
        return buffer.array();
    }

    //瀛楄妭鏁扮粍鍒版暣鏁扮殑杞崲
    public static int bytesToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getInt();
    }

    //娴偣鏁板埌瀛楄妭鏁扮粍鐨勮浆鎹�    
    public static byte[] floatToBytes(float floatValue) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(floatValue);
        return buffer.array();
    }

    //瀛楄妭鏁扮粍鍒版诞鐐规暟鐨勮浆鎹�    
    public static float bytesToFloat(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getFloat();
    }

    //闀挎暣鍨嬪埌瀛楄妭鏁扮粍鐨勮浆鎹�    
    public static byte[] longToBytes(long longValue) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(longValue);
        return buffer.array();
    }

    //瀛楄妭鏁扮粍鍒伴暱鏁村瀷鐨勮浆鎹�   
    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }


    //鍙岀簿搴﹀埌瀛楄妭杞崲
    public static byte[] doubleToBytes(double doubleValue) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putDouble(0, doubleValue);
        return buffer.array();
    }

    //瀛楄妭鍒板弻绮惧害杞崲
    public static double byteToDouble(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getDouble();
    }
}
