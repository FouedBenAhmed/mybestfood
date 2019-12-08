package com.projet.bestfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class home extends AppCompatActivity {
private  Button bt;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Obtain the FirebaseAnalytics instance.
        mAuth = FirebaseAuth.getInstance();
        Button eng=findViewById(R.id.eng);
        Button fr=findViewById(R.id.fr);
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Toast.makeText(home.this, "IN english.",
                            Toast.LENGTH_SHORT).show();
                    Locale locale = new Locale("EN");
                    Locale.setDefault(locale);
                    Configuration config=new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    SharedPreferences.Editor editors=getSharedPreferences("Settings", MODE_PRIVATE).edit();
                    editors.putString("My_Lang","EN");
                    editors.apply();
                    recreate();
            }
        });
        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(home.this, "en francais.",
                        Toast.LENGTH_SHORT).show();
                Locale locale = new Locale("FR");
                Locale.setDefault(locale);
                Configuration config=new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                SharedPreferences.Editor editors=getSharedPreferences("Settings", MODE_PRIVATE).edit();
                editors.putString("My_Lang","FR");
                editors.apply();
                recreate();
            }
        });


        TextView register =findViewById(R.id.register);
        final TextView username =findViewById(R.id.username);
        final TextView mdp =findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,Register.class);
                startActivity(i);
            }
        });



bt=findViewById(R.id.login);
bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(username.getText().toString().equals("")){
            Toast.makeText(home.this, "Please enter your username",
                    Toast.LENGTH_SHORT).show();
        }else if(mdp.getText().toString().equals("")){
            Toast.makeText(home.this, "Please enter password.",
                    Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(username.getText().toString(), mdp.getText().toString())
                    .addOnCompleteListener(home.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("errur", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i=new Intent(home.this,MenuAppActivity.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("erreur", "signInWithEmail:failure", task.getException());
                                Toast.makeText(home.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }
});
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
