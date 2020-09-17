import com.example.coex_client.model.forgot.ForgotRequest
import com.example.coex_client.model.forgot.ResetRequest
import com.example.coex_client.model.login.LoginRequest
import com.example.coex_client.model.login.LoginResponse
import com.example.coex_client.model.map.NearbyCWModel
import com.example.coex_client.model.signup.SignupRequest
import com.example.coex_client.model.signup.SignupResponse
import com.example.coex_client.model.user.User
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


private const val USER_API = "http://10.0.2.2:3000/api/"

//private const val USER_API = "https://b8ce0d731aa4.ngrok.io/api/"

private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val client: OkHttpClient = OkHttpClient.Builder().apply {
    this.addInterceptor(interceptor)
}.build()


/*Retrofit needs at least two things available to build a web services API:
* - A base URI for the web service
* - A converter factory */

private val retrofit =
    Retrofit.Builder()                        // Use Retrofit Builder to build a retrofit object
        .addConverterFactory(GsonConverterFactory.create())         // Use Gson converter factory to convert java to JSon and Json to java
        .client(client)
        .baseUrl(USER_API)
        .build()


//
interface UserApiService {

    @Headers("Content-Type:application/json")
    @POST("user/log-in/client")
    fun login(@Body info: LoginRequest): retrofit2.Call<LoginResponse>


    @Headers("Content-Type:application/json")
    @POST("user/sign-up/host")
    fun signup(@Body info: SignupRequest): retrofit2.Call<SignupResponse>

    @POST("user/forgot-password")
    @Headers("Content-Type:application/json")
    fun forgotPassword(
        @Body info : ForgotRequest
    ): retrofit2.Call<ResponseBody>

    @PUT("user/reset-password")
    @Headers("Content-Type:application/json")
    fun resetPassword(
        @Body info : ResetRequest
    ): retrofit2.Call<ResponseBody>

    @GET("user/me")
    fun getUserMe(@Header("Authorization") token : String
    ) : retrofit2.Call<User>

    @GET("co-workings/near")
    fun getCoworkingNearbyAPI(@Header("Authorization") token: String, @Query("maxDistance") distance: Double, @Query("latitude") lat: Double, @Query("longitude") long:Double
    ) : retrofit2.Call<NearbyCWModel>
}


/*NOTE:
* This is for create an instance of Retrofit Service instance.
* Because this call create() method is expensive and the app needs only one retrofit service instance, so lazily initialize the Retrofit Service there */

object UserApi {
    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}


