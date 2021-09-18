package com.hfad.helloword

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_note_list_activity.*
import kotlinx.android.synthetic.main.content_note_list_activity.*

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list_activity)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            // create intent receives context and activity intended
            val activityIntent = Intent(this, MainActivity::class.java)
            // start that activity
            startActivity(activityIntent)
        }
        listNotes.adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, DataManager.notes)
        // implements the adapterView.onitemclicklistner
        listNotes.setOnItemClickListener{parent, view, position, id ->
            val activityIntent = Intent(this, MainActivity::class.java)
            activityIntent.putExtra(NOTE_POSITION, position)
            startActivity(activityIntent)
        }
    }

}
