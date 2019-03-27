package com.nafshadigital.smartcallassistant.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyProfileResponse {

        @SerializedName("user_record")
        @Expose
        private List<UserRecord> userRecord = null;

        public List<UserRecord> getUserRecord() {
            return userRecord;
        }

        public void setUserRecord(List<UserRecord> userRecord) {
            this.userRecord = userRecord;
        }


}
