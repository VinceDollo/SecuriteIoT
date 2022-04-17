package com.example.securitiot.projet.fragments.object;

import static com.google.common.reflect.Reflection.getPackageName;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securitiot.R;
import com.example.securitiot.databinding.FragmentObjetBinding;
import com.example.securitiot.projet.Constants;
import com.example.securitiot.projet.PreferenceManager;
import com.example.securitiot.projet.Utils;
import com.example.securitiot.projet.activities.PrincipalPageActivity;
import com.example.securitiot.projet.fragments.FindProductFragment;
import com.example.securitiot.projet.fragments.domotique.TVFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObjetFragment extends Fragment {

    private FragmentObjetBinding binding;
    private HashMap<Integer, String> hashMap = new HashMap<>();
    private ArrayList<String> arrayList = new ArrayList<>();

    private String name;
    private String image;
    private String description;

    private PreferenceManager preferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentObjetBinding.inflate(inflater);
        View view = binding.getRoot();

        preferenceManager = ((PrincipalPageActivity) requireActivity()).getPreferenceManager();



        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Utils.toast("Bundle not null", getActivity());
            hashMap = (HashMap<Integer, String>) bundle.getSerializable("HashMap");
            name = bundle.getString("name");
            image = bundle.getString("image");
            description = bundle.getString("description");
            binding.description.setText(description);
            binding.name.setText(name);
            String text = "";
            for(Map.Entry<Integer, String> entry : hashMap.entrySet()) {
                arrayList.add(entry.getValue());
                text += "- " + entry.getValue() + "\n\n";
            }
            binding.tvVulnerabilite.setText(text);
        }else {
            Utils.toast("Bundle NULL", getActivity());

        }

        Resources res = getResources();
        String mDrawableName = image;
        int resID = res.getIdentifier(mDrawableName , "drawable",getActivity().getPackageName());
        Drawable drawable = res.getDrawable(resID);
        binding.image.setImageDrawable(drawable );

        setListener();
        return view;
    }

    private void setListener(){
        binding.buttonAdd.setOnClickListener(v-> {
                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                    HashMap<String, Object> userInfoObjet = new HashMap<>();
                    userInfoObjet.put(Constants.KEY_NAME, name);
                     userInfoObjet.put("description", description);
                     userInfoObjet.put(Constants.KEY_COLLECTION_CATEGORIES_VULNERABILITES, arrayList);
                    database.collection(preferenceManager.getString(Constants.KEY_USER_ID)).add(userInfoObjet).addOnSuccessListener(documentReference -> {
                        binding.buttonAdd.setEnabled(false);
                        Utils.toast("Data add successfully", getActivity());
                    }).addOnFailureListener(exception -> {
                        Utils.toast(exception.getMessage(),getActivity());
                    });
                });

        binding.back.setOnClickListener(v->{
            getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new FindProductFragment()).addToBackStack(null).commit();
        });
    }
}