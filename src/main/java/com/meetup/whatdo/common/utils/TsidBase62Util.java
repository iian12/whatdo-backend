package com.meetup.whatdo.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TsidBase62Util {

    private static String alphabet;
    private static final int BASE = 62;

    public TsidBase62Util(@Value("${base62.secret}") String alphabet) {
        TsidBase62Util.alphabet = alphabet;
    }

    public static String encode(long tsid) {
        StringBuilder sb = new StringBuilder();

        while (tsid > 0) {
            sb.append(alphabet.charAt((int) (tsid % BASE)));
            tsid /= BASE;
        }

        return sb.reverse().toString();
    }

    public static long decode(String base62) {
        if (base62 == null || base62.isEmpty()) return 0;
        long result = 0;
        for (char c : base62.toCharArray()) {
            result = result * BASE + alphabet.indexOf(c);
        }

        return result;
    }
}
