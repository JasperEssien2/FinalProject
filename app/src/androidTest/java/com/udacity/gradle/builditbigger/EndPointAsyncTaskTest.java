package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest {

    private String jokeValue = null;

    @Test
    public void doInBackgroundTest() {
        new MainActivity.EndPointAsyncTask(new MainActivity.CallBacks() {
            @Override
            public void onPostExecuteCallback(String s) {
                jokeValue = s;
            }
        }).execute();
        try {
            Thread.sleep(5000);
            assertTrue("------ Joke Value Not Null ------", jokeValue != null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
