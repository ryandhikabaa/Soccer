package com.ryandhikabintang.soccer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ryandhikabintang.soccer.Adapter.jadwalAdapter;
import com.ryandhikabintang.soccer.Constants;
import com.ryandhikabintang.soccer.Model.ModelJadwal;
import com.ryandhikabintang.soccer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventpastActivity extends AppCompatActivity {

    private static final String TAG = EventpastActivity.class.getSimpleName();

    private RecyclerView rvSurah;
    private jadwalAdapter allLeaguesAdapter;
    private List<ModelJadwal> allLeagueList = new ArrayList<>();
    private ProgressDialog mProgress;
    public static final String GOOGLE_ACCOUNT = "google_account";

    SwipeRefreshLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventpast);

        rvSurah = findViewById(R.id.recycler_view );
        swipeLayout = findViewById(R.id.swipe_container);


        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        mProgress.show();
        fetchscheduleApi();



        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override   
            public void onRefresh() {
                allLeagueList.clear();
                fetchscheduleApi();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(true);
                    }
                }, 1500);
                Toast.makeText(getApplicationContext(), "Data is Up to date!", Toast.LENGTH_SHORT).show();// Delay in millis
            }
        });

        setupRecycler();
    }

    private void setupRecycler(){
        allLeaguesAdapter = new jadwalAdapter(this,  allLeagueList);
        rvSurah.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSurah.setHasFixedSize(true);
        rvSurah.setAdapter(allLeaguesAdapter);
    }

    private void fetchscheduleApi() {
        AndroidNetworking.get(com.ryandhikabintang.soccer.Constants.BASE_URL)
                .setTag("leagues")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasilList = response.getJSONArray("events");
                            for (int i = 0; i < hasilList.length(); i++) {
                                JSONObject hasil = hasilList.getJSONObject(i);
                                ModelJadwal item = new ModelJadwal();
                                item.setStrHomeTeam(hasil.getString("strHomeTeam"));
                                item.setStrAwayTeam(hasil.getString("strAwayTeam"));
                                item.setStrDate(hasil.getString("strDate"));
                                item.setStrTime(hasil.getString("strTime"));
                                item.setIntAwayScore(hasil.getString("intAwayScore"));
                                item.setIntHomeScore(hasil.getString("intHomeScore"));
                                item.setStrEvent(hasil.getString("strEvent"));
                                item.setStrHomeYellowCards(hasil.getString("strHomeYellowCards"));
                                item.setStrAwayYellowCards(hasil.getString("strAwayYellowCards"));
                                item.setStrHomeLineupGoalkeeper(hasil.getString("strHomeLineupGoalkeeper"));
                                item.setStrAwayLineupGoalkeeper(hasil.getString("strAwayLineupGoalkeeper"));
                                item.setStrAwayLineupMidfield(hasil.getString("strAwayLineupMidfield"));
                                item.setStrHomeLineupMidfield(hasil.getString("strHomeLineupMidfield"));
                                item.setStrAwayLineupDefense(hasil.getString("strAwayLineupDefense"));
                                item.setStrHomeLineupDefense(hasil.getString("strHomeLineupDefense"));

                                item.setStrThumb(hasil.getString("strThumb"));
                                System.out.println("qwert "+hasil.getString("strEvent"));
                                allLeagueList.add(item);
                            }
                            mProgress.dismiss();
                            allLeaguesAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                                e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("", "onError: " + anError.getErrorBody());
                        Toast.makeText(getApplicationContext(), Constants.EROR, Toast.LENGTH_SHORT).show();
                    }
                });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Membaca file menu dan menambahkan isinya ke action bar jika ada.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void onComposeAction(MenuItem mi) {
        Intent m =new Intent(getApplicationContext(), about_me.class);
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        m.putExtra(about_me.GOOGLE_ACCOUNT, googleSignInAccount);

        startActivity(m);    }
}

