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

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.objects.Hops;

public class DBHelper_Hops extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_Hops _helperInstance;
    private Context _context;
    private Dao<Hops, Integer> hops = null;

    public static DBHelper_Hops getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_Hops(context);

        return _helperInstance;
    }

    public DBHelper_Hops(Context context) {
        super(context, Constants.DATABASE_NAME_Hops, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Hops.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table Hops", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Hops.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table Hops onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertHops(List<Hops> hopsList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_Hops.getInstance(_context);
            Dao<Hops, Integer> daoHops = dbHelper.getDao(Hops.class);

            for (Hops item : hopsList) {
                daoHops.create(item);
            }

            dbHelper.close();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public List<Hops> getHopsList() {
        List<Hops> hopsList = new Vector<>();
        try {
            QueryBuilder<Hops, Integer> queryBuilder = getHopsDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.hops_Name, true);

            List<Hops> results = queryBuilder.query();

            for (Hops item : results) {
                hopsList.add(item);
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return hopsList;
    }

    public Hops getHopsByPk(int selectedPk) {
        try {
            QueryBuilder<Hops, Integer> queryBuilder = getHopsDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.hops_HopsPk, selectedPk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return null;
        }
    }

    public Dao<Hops, Integer> getHopsDataDao() {
        try {
            if (hops == null) {
                hops = getDao(Hops.class);
            }

            return hops;

        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return null;
    }

    public int getHopsCount() {
        try {
            Dao<Hops, Integer> hops = getHopsDataDao();
            List<Hops> list = hops.queryForAll();
            return list.size();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return 0;
    }

    public Hops getHopsByName(String name) {
        try {
            QueryBuilder<Hops, Integer> queryBuilder = getHopsDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.hops_Name, name);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return null;
    }
}
