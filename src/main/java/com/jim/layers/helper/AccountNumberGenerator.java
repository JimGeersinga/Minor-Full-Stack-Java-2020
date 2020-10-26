package com.jim.layers.helper;

import java.util.Random;

public class AccountNumberGenerator {
    private final static Random random = new Random();

    public static String generate() {
        return String.format("%08d", random.nextInt(99999999));
    }
}
