package com.example.securitiot.projet.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.securitiot.R;
import com.example.securitiot.databinding.FragmentHomeBinding;
import com.example.securitiot.projet.Constants;
import com.example.securitiot.projet.PreferenceManager;
import com.example.securitiot.projet.Utils;
import com.example.securitiot.projet.activities.PrincipalPageActivity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button btn;
    private String vulnerabilités = "";

    private HashMap<String, ArrayList<String>> objets = new HashMap<>();
    private HashMap<String, LinearLayout> linearLayoutHashMap = new HashMap<>();

    private PreferenceManager preferenceManager;
    private String id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater);
        View view = binding.getRoot();

        preferenceManager = ((PrincipalPageActivity) requireActivity()).getPreferenceManager();
        checkData();
        databaseChange();
        return view;


    }

    private void setListener(Boolean a) {
        if (a) {
            btn.setOnClickListener(v -> {
                MeowBottomNavigation meowBottomNavigation = ((PrincipalPageActivity) requireActivity()).getMeowBottomNavigation();
                meowBottomNavigation.show(2, true);
                getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new FindProductFragment()).addToBackStack(null).commit();
            });
        } else {
            btn.setOnClickListener(v -> {
                Utils.toast((String) btn.getTag(), getActivity());
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                database.collection(preferenceManager.getString(Constants.KEY_USER_ID)).whereEqualTo(Constants.KEY_NAME, btn.getTag()).get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && task.getResult() != null && task.getResult().size() > 0) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                id = documentSnapshot.getId();
                                //Utils.toast(id, getActivity());

                                database.collection(preferenceManager.getString(Constants.KEY_USER_ID)).document(id).delete().addOnCompleteListener(task1 -> {
                                });
                            }
                        });

            });

        }


    }

    private void databaseChange() {
        FirebaseFirestore.getInstance().collection(preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.err.println("Listen failed: " + error);
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case REMOVED:
                                    Log.d("ID" ,dc.getDocument().getId());
                                    binding.ll1.removeAllViews();
                                    binding.ll.removeAllViews();
                                    vulnerabilités = "";
                                    checkData();
                                    break;
                                default:
                                    break;
                            }
                        }

                    }
                });
    }

    private void checkData() {
        preferenceManager = ((PrincipalPageActivity) requireActivity()).getPreferenceManager();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(preferenceManager.getString(Constants.KEY_USER_ID))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().size() > 0) {

                        for (int i = 0; i < task.getResult().size(); i++) {
                            binding.ll1.removeAllViews();

                            TextView abc = new TextView(HomeFragment.this.getContext());
                            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params4.setMargins(5,20,5,10);
                            abc.setLayoutParams(params4);
                            abc.setTextSize(20);
                            abc.setText("Vulnérabilités des objets choisis");
                            abc.setTextColor(getResources().getColor(R.color.red));
                            abc.setTypeface(null, Typeface.BOLD);
                            binding.ll1.addView(abc);


                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(i);
                            String name = documentSnapshot.getData().get(Constants.KEY_NAME).toString();


                            LinearLayout layout = new LinearLayout(getActivity());
                            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layout.setOrientation(LinearLayout.VERTICAL);

                            LinearLayout layout1 = new LinearLayout(getActivity());
                            layout1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layout1.setOrientation(LinearLayout.HORIZONTAL);
                            layout1.setGravity(Gravity.CENTER_HORIZONTAL);

                            TextView a = new TextView(HomeFragment.this.getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(0,20,30,0);
                            a.setLayoutParams(params);
                            a.setText(name);
                            a.setTextColor(getResources().getColor(R.color.primary));
                            a.setPadding(20, 30, 20, 30);
                            a.setTextSize(20);
                            a.setTypeface(null, Typeface.BOLD);
                            layout1.addView(a);

                            btn = new Button(HomeFragment.this.getContext());
                            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            btn.setLayoutParams(params2);
                            btn.setText("Remove");
                            btn.setTag(name);
                            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_delete, 0, 0, 0);
                            btn.setTextColor(Color.WHITE);
                            btn.setBackgroundResource(R.drawable.background_button);
                            btn.setPadding(20, 30, 20, 30);
                            layout1.addView(btn);

                            layout.addView(layout1);

                            TextView ab = new TextView(HomeFragment.this.getContext());
                            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );

                            ab.setLayoutParams(params3);
                            ArrayList<String> arr = (ArrayList<String>) documentSnapshot.getData().get(Constants.KEY_COLLECTION_CATEGORIES_VULNERABILITES);
                            for (String st : arr) {
                                vulnerabilités += "- " + st + " \n\n ";
                            }
                            ab.setText(vulnerabilités);
                            ab.setTextColor(Color.BLACK);
                            ab.setPadding(20, 25, 20, 5);
                            ab.setGravity(Gravity.CENTER);

                            binding.ll1.addView(ab);
                            binding.ll.addView(layout);
                            setListener(false);
                        }

                    } else {
                        binding.ll.removeAllViews();
                        binding.ll1.removeAllViews();

                        btn = new Button(HomeFragment.this.getContext());
                        btn.setText("Add your first object");
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        btn.setLayoutParams(params);
                        btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_ajout, 0, 0, 0);
                        btn.setTextColor(Color.WHITE);
                        btn.setBackgroundResource(R.drawable.background_button);
                        btn.setPadding(20, 30, 20, 30);
                        btn.setGravity(Gravity.CENTER);
                        binding.ll.addView(btn);
                        setListener(true);
                    }
                });
    }

}