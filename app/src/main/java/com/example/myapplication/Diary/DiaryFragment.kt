package com.example.myapplication.Diary.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.Diary.DiaryMainTabLayoutVPAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryBinding
import com.google.android.material.tabs.TabLayoutMediator


class DiaryFragment : Fragment() {

    lateinit var binding: FragmentDiaryBinding
    private val tapList = arrayListOf("즐겨찾기", "월", "일")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.vpMain.adapter = DiaryMainTabLayoutVPAdapter(requireActivity())
        TabLayoutMediator(binding.tlMain, binding.vpMain){ tab, position ->
            tab.text = tapList[position]
        }.attach()

        // 특정 탭의 너비 변경
        binding.tlMain.postDelayed({
            val tabs = binding.tlMain.getChildAt(0) as ViewGroup
            val tab = tabs.getChildAt(0)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = 30
            tab.layoutParams = layoutParams
        }, 100)

        // 특정 탭의 최소 너비 및 간격 변경
        binding.tlMain.post {
            val tabs = binding.tlMain.getChildAt(0) as ViewGroup

            val tab = tabs.getChildAt(0)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams

            // 간격 설정 (탭 간의 간격을 줄이기 위해 오른쪽 마진을 음수로 설정)
            if (0 < tabs.childCount - 1) {
                layoutParams.marginEnd = -10 // 음수 값으로 설정하여 간격을 줄임
            }

            tab.layoutParams = layoutParams
        }
    }

}