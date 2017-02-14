package com.xing.guokr;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.xing.guokr.bean.Channel;
import com.xing.guokr.bean.ChannelDao;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.xing.guokr", appContext.getPackageName());
    }

    @Test
    public void testDao() {
        ChannelDao channelDao = GuokrApplicaton.getDaoSession().getChannelDao();
        channelDao.insert(new Channel("5572a108b3cdc86cf39001cd", "国内焦点"));
        channelDao.insert(new Channel("5572a108b3cdc86cf39001ce", "国际焦点"));
        channelDao.insert(new Channel("5572a108b3cdc86cf39001cf", "军事焦点"));
        channelDao.insert(new Channel("5572a108b3cdc86cf39001d0", "财经焦点"));
    }

    @Test
    public void textRx() {
    }
}
