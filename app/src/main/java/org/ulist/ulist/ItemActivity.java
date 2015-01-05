package org.ulist.ulist;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ItemActivity extends ActionBarActivity {
    EditText itemName;
    List<Item> items = new ArrayList<Item>();
    ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        itemName = (EditText) findViewById(R.id.inputAddItem);
        itemListView = (ListView) findViewById(R.id.itemListView);

        final Button itemAddBtn = (Button) findViewById(R.id.btnAddItem);
        itemAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), inputManager.HIDE_NOT_ALWAYS);

                Item item = new Item(itemName.getText().toString());
                items.add(item);
                populateList();
                Toast.makeText(getApplicationContext(), itemName.getText().toString()
                        + " has been added!", Toast.LENGTH_SHORT).show();
                itemName.setText("");
            }
        });

        itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemAddBtn.setEnabled(String.valueOf(itemName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        populateList();
    }

    public void populateList() {
        ItemListAdapter adapter = new ItemListAdapter();
        itemListView.setAdapter(adapter);
    }

    private class ItemListAdapter extends ArrayAdapter<Item> {
        public ItemListAdapter() {
            super(ItemActivity.this, R.layout.item_view, items);
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            if(view == null)
                view = getLayoutInflater().inflate(R.layout.item_view, parent, false);

            Item currItem = items.get(pos);

            TextView name = (TextView) view.findViewById(R.id.itemName);
            name.setText(currItem.getName());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
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
}
