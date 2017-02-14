package com.xing.guokr.event;

import com.xing.guokr.base.BaseEvent;
import com.xing.guokr.bean.User;

public class UserInfoEvent extends BaseEvent {

    private User mUser;

    public UserInfoEvent(User user) {
        super(TYPE_USER_INFO);
        this.mUser = user;
    }

    public User getUser() {
        return mUser;
    }
}
