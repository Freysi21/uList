package org.ulist.ulist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
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
                Store store = new Store(storeName.getText().toString());
                stores.add(store);
                Toast.makeText(getApplicationContext(), storeName.getText().toString()
                        + " has been added!", Toast.LENGTH_SHORT).show();
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

    public void populateList() {
        storeListAdapter adapter = new storeListAdapter();
        storeListView.setAdapter(adapter);
    }

    private class storeListAdapter extends ArrayAdapter<Store> {
        public storeListAdapter() {
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