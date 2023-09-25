package web.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import domain.Weather;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import util.IPUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/weather/*")
public class WeatherServlet extends BaseServlet{

    //根据IP地址查询工具数据库找到市级名称，传入城市参数爬虫获取天气
    public void getWeather(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String publicIp = getPublicIp();//获取外网IP
        String cityInfo = IPUtils.getAddress(publicIp);//国家|区域|省份|城市|ISP
        String[] split = cityInfo.split("\\|"); //以|作为分隔符，要转义
        String city = "";
        for (String aSplit : split) if (aSplit.contains("市")) city = aSplit;//拿取市级名称

        String url = "http://portalweather.comsys.net.cn/weather03/api/weatherService/getDailyWeather?cityName="
                + city;

        JSONObject jsonObject = JSONObject.parseObject(Jsoup.connect(url).get().text());

        //获取 results 数组
        JSONArray results = jsonObject.getJSONArray("results");

        JSONObject weather =  results.getJSONObject(0).getJSONArray("daily").getJSONObject(0);

        Weather w = new Weather(weather.getString("date"), city,weather.getString("text_day"),
                weather.getString("code_day"),weather.getString("text_night"),
                weather.getString("code_night"),weather.getString("high"),
                weather.getString("low")
        );

        writeValue(w,response);
    }

    //爬虫获取IP地址
    public static String getPublicIp() throws IOException {

        //网站源码
        Document doc = Jsoup.connect("http://www.net.cn/static/customercare/yourip.asp").get();
        Elements ip = doc.getElementsByTag("h2");//根据标签名找title元素
        System.out.println(ip.text());

        return ip.text();
    }
}
