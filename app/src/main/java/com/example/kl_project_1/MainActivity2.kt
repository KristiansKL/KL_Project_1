package com.example.kl_project_1




import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.max
import java.lang.Math.min


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var text2 = findViewById<View>(R.id.textView5) as TextView
        var text3 = findViewById<View>(R.id.textView6) as TextView


        //Iegūst informāciju no iepriekšējās aktivitātes
        val t1 = intent.getStringExtra("Value1")
        text2.text = t1

        val t2 = intent.getStringExtra("Value2")
        text3.text = t2

        val cActive = intent.getStringExtra("ValueC")


        val btn1 = findViewById(R.id.button5) as Button
        val btn2 = findViewById(R.id.button6) as Button
        val btn3 = findViewById(R.id.button7) as Button
        val btn4 = findViewById(R.id.button8) as Button
        val btn5 = findViewById(R.id.button9) as Button
        val btn6 = findViewById(R.id.button10) as Button
        val btn7 = findViewById(R.id.button11) as Button
        val btn8 = findViewById(R.id.button12) as Button
        val btn9 = findViewById(R.id.button13) as Button

        val btn10 = findViewById(R.id.button14) as Button
        val btn11 = findViewById(R.id.button15) as Button

        val text1 = findViewById<View>(R.id.textView4) as TextView

        var y = "X"
        var lock = 1


        // Spēlētājs vai dators uzspiežot uz pogas izdara gājienu pārveidojot pogas tekstu un krāsu
        //https://stackoverflow.com/questions/32671004/how-to-change-the-color-of-a-button
        fun move(a: Button){
            if (a.text == "" ) {
                if (y == "O") {
                    a.text = "O"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        a.backgroundTintList = getColorStateList(android.R.color.holo_blue_dark)
                    }
                    y = "X"
                } else if (y == "X") {
                    a.text = "X"
                    y = "O"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        a.backgroundTintList = getColorStateList(android.R.color.holo_red_dark)
                    }
                }
                if (y == "X") text1.text = "X TURN" else text1.text = "O TURN"
            }
        }

        fun check(a: Button): String {
            var x = ""
            if(a.text == "") x = ""
            if(a.text == "X") x = "X"
            if(a.text == "O") x = "O"
            return x
        }
        //Matricas izveide, lai varētu darboties ar minimax algoritmu
        val matrix = Array(3) {IntArray(3)}

        // Pārbauda katru pogu un ievada matricā pareizo vērtību
        fun array(){

            if (check(btn1) == "") matrix[0][0] = 0
            if (check(btn1) == "X") matrix[0][0] = 1
            if (check(btn1) == "O") matrix[0][0] = -1

            if (check(btn2) == "") matrix[1][0] = 0
            if (check(btn2) == "X") matrix[1][0] = 1
            if (check(btn2) == "O") matrix[1][0] = -1

            if (check(btn3) == "") matrix[2][0] = 0
            if (check(btn3) == "X") matrix[2][0] = 1
            if (check(btn3) == "O") matrix[2][0] = -1

            if (check(btn4) == "") matrix[0][1] = 0
            if (check(btn4) == "X") matrix[0][1] = 1
            if (check(btn4) == "O") matrix[0][1] = -1

            if (check(btn5) == "") matrix[1][1] = 0
            if (check(btn5) == "X") matrix[1][1] = 1
            if (check(btn5) == "O") matrix[1][1] = -1

            if (check(btn6) == "") matrix[2][1] = 0
            if (check(btn6) == "X") matrix[2][1] = 1
            if (check(btn6) == "O") matrix[2][1] = -1

            if (check(btn7) == "") matrix[0][2] = 0
            if (check(btn7) == "X") matrix[0][2] = 1
            if (check(btn7) == "O") matrix[0][2] = -1

            if (check(btn8) == "") matrix[1][2] = 0
            if (check(btn8) == "X") matrix[1][2] = 1
            if (check(btn8) == "O") matrix[1][2] = -1

            if (check(btn9) == "") matrix[2][2] = 0
            if (check(btn9) == "X") matrix[2][2] = 1
            if (check(btn9) == "O") matrix[2][2] = -1
        }
        // Kāda pozīcija ir katrai pogai matricā. Ievadot rindas un kollonas vērtību izvada piemēroto pogu
        fun whatbutton(i: Int, j: Int): Button {
            var a = btn1

            if(i == 0 && j == 0) a = btn1
            if(i == 1 && j == 0) a = btn2
            if(i == 2 && j == 0) a = btn3
            if(i == 0 && j == 1) a = btn4
            if(i == 1 && j == 1) a = btn5
            if(i == 2 && j == 1) a = btn6
            if(i == 0 && j == 2) a = btn7
            if(i == 1 && j == 2) a = btn8
            if(i == 2 && j == 2) a = btn9
            return a
        }


        // Winner funkcija pasaka kad ir uzvarētājs un zaudētājs izvadot piemērotāko skaitli(10 ja uzvar X -10 ja uzvar O)
        // Kods ņemts no https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
        // Sākuma es biju izveidojis citu kodu priekš uzvarētāja noteikšanas, bet tas bija pāŗak garš un šitas bija daudz vienkāršāks un efektīvāks
        fun winner(board: Array<IntArray>):Int{

            for(i in 0..2){
                if(board[i][0]==board[i][1]&& board[i][1] == board[i][2]){
                    if(board[i][0] == 1)return 10
                    else if (board[i][0] == -1)return -10
                }
            }
            for(j in 0..2){
                if(board[0][j]==board[1][j]&& board[1][j] == board[2][j]){
                    if(board[0][j] == 1)return 10
                    else if (board[0][j] == -1)return -10
                }
            }
                if(board[0][0]==board[1][1]&& board[1][1] == board[2][2]){
                    if(board[0][0] == 1)return 10
                    else if (board[0][0] == -1)return -10
            }
            if(board[0][2]==board[1][1]&& board[1][1] == board[2][0]) {
                if (board[0][2] == 1) return 10
                else if (board[0][2] == -1) return -10
            }
            return 0
        }
        // Nosaka vai ir neišķirts
        fun draw(board: Array<IntArray>): Boolean{
            for(i in 0..2){
                for (j in 0..2){
                    if(board[i][j] == 0) return true
                }
            }
            return false
        }

        // minimax funkcija, kas iedot vērtību katram gajienam, lai ļautu datoram izvēlēties labāko gājienu
        fun minimax(board: Array<IntArray>,depth: Int, maxPlayer: Boolean): Int {
            //Izmantotie avoti
            //https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
            //https://www.youtube.com/watch?v=trKjYdBASyQ&ab_channel=TheCodingTrain

            var score = winner(board)
            if(score == 10) return score
            if(score== -10) return score
            if(draw(board)== false) return 0

            if (maxPlayer) {
                var best = -1000
                for (i in 0..2) {
                    for (j in 0..2) {
                        if (board[i][j] == 0) {
                            board[i][j] = 1
                            var score = minimax( board,depth + 1, false)
                            board[i][j] = 0
                            best = max(score, best)
                        }
                    }
                }
                return best
            } else {
                var best = 1000
                for (i in 0..2) {
                    for (j in 0..2) {
                        if (board[i][j] == 0) {
                            board[i][j] = -1
                            var score = minimax( board,depth + 1, true)
                            board[i][j] = 0
                            best = min(score, best)
                        }
                    }
                }
                return best
            }
        }

        // Izvada uzvarētājus
        fun winnerscreen(){

            // Koda daļa ņemta no https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
            val buildertie = AlertDialog.Builder(this)
            buildertie.setMessage("Draw, No one Wins and No one Loses")
            buildertie.setCancelable(true)
            var builderwx = AlertDialog.Builder(this)
            builderwx.setMessage("Winner Is X, Good Luck Next Time O")
            builderwx.setCancelable(true)
            var builderwo = AlertDialog.Builder(this)
            builderwo.setMessage("Winner Is O, Good Luck Next Time X")
            builderwo.setCancelable(true)

            val alerttie = buildertie.create()
            val alertwx = builderwx.create()
            val alertwo = builderwo.create()


            if(draw(matrix) == false) {
                text1.text = "DRAW"
                lock = 0
                alerttie.show()
            }
            if(winner(matrix)==10) {
                text1.text = "WINNER X"
                lock = 0
                alertwx.show()
            }
            if(winner(matrix)==-10) {
                text1.text = "WINNER O"
                lock = 0
                alertwo.show()
            }
        }
        // nosaka labāko gājienu
        // Koda daļa ņemta no https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
        fun bestMove(board: Array<IntArray>): Array<Int> {
            var bestVal = -1000
            var bestMove = arrayOf(-1,-1)

            for(i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1
                        var mVal = minimax(matrix, 0, false)
                        board[i][j] = 0
                        if (mVal > bestVal) {
                            bestMove = arrayOf(i, j)
                            bestVal = mVal
                        }
                    }
                }
            }
            winnerscreen()
            return bestMove
        }


        //Ja ir izvēlēts PVC poga, tad dators veic gājienus
        fun ai(){
        if (cActive == "1"){
            text2.text = "Computer X"
            text3.text = "Player O"
            move(whatbutton(bestMove(matrix)[0],bestMove(matrix)[1]))
            array()
            winnerscreen()
          }
        }
        // Izdara pirmo gājienu nejauši
        if(cActive == "1"){
            val rndi = (0..2).random()
            val rndj = (0..2).random()
            move(whatbutton(rndi,rndj))
        }

        // Kad katru pogu uzspiež tā izdara vairākas funkcijas un ja viens no spēlētājiem uzvar, tad uz pogas vairs nevar uzspiest
          btn1.setOnClickListener {
            if(lock != 0){
                move(btn1)
                array()
                ai()
                winnerscreen()}
            }

         btn2.setOnClickListener {
            if(lock != 0){
                move(btn2)
                array()
                ai()
                winnerscreen()}
            }

          btn3.setOnClickListener {
            if(lock != 0){
                move(btn3)
                array()
                ai()
                winnerscreen()}
            }

         btn4.setOnClickListener {
            if(lock != 0){
                move(btn4)
                array()
                ai()
                winnerscreen()}
            }

          btn5.setOnClickListener {
              if(lock != 0){
                  move(btn5)
                  array()
                  ai()
                  winnerscreen()}
            }

         btn6.setOnClickListener {
            if(lock != 0){
                move(btn6)
                array()
                ai()
                winnerscreen()}
            }

           btn7.setOnClickListener {
            if(lock != 0) {
                move(btn7)
                array()
                ai()
                winnerscreen()}
            }

           btn8.setOnClickListener {
            if(lock != 0) {
                move(btn8)
                array()
                ai()
                winnerscreen()}
            }

           btn9.setOnClickListener {
            if(lock != 0 ){
                move(btn9)
                array()
                ai()
                winnerscreen()}
            }
        // Poga btn10 ir "menu" poga kas atgriež spēlētāju uz pirmo aktivitāti
        btn10.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ValueP","1")
            startActivity(intent)

        }
        // Poga btn11 ir "again", kas reseto visu šo aktivitāti, bet atstājot spēlētāju vārdus vai aktivizējot datora gājienus
        btn11.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            var i = "0"
            if(cActive == "1") i = "1"
            intent.putExtra("ValueC",i)
            intent.putExtra("Value1",t1)
            intent.putExtra("Value2",t2)
            startActivity(intent)

        }
    }
}


