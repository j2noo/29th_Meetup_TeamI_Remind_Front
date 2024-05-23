package com.example.remind.feature.screens.doctor

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.example.remind.app.Screens
import com.example.remind.core.base.BaseViewModel
import com.example.remind.data.model.request.SetAcceptrequest
import com.example.remind.data.network.adapter.ApiResult
import com.example.remind.data.network.interceptor.TokenManager
import com.example.remind.domain.usecase.doctor_usecase.GetPatientUseCase
import com.example.remind.domain.usecase.doctor_usecase.GetRequestUseCase
import com.example.remind.domain.usecase.patience_usecase.GetFeelingPercentUseCase
import com.example.remind.domain.usecase.patience_usecase.GetMedicineRateUseCase
import com.example.remind.domain.usecase.patience_usecase.GetMonthlyMedicineUseCase
import com.example.remind.domain.usecase.patience_usecase.GetMoodChartUseCase
import com.example.remind.domain.usecase.patience_usecase.PrescriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val getPatientUseCase: GetPatientUseCase,
    private val getRequestUseCase: GetRequestUseCase,
    private val getMedicineRateUseCase: GetMedicineRateUseCase,
    private val getMoodChartUseCase: GetMoodChartUseCase,
    private val prescriptionUseCase: PrescriptionUseCase,
    private val getMonthlyMedicineUseCase: GetMonthlyMedicineUseCase,
    private val getFeelingPercentUseCase: GetFeelingPercentUseCase
): BaseViewModel<DoctorContract.Event, DoctorContract.State, DoctorContract.Effect>(
    initialState = DoctorContract.State()
) {
    val year = LocalDate.now().year
    val month = LocalDate.now().monthValue
    val date = LocalDate.now().dayOfMonth
    init {
        viewModelScope.launch {
            getPatients()
            getRequestPatients()
            getMedicineRate()
            getMoodChartData(year, month, date-6)
            getPrescription()
            getMonthMedicine()
            getFeelingPerCent()
        }
    }
    override fun reduceState(event: DoctorContract.Event) {
        when(event) {
            is DoctorContract.Event.RegisterButtonClicked -> {
                navigateToRoute(
                    destination = Screens.Doctor.DoctorPatienceRegister.route,
                    current = Screens.Doctor.DoctorMain.route,
                    inclusiveData = false
                )
            }
            is DoctorContract.Event.acceptButtonClicked -> {
                getAccept(body = SetAcceptrequest(
                    memberId = event.memberId
                )
                )
            }
            is DoctorContract.Event.ClickedToManage -> {
                updateState(currentState.copy(
                    memberId = event.memberId
                ))
                navigateToRoute(
                    destination = Screens.Doctor.PatienceManage.route,
                    current = Screens.Doctor.DoctorMain.route,
                    inclusiveData = false
                )
            }
            is DoctorContract.Event.ClickToUpdate -> {
                navigateToRoute(
                    destination = Screens.Doctor.PrescriptionUpdate.route,
                    current = Screens.Doctor.PatienceManage.route,
                    inclusiveData = false
                )
            }
            is DoctorContract.Event.ClickToMedicine -> {
                navigateToRoute(
                    destination = Screens.Doctor.ManageMedicine.route,
                    current = Screens.Doctor.PatienceManage.route,
                    inclusiveData = false
                )
            }
            is DoctorContract.Event.ClickToMood -> {
                navigateToRoute(
                    destination = Screens.Doctor.ManageMood.route,
                    current = Screens.Doctor.PatienceManage.route,
                    inclusiveData = false
                )
            }
            is DoctorContract.Event.ClickToBottomSheet -> {
                navigateToRoute(
                    destination = Screens.Doctor.ExDoctorBottomSheet.route,
                    current = Screens.Doctor.ManageMood.route,
                    inclusiveData = false
                )
            }
        }
    }

    fun navigateToRoute(destination: String, current: String, inclusiveData: Boolean) {
        postEffect(
            DoctorContract.Effect.NavigateTo(
                destination = destination,
                navOptions = navOptions {
                    popUpTo(current) {
                        inclusive = inclusiveData
                    }
                }
            )
        )
    }
     fun getPatients() {
        viewModelScope.launch {
            val result = getPatientUseCase.invoke("ACCEPT")
            when(result) {
                is ApiResult.Success -> {
                    updateState(currentState.copy(
                        doctorData =  result.data.data
                    ))
                }
                else -> {}
            }
        }
    }

     fun getRequestPatients() {
        viewModelScope.launch {
            val result = getPatientUseCase.invoke("PENDING")
            when(result) {
                is ApiResult.Success -> {
                    updateState(currentState.copy(
                        acceptList = result.data.data
                    ))
                } else -> {}
            }
        }
    }

    private fun getAccept(body: SetAcceptrequest) {
        viewModelScope.launch {
            val result = getRequestUseCase.invoke(body)
            when(result) {
                is ApiResult.Success -> {
                    updateState(currentState.copy(
                        connectionStatus = true
                    ))
                }
                else ->{}
            }
        }
    }
    private fun getMedicineRate() {
        viewModelScope.launch {
            val result = currentState.memberId?.let { getMedicineRateUseCase.invoke(it) }
            when(result){
                is ApiResult.Success -> {
                    updateState(currentState.copy(
                        rate = result.data.data
                    ))
                }
                else ->{}
            }
        }
    }
    private fun getMoodChartData(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            val result = getMoodChartUseCase.invoke(year, month, day, 7)
            when(result) {
                is ApiResult.Success -> {
                    updateState(currentState.copy(
                        moodChartData = result.data.data,
                        xAxisData = result.data.data.moodChartDtos.map { "${it.day}일" },
                        yAxisData = result.data.data.moodChartDtos.map { it.score }
                    ))
                }
                else -> {}
            }
        }
    }
    private fun getPrescription() {
        viewModelScope.launch {
            val result = currentState.memberId?.let { prescriptionUseCase.invoke(it) }
            when(result) {
                is ApiResult.Success -> {
                    updateState(currentState.copy(
                        prescription = result.data.data
                    ))
                }
                else ->{}
            }
        }
    }
    private fun getMonthMedicine() {
        viewModelScope.launch {
            val result = getMonthlyMedicineUseCase.invoke(currentState.memberId ?: 1, 2024, 5)
            when(result) {
                is ApiResult.Success -> {
                    updateState(currentState.copy(
                        getMonthlyMedicineResponse = result.data
                    ))
                }
                else -> {}
            }
        }
    }
    private fun getFeelingPerCent() {
        viewModelScope.launch {
            val result = getFeelingPercentUseCase.invoke()
            when(result) {
                is ApiResult.Success -> {
                    if(result.data.data != null) {
                        updateState(currentState.copy(
                            feelingTotalPerCent = result.data.data
                        ))
                    }
                }
                else ->{}
            }
        }
    }

}