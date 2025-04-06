package com.shikhar03stark.util;

import java.util.Random;

public class RandomIdGenerator {

    private static final Random random = new Random();

    public static String generateRandomId(int length) {
        StringBuilder id = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomChar = random.nextInt(36); // 0-9 + a-z
            if (randomChar < 10) {
                id.append((char) ('0' + randomChar)); // 0-9
            } else {
                id.append((char) ('a' + randomChar - 10)); // a-z
            }
        }
        return id.toString();
    }
}
