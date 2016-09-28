package crawlingDouban;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {  
	  
    public static final String URL = "https://book.douban.com/subject_search";
    //搜索网址
    public static final int NUM = 15;
    //每页显示数量
    
    public static final String START = "?start=";
    public static final String INISEARCH = "?search_text=";
    public static final String SEARCH = "&search_text=";
    public static final String CAT ="&cat=1001"; 
    //拼接动态URL
    
    /**
     * 使用正则表达式从字符串中提取单一数字
     */
    public static int getInt(String text)
    {
    	int result;
    	
    	if (text.isEmpty())
    		result = 0;
    	String regEx="[^0-9]";   
    	Pattern pattern = Pattern.compile(regEx);      
    	Matcher matcher = pattern.matcher(text);      
    	result = Integer.parseInt(matcher.replaceAll("").trim()); 
    	return result;
    }
 
}  