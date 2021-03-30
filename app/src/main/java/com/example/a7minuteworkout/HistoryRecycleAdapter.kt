package com.example.a7minuteworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_recycler.*
import kotlinx.android.synthetic.main.history_recycler.view.*

class HistoryRecycleAdapter(val context: Context,val myList:ArrayList<data>)
    :RecyclerView.Adapter<HistoryRecycleAdapter.myHolder> (){
    inner class myHolder(view : View):RecyclerView.ViewHolder(view){
        var llHistory=view.llHistory
        var edit=view.edit
        var delete=view.delete
        var idDate=view.idDate
        var idQuote=view.idQuote

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        return myHolder(LayoutInflater.from(context).inflate((R.layout.history_recycler)
                ,parent,false))
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        val singleData  =myList[position]
        holder.idDate.text=singleData.date
        holder.idQuote.text=singleData.quote
        if(position%2==0){
            holder.llHistory.setBackgroundColor(ContextCompat.getColor(context,R.color.lightGrey))
        }else{
            holder.llHistory.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
        }

        holder.edit.setOnClickListener {
            if(context is History) {
                context.updateDialog(singleData)
            }
        }
        holder.delete.setOnClickListener {
            if(context is History) {
                context.deleteDialog(singleData)
            }
        }


    }

    override fun getItemCount(): Int {
      return myList.size
    }
}

