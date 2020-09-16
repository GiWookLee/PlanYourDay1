package com.thewooks.planyourday

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.thewooks.planyourday.databinding.FragmentTab1Binding
import com.thewooks.planyourday.databinding.OneLineItemBinding
import kotlinx.android.synthetic.main.fragment_tab1.*
import kotlinx.android.synthetic.main.popup_button.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException
import java.lang.NumberFormatException
import java.lang.RuntimeException
import java.lang.reflect.InvocationTargetException

class FragmentTab_1 : Fragment() {

    private lateinit var binding: FragmentTab1Binding
    private val viewModel: Fragment_1_ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTab1Binding.inflate(layoutInflater)
        try {
            viewModel.GetData()
        } catch (e: RuntimeException) {

        }
        binding.recyclerview1.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerview1.adapter = OneLineAdapter(
            viewModel.data,
            changeColorListener = {
                viewModel.changeColorListener(it)
                binding.recyclerview1.adapter?.notifyDataSetChanged()
            },
            checkBoxONOFF = {
                viewModel.checkboxONOFF(it)
                binding.recyclerview1.adapter?.notifyDataSetChanged()
            },
            textButton = {
                viewModel.textButton(it)
                binding.recyclerview1.adapter?.notifyDataSetChanged()
            }
        )
//화면 구성
//        for (i in 0..23) {
//            var onelineList = OneLineData(false, "", "$i:00 ~  $i:30", R.color.Default)
//            var a: Int = i + 1
//            var onelineList2 = OneLineData(false, "", "$i:30 ~ $a:00", R.color.Default)
//            viewModel.addTimeList(onelineList)
//            viewModel.addTimeList(onelineList2)
//        }
//        binding.recyclerview1.adapter?.notifyDataSetChanged()


        binding.getObjective.setOnClickListener {
            var intent: Intent = Intent(this.context, PopupActivity::class.java)
            startActivity(intent)
        }


        binding.resetLayout.setOnClickListener {
            viewModel.data.clear()
            nowData.text = ""
            for (i in 0..23) {
                var onelineList = OneLineData(false, "", "$i:00 ~  $i:30", R.color.Default)
                var a: Int = i + 1
                var onelineList2 = OneLineData(false, "", "$i:30 ~ $a:00", R.color.Default)
                viewModel.addTimeList(onelineList)
                viewModel.addTimeList(onelineList2)
            }
            binding.recyclerview1.adapter?.notifyDataSetChanged()
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        var sharedPreferences: SharedPreferences? =
            context?.getSharedPreferences("textButton", 0)
        var getText: String? = sharedPreferences?.getString("textButton", null)
        binding.nowData.text = getText
        binding.recyclerview1.adapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.StoreData(viewModel.data)


    }
}


class OneLineAdapter(
    private var items: List<OneLineData>,                    // (items) 으로 for문을 가져옴
    var changeColorListener: (oneLine1: OneLineData) -> Unit,
    var checkBoxONOFF: (oneLine1: OneLineData) -> Unit,
    var textButton: (oneLine1: OneLineData) -> Unit
) :
    RecyclerView.Adapter<OneLineAdapter.OneLineViewHolder>() {
    class OneLineViewHolder(val binding: OneLineItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneLineViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_line_item, parent, false)
        return OneLineViewHolder(OneLineItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: OneLineViewHolder, position: Int) {
        holder.binding.timeButton.text = items[position].timetext
        holder.binding.timeButton.setBackgroundResource(items[position].timecolor)
        holder.binding.checkboxMain.isChecked = items[position].checkBox
        holder.binding.buttonMain.text = items[position].button //팝업화면 Edittext 만들어서 연동해야됨.

//~~~~~~~~~
        holder.binding.buttonMain.setOnClickListener {
            textButton.invoke(items[position])
        }
//~~~~~~~~~~
        holder.binding.timeButton.setOnClickListener {
            Log.d("timeButton", "${items[position]}")
            changeColorListener.invoke(items[position])
        }

        holder.binding.checkboxMain.setOnClickListener {
            checkBoxONOFF.invoke(items[position])
        }

        holder.binding.buttonMain.setOnClickListener {
            textButton.invoke(items[position])

        }
    }

    override fun getItemCount() = items.size     // items은 for문이니 그거 에 맞게 갯수를 만듬
}

