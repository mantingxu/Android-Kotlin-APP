package com.example.rent




import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


//class BallAdapter(val ballList: List<Balls>): Adapter<BallAdapter.ViewHolder>() {
class EditAdapter(private val ballList: ArrayList<house>, val clickListener: (house,Int) -> Unit): RecyclerView.Adapter<EditAdapter.ViewHolder>() {
    //
    private var mObjects : ArrayList<house> = ArrayList<house>()
    init {
        mObjects = ballList
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val ballName: TextView = view.findViewById(R.id.textView4)
//        val ballPrice: TextView = view.findViewById(R.id.price)
//        val ballTel: TextView = view.findViewById(R.id.phone)
//        val ballAddress: TextView = view.findViewById(R.id.address)
//        //        val ballAvailable: TextView = view.findViewById(R.id.available)
//        val ballKeeper: TextView = view.findViewById(R.id.keeper)
//        val ballimage :ImageView = view.findViewById(R.id.image)
//        //        val ballremark: TextView = view.findViewById(R.id.remark)
        val btn_remove:Button = view.findViewById(R.id.remove)
//        val btn_edit:Button = view.findViewById(R.id.edit)


    }



    // 刪除項目

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.edit_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ballList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val count = ballList.size
        val ball = ballList[position]
//        holder.ballImage.setImageURI(ball.arrayUri?.get(0))
        //Picasso.get().load(ball.arrayUri?.get(0)).into(holder.ballimage)
//        val url = Uri.parse(ball.arrayUri?.get(0))
//        Picasso.get().load(url).resize(100,150).into(holder.ballimage)
        holder.ballName.text = ball.name
//        holder.ballAddress.text = ball.address
//        holder.ballKeeper.text = ball.keeper
//        holder.ballPrice.text = ball.price
//        holder.ballremark.text = ball.remark
//        holder.ballTel.text = ball.tel
//        holder.itemView.setOnClickListener { view: View ->
//            clickListener(ball,position)
//        }

        holder.btn_remove.setOnClickListener {

//            FirebaseDatabase.getInstance().getReference("house").child(ballList[position].toString()).removeValue().addOnSuccessListener {
//                ballList.removeAt(position)
//                notifyItemRemoved(position)

            clickListener(ball,position)
            for(i in 0 until count)
            {
                Log.d("TAG",i.toString())
            }
//            ballList.removeAt(position)
//            notifyItemRemoved(position)
        }


    }

}




