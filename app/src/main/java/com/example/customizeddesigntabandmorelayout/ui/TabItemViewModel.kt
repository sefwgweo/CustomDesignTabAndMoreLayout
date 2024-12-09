package com.example.customizeddesigntabandmorelayout.ui

import android.graphics.Color.parseColor
import com.example.customizeddesigntabandmorelayout.model.TabDto

class TabItemViewModel(tabDto: TabDto) {
    val name = tabDto.name
    val textColor = parseColor(tabDto.textColor)
    val backgroundColor = parseColor(tabDto.backgroundColor)
    val indicatorColor = parseColor(tabDto.indicatorColor)
    val useImage = tabDto.useImage
}