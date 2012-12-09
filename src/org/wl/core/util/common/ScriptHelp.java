package org.wl.core.util.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 脚本处理类 1.实现压缩单个js文件--去除注释换行和多个空格,最终输出一行字符串 2.实现批量js压缩功能，压缩目录下的所有js文件到对应的输出目录下
 * 3.实现js合并功能 根据需要压缩的列表和输出文件路径及是否压缩生成对应文件
 * 
 * @version 1.0
 */
public class ScriptHelp {
	private static final String ENCODE = "UTF-8";

	/**
	 * 批量压缩js，压缩当前目录下的所有js
	 * 
	 * @param baseScriptPath
	 *            要压缩的文件目录
	 */
	@SuppressWarnings("unchecked")
	public static void batchCompressJS(String baseScriptPath) {
		// 获取当前目录下所以js文件路径(不包括子目录)
		List fileList = getListFiles(baseScriptPath, "js", true);

		for (Iterator i = fileList.iterator(); i.hasNext();) {
			String fromFile = (String) i.next();
			String toFile = StringUtil.replaceLast(fromFile, ".js", ".min").concat(".js");
			compressSingleJS(fromFile,toFile);
		}
	}

	/**
	 * 压缩单个js文件
	 * 
	 * @param fromFile
	 * @param toFile
	 */
	public static void compressSingleJS(String fromFile, String toFile) {
		String content = readFile(fromFile);
		writeFile(compressJS(content), toFile);
	}

	/**
	 * 合并js文件
	 * 
	 * @param fileList
	 *            文件全路径的list,需要按顺序
	 * @param toFile
	 *            输出文件的全路径
	 * @param isCompress
	 *            是否压缩
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void mergeJS(List fileList, String toFile, Boolean isCompress) {
		String content = "";
		for (Iterator i = fileList.iterator(); i.hasNext();) {
			String fromFile = (String) i.next();
			content += readFile(fromFile);
		}
		if (isCompress == true)
			writeFile(compressJS(content), toFile);
		else
			writeFile(content, toFile);
	}

	/**
	 * 去除注释、多个空格和换行，最终形成一行的字符串
	 * 
	 * @param content
	 *            要压缩的内容
	 * @return 压缩后的内容
	 */
	public static String compressJS(String content) {
		// 防止替换alert("/**xcxc****/");的情况加入([^\"])，但文件第一行的注释无法替换
		// 把replaceAll("\\/\\/[^\\n]*","")放后面防止/*//dfd*/这种情况的无效替换以提高效率
		content = content.replaceAll("([^\"])\\/\\*([^\\*^\\/]*|[\\*^\\/*]*[^\\**\\/]*)*\\*\\/","$1").replaceAll("\\/\\/[^\\n]*", "");
		// 如果开头为/*注释则截取掉
		if (content.indexOf("/*") == 0)
			content = content.substring(content.indexOf("*/") + 2, content.length());
		content = content.replaceAll("\\s{2,}", " ").replaceAll("\r\n", "").replaceAll("\n", "");

		return content;
	}

	/**
	 * 输出文件，编码为UTF-8 用记事本另存为：fileContent 全部为英文则为ansi 包含中文则为UTF-8
	 * 
	 * @param content
	 *            要输出的文件内容
	 * @param comspec
	 *            全路径名
	 */
	public static void writeFile(String content, String comspec) {
		try {
			FileOutputStream fos = new FileOutputStream(comspec);
			Writer out = new OutputStreamWriter(fos, ENCODE);
			out.write(content);
			System.out.println("成功输出文件：" + comspec);
			out.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("写文件操作出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return String
	 */
	public static String readFile(String filePath) {
		StringBuilder sb = new StringBuilder();
		try {
			File file = new File(filePath);
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODE);
			BufferedReader reader = new BufferedReader(read);
			String s = reader.readLine();
			while (s != null) {
				sb.append(s);
				sb.append("\r\n");
				s = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static List<String> fileList = new ArrayList<String>();

	/**
	 * @param path
	 *            文件路径
	 * @param suffix
	 *            后缀名
	 * @param isdepth
	 *            是否遍历子目录
	 * @return fileList
	 */
	@SuppressWarnings("unchecked")
	public static List getListFiles(String path, String suffix, boolean isdepth) {
		File file = new File(path);
		return listFile(path, file, suffix, isdepth);
	}

	/**
	 * 获取当前目录下文件路径
	 * 
	 * @param path
	 * @param f
	 * @param suffix
	 * @param isdepth
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List listFile(String path, File f, String suffix,
			boolean isdepth) {
		// 是目录，同时需要遍历子目录
		String temp = path.replaceAll("/", "\\\\");
		if ((f.isDirectory() && isdepth == true)
				|| temp.equals(f.getAbsolutePath())) {
			File[] t = f.listFiles();
			for (int i = 0; i < t.length; i++) {
				listFile(path, t[i], suffix, isdepth);
			}
		} else {
			addFilePath(f, suffix, isdepth);
		}
		return fileList;
	}

	/**
	 * 添加文件路径到list中
	 * 
	 * @param f
	 * @param suffix
	 * @param isdepth
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List addFilePath(File f, String suffix, boolean isdepth) {
		String filePath = f.getAbsolutePath().replaceAll("\\\\", "/");
		if (suffix != null) {
			int begIndex = filePath.lastIndexOf(".");
			String tempsuffix = "";

			if (begIndex != -1)// 防止是文件但却没有后缀名结束的文件
			{
				tempsuffix = filePath
						.substring(begIndex + 1, filePath.length());
			}
			if (tempsuffix.equals(suffix)) {
				fileList.add(filePath);
			}
		} else {
			fileList.add(filePath);// 后缀名为null则为所有文件
		}
		return fileList;
	}

}