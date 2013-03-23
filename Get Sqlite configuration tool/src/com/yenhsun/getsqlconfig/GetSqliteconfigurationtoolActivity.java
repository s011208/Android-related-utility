
package com.yenhsun.getsqlconfig;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.TextView;

public class GetSqliteconfigurationtoolActivity extends Activity {
    private TextView mShowSettingsView;

    private TestDatabase mTestDatabase;

    private String TABLE = "test_table";

    private ArrayList<String> mDBSetting;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTestDatabase = new TestDatabase(this);
        mShowSettingsView = (TextView)findViewById(R.id.txt);
        StringBuilder settings = new StringBuilder();
        mDBSetting = mTestDatabase.showDBSetting();
        for (int i = 0; i < mDBSetting.size(); i++) {
            settings.append(mDBSetting.get(i) + "\n");
        }

        mShowSettingsView.setText("" + settings.toString());
    }

    class TestDatabase extends SQLiteOpenHelper {
        private static final String DB_NAME = "test.db";

        private static final int DB_VERSION = 1;

        private SQLiteDatabase mDB;

        public TestDatabase(Context c) {
            super(c, DB_NAME, null, DB_VERSION);
            mDB = this.getWritableDatabase();
            showDBSetting();
            createTable();
        }

        ArrayList<String> initDBSetting() {
            ArrayList<String> rtn = new ArrayList<String>();
            rtn.add("journal_mode");
            rtn.add("synchronous");
            rtn.add("wal_checkpoint");
            rtn.add("wal_autocheckpoint");
            rtn.add("auto_vacuum");
            rtn.add("automatic_index");
            rtn.add("cache_size");
            rtn.add("checkpoint_fullfsync ");
            rtn.add("compile_options");
            rtn.add("count_changes");
            rtn.add("default_cache_size");
            rtn.add("encoding");
            rtn.add("foreign_keys");
            rtn.add("freelist_count");
            rtn.add("fullfsync");
            rtn.add("ignore_check_constraints ");
            rtn.add("integrity_check ");
            rtn.add("journal_size_limit ");
            rtn.add("legacy_file_format ");
            rtn.add("locking_mode ");
            rtn.add("max_page_count ");
            rtn.add("page_count ");
            rtn.add("page_size ");
            rtn.add("quick_check ");
            rtn.add("read_uncommitted ");
            rtn.add("recursive_triggers ");
            rtn.add("reverse_unordered_selects ");
            rtn.add("schema_version ");
            rtn.add("secure_delete ");
            rtn.add("shrink_memory ");
            rtn.add("temp_store ");
            rtn.add("temp_store_directory ");
            return rtn;
        }

        ArrayList<String> showDBSetting() {
            ArrayList<String> DBSettings = initDBSetting();
            ArrayList<String> rtn = new ArrayList<String>();
            for (int i = 0; i < DBSettings.size(); i++) {
                try {
                    String s = DatabaseUtils.stringForQuery(mDB, "PRAGMA " + DBSettings.get(i),
                            null);
                    rtn.add("-DBSetting-\t\t" + DBSettings.get(i) + " :\t\t" + s);
                } catch (Exception e) {
                }
            }
            return rtn;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

        }

        void dropTable() {
            mDB.execSQL("DROP TABLE IF EXISTS " + TABLE);
        }

        void createTable() {
            mDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (a INTEGER, b INTEGER, c TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }

    }

}
