package com.ecommerce.usermanagement.domain;

public class Constants {

    public final static String EMAIL_SUBJECT = "Email Doğrulama";
    public final static String EMAIL_MESSAGE = "Doğrulama için kodunuz : ";
    public final static int VERIFICATION_CODE_LENGTH = 6;

    public final static int REDIS_EXPIRATION_MIN = 5;
}
