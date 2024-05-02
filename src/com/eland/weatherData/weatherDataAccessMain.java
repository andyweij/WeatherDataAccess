package com.eland.weatherData;

import com.eland.weatherData.service.WeatherInfoHandled;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;


public class weatherDataAccessMain {

    private static Logger logger = LogManager.getLogger(weatherDataAccessMain.class.getName());

    //args[0]=apiUrl.properties
    public static void main(String[] args){
//      Apache HttpClient 透過createDefault來創建一個 CloseableHttpClient 實例，使你能夠發送 HTTP 請求並處理相應，實例已經配置好了一些基本的參數，例如連接池、超時設置等，可以方便地用於執行 HTTP 請求。

        WeatherInfoHandled weatherInfoHandled = new WeatherInfoHandled();
        logger.debug("UrlConfigName:" + args[0]);
//          取得propertiesConfig(url)
        Map<String, String> urlMap = weatherInfoHandled.configReading(args[0]);

        for (String apiUrlKey : urlMap.keySet()) {
            logger.debug("API Url:" + apiUrlKey);
            String weatherApiInfoData = weatherInfoHandled.httpApiDataGet(urlMap.get(apiUrlKey));
            logger.debug("Start taskLog Data Conversion");
            weatherInfoHandled.taskLogConversion(weatherApiInfoData);
            logger.debug("Finished taskLog Data Conversion");
            logger.debug("Start weatherInfo Data Conversion");
            weatherInfoHandled.weatherInfoConversion(weatherApiInfoData);
            logger.debug("Finished weatherInfo Data Conversion");
        }
        logger.debug("Finished work");
    }
}