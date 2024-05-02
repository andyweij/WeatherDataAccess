package com.eland.weatherData.model;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "weather_info", schema = "weather", catalog = "weather")
public class WeatherInfoEntity {
    private int id;
    private String city;
    private Date date;
    private Time sunrise;
    private Integer high;
    private Integer low;
    private Time sunset;
    private String aqi;
    private String fx;
    private Integer fl;
    private String type;
    private String notice;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "sunrise")
    public Time getSunrise() {
        return sunrise;
    }

    public void setSunrise(Time sunrise) {
        this.sunrise = sunrise;
    }

    @Basic
    @Column(name = "high")
    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    @Basic
    @Column(name = "low")
    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    @Basic
    @Column(name = "sunset")
    public Time getSunset() {
        return sunset;
    }

    public void setSunset(Time sunset) {
        this.sunset = sunset;
    }

    @Basic
    @Column(name = "aqi")
    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    @Basic
    @Column(name = "fx")
    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    @Basic
    @Column(name = "fl")
    public Integer getFl() {
        return fl;
    }

    public void setFl(Integer fl) {
        this.fl = fl;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "notice")
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherInfoEntity that = (WeatherInfoEntity) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(sunrise, that.sunrise) &&
                Objects.equals(high, that.high) &&
                Objects.equals(low, that.low) &&
                Objects.equals(sunset, that.sunset) &&
                Objects.equals(aqi, that.aqi) &&
                Objects.equals(fx, that.fx) &&
                Objects.equals(fl, that.fl) &&
                Objects.equals(type, that.type) &&
                Objects.equals(notice, that.notice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, sunrise, high, low, sunset, aqi, fx, fl, type, notice);
    }
}
