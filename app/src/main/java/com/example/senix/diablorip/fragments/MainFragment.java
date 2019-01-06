package com.example.senix.diablorip.fragments;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.senix.diablorip.R;
import com.example.senix.diablorip.model.Skills;


public class MainFragment extends Fragment implements  TemplarFragment.OnSkillSelected, EnchantressFragment.OnSkillSelected, ScoundrelFragment.OnSkillSelected {
TextView nombreTemplar;
TextView nombreCanalla;
TextView nombreMagic;
Typeface myfont;
Button btnClickmeTemplar;
Button btnClickmeCanalla;
Button btnClickmeHechicera;
    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_followers, container, false);

        nombreTemplar = view.findViewById(R.id.nombreTemplar);
        myfont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/diablo_h.ttf");
        nombreTemplar.setTypeface(myfont);
        btnClickmeTemplar = view.findViewById(R.id.Templar);
        btnClickmeTemplar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new TemplarFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_fragment, fragment)
                        .commit();
            }
        });

        nombreCanalla = view.findViewById(R.id.nombreCanalla);
        nombreCanalla.setTypeface(myfont);
        btnClickmeCanalla = view.findViewById(R.id.Canalla);
        btnClickmeCanalla.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new ScoundrelFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_fragment, fragment)
                        .commit();
            }
        });

        nombreMagic = view.findViewById(R.id.nombreMagic);
        nombreMagic.setTypeface(myfont);
        btnClickmeHechicera = view.findViewById(R.id.Magic);
        btnClickmeHechicera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new EnchantressFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_fragment, fragment)
                        .commit();
            }
        });

        return view;
    }


    @Override
    public void onChange(Skills skills) {

    }
}