class Fragment_1_ViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    var data = arrayListOf<OneLineData>()

    fun addTimeList(oneLine1: OneLineData) {
        data.add(oneLine1)
    }

    fun checkboxONOFF(oneLine1: OneLineData) {
        oneLine1.checkBox = !oneLine1.checkBox
    }

    //~~~~~~~~
    fun textButton(oneLine1: OneLineData) {

        var sharedPreferences: SharedPreferences =
            context.getSharedPreferences("textButton", 0)
        var getText: String? = sharedPreferences.getString("textButton", null)
        oneLine1.button = getText
    }

    //~~~~~~~~~~~
    fun changeColorListener(oneLine1: OneLineData) {

        var sharedPreferences: SharedPreferences = context.getSharedPreferences("countData", 0)
        var getCount: Int? = sharedPreferences.getInt("countData", 0)
        Log.d("getCountinFrag", "$getCount")
        Log.d("countFrag", "$getCount")
        when (getCount) {
            1 -> oneLine1.timecolor = R.color.MyOrange
            2 -> oneLine1.timecolor = R.color.MyYellow
            3 -> oneLine1.timecolor = R.color.MyGreen
            4 -> oneLine1.timecolor = R.color.MyBlue
            5 -> oneLine1.timecolor = R.color.MyIndigo
            6 -> oneLine1.timecolor = R.color.MyPurple
            else -> oneLine1.timecolor = R.color.MyRed
        }
    }

    fun StoreData(data: ArrayList<OneLineData>) {
        var mSharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this.context)
        var mEditor: SharedPreferences.Editor = mSharedPreferences.edit()
        var gson = Gson()
        var json = gson.toJson(data)
        mEditor.putString("data_oneline", json)
        mEditor.apply()

        Log.d("wwwwwww", "$json")

    }


    fun GetData(): ArrayList<OneLineData> {
        var mSharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this.context)
        var getDataString = mSharedPreferences.getString("data_oneline", null)
        var jarray: JSONArray = JSONArray(getDataString)

        Log.d("jarray_oneline", "$jarray")
        Log.d("jarrayLength_oneline", "${jarray.length()}")
//JSONObject로 바꿈
        var arrayJson = arrayListOf<JSONObject>()
        for (i in 0 until jarray.length()) {
            var tempJson: JSONObject = jarray.getJSONObject(i)
            arrayJson.add(tempJson)
            Log.d("arrayJson", "${arrayJson.get(i)}")
        }
//        //넣기
        var arrayOnelineData = arrayListOf<OneLineData>()
        for (i in 0 until jarray.length()) {
            var tempOneLineData: JSONObject = arrayJson.get(i)

            var checkBox = tempOneLineData.get("checkBox") as Boolean
            var button: String? = tempOneLineData.get("button").toString()
            var timetext: String = tempOneLineData.get("timetext").toString()
            var timecolor: Int = tempOneLineData.get("timecolor") as Int

            Log.d("checkBox", "$checkBox")
            Log.d("button", "$button")
            Log.d("timetext", "$timetext")
            Log.d("timecolor", "$timecolor")

            arrayOnelineData.add(i, OneLineData(checkBox, button, timetext, timecolor))
            Log.d("arrayMonthlyData", "${arrayOnelineData.get(i)}")
        }
        Log.d("arrayMonthlyData", "$arrayOnelineData")

        this.data = arrayOnelineData

        return arrayOnelineData
    }

//
//    fun getDataForFirst(): ArrayList<OneLineData> {
//
//    }
}















