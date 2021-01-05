package com.example.nectar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private Boolean desCh = true;
    String proId ;
    private FirebaseAuth firebaseAuth;
    private ImageButton backBtn,FavouriteBtn,minusBtn,plusBtn,productDesHideBtn;
    private TextView discountNoteTv,productNameTv,productQuantityTv ,shopNameTv,addressTv
            ,productDiscountPriceTv,productOriginalPriceTv,productDescription;
    private ImageView productImageIv;
    private CircularImageView shopPhoto;
    private EditText productSize;
    private Button addToCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        StatusBarUtil.setTransparent(this);

        backBtn = findViewById(R.id.backBtn);
        discountNoteTv = findViewById(R.id.discountNoteTv);
        productImageIv = findViewById(R.id.productImageIv);
        productNameTv = findViewById(R.id.productNameTv);
        FavouriteBtn = findViewById(R.id.FavouriteBtn);
        productQuantityTv = findViewById(R.id.productQuantityTv);
        shopPhoto = findViewById(R.id.shopPhoto);
        addressTv = findViewById(R.id.addressTv);
        shopNameTv = findViewById(R.id.shopNameTv);
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        productDiscountPriceTv = findViewById(R.id.productDiscountPriceTv);
        productOriginalPriceTv = findViewById(R.id.productOriginalPriceTv);
        productDesHideBtn = findViewById(R.id.productDesHideBtn);
        productDescription = findViewById(R.id.productDescription);
        addToCartBtn = findViewById(R.id.addToCartBtn);

        proId = getIntent().getStringExtra("proID");
        firebaseAuth = FirebaseAuth.getInstance();
        loadProductDetails();

        productDesHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (desCh){
                    desCh = false;
                    productDescription.setVisibility(View.GONE);
                    productDesHideBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else {
                    desCh = true;
                    productDescription.setVisibility(View.VISIBLE);
                    productDesHideBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void loadProductDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Products");
        ref.orderByChild("productId").equalTo(proId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String shopUid = ""+ds.child("shopUid").getValue();


                            String productTitle = ""+ds.child("productTitle").getValue();
                            String productImage = ""+ds.child("productImage").getValue();
                            String productDescription1 = ""+ds.child("productDescription").getValue();
                            String productQuantity = ""+ds.child("productQuantity").getValue();
                            String originalPrice = ""+ds.child("originalPrice").getValue();
                            String discountNote = ""+ds.child("discountNote").getValue();
                            String discountAvailable = ""+ds.child("discountAvailable").getValue();
                            String productDiscountedPrice = ""+ds.child("discountPrice").getValue();


                            productNameTv.setText(productTitle);
                            productQuantityTv.setText(productQuantity);
                            if(discountAvailable.equals("true")){
                                discountNoteTv.setVisibility(View.VISIBLE);
                                productDiscountPriceTv.setVisibility(View.VISIBLE);
                                productOriginalPriceTv.setPaintFlags(productOriginalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            }
                            else {
                                discountNoteTv.setVisibility(View.GONE);
                                productDiscountPriceTv.setVisibility(View.GONE);

                            }
                            productDiscountPriceTv.setText("$"+productDiscountedPrice);
                            productOriginalPriceTv.setText("$"+originalPrice);
                            discountNoteTv.setText(discountNote);
                            productDescription.setText(productDescription1);

                            try {
                                Picasso.get().load(productImage).placeholder(R.drawable.ic_store).into(productImageIv);
                            }
                            catch (Exception e){
                                productImageIv.setImageResource(R.drawable.ic_store);
                            }

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.orderByChild("uid").equalTo(shopUid)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds2: snapshot.getChildren()){
                                                String UserImage = ""+ds2.child("profileImage").getValue();
                                                String shopName = ""+ds2.child("shopName").getValue();
                                                String UserCity = ""+ds2.child("city").getValue();
                                                String UserCountry = ""+ds2.child("country").getValue();

                                                try {
                                                    Picasso.get().load(UserImage).placeholder(R.drawable.ic_baseline_person_24).into(shopPhoto);
                                                }
                                                catch (Exception e){
                                                    shopPhoto.setImageResource(R.drawable.ic_baseline_person_24);
                                                }
                                                addressTv.setText(UserCity+", " +UserCountry);
                                                shopNameTv.setText(shopName);

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}