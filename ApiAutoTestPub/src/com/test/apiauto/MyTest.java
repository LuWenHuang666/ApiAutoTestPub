package com.test.apiauto;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/6/10.
 */
public class MyTest {
    static String tologinUrl = "http://hzxbuyer.hsilu.com:8091/view/login/tologin";
    static String loginUrl = "http://hzxbuyer.hsilu.com:8091/view/login/loginvalidate";
    static String url = "http://hzxbuyer.hsilu.com:8091/";
    //static HttpClient httpClient = new DefaultHttpClient();

    public static String getRequsetBody(HttpResponse response) {
        StringBuffer sb = new StringBuffer("");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"))) {
            String line;
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void main(String[] args) throws URISyntaxException, IOException {
    	
    	CloseableHttpClient httpClient = HttpClients.createDefault();  
        //访问登录界面，获取token，请求头Cookie信息等
        HttpGet getRequest = new HttpGet(tologinUrl);
        //getRequest.setURI(new URI(tologinUrl));
        HttpResponse response = httpClient.execute(getRequest);
        String responseBody = getRequsetBody(response);
        //System.out.println(responseBody);
        
        Document doc = Jsoup.parse(responseBody);
        Elements csrf = doc.getElementsByAttributeValue("name", "_csrf");
		String _csrf = csrf.attr("content");
		System.out.println("_csrf: "+_csrf);
		//List<String> list = new ArrayList<>();
		StringBuffer set_cookie = new StringBuffer();
		for (Header header : response.getAllHeaders()) {
            if("Set-Cookie".equals(header.getName())) {
//                System.out.println(header.getClass().getName());
                //System.out.println(header.getName() + " : " + header.getValue());
//                postRequest.addHeader(header);
                //postRequest.setHeader("Cookie", header.getValue());
                //list.add(header.getValue());
                set_cookie.append(header.getValue());
            }
        }
		System.out.println("set_cookie: "+set_cookie);
		
		

		//String parString = "account=18006096894&password=a1d2c8d0db5cc8ebcb436c1ae6190808&jCaptcha=";
        HttpPost postRequest = new HttpPost();
        postRequest.setURI(new URI(loginUrl));
        postRequest.setHeader("X-CSRF-TOKEN", _csrf);
        postRequest.setHeader("Cookie", set_cookie.toString());
        
/*        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("account", "regedit0726"));
        nvps.add(new BasicNameValuePair("password", "b1a0b8ac4a382341f9fcaa0807dab17f"));
        nvps.add(new BasicNameValuePair("jCaptcha", ""));
        postRequest.setEntity(new UrlEncodedFormEntity(nvps));
        System.out.print("postRequest"+postRequest);
        HttpResponse response2 = httpClient.execute(postRequest);
        responseBody = getRequsetBody(response2);
        System.out.println(responseBody);*/
        
       
        
       String parString = "portId=3&portDate=2016-06-12&materialGoods=%5B%7B%22listImage%22%3A%22http%3A%2F%2Fimg.heysroad.com%2Fhzx%2Ftest%2Fupload%2Fshop%2Fc74d97b01eae257e44aa9d5bade97baf%2Fgoods%2Fstore%2F5344887a6c9d4bb199f0dec6ea28137f.jpg%22%2C%22id%22%3A47%2C%22materialName%22%3A%22%E4%B9%92%E4%B9%93%E7%90%83%22%2C%22price%22%3A52%2C%22unit%22%3A%22%E6%89%93%22%2C%22subtotal%22%3A52%2C%22quantity%22%3A1%2C%22orderComments%22%3A%22%22%7D%5D&othersGoods=%5B%5D&totalPirce=52&msg=&deliveryAddressId=26&invoiceId=-1&idCardNo=";
       String logString = "account=18006096894&password=a1d2c8d0db5cc8ebcb436c1ae6190808&jCaptcha=";
       
       postRequest.setEntity(new UrlEncodedFormEntity(stringToList(logString)));
       HttpResponse response2 = httpClient.execute(postRequest);
       responseBody = getRequsetBody(response2);
       System.out.println("responseBody  "+responseBody);
       
       
       
    	}
    
    public static List<NameValuePair> stringToList(String parString){
    	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    	String[] arrayStr1 = new String[] {};// 字符数组  
        String[] arrayStr2 = new String[] {};// 字符数组  
        arrayStr1 = parString.split("&");// 字符串转字符数组  
        for (String string1 : arrayStr1) {
     	   //System.out.println(arstring);
            arrayStr2 = string1.split("=");// 字符串转字符数组
//            for (String arstring2 : arrayStr2) {
//         	   System.out.println(arstring2);
// 			}
            String valueString = "";
            if (arrayStr2.length==2) {
 				valueString = arrayStr2[1];
 			}
            nvps.add(new BasicNameValuePair(arrayStr2[0], valueString));
 		}
        return nvps;
    }

}
