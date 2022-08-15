package com.fzcoder.opensource.animeisland.util;

import java.util.UUID;

public class IdGenerator {
    public static String generateVideoItemId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateBangumiId() {
        // return Long.toString(System.currentTimeMillis());
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String  generateEpisodeId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
