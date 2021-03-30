package com.example.a7minuteworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycle_item.view.*

class recycleAdapter(val list:ArrayList<exercise>,val context:Context):RecyclerView.Adapter<myViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item,parent,false))
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
       val item=list[position]
      holder.recycleitem.text=item.id.toString()
        if(item.isSelected) {
            holder.recycleitem.background =
                ContextCompat.getDrawable(context, R.drawable.recycle_between_shape)
            holder.recycleitem.setTextColor(ContextCompat.getColor(context,R.color.red))
        }

        if(item.isCompleted) {
            holder.recycleitem.background =
                ContextCompat.getDrawable(context, R.drawable.recycle_after_shape)
            holder.recycleitem.setTextColor(ContextCompat.getColor(context,R.color.black))
        }



    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class myViewHolder(view: View):RecyclerView.ViewHolder(view){
     val recycleitem=view.myItem
}
