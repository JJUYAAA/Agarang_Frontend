package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentDiaryMainCardBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiaryMainCardFragment : Fragment() {
    private lateinit var binding: FragmentDiaryMainCardBinding
    private lateinit var dateAdapter: DateAdapter
    private lateinit var cardAdapter: CardviewAdapter
    private var diaryDataList: List<DiaryMainDayData> = listOf()
    private var currentPosition = 0
    private var scrollJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiaryMainCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val data = it.getSerializable("data") as? ArrayList<DiaryMainDayData>
            val position = it.getInt("position", 0)
            if (data != null) {
                setData(data, position)
            }
        }
    }

    private fun setupRecyclerViews() {
        dateAdapter = DateAdapter(diaryDataList) { position ->
            scrollToPosition(position)
        }
        binding.rvDiaryDate.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        cardAdapter = CardviewAdapter(diaryDataList)
        binding.rvDiaryCardView.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (firstVisibleItemPosition != RecyclerView.NO_POSITION && firstVisibleItemPosition != currentPosition) {
                        currentPosition = firstVisibleItemPosition
                        updateDateSelection(currentPosition)
                    }
                }
            })
        }

        scrollToPosition(currentPosition)
    }

    private fun scrollToPosition(position: Int) {
        scrollJob?.cancel()
        scrollJob = MainScope().launch {
            delay(100) // debounce
            binding.rvDiaryCardView.smoothScrollToPosition(position)
            binding.rvDiaryDate.smoothScrollToPosition(position)
            dateAdapter.updateSelectedPosition(position)
        }
    }

    private fun updateDateSelection(position: Int) {
        dateAdapter.updateSelectedPosition(position)
        binding.rvDiaryDate.smoothScrollToPosition(position)
    }

    fun setData(data: List<DiaryMainDayData>, initialPosition: Int) {
        diaryDataList = data
        currentPosition = initialPosition
        setupRecyclerViews()
    }
}