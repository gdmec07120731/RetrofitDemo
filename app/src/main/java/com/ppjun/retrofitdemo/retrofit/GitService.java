package com.ppjun.retrofitdemo.retrofit;

import com.ppjun.retrofitdemo.bean.GitReturnCode;
import com.ppjun.retrofitdemo.bean.Repos;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Package :com.ppjun.retrofitdemo.retrofit
 * Description :
 * Author :Rc3
 * Created at :2016/10/21 14:02.
 */

public interface GitService {
    @GET("users/{user}/repos")
    Call<List<Repos>> listRepos(@Path("user") String user);


    @POST
    @FormUrlEncoded
    Call<GitReturnCode> init(@Url String url,
                             @Field("id") String id,
                             @Field("key") String key);

    @Multipart
    @PUT
    Call<GitReturnCode> addCarInfo(@Url String url,
                                   @Part("uuid") RequestBody uuid,
                                   @Part("grand") RequestBody grand,
                                   @Part("plate_number") RequestBody plateNumber,
                                   @Part("frame_number") RequestBody frameNumber,
                                   @Part MultipartBody.Part da,
                                   @Part MultipartBody.Part dd
    );

    @GET
    Call<ResponseBody> download(@Url String url);
}
