package com.example.securitiot.projet.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securitiot.R;
import com.example.securitiot.databinding.FragmentFindProductBinding;


public class FindProductFragment extends Fragment {

    private FragmentFindProductBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFindProductBinding.inflate(inflater);
        View view = binding.getRoot();

        setListener();


        // Inflate the layout for this fragment
        return view;
    }

    private void setListener() {
        binding.domotique.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new DomotiqueFragment()).addToBackStack(null).commit();
        });

        binding.transport.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new TransportFragment()).addToBackStack(null).commit();
        });

        binding.medical.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new MedicalFragment()).addToBackStack(null).commit();
        });
    }
}