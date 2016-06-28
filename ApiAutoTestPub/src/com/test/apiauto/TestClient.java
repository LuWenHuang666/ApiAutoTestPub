package com.test.apiauto;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestClient a=new TestClient();
        try {
            //a.client();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
/**发起post请求或get请求的客户端
 * @param gl 全局参数
 * @param pa 测试用例中 接口请求参数和比较参数
 * @throws Exception
 */
public static void client(GlobalSetting gl,Param pa) throws Exception{
            try {
                // 接报文的地址
                //String filePath="D:\\learn\\test.xls";
            	//发送的参数
                String param= pa.getParam();
                //接口的URL
                URL serverUrl= new URL(pa.getUrl());

                URLConnection uct= serverUrl.openConnection();
                HttpURLConnection hutc=(HttpURLConnection)uct;	
                // 接口的请求类型
                hutc.setRequestMethod(pa.getMethod());
                
                // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true, 默认情况下是false; 
                hutc.setDoOutput(true);
                
                // 设置是否从httpUrlConnection读入，默认情况下是true
                hutc.setDoInput(true);	
                //hutc.setAllowUserInteraction(true);
                
                // 开启流，写入数据data
                OutputStream out=hutc.getOutputStream();
                
                out.write(param.getBytes("UTF-8"));
                out.flush();
                out.close();
                
                // 获取返回的数据	
                StringBuffer buffer=new StringBuffer();
                BufferedReader reader = null;
                InputStream ins=hutc.getInputStream();
                reader = new BufferedReader(new InputStreamReader(ins,"UTF-8"));
                String sg=reader.readLine();
                if (sg!= null){
                       buffer.append(sg);
                 }
                System.out.println("接收返回值:" + buffer);
                //结果断言
                CompareRes.compare(gl,pa, buffer.toString());
                
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

}
