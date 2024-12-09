package com.example.customizeddesigntabandmorelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import com.example.customizeddesigntabandmorelayout.R
import com.example.customizeddesigntabandmorelayout.databinding.FragmentItemBinding
import com.example.customizeddesigntabandmorelayout.databinding.ListItemCarouselBinding

class YellowFragment : Fragment((R.layout.fragment_item)) {
    companion object {
        fun newInstance(): YellowFragment {
            return YellowFragment()
        }
    }

    private lateinit var binding: FragmentItemBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        initUIComponent()
    }

    val sectionViewTypeProvider = object : ItemViewTypeProvider {
        override fun getLayoutRes(modelCollectionItem: CollectionItemViewModel): Int {
            return when (modelCollectionItem) {
                is SectionCarouselItemViewModel -> R.layout.list_item_carousel
                is CarouselItemViewModel -> R.layout.list_item_horizontal
                is SectionSingleItemViewModel -> R.layout.list_item_single
                else -> throw IllegalArgumentException("Unknown item type")
            }
        }
    }

    private val onPostBindViewListener: ((CollectionItemViewModel, ViewGroup) -> Unit) =
        onPostBindViewListener@{ viewModel, viewGroup ->
            when (viewModel) {
                is SectionCarouselItemViewModel -> {
                    val carouselItemBinding = ListItemCarouselBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                    carouselItemBinding.recyclerView.adapter = RecyclerViewAdapter(getList(), sectionViewTypeProvider)
                }
                else -> return@onPostBindViewListener
            }
        }

    private fun initUIComponent() {
        binding.recyclerView.adapter = RecyclerViewAdapter(getList(), sectionViewTypeProvider, onPostBindViewListener)
    }

    private fun getList(): ObservableArrayList<CollectionItemViewModel> {
        return ObservableArrayList<CollectionItemViewModel>().apply {
            add(SectionCarouselItemViewModel(
                listOf<CarouselItemViewModel>(
                    CarouselItemViewModel(
                        "Description 1",
                        ContextCompat.getDrawable(requireContext(), R.drawable.img1)
                    ),
                    CarouselItemViewModel(
                        "Description 2",
                        ContextCompat.getDrawable(requireContext(), R.drawable.img2)
                    ),
                    CarouselItemViewModel(
                        "Description 3",
                        ContextCompat.getDrawable(requireContext(), R.drawable.img3)
                    )
                )
            ))
            add(SectionSingleItemViewModel(
                "Description 4",
                ContextCompat.getDrawable(requireContext(), R.drawable.img2)
            ))
            add(SectionSingleItemViewModel(
                "Description 5",
                ContextCompat.getDrawable(requireContext(), R.drawable.img3)
            ))
        }
    }
}