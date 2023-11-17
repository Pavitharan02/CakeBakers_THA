package com.pavitharan.tha_204151x.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Item.class}, version = 1,exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    private static AppDB appDB;
    public abstract ItemDao Dao();

    public static synchronized AppDB getInstance(Context context) {
        if (appDB == null) {
            appDB = Room.databaseBuilder(context.getApplicationContext(),
                            AppDB.class, "THA_DB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return appDB;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(appDB).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(AppDB instance) {
            ItemDao dao = instance.Dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
