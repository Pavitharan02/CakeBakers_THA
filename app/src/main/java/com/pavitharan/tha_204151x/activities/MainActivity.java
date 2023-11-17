package com.pavitharan.tha_204151x.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavitharan.tha_204151x.R;
import com.pavitharan.tha_204151x.adapters.ItemAdapter;
import com.pavitharan.tha_204151x.data.Item;
import com.pavitharan.tha_204151x.data.ItemViewModel;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView_items;
    ItemAdapter itemAdapter;
    FloatingActionButton floatingActionButton;
    ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.fab_add_item);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);
            }
        });

        recyclerView_items = findViewById(R.id.rv_items);
        recyclerView_items.setLayoutManager(new LinearLayoutManager(this));

        itemAdapter = new ItemAdapter(MainActivity.this);
        recyclerView_items.setAdapter(itemAdapter);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);


        itemViewModel.getAllItems().observe(this, itemInfos -> {
                    itemAdapter.submitList(itemInfos);
                }
        );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = data;
        if (i != null) {
            if (resultCode == 1) {
                Item item = new Item();
                item.name = i.getExtras().getString("Title").toString();
                item.description = i.getExtras().getString("Description").toString();
                item.price = i.getExtras().getDouble("Price");
                itemViewModel.insert(item);
                recyclerView_items.smoothScrollToPosition(itemViewModel.getAllItems().getValue().size());
            } else if (resultCode == 2) {
                Item item = new Item();
                item.setItemid(i.getExtras().getInt("ID"));
                item.setName(i.getExtras().getString("Title").toString());
                item.setDescription(i.getExtras().getString("Description").toString());
                item.setPrice(i.getExtras().getDouble("Price"));
                itemViewModel.update(item);
            } else {
                itemViewModel.delete(itemAdapter.getItemAt(i.getExtras().getInt("Position")));
            }
        }
    }

}