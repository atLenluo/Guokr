package com.xing.guokr.utils;


import android.content.Context;

import com.xing.guokr.bean.NewsItem;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareUtils {

    public static void shareNews(Context context, NewsItem newsItem) {
        if (newsItem == null) {
            return;
        }
        ShareSDK.initSDK(context);

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(newsItem.getTitle());
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(newsItem.getLink());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(newsItem.getDesc());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(newsItem.getLink());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(newsItem.getSource());
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(newsItem.getLink());
        // 启动分享GUI
        oks.show(context);
    }
}
