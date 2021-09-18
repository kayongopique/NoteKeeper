package com.hfad.helloword

import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.*

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest{

    @Rule
    @JvmField
    val noteListActivity = ActivityTestRule(NoteListActivity::class.java)

    @Test
    fun createNewNote() {
        val course = DataManager.courses["android_async"]
        val noteTile = "This is a test note"
        val noteText = "This is the body of our test note"

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.spinnerCourses)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java), equalTo(course))).perform(click())

        onView(withId(R.id.textNoteTitle)).perform(typeText(noteTile))
        onView(withId(R.id.textNoteText)).perform(typeText(noteText), closeSoftKeyboard())

        pressBack()

        val note = DataManager.notes.last()
        assertEquals(course, note.course)
        assertEquals(noteTile, note.title)
        assertEquals(noteText, note.text)
    }

    }
