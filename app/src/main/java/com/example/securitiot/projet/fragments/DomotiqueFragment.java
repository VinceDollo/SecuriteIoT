package com.example.securitiot.projet.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securitiot.R;
import com.example.securitiot.databinding.FragmentDomotiqueBinding;
import com.example.securitiot.projet.fragments.domotique.CameraFragment;
import com.example.securitiot.projet.fragments.domotique.CoffeMakerFragment;
import com.example.securitiot.projet.fragments.domotique.EnceinteFragment;
import com.example.securitiot.projet.fragments.domotique.LampesFragment;
import com.example.securitiot.projet.fragments.domotique.TVFragment;

public class DomotiqueFragment extends Fragment {

    private FragmentDomotiqueBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDomotiqueBinding.inflate(inflater);
        View view = binding.getRoot();

        setListener();


        // Inflate the layout for this fragment
        return view;
    }

    private void setListener() {

        binding.back.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new FindProductFragment()).addToBackStack(null).commit();
        });

        binding.enceintes.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new EnceinteFragment()).addToBackStack(null).commit();

        });

        binding.camera.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new CameraFragment()).addToBackStack(null).commit();
        });

        binding.alarme.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new EnceinteFragment()).addToBackStack(null).commit();
        });

        binding.tvs.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new TVFragment()).addToBackStack(null).commit();
        });

        binding.thermostat.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new EnceinteFragment()).addToBackStack(null).commit();
        });

        binding.lampes.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new LampesFragment()).addToBackStack(null).commit();
        });

        binding.coffee.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new CoffeMakerFragment()).addToBackStack(null).commit();

        });

        binding.montre.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new EnceinteFragment()).addToBackStack(null).commit();
        });


    }
}