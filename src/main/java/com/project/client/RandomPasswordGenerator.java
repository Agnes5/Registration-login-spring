package com.project.client;

import java.util.Random;

public class RandomPasswordGenerator {
    private static final String ALPHA_CAPS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA   = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM     = "0123456789";
    private static final String SPL_CHARS   = "!@#$%^&*_=+-/";
    private static final int min = 8;
    private static final int max = 32;

    public static char[] generatePassword() {
        Random random = new Random();
        int len = random.nextInt(max - min + 1) + min;
        char[] password = new char[len];

        password[getNextIndex(random, len, password)] = ALPHA_CAPS.charAt(random.nextInt(ALPHA_CAPS.length()));
        password[getNextIndex(random, len, password)] = NUM.charAt(random.nextInt(NUM.length()));
        password[getNextIndex(random, len, password)] = SPL_CHARS.charAt(random.nextInt(SPL_CHARS.length()));

        for (int i = 0; i < len; i++) {
            if (password[i] == 0) {
                password[i] = ALPHA.charAt(random.nextInt(ALPHA.length()));
            }
        }

        return password;
    }

    private static int getNextIndex(Random rnd, int len, char[] pswd) {
        int index;
        while(pswd[index = rnd.nextInt(len)] != 0);
        return index;
    }
}
