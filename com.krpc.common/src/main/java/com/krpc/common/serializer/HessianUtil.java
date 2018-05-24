package com.krpc.common.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * Hessian序列化及反序列工具
 * @author yangzhenkun
 *
 */
public class HessianUtil {

	public static byte[] serialize(Object obj) throws IOException {
		if (obj == null)
			throw new NullPointerException();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		ho.writeObject(obj);
		return os.toByteArray();
	}

	public static Object deserialize(byte[] by, ClassLoader classLoader) throws IOException {
		if (by == null)
			throw new NullPointerException();

		ByteArrayInputStream is = new ByteArrayInputStream(by);
		ClassLoader old = null;
		if (classLoader != null) {
			old = Thread.currentThread().getContextClassLoader();
			// 切换当前线程classloader，保证动态加载的类不会报CNF
			Thread.currentThread().setContextClassLoader(classLoader);
		}
		HessianInput hi = new HessianInput(is);
		Object obj = hi.readObject();

		if (classLoader != null) {
			Thread.currentThread().setContextClassLoader(old);
		}
		return obj;
	}

}
