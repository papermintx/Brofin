package com.example.brofin.data.repository

import android.util.Log
import com.example.brofin.data.mapper.toGadgetRecommendation
import com.example.brofin.data.mapper.toGameRecommendation
import com.example.brofin.data.mapper.toLuxuryRecommendation
import com.example.brofin.data.mapper.toMobilRecommendation
import com.example.brofin.data.mapper.toMotorRecommendation
import com.example.brofin.data.remote.service.ApiService
import com.example.brofin.data.remote.service.PredictApiService
import com.example.brofin.data.remote.service.RecommendationApiService
import com.example.brofin.data.remote.dto.AddDiaryResponseDto
import com.example.brofin.data.remote.dto.AddExpensesResponseDto
import com.example.brofin.data.remote.dto.AddOrUpateBudgetingResponseDto
import com.example.brofin.data.remote.dto.AddUserBalanceResponseDto
import com.example.brofin.data.remote.dto.EditProfileResponseDto
import com.example.brofin.data.remote.dto.GetAllDataResponseDto
import com.example.brofin.data.remote.dto.LoginResponseDto
import com.example.brofin.data.remote.dto.PredictResponseDto
import com.example.brofin.data.remote.dto.RegisterResponseDto
import com.example.brofin.data.remote.dto.SetupBudgeingResponseDto
import com.example.brofin.data.remote.dto.UpdateBalanceResponseDto
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.GadgetRecommendation
import com.example.brofin.domain.models.GameRecommendation
import com.example.brofin.domain.models.LuxuryRecommendation
import com.example.brofin.domain.models.MobilRecommendation
import com.example.brofin.domain.models.MotorRecommendation
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.utils.BaseUrl
import com.example.brofin.utils.PredictUrl
import com.example.brofin.utils.RecommendationUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RemoteDataRepositoryImpl @Inject constructor(
    @BaseUrl private val apiService: ApiService,
    @PredictUrl private val predictService: PredictApiService,
    @RecommendationUrl private val recommendationService: RecommendationApiService
): RemoteDataRepository {

    override suspend fun register(
        email: String,
        password: String,
        name:String
        ): RegisterResponseDto {
        return withContext(Dispatchers.Default) {
            try {
                val response = apiService.register(name, email, password)
                if (response.isSuccessful){
                    if (response.body() != null){
                        response.body()!!
                    } else {
                        throw Exception("Terjadi kesalahan yang tidak terduga!")
                    }
                } else {
                    throw Exception("Pendaftaran gagal email kamu sudah di gunakan!")
                }
            } catch (e: Exception){
                Log.e(TAG, "error when register $e")
                throw e
            }
        }
    }


    override suspend fun login(
        email: String,
        password: String
    ): LoginResponseDto {
        return withContext(Dispatchers.Default){
            try {
               val response = apiService.login(email, password)
                if (response.isSuccessful){
                    if (response.body() != null){
                        response.body()!!
                    } else {
                        throw Exception("login gagal terjadi kesalahan dalam server aplikasi")
                    }
                } else {
                    throw Exception("Login gagal cek kembali email dan password kamu")
                }
            } catch (e: Exception) {
                Log.e(TAG, "error when login $e")
                throw e
            }
        }
    }

    override suspend fun editUserProfile(
        username: RequestBody?,
        gender: RequestBody?,
        dob: RequestBody?,
        savings: RequestBody?,
        photo: MultipartBody.Part?
    ): EditProfileResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.editUserProfile(
                    username,
                    gender,
                    dob,
                    savings,
                    photo
                )
            } catch (e: Exception){
                Log.e(TAG, "error when edit UserProfile")
                throw e
            }
        }
    }

    override suspend fun getAllData(): GetAllDataResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.getAllData()
            } catch (e: Exception){
                Log.e(TAG, "Error when get all data")
                throw e
            }
        }
    }

    override suspend fun addBudgetingDiary(
        monthAndYear: RequestBody,
        date: RequestBody,
        description: RequestBody?,
        amount: RequestBody,
        categoryId: RequestBody,
        photo: MultipartBody.Part?
    ): AddDiaryResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.addBudgetingDiary(
                    monthAndYear,
                    date,
                    description,
                    amount,
                    categoryId,
                    photo
                )
            } catch (e:Exception){
                Log.e(TAG, "error when add budgeting diary")
                throw e
            }
        }
    }

    override suspend fun addUserBalance(
        monthAndYear: Long,
        amount: Double,
        currentBalance: Double
    ): AddUserBalanceResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.addBalance(monthAndYear, amount, currentBalance)
            } catch (e: Exception){
                Log.e(TAG, "error when adding userBalance bro")
                throw e
            }
        }
    }

    override suspend fun editbalance(monthAndYear: Long, amount: Double, currentBalance: Double): UpdateBalanceResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.editBalance(monthAndYear, amount, currentBalance)
            } catch (e: Exception){
                Log.e(TAG, "error when editing balance")
                throw e
            }
        }
    }

    override suspend fun addBudgeting(
        monthAndYear: Long,
        total: Double,
        essentialNeedsLimit: Double,
        wantsLimit: Double,
        savingsLimit: Double,
        isReminder: Boolean): AddOrUpateBudgetingResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.addBudgeting(Budgeting(
                    monthAndYear,
                    total,
                    essentialNeedsLimit,
                    wantsLimit,
                    savingsLimit,
                ))
            } catch (e: Exception){
                Log.e(TAG, "error when adding budgeting")
                throw e
            }
        }
    }

    override suspend fun addExpenses(
        monthAndYear: RequestBody,
        date: RequestBody,
        description: RequestBody?,
        amount: RequestBody,
        categoryId: RequestBody,
        total: RequestBody,
        essentialNeeds: RequestBody,
        wants: RequestBody,
        savingsLimit: RequestBody,
        balance: RequestBody,
        currentBalance: RequestBody,
        image: MultipartBody.Part?
    ): AddExpensesResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.addExpenses(
                    monthAndYear,
                    date,
                    description,
                    amount,
                    categoryId,
                    isReminder = "false".toRequestBody("text/plain".toMediaTypeOrNull()),
                    total,
                    essentialNeeds,
                    wants,
                    savingsLimit,
                    balance,
                    currentBalance,
                    image,
                )
            } catch (e: Exception){
                Log.e(TAG, "error when adding expenses")
                throw e
            }
        }
    }

    override suspend fun setupBudgeting(
        monthAndYear: RequestBody,
        total: RequestBody,
        essentialNeedsLimit: RequestBody,
        wantsLimit: RequestBody,
        balance: RequestBody,
        currentBalance: RequestBody,
        isReminder: RequestBody,
        savingsLimit: RequestBody,
        savings: RequestBody,
        dob: RequestBody,
        username: RequestBody,
        image: MultipartBody.Part?
    ): SetupBudgeingResponseDto {
        return withContext(Dispatchers.Default) {
            try {
                apiService.setupBudgeting(
                    monthAndYear,
                    total,
                    essentialNeedsLimit,
                    wantsLimit,
                    balance,
                    gender = "tidak diketahui".toRequestBody("text/plain".toMediaTypeOrNull()),
                    currentBalance,
                    isReminder,
                    savingsLimit,
                    savings,
                    dob,
                    username,
                    image
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error when setting up budgeting", e)
                throw e
            }
        }
    }


    override suspend fun predictHouse(predictRequest: RequestBody): PredictResponseDto {
        return withContext(Dispatchers.Default) {
            try {
                val response = predictService.predictHouse(predictRequest)

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        return@withContext body
                    } ?: throw Exception("Gagal saat mendapatkan data, response body null")
                } else {
                    throw Exception("Gagal saat prediksi, response gagal dengan status: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error when predicting house: ${e.message}")
                throw Exception("Error saat prediksi: ${e.message}")
            }
        }
    }

    override suspend fun getRecommendationMotor(request: RequestBody): List<MotorRecommendation> {
        return withContext(Dispatchers.Default) {
            try {
                val responseDto = recommendationService.getRecommendationMotor(request)
                if (responseDto.isSuccessful) {
                    responseDto.body()?.map {
                        it.toMotorRecommendation()
                    } ?: emptyList()
                } else {
                    throw Exception("Gagal mendapatkan Rekomendasi Motor")
                }
            } catch (e: Exception) {
                Log.e("RecommendationError", "Error fetching motor recommendations: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun getRecommendationMobil(request: RequestBody): List<MobilRecommendation> {
        return withContext(Dispatchers.Default) {
            try {
                val responseDto = recommendationService.getRecommendationMobil(request)
                if (responseDto.isSuccessful) {
                    responseDto.body()?.map {
                        it.toMobilRecommendation()
                    } ?: emptyList()
                } else {
                    throw Exception("Gagal mendapatkan Rekomendasi Mobil")
                }
            } catch (e: Exception) {
                Log.e("RecommendationError", "Error fetching mobil recommendations: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun getRecommendationGadget(request: RequestBody): List<GadgetRecommendation> {
        return withContext(Dispatchers.Default) {
            try {
                val responseDto = recommendationService.getRecommendationGadget(request)
                if (responseDto.isSuccessful) {
                    responseDto.body()?.map {
                        it.toGadgetRecommendation()
                    } ?: emptyList()
                } else {
                    throw Exception("Gagal mendapatkan Rekomendasi Gadget")
                }
            } catch (e: Exception) {
                Log.e("RecommendationError", "Error fetching gadget recommendations: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun getRecommendationLuxury(request: RequestBody): List<LuxuryRecommendation> {
        return withContext(Dispatchers.Default) {
            try {
                val responseDto = recommendationService.getRecommendationLuxury(request)
                if (responseDto.isSuccessful) {
                    responseDto.body()?.map {
                        it.toLuxuryRecommendation()
                    } ?: emptyList()
                } else {
                    throw Exception("Gagal mendapatkan Rekomendasi Luxury")
                }
            } catch (e: Exception) {
                Log.e("RecommendationError", "Error fetching luxury recommendations: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun getRecommendationGame(request: RequestBody): List<GameRecommendation> {
        return withContext(Dispatchers.Default) {
            try {
                val responseDto = recommendationService.getRecommendationGame(request)
                if (responseDto.isSuccessful) {
                    responseDto.body()?.map {
                        it.toGameRecommendation()
                    } ?: emptyList()
                } else {
                    throw Exception("Gagal mendapatkan Rekomendasi Game")
                }
            } catch (e: Exception) {
                Log.e("RecommendationError", "Error fetching game recommendations: ${e.message}", e)
                emptyList()
            }
        }
    }

    companion object{
        const val TAG = "RemoteDataRepositoryImpl"
    }
}