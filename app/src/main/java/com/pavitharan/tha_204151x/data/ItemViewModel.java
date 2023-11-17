package com.pavitharan.tha_204151x.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository repository;
    private LiveData<List<Item>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        repository = new ItemRepository(application);
        allItems = repository.getAllItems();
    }

    public void insert(Item model) {
        repository.insert(model);
    }

    public void update(Item model){
        repository.update(model);
    }
    public void delete(Item model){
        repository.delete(model);
    }
    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }
}
