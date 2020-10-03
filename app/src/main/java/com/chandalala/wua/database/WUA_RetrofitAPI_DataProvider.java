package com.chandalala.wua.database;

import com.chandalala.wua.objects.Event;
import com.chandalala.wua.objects.PaymentHistory;
import com.chandalala.wua.objects.Course;
import com.chandalala.wua.objects.Results;
import com.chandalala.wua.objects.Student;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WUA_RetrofitAPI_DataProvider {

    @POST("login.php")
    Call<Student> getAccount(@Query("student_number") String student_number, @Query("password") int password);

    @POST("results.php")
    Call<Results[]> getResults(@Query("student_number") String student_number);

    @POST("payments.php")
    Call<PaymentHistory[]> getPaymentHistory(@Query("student_number") String student_number);

    @POST("timetable.php")
    Call<Course[]> getTimetable(@Query("programme_id") String programme_id, @Query("semester") String semester
                                ,@Query("session") String session);

    @GET("change_password.php")
    Call<String> changePassword(@Query("student_number") String student_number, @Query("old_password") String old_password
            , @Query("new_password") String new_password);

    @Multipart
    @POST("new_student_id.php")
    Call<String> requestStudentID(
            @Part MultipartBody.Part file,
            @Part("student_number") RequestBody student_number,
            @Part("name") RequestBody name,
            @Part("surname") RequestBody surname,
            @Part("programme") RequestBody programme);

    @POST("event_updates.php")
    Call<Event[]> getEvents();

}
