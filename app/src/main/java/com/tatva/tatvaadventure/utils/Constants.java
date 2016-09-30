package com.tatva.tatvaadventure.utils;

/*
 email:- tatva1010@gmail.com
 password:- tatvapassword
 */

import com.tatva.tatvaadventure.model.EventDetail;

public class Constants {
    //Google GCM API information
    public static String API_KEY = "AIzaSyAMItmKtBMXH1Z-X5Fw-ZwrYfdii7gQxeU";

    //Web-API Auth + URL.
    public static String URL_TEST = "http://192.168.1.90:8080/Tatva/post/test";

    public static String AUTH_REG = "AUTH_REG";
    public static String URL_REG = "http://192.168.1.90:8080/Tatva/device/register";

    public static String AUTH_DEL = "AUTH_DEL";
    public static String URL_DEL = "http://192.168.1.90:8080/Tatva/device/removeall";

    public static String AUTH_REM = "AUTH_REM";
    public static String URL_REM = "http://192.168.1.90:8080/Tatva/content/delete";

    public static String AUTH_SAVE = "AUTH_SAVE";
    public static String URL_SAVE = "http://192.168.1.90:8080/Tatva/newcontent/add";

    public static String AUTH_GET = "AUTH_GET";
    public static String URL_GET = "http://192.168.1.90:8080/Tatva/getcontent/fetchall";

    public static String AUTH_PUSH = "AUTH_PUSH";
    public static String URL_PUSH = "http://192.168.1.90:8080/Tatva/push/gcm";

    //HTTP Response Code
    public static String S_OK_200 = "OK";                //success
    public static String S_BR_400 = "BAD REQUEST";        //Unmatched request packet
    public static String S_UA_401 = "UNAUTHORIZED";        //Invalid auth-key
    public static String S_F_403 = "FORBIDDEN";            //Exception: http-client-connection
    public static String S_NF_404 = "NOT FOUND";
    public static String S_NA_406 = "NOT ACCEPTABLE";    //duplicate-details
    public static String S_TO_504 = "TIMEOUT";
    public static String S_C_409 = "CONFLICT";
    public static String S_ISE_500 = "INTERNAL SERVER ERROR";    //Exception, Connection-error(jdbc)

    //Custom Response Code
    public static String C_JPE_71 = "JSON Packing Exception";    //JSONException(packing)
    public static String C_JRE_72 = "JSON Packing Exception";    //JSONException (parsing)
    public static String C_UHE_73 = "Server Connection Failed";    //3rd party server connection fail (gcm-http).

    //Cache Keys
    public static String CACHE_REG = "register";

    //Current Event Detail
    public static EventDetail eventDetail = null;
}