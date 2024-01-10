package com.example.taskset

data class Note(val id:Int,val title:String,val content:String, var dueDateMillis: Long,val priority: String,
                val status: String,val category:String )

