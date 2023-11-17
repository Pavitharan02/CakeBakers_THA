package com.pavitharan.tha_204151x.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;

    public ItemRepository(Application application) {
        AppDB database = AppDB.getInstance(application);
        itemDao = database.Dao();
        allItems = itemDao.getAllItems();
    }

    public void insert(Item model) {new InsertItem(itemDao).execute(model);}

    public void update(Item itemInfo) {
        new UpdateItemInfo(itemDao).execute(itemInfo);
    }
    public void delete(Item itemInfo) {
        new DeleteItem(itemDao).execute(itemInfo);
    }
    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    private static class InsertItem extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;
        private InsertItem(ItemDao itemInfoDao) {
            this.itemDao = itemInfoDao;
        }

        @Override
        protected Void doInBackground(Item... itemInfos) {
            itemDao.addItem(itemInfos[0]);
            return null;
        }
    }

    private static class UpdateItemInfo extends AsyncTask<Item, Void, Void> {
        private ItemDao dao;
        private UpdateItemInfo(ItemDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Item... itemInfos) {
            dao.updateItem(itemInfos[0]);
            return null;
        }
    }

    private static class DeleteItem extends AsyncTask<Item, Void, Void> {
        private ItemDao dao;
        private DeleteItem(ItemDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Item... itemInfos) {
            dao.deleteItem(itemInfos[0]);
            return null;
        }
    }


}
