package com.example.lab_week_10_77881.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "total")
data class Total(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    // @Embedded means these values will be flattened into the table
    @Embedded
    val total: TotalObject
)

// This is the embedded object holding 2 fields
data class TotalObject(
    @ColumnInfo(name = "value")
    val value: Int,

    @ColumnInfo(name = "date")
    val date: String
)
