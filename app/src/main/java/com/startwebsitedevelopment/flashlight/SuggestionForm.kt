package com.startwebsitedevelopment.flashlight

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_suggestion_form.*
import java.lang.Exception

class SuggestionForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion_form)

        setupActionBar()

        submit_form.setOnClickListener {
            val person_name = person_name.text
            val person_email = person_email.text
            val suggestion_text = suggestion_message.text

            val recepient = "startwebsitedevelopment@gmail.com"
            val subject = "Suggestion by $person_name"
            val message = "Person Name : $person_name having email address $person_email has suggested the following text: $suggestion_text"

            sendEmail(recepient, subject, message)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupActionBar(){
        supportActionBar?.title = "Suggestion Form"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun sendEmail(recepient: String, subject: String, message: String){
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.type = "message/rfc822"
//        mIntent.setDataAndType(Uri.parse("mailto:"), "message/rfc822")

        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recepient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email client..."))
        }catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}