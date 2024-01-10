package com.example.taskset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.taskset.databinding.ActivityUpdateBinding
import java.util.Calendar

class UpdateNoteActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NotesDBHelper
    private var noteId:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NotesDBHelper(this)

        noteId=intent.getIntExtra("note_id",-1)
        if(noteId==-1)
        {
            finish()
            return
        }

        val note=db.getNoteById(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle=binding.updateTitleEditText.text.toString()
            val newContent=binding.updateContentEditText.text.toString()
            val newCategory=binding.updateCategoryEditText.text.toString()

            val datePicker = binding.updateDatePicker
            val year = datePicker.year
            val month = datePicker.month
            val dayOfMonth = datePicker.dayOfMonth

            // Set the due date in the calendar
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }

            // Save dueDateMillis to the database along with other task details
            val dueDateMillis = calendar.timeInMillis
            val prioritySpinner = binding.updatePrioritySpinner
            val selectedPriority = prioritySpinner.selectedItem.toString()

            val statusRadioGroup = binding.updateStatusRadioGroup
            val checkedRadioButtonId = statusRadioGroup.checkedRadioButtonId

            var selectedStatus = ""
            if (checkedRadioButtonId != -1) {
                // Find the checked RadioButton
                val checkedRadioButton = findViewById<RadioButton>(checkedRadioButtonId)

                // Get the text or value of the checked RadioButton
                selectedStatus = checkedRadioButton.text.toString()
            }
            val updatedNote = Note(
                noteId,
                newTitle,
                newContent,
                dueDateMillis,
                selectedPriority,
                selectedStatus,
                newCategory
            )

            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()
        }

    }
}