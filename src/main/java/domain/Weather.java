package domain;

public class Weather {
    String date;        //日期
    String locate;      //位置
    String day_type;    //白天天气
    String day_C;       //白天平均气温
    String night_type;  //夜晚天气
    String night_C;     //夜晚评价气温
    String high_C;      //最高温
    String low_C;       //最低温

    public Weather(String date,String locate, String day_type, String day_C, String night_type, String night_C, String high_C, String low_C) {
        this.date = date;
        this.locate = locate;
        this.day_type = day_type;
        this.day_C = day_C;
        this.night_type = night_type;
        this.night_C = night_C;
        this.high_C = high_C;
        this.low_C = low_C;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getDay_type() {
        return day_type;
    }

    public void setDay_type(String day_type) {
        this.day_type = day_type;
    }

    public String getDay_C() {
        return day_C;
    }

    public void setDay_C(String day_C) {
        this.day_C = day_C;
    }

    public String getNight_type() {
        return night_type;
    }

    public void setNight_type(String night_type) {
        this.night_type = night_type;
    }

    public String getNight_C() {
        return night_C;
    }

    public void setNight_C(String night_C) {
        this.night_C = night_C;
    }

    public String getHigh_C() {
        return high_C;
    }

    public void setHigh_C(String high_C) {
        this.high_C = high_C;
    }

    public String getLow_C() {
        return low_C;
    }

    public void setLow_C(String low_C) {
        this.low_C = low_C;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", locate='" + locate + '\'' +
                ", day_type='" + day_type + '\'' +
                ", day_C='" + day_C + '\'' +
                ", night_type='" + night_type + '\'' +
                ", night_C='" + night_C + '\'' +
                ", high_C='" + high_C + '\'' +
                ", low_C='" + low_C + '\'' +
                '}';
    }
}
