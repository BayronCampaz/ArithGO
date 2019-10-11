package edu.icesi.arithgo.control.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.util.ArrayList;

import edu.icesi.arithgo.ExchangeActivity;
import edu.icesi.arithgo.R;
import edu.icesi.arithgo.model.data.CRUDScore;
import edu.icesi.arithgo.model.entity.Product;
import edu.icesi.arithgo.model.entity.Score;

public class ProductAdapter extends BaseAdapter {

    ArrayList<Product> products;
    private Score score;
    private OnRefreshViewListener refreshViewListener;

    public  ProductAdapter(Context context){
        refreshViewListener = (OnRefreshViewListener)context;
        products = new ArrayList<>();
        initializeProducts();
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, final ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View rowView = inflater.inflate(R.layout.product_row, null);
        TextView nameTv = rowView.findViewById(R.id.name_product_tv);
        final TextView pointsProductTv = rowView.findViewById(R.id.points_products_tv);
        ImageButton exchangeBtn = rowView.findViewById(R.id.exchange_products);

        nameTv.setText(products.get(pos).getName());
        pointsProductTv.setText(products.get(pos).getValue() + " puntos");

        score = CRUDScore.getScore();
        if(score==null){
            score = new Score("1", 0);
            CRUDScore.insertScore(score);
        }


        exchangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext())
                        .setTitle("Canjear")
                        .setMessage("¿Desea canjear este articulo?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int cost = products.get(pos).getValue();
                                if(cost <= score.getPoints()){
                                    score = new Score("1", score.getPoints() - cost);
                                    CRUDScore.updatePoints(score);
                                    notifyDataSetChanged();
                                    refreshViewListener.refreshView();


                                }else{
                                    Toast toast = Toast.makeText(viewGroup.getContext(), "No tienes suficientes puntos", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                dialogInterface.dismiss();

                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }

                        });
                builder.show();
            }
        });

        return rowView;

    }

    public void initializeProducts(){

        Product lapiceroIcesi = new Product("Lapicero Icesi",20);
        Product cuaderno =      new Product("Cuaderno      ",30);
        Product libretaIcesi =  new Product("Libreta Icesi ",40);
        Product camisetaIcesi = new Product("Camiseta Icesi",80);
        Product sacoIcesi =     new Product("Saco Icesi   ",100);
        products.add(lapiceroIcesi);
        products.add(cuaderno);
        products.add(libretaIcesi);
        products.add(camisetaIcesi);
        products.add(sacoIcesi);

    }
}
