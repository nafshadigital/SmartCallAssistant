package com.nafshadigital.smartcallassistant.network;


import com.nafshadigital.smartcallassistant.service.MaxIdResponse;
import com.nafshadigital.smartcallassistant.vo.AvailableContactsResponse;
import com.nafshadigital.smartcallassistant.vo.CountryVO;
import com.nafshadigital.smartcallassistant.vo.EmptyRequestVO;
import com.nafshadigital.smartcallassistant.vo.FCMNotificationVO;
import com.nafshadigital.smartcallassistant.vo.LastSeenVO;
import com.nafshadigital.smartcallassistant.vo.MyProfileResponse;
import com.nafshadigital.smartcallassistant.vo.NotificationVO;
import com.nafshadigital.smartcallassistant.vo.SaveAllContactVO;
import com.nafshadigital.smartcallassistant.vo.SaveContactResponse;
import com.nafshadigital.smartcallassistant.vo.SendHeartVO;
import com.nafshadigital.smartcallassistant.vo.SendNotificationResponse;
import com.nafshadigital.smartcallassistant.vo.SignUpResponse;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;
import com.nafshadigital.smartcallassistant.vo.UpdateAccountResponse;
import com.nafshadigital.smartcallassistant.vo.UpdateFcmTokenResponse;
import com.nafshadigital.smartcallassistant.vo.UpdateLastSeenResponse;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.vo.VerifyOTPResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Class used to create the request for API clients
 */
public interface ApiInterface {



    @POST("getCountry/")
    Call<List<CountryVO>> getCountry(@Body EmptyRequestVO requestVO);


    @POST("signUp/")
    Call<SignUpResponse> signUp(@Body UsersVO usersVO);

    @POST("checkOTP/")
    Call<VerifyOTPResponse> verifyOTP(@Body UsersVO usersVO);


    @POST("updateAccount/")
    Call<UpdateAccountResponse> updateAccount(@Body UsersVO usersVO);


    @POST("getprofile/")
    Call<MyProfileResponse> getProfile(@Body UsersVO usersVO);


    @POST("sendRandomHeart/")
    Call<ResponseBody> sendRandomHeart(@Body SendHeartVO sendHeartVO);


    @POST("savecontact/")
    Call<SaveContactResponse> saveContact(@Body SyncContactVO syncContactVO);


    @POST("addMultiContacts/")
    Call<AvailableContactsResponse> saveAllContacts(@Body SaveAllContactVO saveAllContactVO);

    @POST("getRegisteredContacts/")
    Call<AvailableContactsResponse> getRegisteredContacts(@Body UsersVO usersVO);


    @POST("updateFCM/")
    Call<UpdateFcmTokenResponse> updateFCM(@Body SyncContactVO syncContactVO);


    //TODO - verify its response
    @POST("updateLastSeen/")
    Call<UpdateLastSeenResponse> updateLastSeen(@Body LastSeenVO lastSeenVO);


    @POST("send_notification/")
    Call<SendNotificationResponse> sendNotification(@Body FCMNotificationVO fcmNotificationVO);


    //TODO - verify its response
    @POST("sendNotify/")
    Call<SendNotificationResponse> sendNotify(@Body NotificationVO notificationVO);


    //TODO - verify its response
    @POST("sendHeart/")
    Call<ResponseBody> sendHeart(@Body SendHeartVO sendHeartVO);


    @POST("getmaxid/")
    Call<MaxIdResponse> getMaxId(@Body NotificationVO notificationVO);



    //TODO - change its response to pojo
    @POST("getnotifyafter/")
    Call<ResponseBody> getnotifyafter(@Body NotificationVO notificationVO);



}