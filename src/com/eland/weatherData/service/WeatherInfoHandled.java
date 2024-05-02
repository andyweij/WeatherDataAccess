package com.eland.weatherData.service;

import com.eland.weatherData.DAO.WeatherInfoDAO;
import com.eland.weatherData.model.TaskLogEntity;
import com.eland.weatherData.model.WeatherInfoEntity;
import com.google.gson.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherInfoHandled {

    private static Logger logger = LogManager.getLogger(WeatherInfoHandled.class.getName());

    public void taskLogConversion(String responseString) {
        // Parse JSON using Gson，JsonParser 類別來解析該 JSON 字串，並將其轉換為 JsonObject
        JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
        logger.info("Crate_JsonObject");
        TaskLogEntity taskLog = null;
        logger.info("TaskLog:" + jsonObject.get("status").getAsString());
        //Access keys and values in JSON
        //Status=200 and  message 不包含not found
        if (jsonObject.size() != 0 && (jsonObject.get("status").getAsString().equals("200") && !jsonObject.get("message").getAsString().contains("not found"))) {

            taskLog = new TaskLogEntity();//建立taskLog物件
            logger.info("Crate_TaskLog");
            taskLog.setMessage(jsonObject.get("message").getAsString());
            taskLog.setCity(jsonObject.get("cityInfo").getAsJsonObject().get("city").getAsString());
            taskLog.setStatus(Integer.parseInt(jsonObject.get("status").getAsString()));
            taskLog.setJsonTxt(responseString);
            String datePattern = "yyyy-MM-dd HH:mm:ss";
            taskLog.setUpdateTime(convertDate(jsonObject.get("time").getAsString(), datePattern));
            try {
            //返回一個代表本機主機的 InetAddress 物件
                InetAddress localMachine = InetAddress.getLocalHost();
                logger.debug("HostName="+localMachine.getHostName());
                logger.debug("HostIP="+localMachine.getHostAddress());
                taskLog.setMachineName(localMachine.getHostName() + "/" + localMachine.getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            WeatherInfoDAO weatherInfoDAO = new WeatherInfoDAO();
            weatherInfoDAO.insertTaskLog(taskLog);
            logger.debug("insert_TaskLogData_Finished");
        }
    }

    public void weatherInfoConversion(String responseString) {
        JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
        JsonArray forecastArray = jsonObject.getAsJsonObject("data").getAsJsonArray("forecast");
        logger.debug("WeatherData:" + forecastArray);
//      取的每日天氣的資訊
//      用於表示 JSON 格式中的數組
        WeatherInfoEntity weatherInfo = null;
        List<WeatherInfoEntity> weatherInfoList = null;
        logger.info("TaskLog:" + jsonObject.get("status").getAsString());
//        message 不包含not found and Status=200
        if (forecastArray.size() != 0 && (jsonObject.get("status").getAsString().equals("200") && !jsonObject.get("message").getAsString().contains("not found"))) {

            weatherInfoList = new ArrayList<>();
            for (JsonElement forecast : forecastArray) {
                JsonObject dayOfInfo = forecast.getAsJsonObject();
                String datePattern = "yyyy-MM-dd";
                weatherInfo = new WeatherInfoEntity();

                logger.debug("Create_weatherInfo:物件建立");
                weatherInfo.setCity(jsonObject.get("cityInfo").getAsJsonObject().get("city").getAsString());
                weatherInfo.setDate(convertDate(dayOfInfo.get("ymd").getAsString(), datePattern));
                logger.debug("weatherInfo:Date格式轉換=" + weatherInfo.getDate());
                weatherInfo.setSunrise(convertTime(dayOfInfo.get("sunrise").getAsString()));
                logger.debug("weatherInfo:SunriseTime格式轉換=" + weatherInfo.getSunrise());
                weatherInfo.setSunset(convertTime(dayOfInfo.get("sunset").getAsString()));
                logger.debug("weatherInfo:setSunsetTime格式轉換=" + weatherInfo.getSunset());
                weatherInfo.setAqi(dayOfInfo.get("aqi").getAsString());
                weatherInfo.setFl(convertStringToNum(dayOfInfo.get("fl").getAsString()));
                weatherInfo.setFx(dayOfInfo.get("fx").getAsString());
                weatherInfo.setHigh(convertStringToNum(dayOfInfo.get("high").getAsString()));
                weatherInfo.setLow(convertStringToNum(dayOfInfo.get("low").getAsString()));
                weatherInfo.setType(dayOfInfo.get("type").getAsString());
                weatherInfo.setNotice(dayOfInfo.get("notice").getAsString());
                weatherInfoList.add(weatherInfo);
            }
            WeatherInfoDAO weatherInfoDAO = new WeatherInfoDAO();
            weatherInfoDAO.insertWeatherData(weatherInfoList);
            logger.info("insert_weatherInfoList_Finished");
        }
    }

    public Date convertDate(String time, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        Date updateTimeObj = new Date();
        try {
            updateTimeObj = sf.parse(time);
            logger.debug("updateTimeObj");
        } catch (ParseException e) {
            logger.debug(e.getStackTrace());
        }
        return updateTimeObj;
    }

    public Time convertTime(String time) {
        SimpleDateFormat timef = new SimpleDateFormat("HH:mm");
        Date dateObj = new Date();
        Time timeObj = new Time(dateObj.getTime());
        try {
            dateObj = timef.parse(time);
            timeObj = new Time(dateObj.getTime());
        } catch (ParseException e) {
            logger.info(e.getStackTrace());
            logger.info(e.getMessage());
        }
        return timeObj;
    }

    public Integer convertStringToNum(String param) {
        Pattern numberPattern = Pattern.compile("-?\\d+");
        Matcher reservedNum = numberPattern.matcher(param);
        if (reservedNum.find()) {
            String numeric = reservedNum.group();
            logger.debug("pattern取字串中的數字(包含-號)=" + numeric);
            return Integer.parseInt(numeric);
        }
        return null;
    }

    public Map<String, String> configReading(String fileName) {
        Map<String, String> apiUrlMap = null;
        Properties apiUrlProp = null;
        try (InputStream inputStream = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8")) {
//          取得apiUrl.properties
            apiUrlProp = new Properties();
            apiUrlProp.load(isr);
            String targetCities[] = apiUrlProp.getProperty("City").split(",");
            apiUrlMap = new HashMap<>();
            for (String apiUrlKey : targetCities) {
                apiUrlMap.put(apiUrlKey, apiUrlProp.getProperty(apiUrlKey));
            }
        } catch (IOException e) {
            logger.info(e.getStackTrace());
            logger.info(e.getMessage());
        }
        return apiUrlMap;
    }

    public String httpApiDataGet(String URL) {

        String responseBody = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
//              創建了一個 GET 請求的建構器，帶入URL
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get(URL).build();
            responseBody = httpclient.execute(httpGet, response -> {
                //status:请求结果 and 短語
                logger.info(response.getCode() + " " + response.getReasonPhrase());
//              獲取 HTTP 響應的實體內容
                final HttpEntity entity = response.getEntity();
//              Convert entity content to a string
                String taskLogRespString = EntityUtils.toString(entity);
                logger.debug(taskLogRespString);
                return taskLogRespString;
            });
        } catch (IOException e) {
            logger.info(e.getStackTrace());
            logger.info(e.getMessage());
        }
        return responseBody;
    }

}
