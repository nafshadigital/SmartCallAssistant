package com.nafshadigital.smartcallassistant.network;

import com.nafshadigital.smartcallassistant.helpers.SmartCallAssistantApiResponse;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Class used to create the request for API clients
 */
public interface SmartCallAssistantApiInterface {

    @POST("savecontact")
    Call<SmartCallAssistantApiResponse> saveContacts(@Body SyncContactVO contact);

}