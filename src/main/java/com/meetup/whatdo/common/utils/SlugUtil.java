package com.meetup.whatdo.common.utils;

import com.github.slugify.Slugify;

public class SlugUtil {
    private static final Slugify slugify = Slugify.builder().build();

    public static String slugify(String input) {
        return slugify.slugify(input);
    }
}
