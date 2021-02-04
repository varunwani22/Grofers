package com.example.grofers_app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ProdectHomeViewHolder extends RecyclerView.ViewHolder {
    private ImageView mproductImage;
    private TextView mProductName;
    private TextView mSellingPrice;
    private TextView mProductPrice;
    private TextView mProductUnit;
    private CardView cardView;
    private OnListnerClick onListnerClick;
    public ProdectHomeViewHolder(@NonNull View itemView,OnListnerClick onListnerClick) {
        super(itemView);
        this.onListnerClick=onListnerClick;
        initView();
    }

    private void getDataOfProduct() {

    }

    private void initView() {
        mproductImage=itemView.findViewById(R.id.ivProdectImage);
        mProductName=itemView.findViewById(R.id.tvProductTitle);
        mSellingPrice=itemView.findViewById(R.id.tvSellingPrice);
        mProductPrice=itemView.findViewById(R.id.tvProductPrice);
        mProductUnit=itemView.findViewById(R.id.tvProdectUnit);
        cardView=itemView.findViewById(R.id.cardViewShowDetails);
    }
    public void setDataForHome(ResponseProdect responseProdect){
        Glide.with(mproductImage).load(responseProdect.getImage()).into(mproductImage);
        mProductName.setText(responseProdect.getTitle());
        mSellingPrice.setText("\u20B9"+responseProdect.getSellingPrice());
        mProductPrice.setText("\u20B9"+responseProdect.getProductMRP());
        mProductUnit.setText(responseProdect.getUnit());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListnerClick.sendDataToDetails(responseProdect,getAdapterPosition());
            }
        });
    }
}
