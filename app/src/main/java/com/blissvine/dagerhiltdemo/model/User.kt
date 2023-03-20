package com.blissvine.dagerhiltdemo.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    val id: Long? = null,
    val name: String? = null,
    val email: String? = null,
    val profileImageFilePath: String,

)