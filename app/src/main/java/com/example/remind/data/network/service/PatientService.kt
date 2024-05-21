package com.example.remind.data.network.service

import com.example.remind.data.model.request.SetMedicineInfoRequest
import com.example.remind.data.model.request.WritingMoodRequest
import com.example.remind.data.model.response.ActivityListResponse
import com.example.remind.data.model.response.GetFeelingActivityResponse
import com.example.remind.data.model.response.GetFeelingPercentResponse
import com.example.remind.data.model.response.MedicineInfoResponse
import com.example.remind.data.model.response.MedicingDailyInfoResponse
import com.example.remind.data.model.response.MoodChartResponse
import com.example.remind.data.model.response.SetMedicineInfoResponse
import com.example.remind.data.model.response.WritingMoodResponse
import com.example.remind.data.network.adapter.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PatientService {
    @GET("/taking-medicine")
    suspend fun getMedicineInfo(
        @Path("memberId") memberId:Int,
        @Path("date") string:Int,
    ): ApiResult<MedicineInfoResponse>

    @GET("/taking-medicine/daily")
    suspend fun getMedicineDailyInfo(
        @Query("memberId") memberId:Int,
        @Query("date") string:String,
    ): ApiResult<MedicingDailyInfoResponse>

    @POST("/taking-medicine")
    suspend fun setMedicineInfo(
        @Body body: SetMedicineInfoRequest
    ): ApiResult<SetMedicineInfoResponse>
    @GET("/activity")
    suspend fun getActivityList(
    ): ApiResult<ActivityListResponse>

    @POST("/mood")
    suspend fun setTodayMood(
        @Body body: WritingMoodRequest
    ): ApiResult<WritingMoodResponse>

    @GET("/mood/chart")
    suspend fun getMoodChart(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
        @Query("size") size: Int,
    ): ApiResult<MoodChartResponse>

    @GET("/mood/chart/percents")
    suspend fun getFeelingPercentChart(): ApiResult<GetFeelingPercentResponse>

    @GET("/mood/chart/percent/activity")
    suspend fun getFeelingPercentChart(
        @Query("feelingType") feelingType:String
    ): ApiResult<GetFeelingActivityResponse>
}