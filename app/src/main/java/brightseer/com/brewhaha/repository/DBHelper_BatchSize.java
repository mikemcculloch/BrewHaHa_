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

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.objects.BatchSize;

public class DBHelper_BatchSize extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_BatchSize _helperInstance;
    private Context _context;
    private Dao<BatchSize, Integer> batchSize = null;

    public static DBHelper_BatchSize getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_BatchSize(context);

        return _helperInstance;
    }

    public DBHelper_BatchSize(Context context) {
        super(context, Constants.DATABASE_NAME_BatchSize, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, BatchSize.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table BatchSize", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, BatchSize.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table BatchSize onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    public void insertBatchSize(List<BatchSize> batchSizeList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_BatchSize.getInstance(_context);
            Dao<BatchSize, Integer> daoBatchSize = dbHelper.getDao(BatchSize.class);

            for (BatchSize item : batchSizeList) {
                daoBatchSize.create(item);
            }

            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BatchSize> getBatchSizeList() {
        try {
            QueryBuilder<BatchSize, Integer> queryBuilder = getBatchSizeDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.batchSize_BatchSizePk, true);
            return queryBuilder.query();
        } catch (SQLException e) {
            return null;
        }
    }

    public BatchSize getBatchSizeByPk(int selectedBatchPk) {
        try {
            QueryBuilder<BatchSize, Integer> queryBuilder = getBatchSizeDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.batchSize_BatchSizePk, selectedBatchPk);

            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return null;
        }
    }

    public Dao<BatchSize, Integer> getBatchSizeDataDao() {
        try {
            if (batchSize == null) {
                batchSize = getDao(BatchSize.class);
            }

            return batchSize;

        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return null;
    }

    public int getBatchSizeCount() {
        try {
            Dao<BatchSize, Integer> batchSize = getBatchSizeDataDao();
            List<BatchSize> list = batchSize.queryForAll();
            return list.size();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return 0;
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return 0;
        }
    }

}
