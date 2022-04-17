package com.example.securitiot.projet.fragments.transport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securitiot.R;
import com.example.securitiot.databinding.FragmentDroneBinding;
import com.example.securitiot.databinding.FragmentGPSBinding;
import com.example.securitiot.projet.Constants;
import com.example.securitiot.projet.fragments.TransportFragment;
import com.example.securitiot.projet.fragments.object.ObjetFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class GPSFragment extends Fragment {

    private FragmentGPSBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGPSBinding.inflate(inflater);
        View view = binding.getRoot();

        setListener();
        return view;
    }

    private void setListener(){
        binding.eview.setOnClickListener(v-> {
            Bundle bundle1 = new Bundle();
            HashMap<Integer, String> vulne = new HashMap<>();
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection(Constants.KEY_COLLECTION_CATEGORIES)
                    .whereEqualTo(Constants.KEY_NAME, "Eview EV-07S GPS")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().size() > 0) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            ArrayList<String> a = new ArrayList<>();
                            a = (ArrayList<String>) documentSnapshot.getData().get(Constants.KEY_COLLECTION_CATEGORIES_VULNERABILITES);
                            int i = 0;
                            for (String b : a) {
                                vulne.put(i, b);
                                i++;
                            }
                            String description = (String) documentSnapshot.getData().get("description");
                            bundle1.putString("description", description);
                            bundle1.putSerializable("HashMap", vulne);
                            bundle1.putString("image", "eview");
                            bundle1.putString("name", "Eview EV-07S GPS");
                            Fragment newF = new ObjetFragment();
                            newF.setArguments(bundle1);
                            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, newF).addToBackStack(null).commit();
                        }
                    });
        });


        binding.back.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new TransportFragment()).addToBackStack(null).commit();
        });

    }
}