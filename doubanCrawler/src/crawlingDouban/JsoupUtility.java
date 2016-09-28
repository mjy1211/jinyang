package crawlingDouban;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Jinyang
 **/
public class JsoupUtility {

	private JsoupUtility() {

	}

	private static final JsoupUtility instance = new JsoupUtility();

	/**
	 * Singleton Pattern
	 **/
	public static JsoupUtility getInstance() {
		return instance;
	}

	public void getDoubanReview() {
		ArrayList<Book> bookList = new ArrayList<Book>();
    String search = "";
    int searchMax;
		try {
			Scanner scanner = new Scanner(System.in);

			System.out.println("请输入搜索关键字");
			search = scanner.nextLine();
			System.out.println("请输入最小评价数，建议数值大于200");
		  searchMax = scanner.nextInt();

			scanner.close();
			String initialUrl = Constants.URL + Constants.INISEARCH + search + Constants.CAT;
			Connection iniConnection = Jsoup.connect(initialUrl);
			Document iniDocument = iniConnection.timeout(8000).get(); // 防止超时
			Elements trr = iniDocument.select("div.trr");
			int total;
			total = Constants.getInt(trr.text().substring(trr.text().indexOf("共")));
			// 解析初次搜索，寻找总共搜索到多少本书,存入total变量
			System.out.println("总共找到" + total + "本书");

			System.out.println("开始遍历，寻找匹配条件的书");
			for (int i = 0; i * Constants.NUM < total; i++) {
				String url = Constants.URL + Constants.START + String.valueOf(i * Constants.NUM) + Constants.SEARCH
						+ search + Constants.CAT;
				System.out.println(url);
				Connection connection = Jsoup.connect(url);
				Document document = connection.timeout(10000).get();
				Elements li = document.select("li.subject-item"); // 得到li-class=subject-item标签

				Iterator<Element> liIter = li.iterator();
				while (liIter.hasNext()) {
					Element element = liIter.next();

					String name = element.select("a[title]").attr("title"); // 得到li中的title标签转化为书名

					double rat;
					String rating = element.select("span.rating_nums").text();
					if (!rating.equals(""))
						rat = Double.parseDouble(rating);// 得到span中的评分转化为float评分
					else
						rat = 0.0;

					int ratingCount;
					String ratCount = element.select("span.pl").text(); // 得到pl中的评价数
					String reg = "\\([0-9]+[\u4E00-\u9FA5]+\\)"; // 前多个数字后多个汉字的正则表达用于提取评价数

					if (ratCount.matches(reg))
						ratingCount = Constants.getInt(ratCount);
					else
						ratingCount = 0;

					// System.out.println(name + " " + rat + " " + ratingCount);
					if (ratingCount > searchMax) {
						Book book = new Book(name, ratingCount, rat);
						bookList.add(book);
					} // 超过输入的评价数则储存在booklist当中
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 不管是否超时执行如下代码
			Collections.sort(bookList);
			Collections.reverse(bookList);
			// 从大到小按评分排序
			Iterator<Book> bookIter = bookList.iterator();
			while (bookIter.hasNext()) {
				Book book = bookIter.next();
				System.out.println(book.toString());// 遍历输出当前结果
				FileInjector.writeFile(book.getRating(), book.getRatingCount(), book.getName()); // 写入文件
			}
			Date day=new Date(); 
			String keyword = "搜索关键字为" + search + ".";
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
      FileInjector.writeFile(df.format(day), keyword);
		}
	}

}