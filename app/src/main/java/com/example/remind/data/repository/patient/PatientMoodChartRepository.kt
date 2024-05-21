package com.example.remind.data.repository.patient

import com.example.remind.data.model.response.GetFeelingActivityResponse
import com.example.remind.data.model.response.GetFeelingPercentResponse
import com.example.remind.data.network.adapter.ApiResult

interface PatientMoodChartRepository {
    suspend fun getFeelingPercentChart(): ApiResult<GetFeelingPercentResponse>
    suspend fun getFeelingTypeActivity(feelingType: String): ApiResult<GetFeelingActivityResponse>

}