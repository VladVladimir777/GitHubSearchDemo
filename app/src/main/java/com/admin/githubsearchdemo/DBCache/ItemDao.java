package com.admin.githubsearchdemo.DBCache;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.admin.githubsearchdemo.GitHubPojo.Item;
import java.util.List;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ItemDao {

    @Insert(onConflict = REPLACE)
    void insertDataCache(List<Item> items);

    @Query("DELETE FROM cacheItems")
    void clearCache();

    @Query("SELECT * FROM cacheItems")
    List<Item> getDataCache();

}
