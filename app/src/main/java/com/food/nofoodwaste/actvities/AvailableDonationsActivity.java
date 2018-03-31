package com.food.nofoodwaste.actvities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.food.nofoodwaste.R;
import com.food.nofoodwaste.adapters.DonationsListAdapter;
import com.food.nofoodwaste.utils.FoodObject;
import com.food.nofoodwaste.utils.MyConstants;
import com.food.nofoodwaste.utils.OnItemClickListener;
import com.food.nofoodwaste.utils.ServiceHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


import android.widget.LinearLayout.LayoutParams;

public class AvailableDonationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DonationsListAdapter donationsListAdapter;
    private ArrayList<FoodObject> foodObjects;
    private OnItemClickListener onItemClickListener;
    //private ArrayList<ArrayList<String>> foodItems;
    private DatabaseReference mDatabase;
// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_donations);
        //initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Context mContext = getApplicationContext();

        //foodItems = new ArrayList<ArrayList<String>>();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    try{
                        String address = childDataSnapshot.child("address").getValue().toString();
                        String foodName = childDataSnapshot.child("foodName").getValue().toString();
                        String phoneNumber = childDataSnapshot.child("phoneNumber").getValue().toString();
                        String quantity = childDataSnapshot.child("quantity").getValue().toString();
                        Log.v("WAH",""+ childDataSnapshot.getKey()); //displays the key for the node
                        Log.v("WAH",""+ childDataSnapshot.child("address").getValue());   //gives the value for given keyname
                        Log.v("WAH",""+ childDataSnapshot.child("foodName").getValue());
                        Log.v("WAH",""+ childDataSnapshot.child("phoneNumber").getValue());
                        Log.v("WAH",""+ childDataSnapshot.child("quantity").getValue());

                        CardView card = new CardView(mContext);
                        // Set the CardView layoutParams
                        LayoutParams params = new LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT);
                        params.setMargins(40, 20, 40, 20);
                        card.setLayoutParams(params);
                        // Set CardView corner radius
                        //card.setRadius(9);
                        // Set cardView content padding
                        //card.setContentPadding(15, 15, 15, 15);
                        // Set a background color for CardView
                        card.setCardBackgroundColor(Color.parseColor("#e5e5e5"));

                        // Set the CardView maximum elevation
                        //card.setMaxCardElevation(15);
                        // Set CardView elevation
                        card.setCardElevation(5);
                        // Initialize a new TextView to put in CardView

                        TextView tv = new TextView(mContext);
                        tv.setLayoutParams(params);
                        tv.setText(foodName+" ("+quantity+")");
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                        tv.setTextColor(Color.BLACK);
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);

                        LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                        params2.setMargins(0, 150, 0, 20);
                        TextView tv3 = new TextView(mContext);
                        tv3.setLayoutParams(params2);
                        tv3.setText("\n"+address);
                        tv3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                        tv3.setTextColor(Color.DKGRAY);
                        tv3.setGravity(Gravity.CENTER_HORIZONTAL);

                        LayoutParams params3 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                        params3.setMargins(0, 300, 0, 20);
                        TextView tv4 = new TextView(mContext);
                        tv4.setLayoutParams(params3);
                        tv4.setText("\n\n\n"+phoneNumber.substring(0, 3)+"-"+phoneNumber.substring(3, 6)+"-"+phoneNumber.substring(6, 10));
                        tv4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                        tv4.setTextColor(Color.DKGRAY);
                        tv4.setGravity(Gravity.CENTER_HORIZONTAL);

                        // Put the TextView in CardView
                        card.addView(tv);
                        card.addView(tv3);
                        card.addView(tv4);
                        // Finally, add the CardView in root layout
                        ((LinearLayout)findViewById(R.id.linearlayoutboi)).addView(card);
                    }catch(Exception e){}
                    /*ArrayList<String> tempArray = new ArrayList<>();
                    tempArray.add(address);
                    tempArray.add(foodName);
                    tempArray.add(phoneNumber);
                    tempArray.add(quantity);
                    foodItems.add(tempArray);*/
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String post = dataSnapshot.getValue(String.class);
                Log.e("STRING: ", post);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("WAH", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);*/

        /*onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getApplicationContext(),"clicked: "+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),AvailableDeliveryPlacesActivity.class);
                intent.putExtra("DonationObj",foodObjects.get(position));
                startActivity(intent);
            }
        };

        foodObjects = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager llm = new LinearLayoutManager(AvailableDonationsActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        donationsListAdapter = new DonationsListAdapter(getApplicationContext(),foodObjects);
        donationsListAdapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(donationsListAdapter);

        loadLocations();*/

    }

    private void loadLocations() {
        new loadLocationsAsyncTask().execute();
    }

    private void displayToast(String toastMsg) {
        Toast.makeText(getApplicationContext(),toastMsg,Toast.LENGTH_SHORT).show();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class loadLocationsAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(AvailableDonationsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler serviceHandler = new ServiceHandler();


            // Making a request to url and getting response
            //String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            String sUrl = MyConstants.URL_ROOT+"donate/distance";

            String jsonStr = serviceHandler.performGetCall(sUrl);

            Log.e("Response: ", "--->>> " + jsonStr);

            if (jsonStr != null) try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                if (jsonArray != null && jsonArray.length() > 0) {
                    convertJsonAsObj(jsonArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (foodObjects.size()> 0){
                loadAdapter();
            }
        }

    }

    private void convertJsonAsObj(JSONArray jsonArray) {
        try{
            for (int i = 0;i < jsonArray.length() ;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FoodObject foodObject = new FoodObject();
                if (!jsonObject.isNull("donorMobile"))
                    foodObject.setMobile(jsonObject.getString("donorMobile"));
                //foodObject.setId(jsonObject.getString("id"));
                if (!jsonObject.isNull("foodType"))
                foodObject.setFoodtype(jsonObject.getString("foodType"));

                if (!jsonObject.isNull("quantity"))
                foodObject.setQuantity(jsonObject.getString("quantity"));

                if (!jsonObject.isNull("address"))
                foodObject.setAddress(jsonObject.getString("address"));

                if (!jsonObject.isNull("latitude"))
                foodObject.setLat(jsonObject.getString("latitude"));

                if (!jsonObject.isNull("longitude"))
                foodObject.setLng(jsonObject.getString("longitude"));

                if (!jsonObject.isNull("distance"))
                foodObject.setDistance(jsonObject.getString("distance"));
                //foodObject.sets(jsonObject.getString("donationStatus"));
                foodObjects.add(foodObject);
            }
        }catch (Exception e){}
    }

    private void loadAdapter() {
        if (foodObjects.size() > 0) {
            donationsListAdapter = new DonationsListAdapter(getApplicationContext(), foodObjects);
            donationsListAdapter.setOnItemClickListener(onItemClickListener);
            recyclerView.setAdapter(donationsListAdapter);
            //donationsListAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

}
