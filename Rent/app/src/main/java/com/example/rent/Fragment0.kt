package com.example.rent

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.rent.databinding.Fragment0Binding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment0.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment0 : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<Fragment0Binding>(inflater,
            R.layout.fragment_0,container,false)
        binding.enviroment.setOnClickListener { view: View ->
            //view.findNavController().navigate(Fragment1Directions.actionFragment1ToFragment2())
//            view.findNavController()
//                .navigate(R.id.action_fragment0_to_myMap)
            val intent = Intent()
//            getActivity()?.let { intent.setClass(it, houseActivity::class.java) }
            getActivity()?.let { intent.setClass(it, houseActivity::class.java) }
            startActivity(intent);
        }
        binding.money.setOnClickListener{
            View ->
            val intent = Intent()
            getActivity()?.let { intent.setClass(it, GoogleActivity::class.java) }
            startActivity(intent);
        }

//        binding.money.setOnClickListener { view: View ->
//            //view.findNavController().navigate(Fragment1Directions.actionFragment1ToFragment2())
//            view.findNavController()
//                .navigate(R.id.action_fragment0_to_google)
//        }
        // Inflate the layout for this fragment
//        setHasOptionsMenu(true)
        return binding.root
    }

//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.options_menu, menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment0.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment0().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}