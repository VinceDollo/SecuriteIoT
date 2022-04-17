package com.example.securitiot.projet.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securitiot.R;
import com.example.securitiot.databinding.FragmentMedicalBinding;
import com.example.securitiot.databinding.FragmentTransportBinding;
import com.example.securitiot.projet.fragments.medical.HubFragment;
import com.example.securitiot.projet.fragments.transport.DroneFragment;

public class MedicalFragment extends Fragment {

    private FragmentMedicalBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMedicalBinding.inflate(inflater);
        View view = binding.getRoot();

        setListener();


        // Inflate the layout for this fragment
        return view;
    }

    private void setListener() {

        binding.back.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new FindProductFragment()).addToBackStack(null).commit();
        });


         binding.lampes.setOnClickListener(v->{

         });
        binding.hubs.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new HubFragment()).addToBackStack(null).commit();
        });


    }
}