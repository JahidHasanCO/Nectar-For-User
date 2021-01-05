package com.example.nectar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.HolderProduct> {

    private Context context;
    public ArrayList<ModelProduct> productList2;

    public AdapterProduct(Context context, ArrayList<ModelProduct> productList2) {
        this.context = context;
        this.productList2 = productList2;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_single_row,parent,false);

        return new HolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
//        get data
        ModelProduct modelProduct = productList2.get(position);
        String id = modelProduct.getProductId();
        String uid = modelProduct.getShopUid();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String originalPrice = modelProduct.getOriginalPrice();
        String productCategory = modelProduct.getProductCategory();
        String productDescription = modelProduct.getProductDescription();
        String productIcon = modelProduct.getProductImage();
        String productQuantity = modelProduct.getProductQuantity();
        String productTitle = modelProduct.getProductTitle();
        String timeStamp = modelProduct.getTimestamp();

        //set Data

        holder.productNameTv.setText(productTitle);
        holder.productDiscountPriceTv.setText("$"+discountPrice);
        holder.productOriginalPriceTv.setText("$"+originalPrice);
        holder.discountNoteTv.setText(discountNote);
        holder.productQuantityTv.setText(productQuantity+" , Price");

        if (discountAvailable.equals("true")){
            holder.discountNoteTv.setVisibility(View.VISIBLE);
            holder.productDiscountPriceTv.setVisibility(View.VISIBLE);
            holder.productOriginalPriceTv.setPaintFlags(holder.productOriginalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.discountNoteTv.setVisibility(View.GONE);
            holder.productDiscountPriceTv.setVisibility(View.GONE);
        }
        try {
            Picasso.get().load(productIcon).placeholder(R.drawable.ic_store).into(holder.productIconIv);
        }
        catch (Exception e){
            holder.productIconIv.setImageResource(R.drawable.ic_store);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = modelProduct.getProductId();
                Intent intent = new Intent(v.getContext(),ProductDetailsActivity.class);
                intent.putExtra("proID",productId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList2.size();
    }


    class HolderProduct extends RecyclerView.ViewHolder{

        private ImageView productIconIv;
        private TextView productNameTv,productQuantityTv,productDiscountPriceTv,discountNoteTv
                ,productOriginalPriceTv;
        private ConstraintLayout addToCartBtn;
        public HolderProduct(@NonNull View itemView) {
            super(itemView);

            productIconIv = itemView.findViewById(R.id.productIconIv);
            productNameTv = itemView.findViewById(R.id.productNameTv);
            productQuantityTv = itemView.findViewById(R.id.productQuantityTv);
            productDiscountPriceTv = itemView.findViewById(R.id.productDiscountPriceTv);
            productOriginalPriceTv = itemView.findViewById(R.id.productOriginalPriceTv);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn);
            discountNoteTv = itemView.findViewById(R.id.discountNoteTv);


        }
    }
}
