package com.riyanhariyandi.userscrudproject_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riyanhariyandi.userscrudproject_1.adapter.UsersAdapter;
import com.riyanhariyandi.userscrudproject_1.model.GetUser;
import com.riyanhariyandi.userscrudproject_1.model.User;
import com.riyanhariyandi.userscrudproject_1.rest.ApiClient;
import com.riyanhariyandi.userscrudproject_1.rest.ApiInterface;

import java.security.Policy;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Context {
    Button btnInsert;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private UsersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public  static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = (Button) btnInsert.findViewById();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,InsertActivity.class));
            }
        });
        mRecyclerView = (RecyclerView) mRecyclerView.findViewById();
        mLayoutManager = new LinearLayoutManager(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma = this;
        refresh();
    }

    public void refresh(){
        Call<GetUser> userCall = mApiInterface.getUser();
        userCall.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                List<User> userList = response.body().getData();
                Log.d("Retrofit Get", "Jumlah data User "+ String.valueOf(userList.size()));
                mAdapter = new UsersAdapter(userList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                Log.e("Retrofit Get",t.toString());
            }
        });
    }
}
