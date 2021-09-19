package com.hfad.helloword

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_note_list_activity.*

class MainActivity : AppCompatActivity() {
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//     instance of dataManager
//        var dm = DataManager()
//         arrayadapter for populating courses spinner, providing it a list of values
        val adapterCourses = ArrayAdapter<CourseInfo>(
            this,
            android.R.layout.simple_spinner_item,

//            intance not needed as we can access object directly
//            dm.courses.values.toList())
            DataManager.courses.values.toList()
        )

        // spinner layout to manange dropdown list
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//      assign the spinner adapter property to the courses adapter
        spinnerCourses.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
                intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            createNote()
        }

    }

    private fun createNote() {
        DataManager.notes.add(NoteInfo())
        notePosition = DataManager.notes.lastIndex
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        textNoteTitle.setText(note.title)
        textNoteText.setText(note.text)
//  select the appropriate course from the spinner
        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                if(notePosition < DataManager.notes.lastIndex){
                    moveNext()
                } else {
                    val message = "No more notes"
                    showMessage(message).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMessage(message: String) =  Snackbar.make(textNoteTitle, message, Snackbar.LENGTH_LONG)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        // this calls on prepare options menu
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null){
                menuItem.icon = getDrawable(R.drawable.ic_block_white_24dp)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onRestart() {
        super.onRestart()

    }

    //    save state as activity is about to move out of the fore ground
    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = textNoteTitle.text.toString()
        note.text = textNoteText.text.toString()
        // cast selected item to a type Courseinfo
        note.course = spinnerCourses.selectedItem as CourseInfo
    }
}
