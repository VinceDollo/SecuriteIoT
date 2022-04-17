package com.example.securitiot.projet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.securitiot.R;
import com.example.securitiot.databinding.ActivityPrincipalPageBinding;
import com.example.securitiot.projet.Constants;
import com.example.securitiot.projet.PreferenceManager;
import com.example.securitiot.projet.fragments.FindProductFragment;
import com.example.securitiot.projet.fragments.HomeFragment;
import com.example.securitiot.projet.fragments.ProfilFragment;

public class PrincipalPageActivity extends AppCompatActivity {

    private ActivityPrincipalPageBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager=new PreferenceManager(getApplicationContext());

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).addToBackStack(null).commit();

        binding.bottomAppBar.add(new MeowBottomNavigation.Model(1, R.drawable.img_home));
        binding.bottomAppBar.add(new MeowBottomNavigation.Model(2, R.drawable.img_find_object));
        binding.bottomAppBar.add(new MeowBottomNavigation.Model(3, R.drawable.img_profil));


        binding.bottomAppBar.show(1, true);

        binding.bottomAppBar.setOnClickMenuListener(model -> {
            switch (model.getId()) {
                case 1:
                    replace(new HomeFragment());
                    break;
                case 2:
                    replace(new FindProductFragment());
                    break;
                case 3:
                    replace(new ProfilFragment());
                    break;
                default:
                    break;
            }
            return null;
        });


    }

    public PreferenceManager getPreferenceManager(){
        return preferenceManager;
    }

    public MeowBottomNavigation getMeowBottomNavigation(){
        return binding.bottomAppBar;
    }


    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }
}
