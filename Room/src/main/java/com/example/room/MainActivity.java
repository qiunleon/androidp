package com.example.room;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.room.application.RoomApplication;
import com.example.room.databases.DatabaseContext;
import com.example.room.databases.SongDatabase;
import com.example.room.entities.Song;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SongDatabase db = Room.databaseBuilder(
                        new DatabaseContext(RoomApplication.getInstance()), SongDatabase.class, "local_kmbox.db").addMigrations().build();
                List<Song> list = db.userDao().loadAllByIds(new int[]{24830});
                Song song = new Song();
                song.setSongID(121);
                db.userDao().insert(song);
            }
        }).start();
    }
}
