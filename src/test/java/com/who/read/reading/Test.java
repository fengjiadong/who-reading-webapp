package com.who.read.reading;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @Classname Test
 * @date 2020/3/30 4:37 PM
 * @Created by fengjiadong
 */
public class Test {
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://www.biquge.info/list/3_1.html").get();
		Element newscontent = doc.getElementById("newscontent");

//		Elements images = doc.getElementsByClass("image");
//		for (Element image : images) {
//			String attr = image.getElementsByTag("a").get(0).attr("href");
//			String href = attr.substring(attr.indexOf("fo/")+3, attr.length() - 1);
//			System.out.print(href+"-----");
//			System.out.print(image.getElementsByTag("img").get(0).attr("alt"));
//			System.out.println(image.getElementsByTag("img").get(0).attr("src"));
//		}
		Elements li = newscontent.getElementsByTag("li");
		for (Element element : li) {
//			System.out.println(element.html());
			Elements s1 = element.getElementsByClass("s1");
			Elements s2 = element.getElementsByClass("s2");
			Elements s4 = element.getElementsByClass("s4");
			Elements s5 = element.getElementsByClass("s5");
			System.out.println("书名:" + s2.get(0).getElementsByTag("a").get(0).html());
			String attr = s2.get(0).getElementsByTag("a").get(0).attr("href");
			String href = attr.substring(attr.indexOf("fo/")+3, attr.length() - 1);
			System.out.println("num:" +href);
			System.out.println("类型:" + s1.get(0).html());
			if (s4.size() < 1) {
				System.out.println("作者:" + s5.get(0).html());
			}else{
				System.out.println("作者:" + s4.get(0).html());
			}
		}
	}

	@org.junit.jupiter.api.Test
	public void test() throws IOException {
		String url = "http://www.biquge.info/55_55369/";
		Document doc = Jsoup.connect(url).get();
		Element intro = doc.getElementById("intro");
		System.out.println("简介:"+intro.getElementsByTag("p").get(0).html());

		Element fmimg = doc.getElementById("fmimg");
		Elements img = fmimg.getElementsByTag("img");
		String src = img.attr("src");
		System.out.println("图片：" + src);
	}
}
