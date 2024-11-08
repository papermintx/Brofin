package com.example.brofin.contract.usecase.budgeting.diary

import com.example.brofin.domain.models.BudgetingDiary
import kotlinx.coroutines.flow.Flow

interface GetAllBudgetingDiaryEntriesUseCase {
    suspend operator fun invoke(): Flow<List<BudgetingDiary?>>
}