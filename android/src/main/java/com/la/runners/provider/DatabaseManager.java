
package com.la.runners.provider;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.la.runners.parser.SchemaParser;
import com.la.runners.util.AppLogger;

/**
 * Database Manager responsible to create and upgrade the database.
 * 
 * @author luigi.agosti
 */
public class DatabaseManager extends SQLiteOpenHelper {
	
    private SchemaParser schema;
    
    public DatabaseManager(Context context, SchemaParser schema) {
        super(context, Model.Database.NAME, null, Model.Database.VERSION);
        this.schema = schema;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (AppLogger.isInfoEnabled()) {
            AppLogger.info("Upgrade of the database");
        }
        drop(db);
        onCreate(db);
    }

    private void create(SQLiteDatabase db) {
        if (AppLogger.isInfoEnabled()) {
            AppLogger.info("Creating the database");
        }
        exec(db, schema.getCreateStms());
    }

    private void drop(SQLiteDatabase db) {
        if (AppLogger.isInfoEnabled()) {
            AppLogger.info("Dropping the database");
        }
        exec(db, schema.getDropStms());
    }

    private final void exec(SQLiteDatabase db, List<String> staments) {
        for (String stm : staments) {
            if (AppLogger.isDebugEnabled()) {
                AppLogger.debug(stm);
            }
            try {
                db.execSQL(stm);
            } catch (RuntimeException re) {
                AppLogger.error(re);
            }
        }
    }

}
