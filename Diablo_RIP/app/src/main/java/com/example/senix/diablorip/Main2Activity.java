package com.example.senix.diablorip;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senix.diablorip.Data.DataBaseRoom;
import com.example.senix.diablorip.Adapters.ItemAdapter;
import com.example.senix.diablorip.model.Item;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemAdapter.OnButtonClickedListener {
    private TextView tvUser;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList = new ArrayList<>();
    private DataBaseRoom dbRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp();
            }
        });



        dbRoom= DataBaseRoom.getINSTANCE(this);
        new GetAsyncItems().execute();


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter= new ItemAdapter(this, itemList, this);

        recyclerView.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void createPopUp() {

        builder = new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.popup, null);

        builder.setView(view);

        dialog= builder.create();
        dialog.show();

        final EditText itemName= view.findViewById(R.id.popupItemName);
        final EditText itemDescription= view.findViewById(R.id.popupItemDescription);
        Button saveButton= view.findViewById(R.id.popupSaveItemButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(itemName.getText()) && !TextUtils.isEmpty(itemDescription.getText())){

                    saveItemDb(itemName.getText().toString(), itemDescription.getText().toString());

                }

            }
        });
    }

    private void saveItemDb(String name, String description) {

        Item item= new Item (name, description);
        new AsyncAddItemDB().execute(item);
        dialog.dismiss();
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
            tvUser.setText(bundle.getString("user"));
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
    @Override
    public void onButtonCliked(View v, int position) {

        if (v.getId()== R.id.editITemIcon) {
            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
            editItem(position);
        } else if (v.getId()==R.id.deleteItemIcon) {
            deleteItem(position);

            Toast.makeText(this, "position: "+ position, Toast.LENGTH_SHORT).show();
        }
    }
    private void editItem(final int position) {

        final Item p= itemList.get(position);

        builder = new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.popup, null);

        builder.setView(view);

        dialog= builder.create();
        dialog.show();

        final EditText itemName= view.findViewById(R.id.popupItemName);
        final EditText itemDescription= view.findViewById(R.id.popupItemDescription);
        Button saveButton= view.findViewById(R.id.popupSaveItemButton);

        itemName.setText(p.getName());
        itemDescription.setText(p.getDescription());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(itemName.getText()) && !TextUtils.isEmpty(itemDescription.getText())){

                    p.setName(itemName.getText().toString());
                    p.setDescription(itemDescription.getText().toString());

                    new AsyncEditItemDB(position).execute(p);

                }

                dialog.dismiss();

            }
        });


    }

    private void deleteItem(int position) {
        Item p= itemList.get(position);


        new AsynDeleteItemDB(position).execute(p);
    }

    private class GetAsyncItems extends AsyncTask<Void, Void, List<Item>> {


        @Override
        protected List<Item> doInBackground(Void... voids) {

            List<Item> products= dbRoom.itemdao().getItems();
            return products;
        }

        @Override
        protected void onPostExecute(List<Item> products) {
            itemList.addAll(products);
            adapter.notifyDataSetChanged();
        }
    }


    private class AsyncAddItemDB  extends AsyncTask< Item, Void, Long> {

        Item item;

        @Override
        protected Long doInBackground(Item... items) {

            long id=-1;

            if (items.length!=0) {
                String name = items[0].getName();
                Log.d("Product", name);
                item = items[0];
                id = dbRoom.itemdao().insertItem(items[0]);
                item.setId(id);
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id == -1){
                Snackbar.make(recyclerView, "Error adding item", Snackbar.LENGTH_LONG)
                        .show();
            } else {
                Snackbar.make(recyclerView, "Item added", Snackbar.LENGTH_LONG)
                        .show();
                itemList.add(0,item);
                adapter.notifyItemInserted(0);
            }
        }
    }




    private class AsyncEditItemDB extends AsyncTask<Item, Void, Integer> {

        private int position;

        public AsyncEditItemDB(int position) {
            this.position = position;
        }

        @Override
        protected Integer doInBackground(Item... items) {
            int updatedrows=0;
            if (items.length!=0) {

                updatedrows=dbRoom.itemdao().updateItem(items[0]);

            }

            return updatedrows;
        }

        @Override
        protected void onPostExecute(Integer updatedRows) {
            if (updatedRows == 0){
                Snackbar.make(recyclerView, "Error updating item", Snackbar.LENGTH_LONG)
                        .show();
            } else {
                Snackbar.make(recyclerView, "Item updated", Snackbar.LENGTH_LONG)
                        .show();
                adapter.notifyItemChanged(position);
            }
        }
    }


    private class AsynDeleteItemDB extends AsyncTask <Item, Void, Integer> {
        private int position;
        public AsynDeleteItemDB(int position) {
            this.position=position;
        }

        @Override
        protected Integer doInBackground(Item... items) {

            int deletedrows=0;

            if (items.length!=0) {

                deletedrows=dbRoom.itemdao().deleteItem(items[0]);

            }

            return deletedrows;

        }

        @Override
        protected void onPostExecute(Integer deletedRows) {
            if (deletedRows == 0){
                Snackbar.make(recyclerView, "Error deleting product", Snackbar.LENGTH_LONG)
                        .show();
            } else {
                Snackbar.make(recyclerView, "Product deleted", Snackbar.LENGTH_LONG)
                        .show();
                itemList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = new Fragment_cabeza();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_cabeza) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frament, fragment)
                    .commit();
        } else if (id == R.id.nav_hombreras) {

        } else if (id == R.id.nav_chest) {

        } else if (id == R.id.nav_brazalete) {

        } else if (id == R.id.nav_guantes) {

        } else if (id == R.id.nav_cinturon) {

        }else if (id == R.id.nav_pantalones) {

        }else if (id == R.id.nav_botas) {

        }else if (id == R.id.nav_armas) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
