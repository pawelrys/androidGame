package com.example.znamto

import android.content.Context
import android.widget.Toast
import kotlin.properties.Delegates

//Observer
interface IObserver {
    fun printMessage(text : String, context: Context)
}

class PrintTextListener : IObserver {
    override fun printMessage(text: String, context : Context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}

class Observer (listener: IObserver, context: Context) {
    var text : String by Delegates.observable(
        initialValue = "",
        onChange = {
            property, oldValue, newValue ->
            listener.printMessage(newValue, context)
        }
    )
}