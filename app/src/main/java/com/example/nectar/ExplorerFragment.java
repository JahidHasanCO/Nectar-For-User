package com.example.nectar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ExplorerFragment extends Fragment {

    private RecyclerView categoriesRv;

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        categoriesRv.setLayoutManager(gridLayoutManager);
        categoriesRv.setAdapter(adapter);

        return view;
    }
}