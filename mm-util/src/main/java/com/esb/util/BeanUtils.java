package com.esb.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BeanUtils {
	private BeanUtils() {}
	/**
	 * 物件序列化為byte陣列
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] bean2Byte(Object obj) {
		byte[] bb = null;
		try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			 ObjectOutputStream outputStream = new ObjectOutputStream(byteArray)){
			outputStream.writeObject(obj);
			outputStream.flush();
			bb = byteArray.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bb;
	}
	/**
	 * 位元組陣列轉為Object物件
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object byte2Obj(byte[] bytes) {
		Object readObject = null;
		try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			 ObjectInputStream inputStream = new ObjectInputStream(in)){
			 readObject = inputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return readObject;
	}
}
