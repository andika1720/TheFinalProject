package com.example.thefinalproject.network.api


import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.network.model.mycourse.MyCourseResponse
import com.example.thefinalproject.network.model.order.PostResponse
import com.example.thefinalproject.network.model.order.PutResponseOrder
import com.example.thefinalproject.network.model.order.RequestPutOrder
import com.example.thefinalproject.network.model.user.getuser.GetCurrentUser
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.login.LoginResponse
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.OtpResponse
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpRequest
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpResponse
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.network.model.user.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("courses")
    suspend fun getDataByCategory(
        @Query("category") category: String?): CategoryResponse
    @GET("courses/{id}")
    suspend fun getDataById(
        @Header("authorization") token : String?,
        @Path("id") id: String
    ): DetailResponse

    @GET("courses/{id}")
    suspend fun getDataById1(
        @Path("id") id: String
    ): DetailResponse

    @GET("courses")
    suspend fun getCourseByTitle(
        @Query("title") title: String?
    ) : CategoryResponse
    @GET("courses")
    suspend fun getFilterCourse(
        @Query("id") id: String?,
        @Query("category") category: String?,
        @Query("level") level: String?,
        @Query("type") type: String?,
        @Query("search") search: String?,
    ) : ListResponse


    //AUTH
    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @POST ("account-verify")
    suspend fun checkOtp(
        @Body otpRequest: OtpRequest
    ): OtpResponse

    @GET ("current-user")
    suspend fun currentUser(
        @Header("authorization") token : String?
    ): GetCurrentUser

    @POST ("resend-otp")
    suspend fun resendOtp(
        @Body resendOtpRequest: ResendOtpRequest
    ): ResendOtpResponse


    //MYCOURSE
    @GET("my-courses")
    suspend fun myCourse(
        @Header("authorization") token : String?
    ): MyCourseResponse
    //ORDER
    @POST("orders/{id}")
    suspend fun ordersId(
        @Header("authorization") token : String?,
        @Path("id") courseId: String?
    ) : PostResponse

    @PUT("orders/{id}")
    suspend fun updatePayment(
        @Header("authorization") token : String,
        @Path("id") id: String,
        @Body requestOrder: RequestPutOrder
    ) : PutResponseOrder
}