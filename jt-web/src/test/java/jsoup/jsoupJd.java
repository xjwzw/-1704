package jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class jsoupJd {
	
	//获取所有的三级分类
	public static List<String> getAllLevel3() throws IOException{
		List<String> list=new ArrayList<String>();
		String url="https://www.jd.com/allSort.aspx";
		Elements ele=Jsoup.connect(url).get().select(".items dl dd a");
		for (Element element : ele) {
			String href="http:"+element.attr("href");
			if(href.startsWith("http://list.jd.com/list.html?cat=")){
			System.out.println(href);
			list.add(href);
			}
		}
		return list;
	}
	//获取某个分类的总页数
	public static Integer getPageNum(String catUrl){		
		try {
			 
		 String text = Jsoup.connect(catUrl).get().select("#J_topPage .fp-text i").get(0).text();
			Integer pageNum=Integer.parseInt(text);
			return pageNum;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	
	@Test
	public void run() throws IOException{
		//jsoupJd.getAllLevel3();
		String url="http://list.jd.com/list.html?cat=6233,6291,6302";
		System.out.println(jsoupJd.getPageNum(url));
		
	}
	
}
