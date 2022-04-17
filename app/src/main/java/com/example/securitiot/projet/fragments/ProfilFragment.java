package com.example.securitiot.projet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securitiot.databinding.FragmentProfilBinding;
import com.example.securitiot.projet.Constants;
import com.example.securitiot.projet.PreferenceManager;
import com.example.securitiot.projet.activities.PrincipalPageActivity;


public class ProfilFragment extends Fragment {

    private FragmentProfilBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilBinding.inflate(inflater);
        View view = binding.getRoot();

        preferenceManager = ((PrincipalPageActivity) requireActivity()).getPreferenceManager();

        loadInformation();

        setListener();

        return view;
    }

    private void setListener(){
        binding.buttonDisconnect.setOnClickListener(v-> signOut());
    }

    private void loadInformation(){
        binding.name.setText(preferenceManager.getString(Constants.KEY_NAME));
        binding.tvName.setText(preferenceManager.getString(Constants.KEY_NAME));
        binding.tvEmail.setText(preferenceManager.getString(Constants.KEY_EMAIL));
    }

    private void signOut(){
        preferenceManager.clear();
        requireActivity().finish();
    }
}