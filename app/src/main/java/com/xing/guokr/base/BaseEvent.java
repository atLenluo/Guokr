package com.xing.guokr.base;

// 所有事件的基类
public class BaseEvent {

    public static final int TYPE_MESSAGE_BAR = 0; // 显示消息栏事件
    public static final int TYPE_USER_INFO = 1; // 更改用户信息

    // 事件类型
    private int type;

    public BaseEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
