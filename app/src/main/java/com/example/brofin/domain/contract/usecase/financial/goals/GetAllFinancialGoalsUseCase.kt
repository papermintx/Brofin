package com.example.brofin.domain.contract.usecase.financial.goals

import com.example.brofin.domain.models.FinancialGoals
import kotlinx.coroutines.flow.Flow

interface GetAllFinancialGoalsUseCase {
    suspend operator fun invoke(): Flow<List<FinancialGoals?>>
}