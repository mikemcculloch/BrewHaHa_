package brightseer.com.brewhaha.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.objects.Laboratory;

public class DBHelper_Laboratory extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_Laboratory _helperInstance;
    private Context _context;
    private Dao<Laboratory, Integer> laboratory = null;

    public static DBHelper_Laboratory getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_Laboratory(context);

        return _helperInstance;
    }

    public DBHelper_Laboratory(Context context) {
        super(context, Constants.DATABASE_NAME_Laboratory, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Laboratory.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table Laboratory", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Laboratory.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table Laboratory onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertLaboratory(List<Laboratory> laboratoryList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_Laboratory.getInstance(_context);
            Dao<Laboratory, Integer> daoLaboratory = dbHelper.getDao(Laboratory.class);

            for (Laboratory item : laboratoryList) {
                daoLaboratory.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Laboratory> getLaboratoryList() {
        List<Laboratory> results = new Vector<>();
        try {
            QueryBuilder<Laboratory, Integer> queryBuilder = getLaboratoryDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.laboratory_LaboratoryPk, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Dao<Laboratory, Integer> getLaboratoryDataDao() {
        try {
            if (laboratory == null) {
                laboratory = getDao(Laboratory.class);
            }
            return laboratory;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Laboratory getLaboratory(int laboratoryPk) {
        try {
            QueryBuilder<Laboratory, Integer> queryBuilder = getLaboratoryDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.laboratory_LaboratoryPk, laboratoryPk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getPostion(int laboratoryPk) {
        try {

            List<Laboratory> laboratoryList = getLaboratoryList();
            Laboratory laboratory = null;
            for (Laboratory item : laboratoryList) {
                if (item.getLaboratoryPk() == laboratoryPk) {
                    laboratory = item;
                    break;
                }
            }

            return laboratoryList.indexOf(laboratory);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getPostion(int laboratoryPk, List<Laboratory> laboratoryList) {
        try {
            Laboratory laboratory = null;
            for (Laboratory item : laboratoryList) {
                if (item.getLaboratoryPk() == laboratoryPk) {
                    laboratory = item;
                    break;
                }
            }

            return laboratoryList.indexOf(laboratory);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getLaboratoryCount() {
        try {
            Dao<Laboratory, Integer> laboratory = getLaboratoryDataDao();
            List<Laboratory> list = laboratory.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
