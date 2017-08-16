package jsoup;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestJsoup {
	@Test
	public void test() throws IOException{
		String url="https://list.jd.com/list.html?cat=670,671,672";
		Connection cn=Jsoup.connect(url);
		Response response=cn.execute();
		String html=response.body();
		System.out.println(html);
		
	}
	@Test
	public void site() throws IOException{
		String url="https://item.jd.com/5025518.html";
		Document doc=Jsoup.connect(url).get();
		Elements ele=doc.getElementsByTag("a");
		for (Element element : ele) {
			System.out.println(element.attr("href"));;
		}
	}
	
}
