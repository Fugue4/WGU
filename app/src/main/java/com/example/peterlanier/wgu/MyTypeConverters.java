package com.example.peterlanier.wgu;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyTypeConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<String> stringToArrayList(String data) {
        if (data == null) {
            return new ArrayList<String>();
        }

        Type arrayListType = new TypeToken<ArrayList<String>>() {}.getType();

        return gson.fromJson(data, arrayListType);
    }

    @TypeConverter
    public static String arrayListToString(ArrayList<String> String) {
        return gson.toJson(String);
    }
}
