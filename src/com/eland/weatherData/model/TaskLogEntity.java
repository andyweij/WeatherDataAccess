package com.eland.weatherData.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "task_log", schema = "dbo", catalog = "task")
public class TaskLogEntity {
    private int id;
    private String city;
    private Date updateTime;
    private int status;
    private String machineName;
    private String message;
    private String jsonTxt;

    @Basic
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
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "machine_name")
    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "json_txt")
    public String getJsonTxt() {
        return jsonTxt;
    }

    public void setJsonTxt(String jsonTxt) {
        this.jsonTxt = jsonTxt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskLogEntity that = (TaskLogEntity) o;
        return id == that.id &&
                status == that.status &&
                Objects.equals(city, that.city) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(machineName, that.machineName) &&
                Objects.equals(message, that.message) &&
                Objects.equals(jsonTxt, that.jsonTxt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, updateTime, status, machineName, message, jsonTxt);
    }
}
