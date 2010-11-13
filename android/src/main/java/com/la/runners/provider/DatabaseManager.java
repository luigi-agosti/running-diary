
package com.la.runners.provider;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.la.runners.R;
import com.la.runners.Runners;
import com.la.runners.parser.SchemaParser;
import com.la.runners.provider.SyncProvider.Sync;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Constants;
import com.la.runners.util.Notifier;

public class DatabaseManager extends SQLiteOpenHelper {
    
    private Context context;
    private SchemaParser schema;
    
    public DatabaseManager(Context context) {
        super(context, Model.Database.NAME, null, Model.Database.VERSION);
        this.context = context;
    }
    
    private SchemaParser getSchema() {
        if(schema == null) {
            schema = new SchemaParser(Runners.getHttpManager(context).getUrlAsStream(Constants.Server.SCHEMA_URL, context));
        }
        return schema;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AppLogger.info("Upgrade of the database");
        drop(db);
        onCreate(db);
    }

    private void create(SQLiteDatabase db) {
        AppLogger.info("Creating the database");
        List<String> stms = getSchema().getCreateStms();
        stms.add(Sync.CREATE_STM);
        exec(db, getSchema().getCreateStms());
    }

    private void drop(SQLiteDatabase db) {
        AppLogger.info("Dropping the database");
        List<String> stms = getSchema().getDropStms();
        stms.add(Sync.DROP_STM);
        exec(db, stms);
    }

    private final void exec(SQLiteDatabase db, List<String> staments) {
        for (String stm : staments) {
            AppLogger.debug(stm);
            try {
                db.execSQL(stm);
            } catch (RuntimeException re) {
                AppLogger.error(re);
                Notifier.toastMessage(context, R.string.error_11);
            }
        }
    }

}
