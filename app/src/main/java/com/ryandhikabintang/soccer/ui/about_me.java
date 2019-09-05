package com.ryandhikabintang.soccer.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ryandhikabintang.soccer.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class about_me extends AppCompatActivity {
    public static final String GOOGLE_ACCOUNT = "google_account";

    private Toolbar toolbar;
    private CircleImageView imageView;
    private TextView nickname,fullname,email;
    private GoogleSignInClient googleSignInClient;

    private Button sign_out;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        toolbar = (Toolbar) findViewById(R.id.tl_detail);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView =  findViewById(R.id.imageview);
        nickname =  findViewById(R.id.tv_nickname);
        fullname =  findViewById(R.id.tv_full_name);
        email =  findViewById(R.id.email);
        sign_out =  findViewById(R.id.sign_out);
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        Glide.with(this)
                .load(googleSignInAccount.getPhotoUrl())
                .into(imageView);
        nickname.setText("Nickname = "+googleSignInAccount.getGivenName());
        fullname.setText("Fullname = "+googleSignInAccount.getDisplayName());
        email.setText("Email = "+googleSignInAccount.getEmail());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.server_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_outDialog(about_me.this).show();
            }
        });




        this.setTitle("About me");

    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public AlertDialog.Builder sign_outDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure to sign out ?");
        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent=new Intent(about_me.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        return builder;
    }

}
