package com.example.nectar;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class ShopFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView exclusiveProductRv;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ViewPager imageSliderVp;
    private CircleIndicator circleIndicatorCi;
    private Timer timer;
    private Handler handler;

    private ArrayList<ModelProduct> productList2;
    private AdapterProduct adapterProduct;
    private ImageSliderAdapter imageSliderAdapter;
    private List<Integer> imageList = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    public ShopFragment() {

    }



    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        // Inflate the layout for this fragment
        exclusiveProductRv = view.findViewById(R.id.exclusiveProductRv);
        imageSliderVp = view.findViewById(R.id.imageSliderVp);
        circleIndicatorCi = view.findViewById(R.id.circleIndicatorCi);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        imageList.add(R.drawable.banner1);
        imageList.add(R.drawable.banner2);
        imageList.add(R.drawable.banner3);
        imageSliderAdapter = new ImageSliderAdapter(imageList);
        imageSliderVp.setAdapter(imageSliderAdapter);
        circleIndicatorCi.setViewPager(imageSliderVp);

        handler = new Handler();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int i = imageSliderVp.getCurrentItem();
                        if (i == imageList.size() -1){
                            i = 0;
                            imageSliderVp.setCurrentItem(i,true);
                        }
                        else {
                            i++;
                            imageSliderVp.setCurrentItem(i,true);
                        }

                    }
                });
            }
        },4000,4000);

        loadExClusiveProducts(view);


        return view;
    }

    private void loadExClusiveProducts(View view) {
        productList2 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList2.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String dis = (String) ds.child("discountAvailable").getValue();
                    if (dis.equals("true")){
                        ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                        productList2.add(modelProduct);
                    }

                }
                adapterProduct = new AdapterProduct(getContext(),productList2);
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(),1,GridLayoutManager.HORIZONTAL,false);
                exclusiveProductRv.setLayoutManager(gridLayoutManager2);
                exclusiveProductRv.setAdapter(adapterProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}