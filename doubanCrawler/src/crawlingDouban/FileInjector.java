package crawlingDouban;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 将书名、评论次数、评分写入txt文件
 * 
 * @param name
 *            书名
 * @param ratingCount
 *            评论次数
 * @param rating
 *            评分
 */

public class FileInjector {

	public static void writeFile(double rating, int ratingCount, String name) {
		File file = new File("/home/jinyang/Documents/results.txt"); //更换环境请更改输出路径
		file.setWritable(true, false);// 增加权限
		Writer writer = null;
		try {
			writer = new FileWriter(file, true);
			writer.write(rating + " " + ratingCount + "  " + name + "\r\n");
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void writeFile(String time, String keyword) {
		File file = new File("/home/jinyang/Documents/results.txt"); //更换环境请更改输出路径
		file.setWritable(true, false);
		Writer writer = null;
		try {
			writer = new FileWriter(file, true);
			writer.write("结束，当前时间为" + time + ", " + keyword + "\r\n");
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
