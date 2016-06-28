package com.test.apiauto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ZETLoginIni {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**初始化海之讯登录页面，取得scrf和cookie存入全局参数类
	 * @param gl
	 */
	public static void zetlogin(GlobalSetting gl){
		String url = "http://hzxbuyer.hsilu.com:8091/view/login/tologin";
		
		try {
			//CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpClient httpClient = gl.getHttpClient();
	        HttpGet getRequest = new HttpGet();
	        getRequest.setURI(new URI(url));
	        HttpResponse response = httpClient.execute(getRequest);
	        String responseBody = getRequsetBody(response);
	        //设置scrf
	        Document doc = Jsoup.parse(responseBody);
	        Elements csrf = doc.getElementsByAttributeValue("name", "_csrf");
			String _csrf = csrf.attr("content");
			gl.set_csrf(_csrf);
			//设置set-cookie
			StringBuffer set_cookie = new StringBuffer();
			for (Header header : response.getAllHeaders()) {
	            if("Set-Cookie".equals(header.getName())) {
	                //System.out.println(header.getName() + " : " + header.getValue());
	                set_cookie.append(header.getValue());
	            }
	        }
			System.out.println("Cookie: "+set_cookie);
			System.out.println("_csrf: "+_csrf);
	        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	/**response结果
     * @param response  传入
     * @return  返回response 结果字符串
     */
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
        //System.out.println("返回信息： "+sb.toString());
        return sb.toString();
    }

}
