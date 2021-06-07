//package com.example.rent
//
//import android.content.Context
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.inputmethod.InputMethodManager
//import androidx.core.content.ContextCompat.getSystemService
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.viewModels
//import androidx.navigation.findNavController
//import com.example.rent.databinding.Fragment1Binding
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [Fragment1.newInstance] factory method to
// * create an instance of this fragment.
// */
//class Fragment1 : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        val binding = DataBindingUtil.inflate<Fragment1Binding>(inflater,
//            R.layout.fragment_1,container,false)
//        binding.front.setOnClickListener { view: View ->
//            //view.findNavController().navigate(Fragment1Directions.actionFragment1ToFragment2())
//            view.findNavController()
//                .navigate(R.id.action_fragment_to_fragment2)
//        }
//        binding.backRight.setOnClickListener { view: View ->
//            //view.findNavController().navigate(Fragment1Directions.actionFragment1ToFragment2())
//            view.findNavController()
//                .navigate(R.id.action_fragment1_to_fragment3)
//        }
//        binding.backLeft.setOnClickListener { view: View ->
//            //view.findNavController().navigate(Fragment1Directions.actionFragment1ToFragment2())
//            view.findNavController()
//                .navigate(R.id.action_fragment1_to_fragment4)
//        }
//        //val args = Fragment1Args.fromBundle(requireArguments())
//
//        binding.free.setOnClickListener { view: View ->
//            val longtitude = 121.565000.toFloat();
//            val latitude = 25.033611.toFloat();
////            val address = "NDHU";
//            val name = "taipei";
////            val address = binding.location.text.toString();
////            val name = binding.location.text.toString();
//
////            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//            view.findNavController().navigate(Fragment1Directions.actionFragment1ToMyMap(name,longtitude,latitude))
////            view.findNavController()
////                .navigate(R.id.action_fragment1_to_fragment5)
////            view.findNavController()
////                .navigate(R.id.action_fragment1_to_myMap)
//        }
//
//        return binding.root
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment Fragment1.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                Fragment1().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
//}