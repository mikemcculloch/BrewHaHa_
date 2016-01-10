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
import brightseer.com.brewhaha.objects.Yeast;

public class DBHelper_Yeast extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_Yeast _helperInstance;
    private Context _context;
    private Dao<Yeast, Integer> yeast = null;

    public static DBHelper_Yeast getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_Yeast(context);

        return _helperInstance;
    }

    public DBHelper_Yeast(Context context) {
        super(context, Constants.DATABASE_NAME_Yeast, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Yeast.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table Yeast", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Yeast.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table Yeast onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertYeast(List<Yeast> yeastList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_Yeast.getInstance(_context);
            Dao<Yeast, Integer> daoYeast = dbHelper.getDao(Yeast.class);

            for (Yeast item : yeastList) {
                daoYeast.create(item);
            }

            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Yeast> getYeastList() {
        List<Yeast> yeastList = new Vector<>();
        try {
            QueryBuilder<Yeast, Integer> queryBuilder = getYeastDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.yeast_Name, true);

            List<Yeast> results = queryBuilder.query();

            for (Yeast item : results) {
                yeastList.add(item);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        return yeastList;
    }

    public Yeast getYeastByPk(int selectedPk) {
        Yeast yeast = new Yeast();
        try {
            QueryBuilder<Yeast, Integer> queryBuilder = getYeastDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.yeast_YeastPk, selectedPk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
//            e.printStackTrace();
            return null;
        }
    }

    public Dao<Yeast, Integer> getYeastDataDao() {
        try {
            if (yeast == null) {
                yeast = getDao(Yeast.class);
            }

            return yeast;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getYeastCount() {
        try {
            Dao<Yeast, Integer> yeast = getYeastDataDao();
            List<Yeast> list = yeast.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public Yeast getYeastByName(String name) {
        try {
            QueryBuilder<Yeast, Integer> queryBuilder = getYeastDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.yeast_Name, name);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
