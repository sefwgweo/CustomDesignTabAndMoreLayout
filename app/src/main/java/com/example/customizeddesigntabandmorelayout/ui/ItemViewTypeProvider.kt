package com.example.customizeddesigntabandmorelayout.ui

import androidx.databinding.library.baseAdapters.BR

interface ItemViewTypeProvider {

    fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int

    fun getBindingVariableId(modelCollectionItem: CollectionItemViewModel) = BR.viewModel
}