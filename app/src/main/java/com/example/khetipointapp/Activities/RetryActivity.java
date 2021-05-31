package com.example.khetipointapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.khetipointapp.R;
import com.example.khetipointapp.databinding.ActivityRetryBinding;

public class RetryActivity extends AppCompatActivity {
    ActivityRetryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        menuBarTap();
        binding.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RetryActivity.this, MainActivity.class));
            }
        });
    }






    //......................................................................................ActionBar
    private void menuBarTap() {
        binding.tripleDot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(RetryActivity.this,binding.tripleDot2);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.starSort:
                                Toast.makeText(RetryActivity.this, "Sort by star", Toast.LENGTH_SHORT).show();
                                return  true;
                            case R.id.nameSort:
                                Toast.makeText(RetryActivity.this, "Sort by name", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });

                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });
    }
}