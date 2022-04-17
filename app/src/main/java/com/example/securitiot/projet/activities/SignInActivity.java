package com.example.securitiot.projet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.securitiot.databinding.ActivitySignInBinding;
import com.example.securitiot.projet.Constants;
import com.example.securitiot.projet.PreferenceManager;
import com.example.securitiot.projet.Utils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;
    private int remainingTries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        remainingTries = 3;
        preferenceManager=new PreferenceManager(getApplicationContext());

        setListener();
    }

    private void setListener(){
        binding.textCreateNewAccount.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(i);
        });

        binding.buttonSignIn.setOnClickListener(v->{
            if(isValidInformation()){
                signIn();
            }
        });
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonSignIn.setVisibility(View.VISIBLE);

        }
    }

    private Boolean isValidInformation() {
        if(binding.inputEmail.getText().toString().trim().isEmpty()) {
            Utils.toast("Enter email", SignInActivity.this);
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
            Utils.toast("Enter valid email", SignInActivity.this);
            return false;
        }else if(binding.inputPassword.getText().toString().trim().isEmpty()){
            Utils.toast("Enter password", SignInActivity.this);
            return false;
        }else {
            return true;
        }
    }

    private void signIn() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USER_NAME)
                .whereEqualTo(Constants.KEY_EMAIL, binding.inputEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.inputPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGN_IN, true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getData().get(Constants.KEY_NAME).toString());
                        preferenceManager.putString(Constants.KEY_OBJECTS_IN_HOUSE, documentSnapshot.getData().get(Constants.KEY_OBJECTS_IN_HOUSE).toString());
                        preferenceManager.putString(Constants.KEY_EMAIL, documentSnapshot.getData().get(Constants.KEY_EMAIL).toString());
                        Intent i = new Intent(getApplicationContext(), PrincipalPageActivity.class);
                        loading(false);
                        startActivity(i);
                    } else {
                        loading(false);
                        remainingTries--;
                        Utils.toast("Email/password are not corrects, remaining tries : " + remainingTries, SignInActivity.this);
                        if (remainingTries == 0) {
                            binding.buttonSignIn.setEnabled(false);
                            binding.buttonSignIn.setBackgroundColor(Color.DKGRAY);
                        }
                    }
                });
    }
}