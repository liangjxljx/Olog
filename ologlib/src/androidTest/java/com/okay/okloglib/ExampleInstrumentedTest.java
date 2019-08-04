package com.okay.okloglib;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ljx.ologlib.api.StackTraceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.okay.okloglib.test", appContext.getPackageName());
    }

    @Test
    public void textStackUtils(){
        System.out.print(StackTraceUtils.getUpperStack(ExampleInstrumentedTest.class.getSimpleName()));
    }
}
