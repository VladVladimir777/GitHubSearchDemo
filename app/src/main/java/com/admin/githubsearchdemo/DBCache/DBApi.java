package com.admin.githubsearchdemo.DBCache;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.admin.githubsearchdemo.GitHubPojo.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class DBApi extends RoomDatabase {

    public abstract ItemDao itemDao();

    private static DBApi dbApi;

    public static DBApi getInstance(Context context) {
        if (dbApi == null) {
            dbApi = Room.databaseBuilder(context.getApplicationContext(), DBApi.class, "gitHubCache.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return dbApi;
    }

    public void destroyInstance() {
        dbApi = null;
    }

}
