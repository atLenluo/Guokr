package com.xing.guokr.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Channel {

    @Id
    private String channelId;
    private String name;

    @Generated(hash = 833255958)
    public Channel(String channelId, String name) {
        this.channelId = channelId;
        this.name = name;
    }

    @Generated(hash = 459652974)
    public Channel() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Channel channel = (Channel) o;

        return channelId.equals(channel.channelId);

    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                '}';
    }
}
