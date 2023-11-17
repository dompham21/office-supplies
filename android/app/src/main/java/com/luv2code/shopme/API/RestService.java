package com.luv2code.shopme.API;

import com.luv2code.shopme.Request.AddressRequest;
import com.luv2code.shopme.Request.CartRequest;
import com.luv2code.shopme.Request.ChangeEmailRequest;
import com.luv2code.shopme.Request.ChangeNameRequest;
import com.luv2code.shopme.Request.ChangePasswordRequest;
import com.luv2code.shopme.Request.ChangePhoneRequest;
import com.luv2code.shopme.Request.GenerateTokenForgotPassword;
import com.luv2code.shopme.Request.OTPRequest;
import com.luv2code.shopme.Request.OrderRequest;
import com.luv2code.shopme.Request.ReviewRequest;
import com.luv2code.shopme.Request.ValidateTokenForgotRequest;
import com.luv2code.shopme.Request.VerifyTokenNewPasswordRequest;
import com.luv2code.shopme.Response.AddressResponse;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.BuyHistoryResponse;
import com.luv2code.shopme.Response.CartResponse;
import com.luv2code.shopme.Response.ListAddressResponse;
import com.luv2code.shopme.Response.ListCartResponse;
import com.luv2code.shopme.Response.ListCategoryResponse;
import com.luv2code.shopme.Request.LoginRequest;
import com.luv2code.shopme.Response.ListDistrictResponse;
import com.luv2code.shopme.Response.ListOrderResponse;
import com.luv2code.shopme.Response.ListOrderStatusResponse;
import com.luv2code.shopme.Response.ListPostersResponse;
import com.luv2code.shopme.Response.ListProductResponse;
import com.luv2code.shopme.Response.ListProvinceResponse;
import com.luv2code.shopme.Response.ListReviewResponse;
import com.luv2code.shopme.Response.ListSuggestResponse;
import com.luv2code.shopme.Response.ListWardResponse;
import com.luv2code.shopme.Response.OrderResponse;
import com.luv2code.shopme.Response.OtpResponse;
import com.luv2code.shopme.Response.ProductResponse;
import com.luv2code.shopme.Response.RefreshTokenResponse;
import com.luv2code.shopme.Request.SignupRequest;
import com.luv2code.shopme.Model.User;
import com.luv2code.shopme.Response.LoginResponse;
import com.luv2code.shopme.Response.ReviewResponse;
import com.luv2code.shopme.Response.UserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestService {

    @Headers("Content-Type: application/json")
    @POST("api/auth/signup")
    Call<LoginResponse> register(@Body SignupRequest signupRequest);

    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @GET("api/auth/token/refresh")
    Call<RefreshTokenResponse> refreshAccessToken(@Header("Authorization") String refreshToken);

    @POST("api/v1/reset_password")
    Call<LoginResponse> resetPassword(@Body User user);

    @Headers("Content-Type: application/json")
    @GET("api/user/test")
    Call<Void>  getTest();

    @Headers("Content-Type: application/json")
    @GET("api/category")
    Call<ListCategoryResponse>  getListCategories();

    @Headers("Content-Type: application/json")
    @GET("api/poster")
    Call<ListPostersResponse>  getListsPoster();

    @Headers("Content-Type: application/json")
    @GET("api/product")
    Call<ListProductResponse>  getListProducts(@Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize,
                                               @Query("sortField") String sortField, @Query("sortDirection") String sortDir,
                                               @Query("categoryId") Long categoryId);

    @Headers("Content-Type: application/json")
    @GET("api/product/promotion")
    Call<ListProductResponse>  getListPromotionProducts();

    @Headers("Content-Type: application/json")
    @GET("api/product/{id}")
    Call<ProductResponse>  getDetailProduct(@Path("id") Integer id);

    @Headers("Content-Type: application/json")
    @GET("api/review/get/{productId}")
    Call<ListReviewResponse>  getReviewsByProductId(@Path("productId") Integer productId, @Query("pageNo") Integer pageNo);

    @Headers({"Content-Type: application/json"})
    @POST("api/cart/add")
    Call<CartResponse>  addToCart(@Header("Authorization") String authorization, @Body CartRequest cartRequest);

    @Headers({"Content-Type: application/json"})
    @PUT("api/cart")
    Call<CartResponse>  updateCart(@Header("Authorization") String authorization, @Body CartRequest cartRequest);

    @Headers({"Content-Type: application/json"})
    @DELETE("api/cart/{id}")
    Call<CartResponse>  deleteCart(@Header("Authorization") String authorization, @Path("id") Integer id);

    @Headers({"Content-Type: application/json"})
    @GET("api/cart")
    Call<ListCartResponse>  getListCart(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("api/address/province")
    Call<ListProvinceResponse>  getProvinces();

    @Headers("Content-Type: application/json")
    @GET("api/address/district/{id}")
    Call<ListDistrictResponse>  getDistricts(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @GET("api/address/ward/{id}")
    Call<ListWardResponse>  getWards(@Path("id") String id);

    @Headers({"Content-Type: application/json"})
    @GET("api/address/get")
    Call<ListAddressResponse>  getAddressByUser(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json"})
    @GET("api/address/default")
    Call<AddressResponse>  getAddressDefaultByUser(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json"})
    @POST("api/address/add")
    Call<AddressResponse>  addAddress(@Header("Authorization") String authorization, @Body AddressRequest addressRequest);

    @Headers({"Content-Type: application/json"})
    @PUT("api/address/update/{id}")
    Call<BaseResponse>  updateAddress(@Header("Authorization") String authorization, @Path("id") Integer id, @Body AddressRequest addressRequest);

    @Headers({"Content-Type: application/json"})
    @DELETE("api/address/delete/{id}")
    Call<BaseResponse>  deleteAddress(@Header("Authorization") String authorization, @Path("id") Integer id);

    @Headers({"Content-Type: application/json"})
    @POST("api/order/add")
    Call<BaseResponse> addOrder(@Header("Authorization") String authorization, @Body OrderRequest orderRequest);

    @Headers({"Content-Type: application/json"})
    @GET("api/order/get/{id}")
    Call<ListOrderResponse> getOrderByStatus(@Header("Authorization") String authorization, @Path("id") Integer id);

    @Headers({"Content-Type: application/json"})
    @GET("api/order/order-status/get")
    Call<ListOrderStatusResponse> getListOrderStatus(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json"})
    @GET("api/order/detail/{id}")
    Call<OrderResponse> getOrderDetail(@Header("Authorization") String authorization, @Path("id") Integer id);

    @Headers({"Content-Type: application/json"})
    @GET("api/otp/generateOTP")
    Call<BaseResponse> generateOTP(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json"})
    @POST("api/otp/validateOTP")
    Call<BaseResponse> validateOTP(@Header("Authorization") String authorization, @Body OTPRequest otpRequest);

    @Headers({"Content-Type: application/json"})
    @GET("api/profile")
    Call<UserResponse> getProfile(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json"})
    @PUT("api/profile/email")
    Call<LoginResponse> changeEmail(@Header("Authorization") String authorization, @Body ChangeEmailRequest changeEmailRequest);

    @Headers({"Content-Type: application/json"})
    @GET("api/profile/buy-history")
    Call<BuyHistoryResponse> getBuyHistory(@Header("Authorization") String authorization, @Query("type") Integer type);

    @Headers({"Content-Type: application/json"})
    @PUT("api/profile/phone")
    Call<UserResponse> changePhone(@Header("Authorization") String authorization, @Body ChangePhoneRequest changePhoneRequest);

    @Headers({"Content-Type: application/json"})
    @PUT("api/profile/name")
    Call<UserResponse> changeName(@Header("Authorization") String authorization, @Body ChangeNameRequest changeNameRequest);

    @Multipart
    @POST("api/profile/avatar")
    Call<UserResponse> changeAvatar(@Header("Authorization") String authorization,  @Part MultipartBody.Part file);

    @Headers({"Content-Type: application/json"})
    @PUT("api/profile/password")
    Call<BaseResponse> changePassword(@Header("Authorization") String authorization, @Body ChangePasswordRequest changePasswordRequest);

    @Headers({"Content-Type: application/json"})
    @POST("api/review/add")
    Call<BaseResponse> addReview(@Header("Authorization") String authorization, @Body ReviewRequest reviewRequest);

    @Headers({"Content-Type: application/json"})
    @GET("api/review/user/get")
    Call<ListReviewResponse> getListReviewByUser(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json"})
    @GET("api/review/detail/{id}")
    Call<ReviewResponse> getDetailReview(@Header("Authorization") String authorization, @Path("id") Integer id);

    @Headers({"Content-Type: application/json"})
    @PUT("api/review/update/{id}")
    Call<BaseResponse> updateReview(@Header("Authorization") String authorization,@Body ReviewRequest reviewRequest, @Path("id") Integer id);

    @Headers({"Content-Type: application/json"})
    @GET("api/product/suggest")
    Call<ListSuggestResponse> getSuggest(@Query("keyword") String keyword);

    @Headers({"Content-Type: application/json"})
    @GET("api/product/search")
    Call<ListProductResponse> getSearch(@Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize,
                                        @Query("priceMin") Double priceMin, @Query("priceMax") Double priceMax,
                                        @Query("sortField") String sortField, @Query("sortDirection") String sortDir,
                                        @Query("categoryIds") List<Integer> categoryIds, @Query("keyword") String keyword);

    @Headers({"Content-Type: application/json"})
    @GET("api/category/page")
    Call<ListCategoryResponse> getListCategoryByPage(@Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize);

    @Headers({"Content-Type: application/json"})
    @POST("api/otp/forgot/generateOTP")
    Call<BaseResponse> generateForgotOTP(@Body GenerateTokenForgotPassword generateTokenForgotPassword);

    @Headers({"Content-Type: application/json"})
    @POST("api/otp/forgot/validateOTP")
    Call<OtpResponse> validateForgotOTP(@Body ValidateTokenForgotRequest validateTokenForgotRequest);


    @Headers({"Content-Type: application/json"})
    @POST("api/auth/forgot/verify")
    Call<BaseResponse> verifyTokenNewPassword(@Body VerifyTokenNewPasswordRequest verifyTokenNewPasswordRequest);

}
