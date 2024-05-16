package com.example.remind.data.repository.auth

import com.example.remind.data.model.request.KakaoLoginRequest
import com.example.remind.data.model.request.OnBoardingRequest
import com.example.remind.data.model.request.TokenRequest
import com.example.remind.data.model.response.OnBoardingResponse
import com.example.remind.data.model.response.SocialLoginResponse
import com.example.remind.data.model.response.TokenResponse
import com.example.remind.data.network.adapter.ApiResult
import retrofit2.http.Body

interface AuthRepository {
    suspend fun getTokenFromKakao(body: KakaoLoginRequest): ApiResult<SocialLoginResponse>
    suspend fun postOnBoardingInfo(body: OnBoardingRequest): ApiResult<OnBoardingResponse>
}