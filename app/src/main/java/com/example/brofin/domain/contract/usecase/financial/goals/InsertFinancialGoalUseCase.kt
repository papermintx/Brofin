package com.example.brofin.domain.contract.usecase.financial.goals

import com.example.brofin.domain.models.FinancialGoals

interface InsertFinancialGoalUseCase {
    suspend operator fun invoke(goal: FinancialGoals)
}