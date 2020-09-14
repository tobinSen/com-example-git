package com.example.spring.mpush.mpush;

import com.alibaba.fastjson.annotation.JSONField;

public class PustMessageDTO {

    private String from_code;

    private String to_app_code;

    private String to_code;

    private Integer type;

    private String sound;

    private String data;

    private String extra;

    private String title;

    private Integer badge;

    private String sub_title_ios;

    private String sub_title_android;

    private String sub_voice_title_ios;

    private Long hold_time;

    private String mid;

    private Long create_time;

    @JSONField(serialize = false)
    private Integer messageType;

    public String getFrom_code() {
        return this.from_code;
    }

    public String getTo_app_code() {
        return this.to_app_code;
    }

    public String getTo_code() {
        return this.to_code;
    }

    public Integer getType() {
        return this.type;
    }

    public String getSound() {
        return this.sound;
    }

    public String getData() {
        return this.data;
    }

    public String getExtra() {
        return this.extra;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSub_title_ios() {
        return this.sub_title_ios;
    }

    public String getSub_title_android() {
        return this.sub_title_android;
    }

    public String getSub_voice_title_ios() {
        return this.sub_voice_title_ios;
    }

    public Long getHold_time() {
        return this.hold_time;
    }

    public String getMid() {
        return this.mid;
    }

    public Long getCreate_time() {
        return this.create_time;
    }

    public Integer getMessageType() {
        return this.messageType;
    }

    public void setFrom_code(String from_code) {
        this.from_code = from_code;
    }

    public void setTo_app_code(String to_app_code) {
        this.to_app_code = to_app_code;
    }

    public void setTo_code(String to_code) {
        this.to_code = to_code;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSub_title_ios(String sub_title_ios) {
        this.sub_title_ios = sub_title_ios;
    }

    public void setSub_title_android(String sub_title_android) {
        this.sub_title_android = sub_title_android;
    }

    public void setSub_voice_title_ios(String sub_voice_title_ios) {
        this.sub_voice_title_ios = sub_voice_title_ios;
    }

    public void setHold_time(Long hold_time) {
        this.hold_time = hold_time;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getBadge() {
        return this.badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String toString() {
        return "PustMessageDTO{from_code='" + this.from_code + '\'' + ", to_app_code='" + this.to_app_code + '\'' + ", to_code='" + this.to_code + '\'' + ", type=" + this.type + ", sound='" + this.sound + '\'' + ", data='" + this.data + '\'' + ", extra='" + this.extra + '\'' + ", title='" + this.title + '\'' + ", badage=" + this.badge + ", sub_title_ios='" + this.sub_title_ios + '\'' + ", sub_title_android='" + this.sub_title_android + '\'' + ", sub_voice_title_ios='" + this.sub_voice_title_ios + '\'' + ", hold_time=" + this.hold_time + ", mid='" + this.mid + '\'' + ", create_time=" + this.create_time + ", messageType=" + this.messageType + '}';
    }
}
