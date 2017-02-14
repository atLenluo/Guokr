package com.xing.guokr.http;

// 网络请求接口
public final class HttpApi {

    public static final String GUOKR_URL = "http://192.168.2.107:8080/GuokrService/";
    public static final String PATH_VERYTEL = "VeryTelServlet";
    public static final String PATH_REGISTER = "RegisterServlet";
    public static final String PATH_LOGIN = "LoginServlet";

    public static final String BASE_URL = "http://route.showapi.com/";
    public static final String PATH_CHANNEL = "109-34"; // 频道查询api
    public static final String PATH_NEWS = "109-35"; // 新闻查询api
    public static final String PATH_HOT = "738-1"; // 热搜榜查询api
    public static final String PATH_VERYCODE = "26-4"; // 验证码生成api
    public static final String APPID = "30095";
    public static final String SECRET = "3284f4dddd11465f9c6978df8f3fbb74";

    public final class Params {
        // 授权参数
        public static final String SHOWAPI_APPID = "showapi_appid";
        public static final String SHOWAPI_SIGN = "showapi_sign";
        // 新闻查询参数
        public static final String CHANNELID = "channelId";
        public static final String CHANNELNAME = "channelName";
        public static final String TITLE = "title";
        public static final String PAGE = "page";
        public static final String NEEDCONTENT = "needContent";
        public static final String NEEDHTML = "needHtml";
        public static final String NEEDALLLIST = "needAllList";
        public static final String MAXRESULT = "maxResult";
        // 热搜榜参数
        public static final String N = "n";
        // 验证码参数
        public static final String TEXTPRODUCER_CHAR_STRING = "textproducer_char_string";
        public static final String TEXTPRODUCER_CHAR_LENGTH = "textproducer_char_length";
        public static final String BORDER = "border";
    }

}
