package com.example.a7minuteworkout

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.history_recycler.*
import kotlinx.android.synthetic.main.update_dialog.*

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(historyToolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "HISTORY"
        }
        historyToolBar?.setNavigationOnClickListener {
            onBackPressed()
        }

        getHistory()


    }

    fun getHistory() {
        val database = sqlLite(this)
        val historyList = database.viewHistory()
        if (historyList.size > 0) {
            llrecycle.visibility = View.VISIBLE
            nodata.visibility = View.GONE
            llrecycle.layoutManager = LinearLayoutManager(this)
            val adapter = HistoryRecycleAdapter(this, historyList)
            llrecycle.adapter = adapter
        } else {
            llrecycle.visibility = View.GONE
            nodata.visibility = View.VISIBLE

        }
    }

    fun updateDialog(myData: data) {


        val dialog = Dialog(this)
        dialog.setContentView(R.layout.update_dialog)
        dialog.setCancelable(false)
        if(dialog.t1!=null) {
            dialog.t1.text = myData.date
        }
        else{
            Toast.makeText(applicationContext, "please enter values", Toast.LENGTH_SHORT).show()
        }
        if(dialog.e1!=null) {
           dialog.e1.setText(myData.quote)
        }else{
            Toast.makeText(applicationContext, "please enter values", Toast.LENGTH_SHORT).show()
        }
        dialog.mydialogUpdate.setOnClickListener(View.OnClickListener  {
           myData.quote = dialog.e1.text.toString()
            val dbh = sqlLite(this)

            if (dialog.e1.text.isNotEmpty()) {

                    //dbh.update(myData.date, myData.quote)
                dbh.update(myData)


                getHistory()
                dialog.dismiss()
            } else {
                Toast.makeText(applicationContext, "please enter values", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })
        dialog.dialogDelete.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun deleteDialog(myData: data) {
        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
         dialog.setTitle("Delete Conformation")
        dialog.setMessage("Are you sure you want to delete ")
        dialog.setIcon(R.drawable.alert)
        val dbh=sqlLite(this)
        dialog.setPositiveButton("Yes"){dialogInterface,which->
             dbh.delete(myData)
            getHistory()
            dialogInterface.dismiss()
        }
        dialog.setNegativeButton("No"){dialogInterface,which->
            dialogInterface.dismiss()
        }

dialog.show()



    }
}