package com.thewooks.planyourday

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.thewooks.planyourday.databinding.FragmentTab2Binding
import com.thewooks.planyourday.databinding.MonthlyItemBinding
import kotlinx.android.synthetic.main.fragment_tab2.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class FragmentTab_2 : Fragment() {

    private lateinit var binding: FragmentTab2Binding
    private val viewModel: Fragment_2_ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTab2Binding.inflate(layoutInflater)

        viewModel.GetData()

        binding.recyclerviewMonthly.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerviewMonthly.adapter = MonthlyAdapter(
            viewModel.data
            ,
            onClickDeleteIcon = {
                viewModel.deleteMonthlyList(it)
                binding.recyclerviewMonthly.adapter?.notifyDataSetChanged()

            },
            onClickItem = {
                viewModel.toggleMonthlylist(it)
                binding.recyclerviewMonthly.adapter?.notifyDataSetChanged()

            }
        )

        binding.addObjectiveButton.setOnClickListener {
            val monthlyList = MonthlyData(binding.editTextMonthly.text.toString(), false)
            viewModel.addMonthlyList(monthlyList)
            binding.recyclerviewMonthly.adapter?.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changeTitletoDate()


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.StoreData(viewModel.data)
    }


    fun changeTitletoDate() {
        val calendar = Calendar.getInstance()
        val month: Int = calendar.get(Calendar.MONTH) + 1
        textView1.text = (month.toString() + "월 목표")
    }

}

class MonthlyAdapter(
    private val myDataset: List<MonthlyData>,
    var onClickDeleteIcon: (monthlydata: MonthlyData) -> Unit,
    var onClickItem: (monthlydata: MonthlyData) -> Unit
) :
    RecyclerView.Adapter<MonthlyAdapter.MonthlyViewholder>() {

    class MonthlyViewholder(val binding: MonthlyItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,    // 아이템을 뷰홀더에 넣는 작업
        viewType: Int
    ): MonthlyAdapter.MonthlyViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.monthly_item, parent, false)
        return MonthlyViewholder(MonthlyItemBinding.bind(view))
    }

    override fun onBindViewHolder(
        holder: MonthlyViewholder,
        position: Int
    ) {  //아이템을 보여주는 기능 (보여지는것은 여기서 수정) // mDataset[position]은 해당 위치의 아이템을 말하는 것
        holder.binding.monthlyEdittext.text = myDataset[position].text

        if (myDataset[position].isDone == true) {
            holder.binding.monthlyEdittext.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        } else {
            holder.binding.monthlyEdittext.apply {
                paintFlags = 0
            }
        }

        holder.binding.root.setOnClickListener {
            onClickItem.invoke(myDataset[position])
        }
        holder.binding.monthlyDeleteImage.setOnClickListener {
            onClickDeleteIcon.invoke(myDataset[position])
        }
    }

    override fun getItemCount() = myDataset.size
}


class Fragment_2_ViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    var data = arrayListOf<MonthlyData>()

    fun StoreData(data: ArrayList<MonthlyData>) {
        var mSharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this.context)
        var mEditor: SharedPreferences.Editor = mSharedPreferences.edit()
        var gson = Gson()
        var json = gson.toJson(data)
        mEditor.putString("data", json)
        mEditor.apply()

        Log.d("wwwwwww", "$json")
    }


    fun GetData(): ArrayList<MonthlyData> {
        var mSharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this.context)
        var getDataString = mSharedPreferences.getString("data", "$data")
        var jarray: JSONArray = JSONArray(getDataString)
        Log.d("jarray", "$jarray")
        Log.d("jarrayLength", "${jarray.length()}")
//JSONObject로 바꿈
        var arrayJson = arrayListOf<JSONObject>()
        for (i in 0 until jarray.length()) {
            var tempJson: JSONObject = jarray.getJSONObject(i)
            arrayJson.add(tempJson)
            Log.d("arrayJson", "${arrayJson.get(i)}")
        }
//        //넣기
        var arrayMonthlyData = arrayListOf<MonthlyData>()
        for (i in 0 until jarray.length()) {
            var tempMonthlyData: JSONObject = arrayJson.get(i)
            var isDone = tempMonthlyData.get("isDone") as Boolean
            var text = tempMonthlyData.get("text").toString()
            Log.d("isDone", "$isDone")
            Log.d("text", "$text")
            arrayMonthlyData.add(i, MonthlyData(text, isDone))
            Log.d("arrayMonthlyData", "${arrayMonthlyData.get(i)}")
        }
        Log.d("arrayMonthlyData", "$arrayMonthlyData")

        this.data = arrayMonthlyData

        return arrayMonthlyData
    }

    fun toggleMonthlylist(monthlydata: MonthlyData) {
        monthlydata.isDone = !monthlydata.isDone
    }

    fun addMonthlyList(monthlydata: MonthlyData) {
        if (data.size > 7) {

        } else {
            data.add(monthlydata)
        }
    }

    fun deleteMonthlyList(monthlyData: MonthlyData) {
        data.remove(monthlyData)
    }

}
