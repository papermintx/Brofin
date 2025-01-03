package com.example.brofin.data.local.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BudgetingWithDiaries(
    @Embedded val budgeting: BudgetingEntity,
    @Relation(
        parentColumn = "monthAndYear",
        entityColumn = "monthAndYear"
    )
    val diaries: List<BudgetingDiaryEntity>
)