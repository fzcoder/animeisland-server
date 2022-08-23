package com.fzcoder.opensource.animeisland.util;

import java.util.UUID;

public class IdGenerator {
    public static String generateVideoItemId() {
        return generateUUID();
    }

    public static String generateBangumiId() {
        return generateUUID();
    }

    public static String  generateEpisodeId() {
        return generateUUID();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
