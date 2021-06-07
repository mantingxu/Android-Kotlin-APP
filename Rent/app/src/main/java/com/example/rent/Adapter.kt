package com.example.rent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso

//class Adapter(var list:List<Int>,var ctx: Context):PagerAdapter() {
class Adapter(var list:ArrayList<String>,var ctx: Context):PagerAdapter() {
    lateinit var layoutInflater:LayoutInflater
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(ctx)

        var view = layoutInflater.inflate(R.layout.item,container,false)

        val img = view.findViewById<ImageView>(R.id.simpleimage)
        val number = view.findViewById<TextView>(R.id.number)
        number.setText((position+1).toString()+"/"+list.size.toString())
        Picasso.get().load(list.get(position)).into(img)
        container.addView(view,0)

        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}