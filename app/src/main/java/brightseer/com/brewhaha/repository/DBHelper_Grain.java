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
import brightseer.com.brewhaha.objects.Grain;

public class DBHelper_Grain extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_Grain _helperInstance;
    private Context _context;
    private Dao<Grain, Integer> grain = null;

    public static DBHelper_Grain getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_Grain(context);

        return _helperInstance;
    }

    public DBHelper_Grain(Context context) {
        super(context, Constants.DATABASE_NAME_Grain, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Grain.class);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Grain.class, false);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertGrain(List<Grain> grainList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_Grain.getInstance(_context);
            Dao<Grain, Integer> daoGrain = dbHelper.getDao(Grain.class);

            for (Grain item : grainList) {
                daoGrain.create(item);
            }

            dbHelper.close();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public List<Grain> getGrainList() {
        List<Grain> grainList = new Vector<>();
        try {
            QueryBuilder<Grain, Integer> queryBuilder = getGrainDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.grain_Name, true);
            List<Grain> results = queryBuilder.query();
            for (Grain item : results) {
                grainList.add(item);
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return grainList;
    }

    public Grain getGrain(int selectedPk) {
        try {
            QueryBuilder<Grain, Integer> queryBuilder = getGrainDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.grain_GrainPk, selectedPk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return null;
        }
    }

    public Dao<Grain, Integer> getGrainDataDao() {
        try {
            if (grain == null) {
                grain = getDao(Grain.class);
            }

            return grain;

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

    public int getGrainCount() {
        try {
            Dao<Grain, Integer> grain = getGrainDataDao();
            List<Grain> list = grain.queryForAll();
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

    public Grain getGrainByName(String name) {
        try {
            QueryBuilder<Grain, Integer> queryBuilder = getGrainDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.grain_Name, name);
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
