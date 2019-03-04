package com.nafshadigital.smartcallassistant.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Malaris on 3/31/2018.
 */

public class ActivityVO implements Serializable {
    public String id = "";
    public String activity_name = "";
    public String activity_message = "";
    public String is_default = "";
    public String is_active = "";

    public ActivityVO() {
        id = "";
        activity_name = "";
        activity_message = "";
        is_default = "";
        is_active = "";
    }

}


