package com.example.nectar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView btmNav = findViewById(R.id.botom_navigationView);
        btmNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout,new ShopFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.homeItem:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_layout,new ShopFragment()).commit();
                            break;
                        case R.id.explorerItem:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_layout,new ExplorerFragment()).commit();
                            break;
                        case R.id.cartItem:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_layout,new CartFragment()).commit();
                            break;
                        case R.id.FavouriteItem:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_layout,new BookmarkFragment()).commit();
                            break;
                        case R.id.AccountItem:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_layout,new AccountFragment()).commit();
                            break;
                    }


//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout
//                                    ,selectedFragment).commit();

                    return true;
                }
            };
}