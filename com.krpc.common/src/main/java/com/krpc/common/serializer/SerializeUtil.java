package com.krpc.common.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @TODO 序列化工具
 * 
 * @author yangzhenkun
 */
public class SerializeUtil {

	private static Logger log = LoggerFactory.getLogger(SerializeUtil.class);
	
	/**
	 * @TODO 对象序列化
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			log.error("序列化错误", e);
		}
		return null;
	}

	
	/**
	 * @TODO 对象反序列化(必需指明 反序列化的类 的ClassLoader)
	 * @param bytes
	 * @param classLoader 该类的加载器
	 * @return
	 */
	public static Object deserialize(byte[] bytes,ClassLoader classLoader) {
		ByteArrayInputStream bais = null;
		ObjectInputStreamWithLoader ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStreamWithLoader(bais,classLoader);
			return ois.readObject();
		} catch (Exception e) {
			log.error("反序列化错误", e);
		}
		return null;
	}
	
	/**
	 * @TODO 对象反序列化（反序列化类为AppClassLoader加载）
	 * @param bytes
	 * @return
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			log.error("反序列化错误", e);
		}
		return null;
	}
	
}
