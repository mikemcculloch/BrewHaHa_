package brightseer.com.brewhaha.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.objects.InstructionSelected;

public class DBHelper_InstructionSelected extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_InstructionSelected _helperInstance;
    private Context _context;
    private Dao<InstructionSelected, Integer> instructionSelected = null;

    public static DBHelper_InstructionSelected getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_InstructionSelected(context);

        return _helperInstance;
    }

    public DBHelper_InstructionSelected(Context context) {
        super(context, Constants.DATABASE_NAME_InstructionSelected, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, InstructionSelected.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table InstructionSelected", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, InstructionSelected.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table InstructionSelected onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertInstructionSelectedList(List<InstructionSelected> instructionSelectedList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_InstructionSelected.getInstance(_context);
            Dao<InstructionSelected, Integer> daoInstructionSelected = dbHelper.getDao(InstructionSelected.class);
            for (InstructionSelected item : instructionSelectedList) {
                daoInstructionSelected.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertInstructionSelected(InstructionSelected instructionSelected) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_InstructionSelected.getInstance(_context);
            Dao<InstructionSelected, Integer> daoInstructionSelected = dbHelper.getDao(InstructionSelected.class);
            daoInstructionSelected.create(instructionSelected);
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<InstructionSelected> getInstructionSelectedByContentItemPk(int contentItemPk, String userToken) {
        try {
            QueryBuilder<InstructionSelected, Integer> queryBuilder = getInstructionSelectedDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.instructionSelected_ContentItemPk, contentItemPk);
            queryBuilder.where().eq(Constants.instructionSelected_UserToken, userToken);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Dao<InstructionSelected, Integer> getInstructionSelectedDataDao() {
        try {
            if (instructionSelected == null) {
                instructionSelected = getDao(InstructionSelected.class);
            }
            return instructionSelected;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void deleteInstructionSelectedRecord(int contentItemPk, int instructionsId) {
        try {
            Dao<InstructionSelected, Integer> dao = getInstructionSelectedDataDao();
            DeleteBuilder<InstructionSelected, Integer> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq(Constants.instructionSelected_ContentItemPk, contentItemPk).and().eq(Constants.InstructionSelected_InstructionsId, instructionsId);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
