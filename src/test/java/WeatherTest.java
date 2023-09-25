import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import domain.Weather;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import util.IPUtils;

import java.io.IOException;


public class WeatherTest {
    public static void main(String[] args) throws Exception {
        String publicIp = getPublicIp();//获取外网IP
        String cityInfo = IPUtils.getAddress(publicIp);//国家|区域|省份|城市|ISP
        System.out.println(cityInfo);
        String[] split = cityInfo.split("\\|"); //以|作为分隔符，要转义
        String city = "";
        for (String aSplit : split) if (aSplit.contains("市")) city = aSplit;//拿取市级名称
        System.out.println(city);

        String url = "http://portalweather.comsys.net.cn/weather03/api/weatherService/getDailyWeather?cityName=" + city;
        System.out.println(Jsoup.connect(url).get().text());

        JSONObject jsonObject = JSONObject.parseObject(Jsoup.connect(url).get().text());

        //获取 results 数组
        JSONArray results = jsonObject.getJSONArray("results");
        System.out.println(results);

        JSONObject weather =  results.getJSONObject(0).getJSONArray("daily").getJSONObject(0);
        System.out.println(weather);

        Weather w = new Weather(weather.getString("date"), city,weather.getString("text_day"),
                weather.getString("code_day"),weather.getString("text_night"),
                weather.getString("code_night"),weather.getString("high"),
                weather.getString("low")
                );
        System.out.println(w);
    }

    public static String getPublicIp() throws IOException {

        //网站源码
        Document doc = Jsoup.connect("http://www.net.cn/static/customercare/yourip.asp").get();
        Elements ip = doc.getElementsByTag("h2");//根据标签名找title元素
        System.out.println(ip.text());

        return ip.text();
    }

}
