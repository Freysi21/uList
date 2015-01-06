package org.ulist.ulist;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FrontPage extends ActionBarActivity {

    private static final int EDIT = 0, DELETE = 1;

    EditText storeName;
    List<Store> stores = new ArrayList<Store>();
    ListView storeListView;
    int longClickedIndex;
    DatabaseHandler dbHandler;
    ArrayAdapter<Store> storeAdapter;

    private class StoreListAdapter extends ArrayAdapter<Store> {
        public StoreListAdapter() {
            super (FrontPage.this, R.layout.store_view, stores);
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            if(view == null)
                view = getLayoutInflater().inflate(R.layout.store_view, parent, false);

            Store currStore = stores.get(pos);

            TextView name = (TextView) view.findViewById(R.id.storeName);
            name.setText(currStore.getName());

            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        storeName = (EditText) findViewById(R.id.inputAddStore);
        storeListView = (ListView) findViewById(R.id.storeListView);
        dbHandler = new DatabaseHandler(getApplicationContext());

        final Button storeAddBtn = (Button) findViewById(R.id.btnAddStore);
        storeAddBtn.setOnClickListener(new View.OnClickListener() { //button addStore button implement

            @Override
            public void onClick(View v) {
                //hide keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), inputManager.HIDE_NOT_ALWAYS);

                Store store = new Store(dbHandler.getStoreCount(), String.valueOf(storeName.getText()));

                if(storeExists(store))
                    Toast.makeText(getApplicationContext(), String.valueOf(storeName.getText()) +
                            " already exists!", Toast.LENGTH_SHORT).show();
                else {
                    dbHandler.createStore(store);
                    stores.add(store);
                    storeAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(storeName.getText())
                            + " has been added!", Toast.LENGTH_SHORT).show();

                    storeName.setText("");
                    return;
                }
            }
        });

        //Store input implement
        storeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if store input is not empty, enable button
                storeAddBtn.setEnabled(String.valueOf(storeName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //get all contact in the database
        if(dbHandler.getStoreCount() != 0)
            stores.addAll(dbHandler.getAllStores());


        populateList();

        //Long clickedItem
        registerForContextMenu(storeListView);
        storeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedIndex = position;
                return false;
            }
        });
    }

    //Checks if a store exists in the list
    public boolean storeExists(Store store) {
        String name = store.getName();
        int storesSize = stores.size();

        for(int i = 0; i < storesSize; i++) {
            if(name.compareToIgnoreCase(stores.get(i).getName()) == 0)
                return true;
        }

        return false;
    }

    public void populateList() {
        storeAdapter = new StoreListAdapter();
        storeListView.setAdapter(storeAdapter);

    }

    //Menu for longClicked Item lists
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.pencil_icon);
        menu.setHeaderTitle("Store options");
        menu.add(Menu.NONE, EDIT, Menu.NONE, "Edit Store");
        menu.add(Menu.NONE, DELETE, Menu.NONE, "Delete Store");
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case EDIT:
                //TODO: implement edit store
                break;
            case DELETE:
                //TODO: implement delete store
                Store store = stores.get(longClickedIndex);
                Toast.makeText(getApplicationContext(), String.valueOf(store.getName()) +
                        " has been deleted", Toast.LENGTH_SHORT ).show();
                dbHandler.deleteStore(stores.get(longClickedIndex));

                stores.remove(longClickedIndex);
                storeAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

}