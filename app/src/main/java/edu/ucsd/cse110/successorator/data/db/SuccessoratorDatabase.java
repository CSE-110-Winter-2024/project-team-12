package edu.ucsd.cse110.successorator.data.db;
// imports
import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This is the database set up for the application.
 */
@Database(entities={TaskEntity.class},version=2)
public abstract class SuccessoratorDatabase extends RoomDatabase{
    public abstract TaskDao taskDao();
}
