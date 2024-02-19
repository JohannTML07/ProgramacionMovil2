package com.example.evaluacion1

import android.app.Application
import com.example.evaluacion1.data.DefaultAppContainer


class SicenetApp : Application() {

    lateinit var container: DefaultAppContainer
    override fun onCreate(){
        super.onCreate()
        container = DefaultAppContainer()
    }
}