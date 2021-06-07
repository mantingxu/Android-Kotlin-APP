package com.example.rent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class houseActivity : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var newRecycleview: RecyclerView
    //private lateinit var newArrayList: ArrayList<house>
    val newArrayList = arrayListOf<house>()
    val displayList = arrayListOf<house>()

    lateinit var imageId: Array<Int>
    lateinit var name: Array<String>
    lateinit var tel: Array<String>
    lateinit var address: Array<String>
    lateinit var price: Array<String>
    lateinit var keeper: Array<String>
    lateinit var remark: Array<String>
    lateinit var mapbtn: ArrayList<Button>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house)
        this.setTitle("租屋一覽")





//
//        imageId = arrayOf(
//            R.drawable.school,
//            R.drawable.back_left,
//            R.drawable.back_right,
//            R.drawable.front,
//            R.drawable.urban
//        )
//
//        name = arrayOf(
//            "沁月莊",
//            "志學郵局樓上套房",
//            "志學村烏杙418號",
//            "蓮緣三期(前門吳全)",
//            "近遠百(透天四房)"
//        )
//
//        tel = arrayOf(
//            "0972183461",
//            "0937467876",
//            "0928546972",
//            "無",
//            "0912200038"
//        )
//
//        address = arrayOf(
//            "花蓮縣壽豐鄉大學路二段1號",
//            "花蓮縣壽豐鄉中山路二段202號",
//            "志學村烏杙418號",
//            "花蓮縣壽豐鄉豐坪路三段129號",
//            "花蓮市林榮街74巷7號"
//        )
//
//        price = arrayOf(
//            "2375元",
//            "5000元",
//            "3750元",
//            "3800元",
//            "16000元"
//        )
//
//        keeper = arrayOf(
//            "管理員",
//            "鄭先生",
//            "江叔叔",
//            "無",
//            "湯小姐"
//        )
//
//        remark = arrayOf(
//            "無",
//            "無",
//            "無",
//            "無",
//            "無"
//        )

        newRecycleview = findViewById(R.id.recycleView)
        newRecycleview.layoutManager = LinearLayoutManager(this)
        newRecycleview.setHasFixedSize(true)
//        newArrayList = ArrayList<house>()
//        newArrayList = arrayListOf<house>()
        getUserdata()


    }


    private fun getUserdata() {
        dbref = FirebaseDatabase.getInstance().getReference("house")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        Log.d("TAG", userSnapshot.toString())
                        val addhouse = userSnapshot.getValue(house::class.java)
                        newArrayList.add(addhouse!!)
                    }
                    displayList.addAll(newArrayList)
                    newRecycleview.adapter = BallAdapter(displayList) { ball: house, position: Int ->
                        Toast.makeText(this@houseActivity,"查看照片請滑動視窗",Toast.LENGTH_LONG).show()
                        Log.e("Activity", "Clicked on item ${ball.name}")

                        val intent = Intent(this@houseActivity, imageActivity::class.java)
                        intent.putExtra("NAME", ball.name)
                        intent.putExtra("price", ball.price)
                        intent.putExtra("ADDRESS", ball.address)
                        intent.putExtra("tel", ball.tel)
                        intent.putExtra("keeper", ball.keeper)
                        intent.putExtra("remark", ball.remark)
                        intent.putExtra("url", ball.arrayUri)
                        startActivity(intent)
                        }

                }
            }
        })


//        for (i in imageId.indices) {
//            val houses = house(
//                name[i],
//                imageId[i],
//                price[i],
//                tel[i],
//                address[i],
//                keeper[i],
//                remark[i],
//                arrayListOf<String>()
//            )
//            newArrayList.add(houses)
//        }


        //var adapter = BallAdapter(newArrayList)
        //newRecycleview.adapter = adapter
//        newRecycleview.adapter = BallAdapter(newArrayList) { ball: house, position: Int ->
////            fun onItemClick(position: Int)
////            {
//            Log.e("Activity", "Clicked on item ${ball.name}")
//
//            val intent = Intent(this@houseActivity, imageActivity::class.java)
////                intent.putExtra("imageId",newArrayList[position].imageId)
//            intent.putExtra("NAME", ball.name)
//            intent.putExtra("price", ball.price)
//            intent.putExtra("ADDRESS", ball.address)
//            intent.putExtra("tel", ball.tel)
//            intent.putExtra("keeper", ball.keeper)
////                intent.putExtra("NAME",newArrayList[position].name)
////                intent.putExtra("price",newArrayList[position].price)
////                intent.putExtra("ADDRESS",newArrayList[position].address)
////                intent.putExtra("tel",newArrayList[position].tel)
////                intent.putExtra("keeper",newArrayList[position].keeper)
//            startActivity(intent)
//
//
//            // }
////                val intent1 =  Intent( this@houseActivity,imageActivity::class.java)
////                startActivity(intent1);
////            Log.e("Activity", "Clicked on item ${it.name}")
//        }
//        adapter.setOnItemClickListener(object :BallAdapter.onItemClickListener
//        {
//            fun onItemClick(position: Int)
//            {
//                val intent =  Intent( this@houseActivity,imageActivity::class.java)
//                intent.putExtra("imageId",newArrayList[position].imageId)
//                intent.putExtra("name",newArrayList[position].name)
//                intent.putExtra("price",newArrayList[position].price)
//                intent.putExtra("address",newArrayList[position].address)
//                intent.putExtra("available",newArrayList[position].available)
//                intent.putExtra("tel",newArrayList[position].tel)
//                intent.putExtra("keeper",newArrayList[position].keeper)
//                startActivity(intent)
//            }
//        })
//        OnItemClickListener { parent, view, position, id ->
//
//            val intent =  Intent( this@houseActivity,imageActivity::class.java)
//            intent.putExtra("imageId",newArrayList[position].imageId)
//            intent.putExtra("name",newArrayList[position].name)
//            intent.putExtra("price",newArrayList[position].price)
//            intent.putExtra("address",newArrayList[position].address)
//            intent.putExtra("available",newArrayList[position].available)
//            intent.putExtra("tel",newArrayList[position].tel)
//            intent.putExtra("keeper",newArrayList[position].keeper)
//            startActivity(intent)
//        }
//        adapter.set()
////        adapter.(object :BallAdapter.onItemClickListener{
////            override fun onItemClick(position: Int){
////
////            }
////        })
//    }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val menuItem = menu!!.findItem(R.id.searchview)


        if(menuItem != null)
        {
            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {


                    if(newText!!.isNotEmpty())
                    {
                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        newArrayList.forEach{
                            if(it.name?.toLowerCase(Locale.getDefault())!!.contains(search))
                            {
                                displayList.add(it)
                            }
                        }
                        newRecycleview.adapter!!.notifyDataSetChanged()
                    }
                    else
                    {
                        displayList.clear()
                        displayList.addAll(newArrayList)
                        newRecycleview.adapter!!.notifyDataSetChanged()
                    }
                    return true

                }


            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}