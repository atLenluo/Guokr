package com.xing.guokr.event;


import com.xing.guokr.base.BaseEvent;


public class MessageBarEvent extends BaseEvent {

    private int iconResId;
    private String message;
    private int showType;

    public MessageBarEvent(int iconResId, String message, int showType) {
        super(TYPE_MESSAGE_BAR);
        this.iconResId = iconResId;
        this.message = message;
        this.showType = showType;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getShowType() {
        return showType;
    }
}
