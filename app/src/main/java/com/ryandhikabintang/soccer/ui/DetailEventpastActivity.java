package com.ryandhikabintang.soccer.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ryandhikabintang.soccer.Model.ModelJadwal;
import com.ryandhikabintang.soccer.R;

public class DetailEventpastActivity extends AppCompatActivity {
    ImageView imageView;

    TextView homeyellowcard, awayyellowcard, AwayLineupDefense, AwayLineupGoalkeeper, HomeLineupDefense,lineupgoalkepperhome,HomeLineupMidfield,AwayLineupMidfield , strEvent ;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_eventpast);
        toolbar = (Toolbar) findViewById(R.id.tl_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        this.setTitle("Detail " + getIntent().getStringExtra("events"));
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        //inisialisasi
        strEvent =  findViewById(R.id.strEvent);
        homeyellowcard =  findViewById(R.id.tv_homeyellowcard);
        awayyellowcard =  findViewById(R.id.tv_awayyellowcard);
        AwayLineupDefense =  findViewById(R.id.tv_AwayLineupDefense);
        AwayLineupGoalkeeper =  findViewById(R.id.tv_AwayLineupGoalkeeper);
        HomeLineupDefense =  findViewById(R.id.tv_HomeLineupDefense);
        lineupgoalkepperhome =  findViewById(R.id.tv_lineupgoalkepperhome);
        HomeLineupMidfield =  findViewById(R.id.tv_HomeLineupMidfield);
        AwayLineupMidfield =  findViewById(R.id.tv_AwayLineupMidfield);
         imageView=findViewById(R.id.imageview);

        final ModelJadwal surah = getIntent().getExtras().getParcelable("hasil");
        System.out.println("teasssds " + getIntent().getStringExtra("events"));
        if(getIntent().getExtras() != null){

            String thumb = getIntent().getStringExtra("events2");
            Glide.with(this)
                    .load(thumb)
                    .into(imageView);

            homeyellowcard.setText(getIntent().getStringExtra("events3"));
            awayyellowcard.setText(getIntent().getStringExtra("events5"));
            AwayLineupDefense.setText(getIntent().getStringExtra("events4"));
            AwayLineupGoalkeeper.setText(getIntent().getStringExtra("events7"));
            HomeLineupDefense.setText(getIntent().getStringExtra("events10"));
            lineupgoalkepperhome.setText(getIntent().getStringExtra("events9"));
            HomeLineupMidfield.setText(getIntent().getStringExtra("events6"));
            AwayLineupMidfield.setText(getIntent().getStringExtra("events8"));
            strEvent.setText(getIntent().getStringExtra("events"));

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
