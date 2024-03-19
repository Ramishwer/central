package com.goev.central.constant;

import com.goev.lib.utilities.GsonDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationConstants {
    public static String APPLICATION_ID;
    public static String CLIENT_ID;
    public static String CLIENT_SECRET;
    public static String USER_NAME;
    public static String USER_PASSWORD;
    public static final Gson GSON=new GsonBuilder().registerTypeAdapter(DateTime.class,new GsonDateTimeSerializer()).create();

}
