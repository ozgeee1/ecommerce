package com.ecommerce.usermanagement.constants;

public class ApplicationConstants {

    public final static String EMAIL_SUBJECT = "Email Doğrulama";

    public final static String EMAIL_MESSAGE = "Doğrulama için kodunuz : ";
    public final static int VERIFICATION_CODE_LENGTH = 6;
    public final static int REDIS_EXPIRATION_MIN = 5;

    public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";

    public static final long JWT_TOKEN_VALIDITY = 5*60*60;

    public static final String JWT_HEADER = "Authorization";
}
