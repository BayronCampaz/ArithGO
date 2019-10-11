package edu.icesi.arithgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.icesi.arithgo.control.adapter.OnRefreshViewListener;
import edu.icesi.arithgo.control.adapter.ProductAdapter;
import edu.icesi.arithgo.model.data.CRUDScore;
import edu.icesi.arithgo.model.entity.Product;
import edu.icesi.arithgo.model.entity.Score;

public class ExchangeActivity extends AppCompatActivity implements OnRefreshViewListener {

    private TextView pointsTv;
    private ListView productList;
    private ProductAdapter adapter;
    private Score score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        pointsTv = findViewById(R.id.points_tv);
        productList = findViewById(R.id.products_list);
        adapter = new ProductAdapter(this);
        productList.setAdapter(adapter);

        score = CRUDScore.getScore();
        if(score==null){
            score = new Score("1", 0);
            CRUDScore.insertScore(score);
        }
        pointsTv.setText("" + score.getPoints());



    }

    public void updatePoints(String data){
        pointsTv.setText(""+data);
    }

    @Override
    public void refreshView() {
        score = CRUDScore.getScore();
        if(score==null){
            score = new Score("1", 0);
            CRUDScore.insertScore(score);
        }
        pointsTv.setText("" + score.getPoints());
    }
}
