package com.example.ohgod.ViewUtility.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ohgod.Model.PlanModel
import com.example.ohgod.R

class PlanRecyclerAdapter : RecyclerView.Adapter<PlanRecyclerAdapter.ViewHolder>{
    var context: Context?
    var items: ArrayList<PlanModel>
    constructor(context: FragmentActivity?, items: ArrayList<PlanModel>){
        this.context = context as Context?
        this.items = items
    }

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plan, null)
        return ViewHolder(v)
    }

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item : PlanModel = items.get(position)
        val pos = position
        holder.planName.text = item.plan_name
        holder.planDate.text = item.plan_date
        holder.planCard.setOnClickListener{
//            var intent : Intent
//            intent = Intent(context,RoomInfoActivity::class.java)
//            intent.putExtra("classId",item.classId)
//            isNowTotal = false;
//            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = this.items.size

    class ViewHolder constructor (itemView : View)
        : RecyclerView.ViewHolder(itemView){
        val planCard = itemView.findViewById<CardView>(R.id.plan_card)
        val planName = itemView.findViewById<TextView>(R.id.plan_name)
        val planDate = itemView.findViewById<TextView>(R.id.plan_date)
    }

}