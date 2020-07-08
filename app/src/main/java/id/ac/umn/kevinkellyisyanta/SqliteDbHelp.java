package id.ac.umn.kevinkellyisyanta;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class    SqliteDbHelp extends SQLiteOpenHelper{

    private static final int D_VERSION = 1;
    private static final String D_NAME = "Credential.db";
    private static final String TABEL_PENGGUNA = "users";
    public static final String KOL_ID = "_id";
    public static final String KOL_USER = "user_name";
    public static final String KOL_PASS = "user_password";

    SQLiteDatabase db;

    public SqliteDbHelp(Context context){
        super(context, D_NAME, null, D_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CREDENTIAL_TABLE = "CREATE TABLE " + TABEL_PENGGUNA
                + "(" + KOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KOL_USER + " TEXT NOT NULL, "
                + KOL_PASS + " TEXT NOT NULL" + ")";
        String INSERT_DATA_AWAL =
                "INSERT INTO " + TABEL_PENGGUNA + " (" +
                        KOL_ID + "," +
                        KOL_USER + "," +
                        KOL_PASS +
                ") VALUES " +
                "('1', 'user', 'useruser')";
        db.execSQL(CREATE_CREDENTIAL_TABLE);
        db.execSQL(INSERT_DATA_AWAL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_PENGGUNA);
        onCreate(db);
    }

    public void onOpen(){
        super.onOpen(db);
        db = this.getWritableDatabase();
    }

    @Override
    public synchronized void close(){
        super.close();
    }

    public boolean cekPengguna(String user, String password){
        String[] columns = { KOL_ID };
        SQLiteDatabase db = getReadableDatabase();
        String selection = KOL_USER + "=?" + " and " + KOL_PASS + "=?";
        String[] selectionArgs = { user, password};
        Cursor cursor = db.query(TABEL_PENGGUNA, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0){
            return true;
        }
        else {
            return false;
        }
    }
}
