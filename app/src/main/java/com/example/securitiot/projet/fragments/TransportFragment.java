package com.example.securitiot.projet.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securitiot.R;
import com.example.securitiot.databinding.FragmentTransportBinding;
import com.example.securitiot.projet.Utils;
import com.example.securitiot.projet.fragments.domotique.TVFragment;
import com.example.securitiot.projet.fragments.transport.DroneFragment;
import com.example.securitiot.projet.fragments.transport.GPSFragment;


public class TransportFragment extends Fragment {

    private FragmentTransportBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTransportBinding.inflate(inflater);
        View view = binding.getRoot();

        setListener();


        // Inflate the layout for this fragment
        return view;
    }

    private void setListener() {

        binding.back.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new FindProductFragment()).addToBackStack(null).commit();
        });


        binding.gps.setOnClickListener(v->{
            Utils.toast("click gps", getActivity());
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new GPSFragment()).addToBackStack(null).commit();
        });
        ;

        binding.drone.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new DroneFragment()).addToBackStack(null).commit();

        });

        binding.lampes.setOnClickListener(v->{

        });


    }
}