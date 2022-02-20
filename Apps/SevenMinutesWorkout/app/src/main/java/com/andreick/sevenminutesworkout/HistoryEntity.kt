package com.andreick.sevenminutesworkout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity (
    @PrimaryKey val date: String
)