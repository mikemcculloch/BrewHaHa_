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
import brightseer.com.brewhaha.objects.UnitOfMeasure;

public class DBHelper_UnitOfMeasure extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_UnitOfMeasure _helperInstance;
    private Context _context;
    private Dao<UnitOfMeasure, Integer> unitOfMeasure = null;

    public static DBHelper_UnitOfMeasure getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_UnitOfMeasure(context);

        return _helperInstance;
    }

    public DBHelper_UnitOfMeasure(Context context) {
        super(context, Constants.DATABASE_NAME_UnitOfMeasure, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UnitOfMeasure.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table UnitOfMeasure", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, UnitOfMeasure.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table UnitOfMeasure onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertUnitOfMeasure(List<UnitOfMeasure> unitOfMeasureList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_UnitOfMeasure.getInstance(_context);
            Dao<UnitOfMeasure, Integer> daoUnitOfMeasure = dbHelper.getDao(UnitOfMeasure.class);

            for (UnitOfMeasure item : unitOfMeasureList) {
                daoUnitOfMeasure.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UnitOfMeasure> getUnitOfMeasureList() {
        List<UnitOfMeasure> results = new Vector<>();
        try {
            QueryBuilder<UnitOfMeasure, Integer> queryBuilder = getUnitOfMeasureDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.unitOfMeasure_UnitOfMeasurePk, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<UnitOfMeasure> getUnitOfMeasureTypeList(int unitType) {
        List<UnitOfMeasure> results = new Vector<>();
        try {
            QueryBuilder<UnitOfMeasure, Integer> queryBuilder = getUnitOfMeasureDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.unitOfMeasure_Type, unitType);
            queryBuilder.orderBy(Constants.unitOfMeasure_Description, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Dao<UnitOfMeasure, Integer> getUnitOfMeasureDataDao() {
        try {
            if (unitOfMeasure == null) {
                unitOfMeasure = getDao(UnitOfMeasure.class);
            }
            return unitOfMeasure;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
//
//    public UnitOfMeasure getUnitOfMeasure(String description) {
//        try {
//            QueryBuilder<UnitOfMeasure, Integer> queryBuilder = getUnitOfMeasureDataDao().queryBuilder();
//            queryBuilder.where().eq(Constants.unitOfMeasure_Description, description);
//            return queryBuilder.query().get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    public UnitOfMeasure getUnitOfMeasure(int unitOfMeasurePk) {
//        try {
//            QueryBuilder<UnitOfMeasure, Integer> queryBuilder = getUnitOfMeasureDataDao().queryBuilder();
//            queryBuilder.where().eq(Constants.unitOfMeasure_UnitOfMeasurePk, unitOfMeasurePk);
//            return queryBuilder.query().get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    public UnitOfMeasure getUnitOfMeasureType(int unitType) {
//        try {
//            QueryBuilder<UnitOfMeasure, Integer> queryBuilder = getUnitOfMeasureDataDao().queryBuilder();
//            queryBuilder.where().eq(Constants.unitOfMeasure_Type, unitType);
//            return queryBuilder.query().get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    public int getPostion(int unitOfMeasurePk) {
        try {

            List<UnitOfMeasure> unitOfMeasureList = getUnitOfMeasureList();
            UnitOfMeasure unitOfMeasure = null;
            for (UnitOfMeasure item : unitOfMeasureList) {
                if (item.getUnitOfMeasurePk() == unitOfMeasurePk) {
                    unitOfMeasure = item;
                    break;
                }
            }

            return unitOfMeasureList.indexOf(unitOfMeasure);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getPostion(int unitOfMeasurePk, List<UnitOfMeasure> unitOfMeasureList) {
        try {
            UnitOfMeasure unitOfMeasure = null;
            for (UnitOfMeasure item : unitOfMeasureList) {
                if (item.getUnitOfMeasurePk() == unitOfMeasurePk) {
                    unitOfMeasure = item;
                    break;
                }
            }

            return unitOfMeasureList.indexOf(unitOfMeasure);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getUnitOfMeasureCount() {
        try {
            Dao<UnitOfMeasure, Integer> unitOfMeasure = getUnitOfMeasureDataDao();
            List<UnitOfMeasure> list = unitOfMeasure.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
