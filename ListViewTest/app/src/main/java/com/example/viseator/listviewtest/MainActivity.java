package com.example.viseator.listviewtest;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Name> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Adap adapter = new Adap(MainActivity.this, R.layout.name, list);
        ListView listView = (ListView) findViewById(R.id.List_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Name name = list.get(position);
                Toast.makeText(MainActivity.this, name.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        Name one = new Name("ONE", R.drawable.avatar);
        Name two = new Name("TWO", R.drawable.back);
        Name three= new Name("Three", R.drawable.cancel);
        Name four= new Name("Four", R.drawable.dislike);
        Name five= new Name("Five", R.drawable.download);
        Name six= new Name("Six", R.drawable.hand);
        Name seven= new Name("Seven", R.drawable.reload);
        Name eight= new Name("Eight", R.drawable.dislike);
        Name nine= new Name("Nine", R.drawable.settings);
        Name ten= new Name("Ten", R.drawable.next);
        Name eleven= new Name("Eleven", R.drawable.garbage);

        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);
        list.add(five);
        list.add(six);
        list.add(seven);
        list.add(eight);
        list.add(nine);
        list.add(ten);
        list.add(eleven);
    }

}
