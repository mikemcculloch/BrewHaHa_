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
import brightseer.com.brewhaha.objects.HopsUse;

public class DBHelper_HopUse extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_HopUse _helperInstance;
    private Context _context;
    private Dao<HopsUse, Integer> hopsUse = null;

    public static DBHelper_HopUse getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_HopUse(context);

        return _helperInstance;
    }

    public DBHelper_HopUse(Context context) {
        super(context, Constants.DATABASE_NAME_HopsUse, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, HopsUse.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table HopsUse", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, HopsUse.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table HopsUse onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertHopsUse(List<HopsUse> hopsUseList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_HopUse.getInstance(_context);
            Dao<HopsUse, Integer> daoHopsUse = dbHelper.getDao(HopsUse.class);

            for (HopsUse item : hopsUseList) {
                daoHopsUse.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HopsUse> getHopsUseList() {
        List<HopsUse> results = new Vector<>();
        try {
            QueryBuilder<HopsUse, Integer> queryBuilder = getHopsUseDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.hopsUse_Description, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Dao<HopsUse, Integer> getHopsUseDataDao() {
        try {
            if (hopsUse == null) {
                hopsUse = getDao(HopsUse.class);
            }
            return hopsUse;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HopsUse getHopsUse(String description) {
        try {
            QueryBuilder<HopsUse, Integer> queryBuilder = getHopsUseDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.hopsUse_Description, description);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HopsUse getHopsUse(int hopsUsePk) {
        try {
            QueryBuilder<HopsUse, Integer> queryBuilder = getHopsUseDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.hopsUse_HopsUsePk, hopsUsePk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getPostion(int hopsUsePk) {
        try {

            List<HopsUse> hopsUseList = getHopsUseList();
            HopsUse hopsUse = null;
            for (HopsUse item : hopsUseList) {
                if (item.getHopsUsePk() == hopsUsePk) {
                    hopsUse = item;
                    break;
                }
            }

            return hopsUseList.indexOf(hopsUse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getPostion(int hopsUsePk, List<HopsUse> hopsUseList) {
        try {
            HopsUse hopsUse = null;
            for (HopsUse item : hopsUseList) {
                if (item.getHopsUsePk() == hopsUsePk) {
                    hopsUse = item;
                    break;
                }
            }

            return hopsUseList.indexOf(hopsUse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getHopsUseCount() {
        try {
            Dao<HopsUse, Integer> hopsUse = getHopsUseDataDao();
            List<HopsUse> list = hopsUse.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
