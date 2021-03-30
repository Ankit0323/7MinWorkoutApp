package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_exercise.view.*
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.recycle_item.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


//passTime=pauseOffSet
class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityExerciseBinding
    private  var countDownTimer:CountDownTimer? =null
    private var passTime =0
    private var exerciseList=ArrayList<exercise>()
    private var exercisePosition=-1
    private var tts:TextToSpeech?=null
    private  var player :MediaPlayer?=null
    private var songList =ArrayList<songs>()
    private var adapter:recycleAdapter?=null
    var stopProgress=true
    /*
  * In textToSpeech there is a problem
  * for first case [run in place]  speaker doesn't speak because it didn't initialize completely
  *
  *
  * oninit doesn't initialize properly
  *
  *
  * so we have to add speech method in oninit [speech( "Get Ready For   ${exerciseList[exercisePosition].name}")]
  *
  *
  * so that it will execute from oninit because it will take time to execute in another method
 
  * */

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        val  actionBar= supportActionBar
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolBar.setNavigationOnClickListener {
          cutomdialog()
        }
        songList= songsConstant.songsList()
      tts= TextToSpeech(this,this)
        exerciseList=constants.exerciseList()
        //songList= songsConstant.songsList()

        setupTimer()
        recycleView()


    }





    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun startTimer( ){
        speech( "Get Ready For   ${exerciseList[exercisePosition].name}")
     progressBar.progress=passTime
        // i changed millisinfuture from 10000 to 1000
        // so to change time from 1 sec to 10 sec
        // you have to change millis i future to 10000
        countDownTimer=object : CountDownTimer(10000 ,1000){
            override fun onTick(millisUntilFinished: Long) {
                passTime++

                progressBar.progress=10-passTime

                binding.count.setText("${millisUntilFinished /1000}")

            }



                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun onFinish() {
                       //exercisePosition++
                        exerciseList[exercisePosition].isSelected=true
                        adapter!!.notifyDataSetChanged()
                       firstLinearLayout.visibility= View.INVISIBLE
                       secondLinearLayout.visibility=View.VISIBLE
                        esetupTimer()
            }

        }.start()
    }

    fun estartTimer( ){

        eprogressBar.progress=passTime

        countDownTimer=object : CountDownTimer(30000 ,1000){
            override fun onTick(millisUntilFinished: Long) {
                passTime++

                binding.eprogressBar.progress=30- passTime
                binding.ecount.setText("${millisUntilFinished /1000}")

            }



            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onFinish() {
                if(player!=null){
                    player!!.stop()
                }
              firstLinearLayout.visibility = View.VISIBLE
             secondLinearLayout.visibility = View.GONE

                if(exercisePosition<11) {

                    exerciseList[exercisePosition].isCompleted=true
                    adapter!!.notifyDataSetChanged()
                    setupTimer()
                }else{
                    val database=sqlLite(applicationContext)
                    database.addToDatabase()
                    idrecyclerView.visibility=View.GONE
                    binding.firstLinearLayout.visibility=View.GONE
                    binding.thirdLinearLayout.visibility=View.VISIBLE
                    Toast.makeText(applicationContext," You completed today's exercise",Toast.LENGTH_SHORT).show()
                    finishButton.setOnClickListener {
                        finish()
                        val intent = Intent(applicationContext,MainActivity::class.java)

                        startActivity(intent)
                    }

                }
            }

        }.start()


    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setupTimer(){



        if(countDownTimer!=null)
        {
            countDownTimer!!.cancel()
        }
        passTime=0

        countDownTimer=null
        binding.count.setText("10")
        exercisePosition++

        binding.nextExercise.setText(exerciseList[exercisePosition].name)
        startTimer()
// i can use it here too but i am using it on startTimer because of custom dialog on toolbar(back arrow top left corner)
       // speech( "Get Ready For   ${exerciseList[exercisePosition].name}")


    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun esetupTimer(){
        try{
            player=MediaPlayer.create(applicationContext,songList[exercisePosition].song)
            player!!.isLooping=false
            player!!.start()

        }catch (e:Exception){
            e.printStackTrace()
        }
        if(countDownTimer!=null)
        {
            countDownTimer!!.cancel()
        }
        passTime=0
        countDownTimer=null
        binding.ecount.setText("30")

       // speech(exerciseList[exercisePosition].name)
        estartTimer()

        val eimage=exerciseList[exercisePosition]
        binding.image.setImageResource(eimage.image)
        binding.name.setText(eimage.name)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.US)
            /*I am assuming that
             why i do not increment "exercisePosition" because setup timer get initialize with oninit
             now when oninit reaches speech line
             this "exercisePosition" is already incresed by 1 in setup timer
            *
             */
            speech("Get Ready For   ${exerciseList[exercisePosition].name}")

            if(result==TextToSpeech.LANG_MISSING_DATA ||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(applicationContext,"language not supported",Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext,"Initialization Failed! ",Toast.LENGTH_SHORT).show()
        }

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun speech(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    fun pauseTimer(){
        //not progressbar but passTime
        //progressBar.progress=0
        passTime=0
      if(countDownTimer!=null){
      countDownTimer!!.cancel()
          if(tts!=null){
              tts!!.stop()

          }
          if(player!=null){
              player!!.stop()
          }
  }


  }
    override fun onDestroy() {

        if(countDownTimer!=null){
            countDownTimer!!.cancel()
        }
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null){
            player!!.stop()
        }
        super.onDestroy()
    }
    fun recycleView(){

        adapter= recycleAdapter(exerciseList,this)
idrecyclerView.layoutManager=LinearLayoutManager(this,
    LinearLayoutManager.HORIZONTAL,false)
        idrecyclerView.adapter=adapter
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun cutomdialog(){
        val dialog= Dialog(this)
        dialog.setContentView(R.layout.custom_dialog)
         pauseTimer()
        dialog.buttonYes.setOnClickListener {
             finish()
            dialog.dismiss()
        }
        dialog.buttonNo.setOnClickListener {
            firstLinearLayout.visibility=View.VISIBLE
            secondLinearLayout.visibility=View.INVISIBLE
            startTimer()
            dialog.dismiss()
        }
        dialog.show()
    }
}