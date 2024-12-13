package com.example.customizeddesigntabandmorelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.customizeddesigntabandmorelayout.R
import com.example.customizeddesigntabandmorelayout.databinding.FragmentTabBinding
import com.example.customizeddesigntabandmorelayout.databinding.TabItemBinding
import com.example.customizeddesigntabandmorelayout.model.TabDto
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.ref.WeakReference

class TabFragment : Fragment(R.layout.fragment_tab) {

    companion object {
        fun newInstance(): TabFragment {
            return TabFragment()
        }
    }

    private lateinit var binding: FragmentTabBinding
    private val viewModel: TabViewModel by viewModels()
    private lateinit var tabList: List<TabDto>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        initUIComponent()
    }

    private fun initUIComponent() {
        binding.toolbar.title = getString(R.string.app_name)
        tabList = viewModel.fetchTabs()
        val tabItemViewModel = tabList.map {
            TabItemViewModel(it)
        }
        val pagerAdapter = TabPagerAdapter(this@TabFragment, tabList)
        binding.pager.apply {
            adapter = pagerAdapter
            offscreenPageLimit = pagerAdapter.itemCount
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabItemViewModel.forEachIndexed { index, viewModel ->
                        binding.tabLayout.setSelectedTabIndicatorColor(tabItemViewModel[position].indicatorColor)
                    }
                }
            })
        }
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            val binding = TabItemBinding.inflate(layoutInflater)
            binding.viewModel = tabItemViewModel[position]
            tab.customView = binding.root
        }.attach()
    }

    private class TabPagerAdapter(
        fragment: TabFragment,
        private val tabList: List<TabDto>,
    ) : FragmentStateAdapter(fragment) {
        private val fragments: MutableList<WeakReference<Fragment>> = mutableListOf()

        override fun getItemCount(): Int = tabList.size
        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> RedFragment.newInstance()
                1 -> YellowFragment.newInstance()
                2 -> BlueFragment.newInstance()
                else -> BlueFragment.newInstance()
            }

            fragments.add(WeakReference(fragment))
            return fragment
        }
    }
}