package com.example.volleyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListAdapter adapter;
    List<User> users;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        users = new ArrayList<>();

        JsonRequest jsonRequest = new JsonObjectRequest(
                "https://reqres.in/api/users?per_page=12",
                response -> {
                    Gson gson = new Gson();
                    Type lsUserType = new TypeToken<List<User>>() {
                    }.getType();
                    try {
                        users = gson.fromJson(response.get("data").toString(), lsUserType);
                        adapter = new ListAdapter(users, this);
                        Log.d("SIZETEST", "onCreate: " + users.size());
                        RecyclerView recyclerView = findViewById(R.id.lstUsers);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        requestQueue.add(jsonRequest);
    }

}