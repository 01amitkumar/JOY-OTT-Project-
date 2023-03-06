package com.seeksolution.joy.Api;

import com.seeksolution.joy.Model.BannerResponse;
import com.seeksolution.joy.Model.CartoonResponse;
import com.seeksolution.joy.Model.CreateUserResponse;
import com.seeksolution.joy.Model.LoginResponse;
import com.seeksolution.joy.Model.PackagesResponse;
import com.seeksolution.joy.Model.UpdatePackageResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    //    @GET("v3/d2c0291b-9635-4566-a9f6-1a6650ab029a")
//    Call<TrendingModelResponse> trending();

    @FormUrlEncoded
    @POST("user")
    Call<CreateUserResponse> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile
    );

    @GET("package")
    Call<PackagesResponse> getSubscriptionPackages();


    @FormUrlEncoded
    @POST ("user")
    Call<UpdatePackageResponse> subscribeToPackage(
            @Field("_method") String _method,
            @Field("user_id") String user_id,
            @Field("package_id") String package_id
    );

    @FormUrlEncoded
    @POST ("login")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("banner")
    Call<BannerResponse> bannerImage();

    @GET("vedios")
    Call<CartoonResponse> GetCartoon();
}
