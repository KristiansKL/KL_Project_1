package com.example.kl_project_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eText1 = findViewById<EditText>(R.id.editText)
        val eText2 = findViewById<EditText>(R.id.editText2)
        val buttonClick1 = findViewById<Button>(R.id.button)
        val buttonClick2 = findViewById<Button>(R.id.button2)

        // Kods par popup message ņemts no https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage("Welcome to my first game TIC-TAC-TOE, hope you will enjoy it. \nYou can choose your player name and also choose game mode PvP or PvC")
        builder1.setCancelable(true)
        val alert11 = builder1.create()
        val a = intent.getStringExtra("ValueP")
        if(a != "1") {
            alert11.show()
        }

        //Šī ir PVP poga, kas aizsūta spēlētājus uz otro aktivitāti un arī aizsūta spēlētāju vārdus
        buttonClick1.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            var ss1 = eText1.getText().toString()
            var ss2 = eText2.getText().toString()
            if (ss1 == "") ss1 = "Player X"
            if (ss2 == "") ss2 = "Player O"

            intent.putExtra("Value1",ss1)
            intent.putExtra("Value2",ss2)
            startActivity(intent)

        }
        // Šī ir PVC poga, kas aizsūta spēlētāju uz otro aktivitāti un aizsūta informāciju, ka spēlētājs spēlē pret datoru
        buttonClick2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            val i = "1"
            intent.putExtra("ValueC",i)
            startActivity(intent)
        }


    }
}


