package com.example.rent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rent.databinding.Fragment2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val ballList = ArrayList<Balls>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<Fragment2Binding>(inflater,
            R.layout.fragment_2,container,false)
//        binding.button.setOnClickListener { view: View ->
//            view.findNavController()
//                .navigate(R.id.action_firstPage_to_secondPage)
//        }
        initBallList()  //set up the data source
        //val layoutManager = LinearLayoutManager(this)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //val layoutManager = GridLayoutManager(this, GridLayoutManager.VERTICAL, false)
        binding.recycleView.layoutManager = layoutManager
       // val adapter = BallAdapter(ballList)  //customized your own adapter
        //binding.recycleView.adapter = adapter
        return binding.root
        // Inflate the layout for this fragment

    }



    private fun initBallList() {
        ballList.add(Balls("沁月莊",R.drawable.school,"2375元", "0972183461","花蓮縣壽豐鄉大學路二段1號","1445","管理員"))
        ballList.add(Balls("志學郵局樓上套房",R.drawable.back_left,"5000元","0937467876","花蓮縣壽豐鄉中山路二段202號","1","鄭先生"))
        ballList.add(Balls("志學村烏杙418號",R.drawable.back_right,"3750元","0928546972","志學村烏杙418號","1","江叔叔"))
        ballList.add(Balls("蓮緣三期(前門吳全)",R.drawable.front,"3800元","無","花蓮縣壽豐鄉豐坪路三段129號","1","無"))
        ballList.add(Balls("近遠百(透天四房)",R.drawable.urban,"16000元","0912200038","花蓮市林榮街74巷7號","1","湯小姐"))

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Fragment2().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}