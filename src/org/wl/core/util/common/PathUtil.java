package org.wl.core.util.common;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PathUtil {

	private static String WEBCLASSES_PATH = null;
	private static String WEBINF_PATH = null;
	private static String WEBROOT_PATH = null;

	static {
		/*
		 * PathUtil.WEBCLASSES_PATH =
		 * Thread.currentThread().getContextClassLoader
		 * ().getResource("").getPath(); PathUtil.WEBINF_PATH =
		 * PathUtil.WEBCLASSES_PATH
		 * .substring(0,PathUtil.WEBCLASSES_PATH.lastIndexOf("classes"));
		 * PathUtil.WEBROOT_PATH =
		 * PathUtil.WEBCLASSES_PATH.substring(0,PathUtil.
		 * WEBCLASSES_PATH.lastIndexOf("WEB-INF"));
		 */
		StringBuffer path = new StringBuffer(PathUtil.getClassLocationURL(PathUtil.class).getPath());
		File file = new File(path.replace(path.lastIndexOf("classes"), path.length(), "").append("classes").toString());
		PathUtil.WEBCLASSES_PATH = file.getPath();
		PathUtil.WEBINF_PATH = file.getParentFile().getPath();
		PathUtil.WEBROOT_PATH = file.getParentFile().getParentFile().getPath();
		/*
		 * 如果配置文件打包在jar 中 需要用流的形式才能读取到 getClass().getResourceAsStream()
		 * getClassLoader().getResourceAsStream();
		 */
	}

	public static String getFileName(String path) {
		String[] ress = path.split("/");
		return ress[ress.length - 1];
	}

	public static String getFilePath(String path, String fileName) {
		return path.replaceAll("/" + fileName, "").replaceFirst("/", "");
	}

	public static String getFilePostfix(String uri) {
		String[] ress = uri.split("\\.");
		return ress.length < 2 ? "" : ress[ress.length - 1];
	}

	public static String getWebClassesPath() {
		return WEBCLASSES_PATH;
	}

	public static String getWebInfPath() {
		return WEBINF_PATH;
	}

	public static String getWebRoot() {
		return WEBROOT_PATH;
	}

	public static String getPathFromClass(Class<?> cls) {
		String path = null;
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			try {
				path = file.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	public static String getFullPathRelateClass(String relatedPath, Class<?> cls) throws IOException {
		String path = null;
		if (relatedPath == null) {
			throw new NullPointerException();
		}
		String clsPath = getPathFromClass(cls);
		File clsFile = new File(clsPath);
		String tempPath = clsFile.getParent() + File.separator + relatedPath;
		File file = new File(tempPath);
		path = file.getCanonicalPath();
		return path;
	}

	/**
	 * 获取类的class文件位置的URL。这个方法是本类最基础的方法，供其它方法调用。
	 */
	private static URL getClassLocationURL(final Class<?> cls) {
		if (cls == null)
			throw new IllegalArgumentException("null input: cls");
		URL result = null;
		final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
		final ProtectionDomain pd = cls.getProtectionDomain();
		if (pd != null) {
			final CodeSource cs = pd.getCodeSource();
			if (cs != null)
				result = cs.getLocation();
			if (result != null) {
				if ("file".equals(result.getProtocol())) {
					try {
						if (result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip"))
							result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
						else if (new File(result.getFile()).isDirectory())
							result = new URL(result, clsAsResource);
					} catch (MalformedURLException ignore) {
					}
				}
			}
		}
		if (result == null) {
			final ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource) : ClassLoader.getSystemResource(clsAsResource);
		}
		return result;
	}

	/**
	 * 方法名称: getFilePath 描述: 用于获取文件的绝对路径 xml class 相对于 classes 目录 js css jpg 相对于
	 * WebRoot 目录
	 * 
	 * @param path
	 * @return
	 */
	public static String getFilePath(String path) {
		StringBuffer result = new StringBuffer();
		if (path.endsWith(".xml")) {
			result.append(PathUtil.getWebClassesPath()).append("/").append(path.replace(".xml", "").replace('.', '/').concat(".xml"));
		} else if (path.endsWith(".class")) {
			result.append(PathUtil.getWebClassesPath()).append("/").append(path.replace(".class", "").replace('.', '/').concat(".class"));
		} else if (path.endsWith(".js") || path.endsWith(".css") || path.endsWith(".jpg")) {
			result.append(PathUtil.getWebRoot()).append("/").append(path);
		} else
			result.append(PathUtil.getWebRoot()).append("/").append(path);
		return result.toString();
	}

	/**
	 * 获取class文件的相对路径 如：com.fantasy.common.security.web.LoginAction
	 * 返回的路径为:/com/fantasy/common/security/web/LoginAction.class
	 * 
	 * @param classes
	 * @return
	 */
	public static String getClassFilePath(Class<?> classes) {
		return classes.getName().replaceAll("\\.", "/").concat(".class");
	}

	public static String getPageTemplateDir(Class<?> classes) {
		return "/".concat(getClassFilePath(classes)).replaceAll("/".concat(classes.getSimpleName()).concat("\\.class$"), "");
	}

	public static String[] getPackagePath(String packages) {
		if (packages == null)
			return null;
		List<String> pathPrefixes = new ArrayList<String>();
		String pathPrefix;
		for (StringTokenizer st = new StringTokenizer(packages, ", \n\t"); st.hasMoreTokens(); pathPrefixes.add(pathPrefix)) {
			pathPrefix = st.nextToken().replace('.', '/');
			if (!pathPrefix.endsWith("/"))
				pathPrefix = (new StringBuilder()).append(pathPrefix).append("/").toString();
		}
		return (String[]) pathPrefixes.toArray(new String[pathPrefixes.size()]);
	}

	public static String getActionConfigPath(String defaultActionConfig) {
		return new StringBuffer().append(PathUtil.getWebClassesPath()).append("/").append(defaultActionConfig.replace(".xml", "").replace('.', '/').concat(".xml")).toString();
	}

	public static String getTmpdir() {
		return System.getProperty("java.io.tmpdir");
	}

}