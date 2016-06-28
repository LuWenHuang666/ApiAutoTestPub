package com.test.apiauto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

public class TestServer extends HttpServlet{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
    private static final long serialVersionUID = 1L;
    private static JSONArray ja;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServer() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        try {
            this.excute(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        try {
            this.excute(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void excute(HttpServletRequest request,HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        String method=request.getMethod();
        String url=request.getRequestURI();
        String param;
           // 获取收到的报文
        BufferedReader reader = request.getReader();
        String line = "";
        line = reader.readLine();
        //ja=new JsonsUtil().ParseJson(line);		
        StringBuffer resultBuffer=new StringBuffer();
        resultBuffer.append("访问方式"+method+"访问成功");
        resultBuffer.append("接收到的数据："+line);
        PrintWriter out =response.getWriter();
        out.println(resultBuffer.toString());
        out.flush();
        out.close();
        
    }

}
