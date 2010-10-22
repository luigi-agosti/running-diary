
package com.la.runners.provider;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.la.runners.R;
import com.la.runners.parser.SchemaParser;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Notifier;
import com.la.runners.util.network.NetworkService;

public class DatabaseManager extends SQLiteOpenHelper {
    
    private Context context;
    private SchemaParser schema;
    
    public DatabaseManager(Context context) {
        super(context, Model.Database.NAME, null, Model.Database.VERSION);
        this.context = context;
    }
    
    private SchemaParser getSchema() {
        if(schema == null) {
            schema = NetworkService.getSchema(context);
        }
        return schema;
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
        exec(db, getSchema().getCreateStms());
    }

    private void drop(SQLiteDatabase db) {
        if (AppLogger.isInfoEnabled()) {
            AppLogger.info("Dropping the database");
        }
        exec(db, getSchema().getDropStms());
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
                Notifier.toastMessage(context, R.string.error_11);
            }
        }
    }

}
