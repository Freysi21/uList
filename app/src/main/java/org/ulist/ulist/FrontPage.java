package org.ulist.ulist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FrontPage extends ActionBarActivity {

// Array of options --> ArrayAdapter --> ListView
// List view: {views; da_items.xml}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        String[] vals = {"Ómar", "Pétur", "kalli"};
        ListView lv = (ListView)findViewById(R.id.storeListView);


    }

}