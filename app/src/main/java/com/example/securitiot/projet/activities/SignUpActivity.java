package com.example.securitiot.projet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.example.securitiot.databinding.ActivitySignUpBinding;
import com.example.securitiot.projet.Constants;
import com.example.securitiot.projet.Utils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();
    }

    private void setListener(){
        binding.textSignIn.setOnClickListener(v -> onBackPressed());
        binding.buttonSignUp.setOnClickListener(v-> {
            if(isValidInformation()){
                signUp();
            }
        });
    }

    private void signUp() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put(Constants.KEY_NAME, binding.inputName.getText().toString().trim());
        userInfo.put(Constants.KEY_EMAIL, binding.inputEmail.getText().toString().trim());
        userInfo.put(Constants.KEY_PASSWORD, binding.inputPassword.getText().toString().trim());
        userInfo.put(Constants.KEY_OBJECTS_IN_HOUSE, 0);

        database.collection(Constants.KEY_COLLECTION_USER_NAME).add(userInfo).addOnSuccessListener(documentReference -> {
            loading(false);
            Intent i = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(i);
            Utils.toast("Account successfully created.",SignUpActivity.this);
        }).addOnFailureListener(exception -> {
            loading(false);
            Utils.toast(exception.getMessage(),SignUpActivity.this);
        });

    }

    private Boolean isValidInformation() {
        if(binding.inputName.getText().toString().trim().isEmpty()){
            Utils.toast("Name empty",SignUpActivity.this);
            return false;
        }else if(binding.inputEmail.getText().toString().trim().isEmpty()){
            Utils.toast("Email empty",SignUpActivity.this);
            return false;
        }else if(binding.inputPassword.getText().toString().trim().isEmpty()){
            Utils.toast("Password empty",SignUpActivity.this);
            return false;
        }else if(binding.inputCheckPassword.getText().toString().trim().isEmpty()) {
            Utils.toast("Password verification empty",SignUpActivity.this);
            return false;
        }else if(!binding.inputPassword.getText().toString().trim().equals(binding.inputCheckPassword.getText().toString().trim())) {
            Utils.toast("Password not equals", SignUpActivity.this);
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
            Utils.toast("Email not valid", SignUpActivity.this);
            return false;
        }else {
            return true;
        }
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignUp.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonSignUp.setVisibility(View.VISIBLE);

        }
    }



}