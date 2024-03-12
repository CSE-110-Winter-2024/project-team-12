package edu.ucsd.cse110.successorator;

import android.app.Application;

import edu.ucsd.cse110.successorator.data.db.RoomTaskRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.ucsd.cse110.successorator.data.db.*;

import edu.ucsd.cse110.successorator.lib.domain.SimpleTaskRepository;


public class SuccessoratorApplication extends Application {
    private InMemoryDataSource dataSource;
    private TaskRepository taskRepository;
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tasks ADD COLUMN date INTEGER NOT NULL DEFAULT 0");
        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE tasks_temp (" +
                    "id INTEGER PRIMARY KEY," +
                    "text TEXT," +
                    "is_done INTEGER NOT NULL," +
                    "sort_order INTEGER NOT NULL," +
                    "date INTEGER," +
                    "tag INTEGER NOT NULL);");

            database.execSQL("INSERT INTO tasks_temp (id, text, is_done, sort_order, date, tag)" +
                    "SELECT id, text, is_done, sort_order, date, tag FROM tasks");

            database.execSQL("DROP TABLE tasks");
            database.execSQL("ALTER TABLE tasks_temp RENAME TO tasks");
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();

        var database = Room.databaseBuilder(
                        getApplicationContext(),
                        SuccessoratorDatabase.class,
                        "successorator-database1"
                )
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .build();
        this.taskRepository=new RoomTaskRepository((database.taskDao()));

        var sharedPreferences=getSharedPreferences("successorator",MODE_PRIVATE);
        var isFirstRun=sharedPreferences.getBoolean("isFirstRun",true);

        if(isFirstRun && database.taskDao().count() ==0) {
            //taskRepository.save(InMemoryDataSource.DEFAULT_TASKS);
            sharedPreferences.edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
