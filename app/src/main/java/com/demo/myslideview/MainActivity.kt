package com.demo.myslideview

import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        label_1?.setOnClickListener {
            Toast.makeText(this@MainActivity,"经济", Toast.LENGTH_SHORT).show()
        }
        label_2?.setOnClickListener {
            Toast.makeText(this@MainActivity,"政治", Toast.LENGTH_SHORT).show()
        }
        label_3?.setOnClickListener {
            Toast.makeText(this@MainActivity,"文化", Toast.LENGTH_SHORT).show()
        }


        myTv_1?.setOnClickListener {
            mySlideView?.closeOrOpenLeft()
        }

        myTv_2?.setParentScrollview(mySlideView)
        myTv_2?.setOnClickListener {
            mySlideView?.closeOrOpenLeft()
        }

        myLinearLayout?.setParentScrollview(mySlideView)

        myTv_3?.setOnClickListener {
            mySlideView?.closeOrOpenLeft()
        }

    }
}
