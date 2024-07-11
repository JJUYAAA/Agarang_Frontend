package Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment: Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var RecentDiaryAdapter: RecentDiaryAdapter?= null
    private var RecentDiaryDataList : ArrayList<RecentDiaryData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initData()
        initRecyclerView()


        // 현재 날짜 가져오기
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        // 날짜를 특정 형식의 문자열로 변환
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)
        binding.tvToday.text = formattedDate

        return binding.root
    }

    private fun initRecyclerView(){
        val spanCount = 3 // 열의 수
        RecentDiaryAdapter = RecentDiaryAdapter(RecentDiaryDataList)
        binding.rvRecentCard.adapter = RecentDiaryAdapter
        binding.rvRecentCard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        /*val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        binding.rvRecentCard.addItemDecoration(itemDecoration)*/
        /*binding.rvRecentCard.layoutManager = GridLayoutManager(context, spanCount)
        binding.rvRecentCard.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.rvRecentCard.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))*/
    }

    private fun initData(){
        RecentDiaryDataList.addAll(
            arrayListOf(
                RecentDiaryData("내용1", R.drawable.recent_card_sample),
                RecentDiaryData("내용2", R.drawable.recent_card_sample),
                RecentDiaryData("내용3", R.drawable.recent_card_sample)
            )

        )

    }

}