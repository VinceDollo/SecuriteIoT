package com.example.securitiot.projet;

import android.content.Context;
import android.widget.Toast;


public class Utils {

    public static void toast(String a, Context c){
        Toast.makeText(c, a, Toast.LENGTH_SHORT).show();
    }

}
