package com.example.customizeddesigntabandmorelayout.ui

import androidx.databinding.ObservableArrayList

class SectionCarouselItemViewModel(
    val dto: List<CarouselItemViewModel>,
) : CollectionItemViewModel {

    fun getCarouselItemViewModels(): ObservableArrayList<CollectionItemViewModel> {
        return ObservableArrayList<CollectionItemViewModel>().apply {
            val items = dto.map { carouselItem ->
                CarouselItemViewModel(
                    carouselItem.description,
                    carouselItem.image
                )
            }
            addAll(items)
        }
    }

    fun onSnapScrolled(position: Int) {
        // Do something
    }
}