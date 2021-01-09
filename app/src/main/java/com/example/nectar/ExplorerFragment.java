package com.example.nectar;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExplorerFragment extends Fragment {

    private RecyclerView categoriesRv,productRv;
    private EditText searchProduct;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private ArrayList<ModelProduct> productList2;
    private AdapterProduct adapterProduct;

    List<String> titles;
    List<Integer> images;
    CategoryAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ExplorerFragment() {

    }


    public static ExplorerFragment newInstance(String param1, String param2) {
        ExplorerFragment fragment = new ExplorerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explorer, container, false);

        categoriesRv = view.findViewById(R.id.categoriesRv);
        productRv = view.findViewById(R.id.productRv);
        searchProduct = view.findViewById(R.id.searchProduct);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        loadAllProducts(view);


        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Beverages");
        titles.add("Beauty & Personal Care");
        titles.add("Bakery & Snacks");
        titles.add("Cooking Oil & Ghee");
        titles.add("Dairy & Eggs");
        titles.add("Fresh Fruits & Vegetable");
        titles.add("Meat & Fish");
        titles.add("Others");


        images.add(R.drawable.beverages);
        images.add(R.drawable.beauty);
        images.add(R.drawable.bakery);
        images.add(R.drawable.oil);
        images.add(R.drawable.dairy);
        images.add(R.drawable.fruits);
        images.add(R.drawable.meat);
        images.add(R.drawable.other);

        adapter = new CategoryAdapter(getActivity(),titles,images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.HORIZONTAL,false);
        categoriesRv.setLayoutManager(gridLayoutManager);

        categoriesRv.setAdapter(adapter);

        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterProduct.getFilter().filter(s);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void loadAllProducts(View view) {
        productList2 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList2.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                    productList2.add(modelProduct);
                }
                adapterProduct = new AdapterProduct(getContext(),productList2);
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
                productRv.setLayoutManager(gridLayoutManager2);
                productRv.setAdapter(adapterProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}