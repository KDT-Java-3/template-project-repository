package com.sparta.project1.domain.user.domain;

import java.util.concurrent.ThreadLocalRandom;

public class NicknameGenerator {
    private static final int NICKNAME_LENGTH = 6;
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private static final String CHARSETS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateNickname() {
        int length = NICKNAME_LENGTH;
        StringBuilder sb = new StringBuilder();
        while (length > 0) {
            int rd = random.nextInt(CHARSETS.length());
            sb.append(CHARSETS.toCharArray()[rd]);
            length -= 1;
        }
        return sb.toString();
    }
}
