package com.test.apiauto;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class zetlogin {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		HttpURLConnection loginrefconn = HttpRequester.httpUrlCon("http://hzxbuyer.hsilu.com:8091/view/login/tologin", "", "", "");
		//HttpRequester.hClient("http://hzxbuyer.hsilu.com:8091/view/login/tologin", "", "", "");
	
		//HttpURLConnection loginconn = HttpRequester.httpUrlCon(loginUrl, loginPar, _csrf, "");
/*		String cookie = loginconn.getHeaderField("Set-Cookie");
		String Location = loginconn.getHeaderField("Location");
		System.out.println("cookie===="+cookie);
		System.out.println("Location====="+Location);
		
		String setcookie = ShowHttpHeaders.getHeaderField(loginconn, "");
		System.out.println("setcookie===="+setcookie);
		
		HttpURLConnection loginrefconn = HttpRequester.httpUrlCon("http://hzxbuyer.hsilu.com:8091/view/login/tologin", "", "", setcookie);
		*/
		
	}
	
	public static String scrf(){
		try {
			Document doc = Jsoup.connect("http://hzxbuyer.hsilu.com:8091/view/login/tologin").get();
			Elements csrf = doc.getElementsByAttributeValue("name", "_csrf");
			String _csrf = csrf.attr("content");
			System.out.println(_csrf);
			return _csrf;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
}
