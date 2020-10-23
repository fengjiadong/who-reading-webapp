//package com.who.read.reading;
//
//import com.who.read.reading.configuration.JsonManager;
//import com.who.read.reading.utils.HttpClient;
//import com.who.read.reading.who.util.UUID;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Classname Test
// * @date 2020/3/30 4:37 PM
// * @Created by fengjiadong
// */
//public class Test {
//	@org.junit.jupiter.api.Test
//	public void uuid(){
//		for (int i = 0; i < 10; i++) {
//			System.out.println("(#id:"+UUID.generateUUID()+"#)-#-");
//		}
//	}
//
//	public static void main(String[] args) throws IOException {
//		Document doc = Jsoup.connect("http://www.biquge.info/list/3_1.html").get();
//		Element newscontent = doc.getElementById("newscontent");
//
////		Elements images = doc.getElementsByClass("image");
////		for (Element image : images) {
////			String attr = image.getElementsByTag("a").get(0).attr("href");
////			String href = attr.substring(attr.indexOf("fo/")+3, attr.length() - 1);
////			System.out.print(href+"-----");
////			System.out.print(image.getElementsByTag("img").get(0).attr("alt"));
////			System.out.println(image.getElementsByTag("img").get(0).attr("src"));
////		}
//		Elements li = newscontent.getElementsByTag("li");
//		for (Element element : li) {
////			System.out.println(element.html());
//			Elements s1 = element.getElementsByClass("s1");
//			Elements s2 = element.getElementsByClass("s2");
//			Elements s4 = element.getElementsByClass("s4");
//			Elements s5 = element.getElementsByClass("s5");
//			System.out.println("书名:" + s2.get(0).getElementsByTag("a").get(0).html());
//			String attr = s2.get(0).getElementsByTag("a").get(0).attr("href");
//			String href = attr.substring(attr.indexOf("fo/")+3, attr.length() - 1);
//			System.out.println("num:" +href);
//			System.out.println("类型:" + s1.get(0).html());
//			if (s4.size() < 1) {
//				System.out.println("作者:" + s5.get(0).html());
//			}else{
//				System.out.println("作者:" + s4.get(0).html());
//			}
//		}
//	}
//
//	@org.junit.jupiter.api.Test
//	public void test() throws IOException, JSONException {
//		String url = "http://127.0.0.1:4399/rmtj/api/test/test3.jsp";
//		String s = HttpClient.doGet(url);
//		JSONArray jsonArray = new JSONArray(s.trim());
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jsonObject = jsonArray.getJSONObject(i);
//			System.out.print(JsonManager.getValue(jsonObject, "name"));
//			System.out.print(JsonManager.getValue(jsonObject, "age"));
//			System.out.print(JsonManager.getValue(jsonObject, "id"));
//			System.out.print(JsonManager.getValue(jsonObject, "username"));
//			System.out.println(JsonManager.getValue(jsonObject, "gender"));
//			System.out.println(JsonManager.getValue(jsonObject, "idNumber"));
//			System.out.println(JsonManager.getValue(jsonObject, "phone"));
//			System.out.println(JsonManager.getValue(jsonObject, "email"));
//		}
//	}
//
//	@org.junit.jupiter.api.Test
//	public void calcolate(){
//
//
//		ArrayList<String> xz = num();
//		for (String s : xz) {
//			System.out.print(s+"-");
//		}
//		System.out.println("-----------------");
//		sum(xz);
//
//
//	}
//
//	public void sum(ArrayList<String> xz) {
//		Boolean is = false;
//		Integer count = 0;
//		while (!is) {
//			count++;
//			ArrayList<String> results = num();
//			Integer isClick = 0;
//			for (String result : results) {
//				boolean contains = xz.contains(result);
//				if(contains){
//					System.out.print(result+"-");
//					isClick++;
//				}
//			}
//			if (isClick > 0) {
//				System.out.println();
//			}
//			if (isClick == 6) {
//				is = true;
//				System.out.println("执行了"+count);
//			}
//		}
//	}
//
//	public ArrayList<String> num(){
//		int max=33,min=1;
//		ArrayList<Integer> objects = new ArrayList<>();
//		for (int i = 0; i < 6; i++) {
//			int ran2 = (int) (Math.random()*(max-min)+min);
//			while (objects.contains(ran2)) {
//				ran2 = (int) (Math.random()*(max-min)+min);
//			}
//			objects.add(ran2);
//		}
//		Collections.sort(objects, new SortByAge()); //按照年龄排序
//		ArrayList<String> list = new ArrayList<>();
//		for (Integer num : objects) {
//			if (num < 10) {
//				list.add("0" + num);
//			}else{
//				list.add(num + "");
//			}
//		}
//		return list;
//	}
//	//年龄排序
//	class SortByAge implements Comparator<Integer> {
//		@Override
//		public int compare(Integer u1, Integer u2) {
//			if(u1 > u2){
//				return 1;
//			}
//			return -1;
//		}
//
//	}
//}
