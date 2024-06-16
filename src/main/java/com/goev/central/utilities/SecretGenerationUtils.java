package com.goev.central.utilities;



import java.util.Random;
import java.util.UUID;

public class SecretGenerationUtils {
    private SecretGenerationUtils() {
    }

    public static String getCode() {
            Random generator = new Random();
            int num = generator.nextInt(89999) + 10000;
            return UUID.randomUUID().toString().substring(0,5)+"-"+String.valueOf(num);
    }
}
