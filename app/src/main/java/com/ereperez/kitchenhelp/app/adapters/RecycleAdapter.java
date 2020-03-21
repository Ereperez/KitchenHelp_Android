package com.ereperez.kitchenhelp.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ereperez.kitchenhelp.R;
import com.ereperez.kitchenhelp.app.activities.ItemsActivity;
import com.ereperez.kitchenhelp.app.models.Item;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * RecycleAdapter to populate the recyclerview with row_items with the data from arraylist
 * Each row has a button to delete the item from the current app view and from the server with a https request
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private ArrayList<Item> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item used
        public View rowView;
        public TextView txt;
        public ImageView pic;
        public Button but;

        public MyViewHolder(View rowView, TextView t, ImageView p, Button b) {
            super(rowView);
            this.rowView = rowView;
            this.txt = t;
            this.pic = p;
            this.but = b;
        }
    }

    // constructor for recycleadapter with arraylist and context (ItemsActivity)
    public RecycleAdapter(ArrayList<Item> myDataset, Context c) {
        mDataset = myDataset;
        context = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        //add all the items on row_item.xml to the viewholder to be populated
        TextView t = rowView.findViewById(R.id.itemText);
        ImageView p = rowView.findViewById(R.id.itemPicture);
        Button b = rowView.findViewById(R.id.itemDelete);

        MyViewHolder vh = new MyViewHolder(rowView, t, p, b);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //get the position from the list
        holder.txt.setTag(R.id.pos, position);
        holder.pic.setTag(R.id.pos, position);
        holder.but.setTag(R.id.pos, position);

        //set the text for the textview on the row
        holder.txt.setText(mDataset.get(position).getName());
        String url = mDataset.get(position).getUrl();

        //Picasso used to populate the imageview on the row
        Picasso.get()
                .load(url)
                .resize(111, 109)
                .centerCrop()
                .into(holder.pic);

        /**
         * Adds the https request delete function to the button on the row
         * Uses Volley library
         */
        holder.but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                RequestQueue queue = Volley.newRequestQueue(context);
                String delUrl = "https://kitchen-help.herokuapp.com/kitchen/" + mDataset.get(position).getId();

                // Request a json response from the provided URL
                StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, delUrl,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response on successful delete
                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //toast error message
                                Toast.makeText(context, "Couldn't delete item: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                queue.add(deleteRequest);

                //remove the item from the current "session" too so that the user doesn't
                // have to back and reload the list to see the change
                mDataset.remove((int)view.getTag(R.id.pos));
                notifyDataSetChanged();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}