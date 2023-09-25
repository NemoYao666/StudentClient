$(function () {
    $.post("weather/getWeather", {}, function (data) {
        $("#weather-date").text(data.date);
        $("#weather-locate").text(data.locate);
        $("#weather-high_low").text(data.low_C+" - "+data.high_C+" ℃");
        $("#weather-day_type").text("白天"+data.day_type+" 平均气温"+data.day_C+" ℃");
        $("#weather-night_type").text("夜晚"+data.night_type+" 平均气温"+data.night_C+" ℃");

    });
});
