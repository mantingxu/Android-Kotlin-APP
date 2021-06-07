package com.example.rent

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ViewSwitcher
import androidx.databinding.DataBindingUtil
import com.example.rent.databinding.Fragment2Binding
import com.example.rent.databinding.Fragment3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment(),ViewSwitcher.ViewFactory {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: Fragment3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    val schoolimages = arrayOf(R.drawable.school1,R.drawable.school2,R.drawable.school3,R.drawable.school4)
    var counter = 0


    private fun setSwitching()
    {
        setFactory()
        setAnimation()
    }

    private fun setFactory()
    {
        binding.imageSwitcher1.setFactory (ViewSwitcher.ViewFactory{
            getImageView()
        })

    }

    private fun getImageView():ImageView
    {
        val imageView = ImageView(context)
        //imageView.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageResource(R.drawable.school1)

        return imageView

    }

    private fun setAnimation()
    {
        val animOut = AnimationUtils.loadAnimation(context,android.R.anim.slide_out_right)
        val animIn = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left)

        val animOut1 = AnimationUtils.loadAnimation(context,android.R.anim.fade_out)
        val animIn1 = AnimationUtils.loadAnimation(context,android.R.anim.fade_in)
        binding.imageSwitcher1.outAnimation = animOut
        binding.imageSwitcher1.inAnimation = animIn
//        imageswitcher_languages.outAnimation = animOut
//        imageswitcher_languages.inAnimation = animOut

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val binding = DataBindingUtil.inflate<Fragment3Binding>(inflater,
            R.layout.fragment_3,container,false)
        setSwitching()
        binding.button7.setOnClickListener{
            counter++
            if(counter == schoolimages.size)
            {
                counter = 0
            }
            val imageChange = schoolimages[counter]
            binding.imageSwitcher1.setImageResource(imageChange)

        }
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_3, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun makeView(): View {
        val v= ImageView(context)
        v.setBackgroundColor(Color.WHITE)
        v.scaleType = ImageView.ScaleType.FIT_CENTER
        return v

    }
}