package org.ulist.ulist;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
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
    EditText storeName;
    List<Store> stores = new ArrayList<Store>();
    ListView storeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        storeName = (EditText) findViewById(R.id.inputAddStore);
        storeListView = (ListView) findViewById(R.id.storeListView);

        final Button storeAddBtn = (Button) findViewById(R.id.btnAddStore);
        storeAddBtn.setOnClickListener(new View.OnClickListener() { //button addStore button implement
            @Override
            public void onClick(View v) {
                //hide keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), inputManager.HIDE_NOT_ALWAYS);

                Store store = new Store(storeName.getText().toString());
                if(storeExists(store))
                    Toast.makeText(getApplicationContext(), storeName.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                else {
                    stores.add(store);
                    populateList();
                    Toast.makeText(getApplicationContext(), storeName.getText().toString()
                            + " has been added!", Toast.LENGTH_SHORT).show();

                    storeName.setText("");
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

        populateList();
    }

    public boolean storeExists(Store store) {
        String name = store.getName();
        for(int i = 0; i < stores.size(); i++) {
            if(name.compareTo(stores.get(i).getName()) == 0)
                return true;
        }

        return false;
    }

    public void populateList() {
        StoreListAdapter adapter = new StoreListAdapter();
        storeListView.setAdapter(adapter);

    }

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

}