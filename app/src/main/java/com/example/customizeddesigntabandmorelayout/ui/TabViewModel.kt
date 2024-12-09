package com.example.customizeddesigntabandmorelayout.ui

import androidx.lifecycle.ViewModel
import com.example.customizeddesigntabandmorelayout.model.TabDto

class TabViewModel : ViewModel() {

    fun fetchTabs(): List<TabDto> {
        return listOf(
            TabDto(
                name = "Red",
                textColor = "#dc143c",
                backgroundColor = "#40dc143c",
                indicatorColor = "#dc143c",
                useImage = false
            ),
            TabDto(
                name = "Blue",
                textColor = "#4169e1",
                backgroundColor = "#404169e1",
                indicatorColor = "#4169e1",
                useImage = false
            ),
            TabDto(
                name = "Yellow",
                textColor = "#f0e68c",
                backgroundColor = "#40f0e68c",
                indicatorColor = "#f0e68c",
                useImage = true
            ),
        )
    }
}