package com.ereperez.kitchenhelp.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ereperez.kitchenhelp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showItemsOnClick(View view){
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
    }

    public void showTastelineOnClick(View view){
        Intent intent = new Intent(this, TastelineActivity.class);
        startActivity(intent);
    }
}
