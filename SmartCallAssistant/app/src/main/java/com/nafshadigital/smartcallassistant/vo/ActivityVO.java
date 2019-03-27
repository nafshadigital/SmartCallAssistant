package com.nafshadigital.smartcallassistant.vo;

import java.io.Serializable;

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


