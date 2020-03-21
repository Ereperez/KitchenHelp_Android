package com.ereperez.kitchenhelp.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.ereperez.kitchenhelp.R;
import com.ereperez.kitchenhelp.app.adapters.RecycleAdapter;
import com.ereperez.kitchenhelp.app.models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
* Activity that handles the population of acvitity_items with row_items with the help of RecycleAdapter
* Uses Volley library and Picasso
 */
public class ItemsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        recyclerView = findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        //use linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get the items and send to adapter
        requestWithVolley();
    }

    /**
     * Requests the items from the json list from the URL
     * Uses Volley library to get the json data
     * Adds the data to a arraylist
     */
    public void requestWithVolley() {
        RequestQueue queue = Volley.newRequestQueue(this);
        // URL for volley to use
        String url ="https://kitchen-help.herokuapp.com/kitchen";

        // Request a json response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //for loop for the objects in array
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //add the objects to the arraylist
                                JSONObject objects = response.getJSONObject(i);
                                String name = objects.getString("name");
                                String url = objects.getString("picture");
                                String id = objects.getString("_id");
                                Item item = new Item(name, url, id);
                                itemList.add(item);

                            } catch(JSONException e){
                                //add error message for the item to list
                                String error = "error loading";
                                Item er = new Item(error, error, error);
                                itemList.add(er);
                                e.printStackTrace();
                            }
                        }
                        //send items to adapter that inserts them to layout
                        recyclerView.setAdapter(new RecycleAdapter(itemList, ItemsActivity.this));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //toast error message
                Toast.makeText(ItemsActivity.this, "Couldn't load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

}
