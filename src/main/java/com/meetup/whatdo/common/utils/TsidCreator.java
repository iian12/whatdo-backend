package com.meetup.whatdo.common.utils;

import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;

@Component
public class TsidCreator {

    public static Long create() {
        return TSID.fast().toLong();
    }
}
