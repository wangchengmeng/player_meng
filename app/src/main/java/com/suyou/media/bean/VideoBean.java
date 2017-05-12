package com.suyou.media.bean;

public class VideoBean extends DownloadBaseBean {

    public static final int TYPE_CHINESE = 0;
    public static final int TYPE_ENGLISH = 1;

    private Integer sid;
    private String  name;
    private String  bigImage;
    private String  smallImage;
    private String  onlineUrl;
    private String  pinyinName;
    private String  producer;
    private Integer type;

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
