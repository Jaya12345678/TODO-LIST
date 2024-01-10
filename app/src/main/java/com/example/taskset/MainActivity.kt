package com.example.taskset

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskset.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db:NotesDBHelper
    private lateinit var notesAdapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NotesDBHelper(this)
        notesAdapter= NotesAdapter(db.getAllNotes(),this)
        binding.notesRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.notesRecyclerView.adapter=notesAdapter

        binding.addButton.setOnClickListener{
            val intent=Intent(this,AddNoteActivity::class.java)
            NotificationHelper.scheduleNotification(this, System.currentTimeMillis())

            notesAdapter.refreshData(db.getAllNotes())
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }

}