package com.moonbanggoo.ohgod.ViewUtility.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.moonbanggoo.ohgod.Model.PlanModel
import com.moonbanggoo.ohgod.Presenter.PlanListFragmentPresenter
import com.moonbanggoo.ohgod.R

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

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item : PlanModel = items.get(position)
        val pos = position
        if(item.plan_name.length > 25){
            holder.planName.text = item.plan_name.substring(0,20) + "..."
        }else{
        holder.planName.text = item.plan_name
        }
        holder.planDate.text = item.plan_date
        holder.planCard.setOnClickListener{

            var item = PlanModel(item.plan_date,item.plan_name)
//            var intent : Intent
//            intent = Intent(context,RoomInfoActivity::class.java)
//            intent.putExtra("classId",item.classId)
//            isNowTotal = false;
//            context?.startActivity(intent)
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(context!!)
            dialogBuilder.setTitle(item.plan_name)
            dialogBuilder.setMessage("이 일정을 삭제하시겠어요?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("예", DialogInterface.OnClickListener {
                        dialog, id -> PlanListFragmentPresenter.deletePlan(item)
                        Toast.makeText(context,"일정이 삭제되었습니다",Toast.LENGTH_SHORT)
                })
                // negative button text and action
                .setNegativeButton("아니요", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle(item.plan_name)
            // show alert dialog
            alert.show()

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