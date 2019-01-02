package com.example.android.jokelib;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class JokeClass {

    private static final Random sRandom = new Random();
    private static final List<String> JOKES = Arrays.asList(
            "Hello broh.. this is my jokee one",
            "Hello broh.. this is my jokee two",
            "Hello broh.. this is my jokee three");

    public static String getJoke() {
        int pos = sRandom.nextInt(JOKES.size());
        return JOKES.get(pos);
    }
}
