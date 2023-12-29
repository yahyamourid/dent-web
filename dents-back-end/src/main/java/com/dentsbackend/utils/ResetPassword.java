package com.dentsbackend.utils;


import com.dentsbackend.entities.User;

import java.time.LocalDateTime;
import java.util.Random;

public class ResetPassword {
    private static final int EXPIRATION_MINUTES = 5;

    public static String generateRandomCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return  Integer.toString(code);
    }
    public static boolean isResetCodeValid(User user) {

            LocalDateTime creationTime = user.getCodeActivationDate();
            LocalDateTime expirationTime = creationTime.plusMinutes(EXPIRATION_MINUTES);
            return LocalDateTime.now().isBefore(expirationTime);

    }


}
