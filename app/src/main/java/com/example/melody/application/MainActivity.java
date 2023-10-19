package com.example.melody.application;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    String[][] mData = {
            {"AutoTest", "com.example.melody.application.AutoTest.AutoTestActivity"},
            {"Raphael", "com.example.melody.application.Raphael.RaphaelActivity"},
            {"Login", "com.example.melody.application.activity.LoginActivity"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button

        // setup listview
        listView = findViewById(R.id.listView);
        MainListAdapter adapter = new MainListAdapter(this, mData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String className = mData[position][1];
                try {
                    Class<?> clazz = Class.forName(className);
                    Intent intent = new Intent(MainActivity.this, clazz);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MainListAdapter extends BaseAdapter {
        private Context mContext;
        private String[][] mData;
        public MainListAdapter(Context context, String[][] data) {
            mContext = context;
            mData = data;
        }
        @Override
        public int getCount() {
            return mData.length;
        }
        @Override
        public Object getItem(int position) {
            return mData[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(mData[position][0]);
            return convertView;
        }
    }
}
