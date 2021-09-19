package com.hfad.helloword

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
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
//        set layout manager for the recyler view
        listItems.layoutManager = LinearLayoutManager(this)

        // connect recycler view with notelist activity
        listItems.adapter = NoteRecyclerAdapter(this, DataManager.notes)
    }

    override fun onResume() {
        super.onResume()
        listItems.adapter?.notifyDataSetChanged()
    }

}
