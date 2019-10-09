package edu.icesi.arithgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.icesi.arithgo.model.QuestionGenerator;
import edu.icesi.arithgo.model.data.CRUDScore;
import edu.icesi.arithgo.model.entity.Score;

public class QuestionActivity extends AppCompatActivity {

    public final static int TIME_TOAST = 5000;

    private TextView questionTv;
    private EditText answerEt;
    private Button acceptBtn;
    private CheckBox sureCb;
    QuestionGenerator questionGenerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionTv = findViewById(R.id.question_tv);
        answerEt = findViewById(R.id.answer_et);
        acceptBtn = findViewById(R.id.accept_btn);
        sureCb = findViewById(R.id.are_sure_cb);

        questionGenerator = new QuestionGenerator();
        String question = questionGenerator.generateNewQuestion();
        questionTv.setText(question);


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast;
                if(sureCb.isChecked()){
                    String answer = answerEt.getText().toString();
                    if(answer.equals("")){
                       toast =  Toast.makeText(QuestionActivity.this, "Escriba una respuesta", Toast.LENGTH_LONG);
                       toast.show();
                    }else{
                       int value =  Integer.parseInt(answer);

                       int winOrLost = 0;

                       if(value==questionGenerator.getResult()){
                         toast =   Toast.makeText(getApplicationContext(), "Respuesta correcta GANASTE 1 punto", Toast.LENGTH_LONG);
                           winOrLost = 1;
                       }else{
                          toast =  Toast.makeText(getApplicationContext(), "Respuesta incorrecta PERDISTE 1 punto", Toast.LENGTH_LONG);
                           winOrLost = -1;
                       }
                        toast.show();
                       //FALTA LO DE LOS PUNTOS
                        Score score = CRUDScore.getScore();
                        if(score==null){
                            score = new Score("1", 0);
                            CRUDScore.insertScore(score);
                        }
                        score.setPoints(score.getPoints()+winOrLost);
                        if(score.getPoints()< 0){
                            score.setPoints(0);
                        }
                        CRUDScore.updatePoints(score);

                        Intent i = new Intent(QuestionActivity.this, MapsActivity.class);
                        startActivity(i);
                    }
                }else{
                    toast = Toast.makeText(QuestionActivity.this, "Marque si esta seguro", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


    }




}
