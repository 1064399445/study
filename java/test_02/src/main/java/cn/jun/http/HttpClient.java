package cn.jun.http;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpClient {

    public static String httpGet(String url,String param) throws Exception{
        String urlName = url + "?" + param;
        URL realUrl = new URL(urlName);
        //打开和url的连接
        URLConnection connection = realUrl.openConnection();
        //设置通用的请求属性
        connection.setRequestProperty("accept","*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        //建立实际的连接
        connection.connect();
        //获取所有的响应头字段
        String result = "";
        Map<String,List<String>> heads = connection.getHeaderFields();
        for(String key : heads.keySet()){
            result += key+":"+heads.get(key)+"\n";
        }
        //定义输入流来读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while((line=reader.readLine())!=null){
            result += line+"\n";
        }

        return result;
    }

    public static void  main(String[] args) throws Exception{
        String s=httpGet(
                "https://static-blog.csdn.net/mdeditor/public/res/bower-libs/MathJax/MathJax.js",
                "config=TeX-AMS-MML_HTMLorMML");
        System.out.println(s);
    }

}
