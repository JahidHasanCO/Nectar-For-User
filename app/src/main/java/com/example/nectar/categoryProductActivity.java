package com.example.nectar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class categoryProductActivity extends AppCompatActivity {

    private RecyclerView categoriesProductRv;
    private String categoryName;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView categoryTv;

    private ArrayList<ModelProduct> productList2;
    private AdapterProduct adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        categoryName = getIntent().getStringExtra("categoryName");

        categoriesProductRv = findViewById(R.id.categoriesProductRv);
        categoryTv = findViewById(R.id.categoryTv);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        categoryTv.setText(categoryName);
        loadProducts();

    }

    private void loadProducts() {
        productList2 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList2.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    if (categoryName.equals(ds.child("productCategory").getValue())){
                        ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                        productList2.add(modelProduct);
                    }

                }
                adapterProduct = new AdapterProduct(categoryProductActivity.this,productList2);
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(categoryProductActivity.this,3,GridLayoutManager.VERTICAL,false);
                categoriesProductRv.setLayoutManager(gridLayoutManager2);
                categoriesProductRv.setAdapter(adapterProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}