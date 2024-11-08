package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgeting_diary")
data class BudgetingDiaryEntity (
    @PrimaryKey(autoGenerate = true) val entryId: Int = 0,
    val date: Long,
    val description: String,
    val amount: Double,
    val isExpense: Boolean
)
