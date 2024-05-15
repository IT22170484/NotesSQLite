package com.example.notessqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notessqlite.databinding.ActivityAddNoteBinding
import com.example.notessqlite.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString().trim() // Trim to remove leading/trailing whitespace
            val newContent = binding.updateContentEditText.text.toString().trim() // Trim to remove leading/trailing whitespace

            if (newTitle.isEmpty() && newContent.isNotEmpty() ) {
                Toast.makeText(this, "Please fill in the title", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            } else if (newContent.isEmpty() && newTitle.isNotEmpty()  ) {
                Toast.makeText(this, "Please fill in the content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else if (newContent.isEmpty() && newTitle.isEmpty()  ) {
                Toast.makeText(this, "Please fill in the title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener


            } else if(newContent.isNotEmpty() && newTitle.isNotEmpty()  ) {
                val updatedNote = Note(noteId, newTitle, newContent)
                db.updateNote(updatedNote)
                finish()
                Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}







