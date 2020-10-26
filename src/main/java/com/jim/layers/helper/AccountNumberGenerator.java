package com.jim.layers.helper;

import org.iban4j.CountryCode;
import org.iban4j.Iban;

import java.util.Random;

public class AccountNumberGenerator {
    private final static Random random = new Random();

    public static String generate() {
        return String.format("%08d", random.nextInt(99999999));
    }

    public static String generateIban(String bankCode, String accountNumber) {
        String accNumberAddition = String.format("%02d", random.nextInt(99));
        Iban iban = new Iban.Builder()
                .countryCode(CountryCode.NL)
                .bankCode(bankCode)
                .accountNumber(accountNumber + accNumberAddition)
                .build();
       return iban.toFormattedString();
    }
}
