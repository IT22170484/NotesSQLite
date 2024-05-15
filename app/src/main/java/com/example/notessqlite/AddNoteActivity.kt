package com.example.notessqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notessqlite.databinding.ActivityAddNoteBinding
import com.example.notessqlite.databinding.ActivityMainBinding

// AddNoteActivity
class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val content = binding.contentEditText.text.toString().trim()

            if (title.isEmpty() && content.isNotEmpty()) {
                Toast.makeText(this, "Please fill in the title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else if (content.isEmpty() && title.isNotEmpty()) {
                Toast.makeText(this, "Please fill in the content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            else if (title.isEmpty() && content.isEmpty()) {
                Toast.makeText(this, "Please fill in the title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            else if(title.isNotEmpty() && content.isNotEmpty()) {
                // Check if the title already exists in the database
                if (db.isTitleExists(title)) {
                    Toast.makeText(this, "A note with this title already exists", Toast.LENGTH_SHORT).show()
                } else {
                    // If the title doesn't exist, insert the note into the database
                    val note = Note(0, title, content)
                    db.insertNote(note)
                    finish()
                    Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}
