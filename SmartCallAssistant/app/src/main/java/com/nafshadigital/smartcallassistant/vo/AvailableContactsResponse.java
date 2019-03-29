package com.nafshadigital.smartcallassistant.vo;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class AvailableContactsResponse {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("list")
        @Expose
        private List<AvailableContact> list = null;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public List<AvailableContact> getList() {
         return list;
        }

        public void setList(List<AvailableContact> list) {
            this.list = list;
        }


}
