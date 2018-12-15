package com.raywenderlich.knockknockmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  companion object {
    private const val RING_EVENT_CHILD = "ring_event"
    private const val RING_RESPONSE_CHILD = "ring_response"
  }

  private lateinit var database: FirebaseDatabase
  private lateinit var databaseReference: DatabaseReference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    database = FirebaseDatabase.getInstance()
    databaseReference = database.reference

    yesButton.setOnClickListener {
      hideRingMessageDialog()
      showDefaultMessage()
      databaseReference
          .child(RING_RESPONSE_CHILD)
          .setValue(true)
      clearReponseChild()
    }

    noButton.setOnClickListener {
      hideRingMessageDialog()
      showDefaultMessage()
      databaseReference
          .child(RING_RESPONSE_CHILD)
          .setValue(false)
      clearReponseChild()
    }

    showDefaultMessage()

    databaseReference
        .child(RING_EVENT_CHILD)
        .addValueEventListener(object : ValueEventListener {
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
              hideDefaultMessage()
              showRingMessageDialog()
            }
          }

          override fun onCancelled(error: DatabaseError) {
            /* Do nothing */
          }
        })
  }

  private fun showDefaultMessage() {
    defaultMessage.visibility = View.VISIBLE
  }

  private fun hideDefaultMessage() {
    defaultMessage.visibility = View.GONE
  }

  private fun showRingMessageDialog() {
    ringMessageDialog.visibility = View.VISIBLE
  }

  private fun hideRingMessageDialog() {
    ringMessageDialog.visibility = View.GONE
  }

  private fun clearReponseChild() {
    databaseReference
        .child(RING_RESPONSE_CHILD)
        .removeValue()
  }
}
