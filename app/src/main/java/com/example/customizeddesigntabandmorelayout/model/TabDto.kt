package com.example.customizeddesigntabandmorelayout.model

data class TabDto(
    val name: String,
    val textColor: String,
    val backgroundColor: String,
    val indicatorColor: String,
    val useImage: Boolean = false,
)
