package com.example.taskset

import android.R as AndroidR
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.taskset.databinding.ActivityAddNoteBinding
import java.util.Calendar


class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db:NotesDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NotesDBHelper(this)

        val priorities = arrayOf("Low", "Medium", "High")
        val adapter = ArrayAdapter(this, AndroidR.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(AndroidR.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter

        binding.saveButton.setOnClickListener{
            val title=binding.titleEditText.text.toString()
            val content=binding.contentEditText.text.toString()
            val taskTimeMillis = System.currentTimeMillis()

            // Schedule notification for the created task (assuming you have the notification code)
            NotificationHelper.scheduleNotification(this, taskTimeMillis)

            // Get the selected due date from DatePicker
            val datePicker = binding.datePicker
            val year = datePicker.year
            val month = datePicker.month
            val dayOfMonth = datePicker.dayOfMonth

            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }

            val dueDateMillis = calendar.timeInMillis

            // Get the selected priority from Spinner
            val prioritySpinner = binding.prioritySpinner
            val selectedPriority = prioritySpinner.selectedItem.toString()

            val status = when (selectedPriority) {
                "Low" -> "Incomplete"
                "Medium" -> "In Progress"
                "High" -> "Completed"
                else -> "Incomplete" // Default to "Incomplete" if priority is not recognized
            }

            val categoryEditText = binding.categoryEditText
            val category = categoryEditText.text.toString()


            val note=Note(0,title,content, dueDateMillis, selectedPriority,status,category)

            db.insertNote(note)
            finish()
            Toast.makeText(this,"Task Saved",Toast.LENGTH_SHORT).show()
        }



    }

}