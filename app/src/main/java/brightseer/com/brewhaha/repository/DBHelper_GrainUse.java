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
import brightseer.com.brewhaha.objects.GrainUse;

public class DBHelper_GrainUse extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_GrainUse _helperInstance;
    private Context _context;
    private Dao<GrainUse, Integer> grainUse = null;

    public static DBHelper_GrainUse getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_GrainUse(context);

        return _helperInstance;
    }

    public DBHelper_GrainUse(Context context) {
        super(context, Constants.DATABASE_NAME_GrainUse, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, GrainUse.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table GrainUse", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, GrainUse.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table GrainUse onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertGrainUse(List<GrainUse> grainUseList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_GrainUse.getInstance(_context);
            Dao<GrainUse, Integer> daoGrainUse = dbHelper.getDao(GrainUse.class);

            for (GrainUse item : grainUseList) {
                daoGrainUse.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<GrainUse> getGrainUseList() {
        List<GrainUse> results = new Vector<>();
        try {
            QueryBuilder<GrainUse, Integer> queryBuilder = getGrainUseDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.grainUse_Description, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Dao<GrainUse, Integer> getGrainUseDataDao() {
        try {
            if (grainUse == null) {
                grainUse = getDao(GrainUse.class);
            }
            return grainUse;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public GrainUse getGrainUse(String description) {
        try {
            QueryBuilder<GrainUse, Integer> queryBuilder = getGrainUseDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.grainUse_Description, description);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public GrainUse getGrainUse(int grainUsePk) {
        try {
            QueryBuilder<GrainUse, Integer> queryBuilder = getGrainUseDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.grainUse_GrainUsePk, grainUsePk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getPostion(int grainUsePk) {
        try {

            List<GrainUse> grainUseList = getGrainUseList();
            GrainUse grainUse = null;
            for (GrainUse item : grainUseList) {
                if (item.getGrainUsePk() == grainUsePk) {
                    grainUse = item;
                    break;
                }
            }

            return grainUseList.indexOf(grainUse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getPostion(int grainUsePk, List<GrainUse> grainUseList) {
        try {
            GrainUse grainUse = null;
            for (GrainUse item : grainUseList) {
                if (item.getGrainUsePk() == grainUsePk) {
                    grainUse = item;
                    break;
                }
            }

            return grainUseList.indexOf(grainUse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getGrainUseCount() {
        try {
            Dao<GrainUse, Integer> grainUse = getGrainUseDataDao();
            List<GrainUse> list = grainUse.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
