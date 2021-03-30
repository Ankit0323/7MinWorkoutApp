package com.example.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Arrays.asList
/*
*  db.update("History", cv, "_id= "+myData.id, null)
*
* here i use               "_id= "+myData.id                  it is correct
*
*
*
* the one which give errors are                     "_id=+$mydata.id"         "_id=+$(mydata.id)"
*  "_id= ?" ,arrayOf(queryList.getString(0))  ------> it give me array index out of bound for-1
*
*
*
*
* */
class sqlLite(context: Context):SQLiteOpenHelper(context,
        "historyDatabase",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

       db?.execSQL("create table History(_id Integer PRIMARY KEY autoincrement, date text,quote text)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
db?.execSQL("drop table if exists history")
        onCreate(db)
    }

    fun add(date :String){
        val cv=ContentValues()

        cv.put("date",date)
        cv.put("quote","you completed all exercise")
        val db=this.writableDatabase
        db.insert("History",null,cv)
        db.close()
    }
    fun addToDatabase(){
        val calendar=Calendar.getInstance()
       // val dateTime=calendar.time
        val dateTime=calendar.time
        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        //val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)

       // val sdf=SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH)
        val date=sdf.format(dateTime)
        add(date.toString())
    }

    fun update( myData:data){
        val db=this.writableDatabase
        var cv=ContentValues()
       // cv.put("id",myData.id)
        //cv.put("date",myData.date)
        cv.put("quote",myData.quote)

         if(!myData.date.isEmpty() || !myData.quote.isEmpty()) {
                 db.update("History", cv, "_id= "+myData.id, null)

         }
        db.close()

    }
    fun delete(myData: data){
        var db=this.writableDatabase
        var queryList=db.rawQuery("SELECT * FROM History",null)


    db.delete("History", "_id= ${myData.id}", null)

        db.close()

    }
    fun viewHistory():ArrayList<data>{
      var list=  ArrayList<data> ()

        val db=readableDatabase
        var queryList=db.rawQuery("SELECT * FROM History",null)
        while(queryList.moveToNext()){
            val id=queryList.getInt(0)
            val date=queryList.getString(1)
            val quote=queryList.getString(2)
            val myData=data(id,date,quote)
           list.add(myData)

        }
        queryList.close()
        return list
    }

}