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

import java.util.ArrayList;

import edu.icesi.arithgo.model.data.CRUDScore;
import edu.icesi.arithgo.model.entity.Product;
import edu.icesi.arithgo.model.entity.Score;

public class ExchangeActivity extends AppCompatActivity {

    private TextView pointsTv;
    private ListView productList;
    private ArrayList<Product> products;
    private ArrayAdapter<Product> adapter;
    private Score score;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        pointsTv = findViewById(R.id.points_tv);
        productList = findViewById(R.id.products_list);
        products = new ArrayList<Product>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        productList.setAdapter(adapter);

        initializeProducts();

        score = CRUDScore.getScore();
        if(score==null){
            score = new Score("1", 0);
            CRUDScore.insertScore(score);
        }
        pointsTv.setText("" + score.getPoints());

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExchangeActivity.this)
                        .setTitle("Canjear")
                        .setMessage("¿Desea canjear este articulo?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int cost = products.get(i).getValue();
                                if(cost < score.getPoints()){
                                    score = new Score("1", score.getPoints() - cost);
                                    CRUDScore.updatePoints(score);
                                    dialogInterface.dismiss();
                                }
                            }
                        });
                builder.show();
            }
        });


    }

    public void initializeProducts(){

        Product lapiceroIcesi = new Product("Lapicero Icesi", 20);
        Product cuaderno = new Product("Cuaderno", 30);
        Product libretaIcesi = new Product("Libreta Icesi", 40);
        Product camisetaIcesi = new Product("Camiseta Icesi", 80);
        Product sacoIcesi = new Product("Saco Icesi", 100);
        products.add(lapiceroIcesi);
        products.add(cuaderno);
        products.add(libretaIcesi);
        products.add(camisetaIcesi);
        products.add(sacoIcesi);


    }

}
