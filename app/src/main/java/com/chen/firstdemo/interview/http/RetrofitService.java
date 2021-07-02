package com.chen.firstdemo.interview.http;


import java.util.AbstractMap;
import java.util.Map;

import kotlin.ParameterName;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

interface RetrofitService {

    @GET("/getJoke")
    Call<Bean> getJoke(@Query("page") int page ,
                       @Query("count")int count ,
                       @Query("type")String type);

    @GET("users/{user}/repos")
    Call<ResponseBody> listRepos(@Path("user") String user);

    /**
     *表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name,
                                           @Field("age") int age);

    /**
     * {@link Part} 后面支持三种类型：
     *      {@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，
     * 其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name,
                                       @Part("age") RequestBody age,
                                       @Part MultipartBody.Part file);

    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    Call<ResponseBody> getCall(@Path("id") int id);


    // @Header
    @GET("user")
    Call<ResponseBody> getUser(@Header("Authorization") String authorization);

    // @Headers
    @Headers("Authorization: authorization")
    @GET("user")
    Call<ResponseBody> getUser();

    // 以上的效果是一致的。
    // 区别在于使用场景和使用方式
    // 1. 使用场景：@Header用于添加不固定的请求头，@Headers用于添加固定的请求头
    // 2. 使用方式：@Header作用于方法的参数；@Headers作用于方法



    /**
     * Map的key作为表单的键
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);

    @GET
    Call<ResponseBody> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);
    // 当有URL注解时，@GET传入的URL就可以省略
    // 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
}
