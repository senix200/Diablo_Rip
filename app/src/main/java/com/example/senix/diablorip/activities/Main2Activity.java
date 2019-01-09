package com.example.senix.diablorip.activities;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.senix.diablorip.R;
import com.example.senix.diablorip.fragments.BarbarianFragment;
import com.example.senix.diablorip.fragments.CrusaderFragment;
import com.example.senix.diablorip.fragments.DemonHunterFragment;
import com.example.senix.diablorip.fragments.Fragment_personalizado;
import com.example.senix.diablorip.fragments.MainFragment;
import com.example.senix.diablorip.fragments.MonkFragment;
import com.example.senix.diablorip.fragments.NecromancerFragment;
import com.example.senix.diablorip.fragments.WitchDoctorFragment;
import com.example.senix.diablorip.fragments.WizardFragment;
import com.example.senix.diablorip.model.Skills;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BarbarianFragment.OnSkillSelected, DemonHunterFragment.OnSkillSelected, MonkFragment.OnSkillSelected, NecromancerFragment.OnSkillSelected, WitchDoctorFragment.OnSkillSelected, WizardFragment.OnSkillSelected, CrusaderFragment.OnSkillSelected {
    private TextView tvUser;
    boolean isMultiPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        tvUser = findViewById(R.id.tvUser);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvUser.setText(bundle.getString("user_id"));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_barbarian) {
            fragment = new BarbarianFragment();
        } else if (id == R.id.nav_demonhunter) {
            fragment = new DemonHunterFragment();
        } else if (id == R.id.nav_monk) {
            fragment = new MonkFragment();
        } else if (id == R.id.nav_necromancer) {
            fragment = new NecromancerFragment();
        } else if (id == R.id.nav_wizard) {
            fragment = new WizardFragment();
        } else if (id == R.id.nav_witchdoctor) {
            fragment = new WitchDoctorFragment();
        }else if (id == R.id.nav_crusader) {
            fragment = new CrusaderFragment();
        }else if (id == R.id.nav_personalizado) {
            fragment = new Fragment_personalizado();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onChange(Skills skill) {



        Fragment fragment = new DetailsSkillActivity();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .commit();
        //Intent intent = new Intent(Main2Activity.this, DetailsSkillActivity.class);
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("skill", skills);
        //intent.putExtras(bundle);
        //startActivity(intent);
    }
}
