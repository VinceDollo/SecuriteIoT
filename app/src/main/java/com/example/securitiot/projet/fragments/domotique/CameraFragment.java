package com.example.securitiot.projet.fragments.domotique;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securitiot.R;
import com.example.securitiot.databinding.FragmentCameraBinding;
import com.example.securitiot.databinding.FragmentEnceinteBinding;
import com.example.securitiot.projet.Constants;
import com.example.securitiot.projet.fragments.DomotiqueFragment;
import com.example.securitiot.projet.fragments.object.ObjetFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class CameraFragment extends Fragment {

    private FragmentCameraBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater);
        View view = binding.getRoot();

        setListener();
        return view;
    }

    private void setListener(){

        binding.back.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new DomotiqueFragment()).addToBackStack(null).commit();
        });

    }
}