package com.xing.guokr.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class NewsItem implements Parcelable {

    private String pubDate; // 发行日期
    private Boolean havePic = false; // 是否有图片
    private String title; // 新闻标题
    private String channelName; // 新闻频道
    private String desc; // 描述（摘要）
    private String source; // 来源
    private String channelId; // 频道ID
    private String link; // 新闻详情链接
    private String content; // 正文
    private String html; // html正文
    private List<ImageItem> imageurls; // 图片

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(Boolean havePic) {
        this.havePic = havePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<ImageItem> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<ImageItem> imageurls) {
        this.imageurls = imageurls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pubDate);
        dest.writeValue(this.havePic);
        dest.writeString(this.title);
        dest.writeString(this.channelName);
        dest.writeString(this.desc);
        dest.writeString(this.source);
        dest.writeString(this.channelId);
        dest.writeString(this.link);
        dest.writeString(this.content);
        dest.writeString(this.html);
        dest.writeList(this.imageurls);
    }

    public NewsItem() {
    }

    protected NewsItem(Parcel in) {
        this.pubDate = in.readString();
        this.havePic = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.title = in.readString();
        this.channelName = in.readString();
        this.desc = in.readString();
        this.source = in.readString();
        this.channelId = in.readString();
        this.link = in.readString();
        this.content = in.readString();
        this.html = in.readString();
        this.imageurls = new ArrayList<ImageItem>();
        in.readList(this.imageurls, ImageItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel source) {
            return new NewsItem(source);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
}
