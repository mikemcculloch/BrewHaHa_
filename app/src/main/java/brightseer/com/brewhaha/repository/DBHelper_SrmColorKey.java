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
import brightseer.com.brewhaha.objects.SrmColorKey;

public class DBHelper_SrmColorKey extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_SrmColorKey _helperInstance;
    private Context _context;
    private Dao<SrmColorKey, Integer> srmColorKey = null;

    public static DBHelper_SrmColorKey getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_SrmColorKey(context);

        return _helperInstance;
    }

    public DBHelper_SrmColorKey(Context context) {
        super(context, Constants.DATABASE_NAME_SrmColorKey, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, SrmColorKey.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table SrmColorKey", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, SrmColorKey.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table SrmColorKey onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertSrmColorKey(List<SrmColorKey> srmColorKeyList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_SrmColorKey.getInstance(_context);
            Dao<SrmColorKey, Integer> daoSrmColorKey = dbHelper.getDao(SrmColorKey.class);

            for (SrmColorKey item : srmColorKeyList) {
                daoSrmColorKey.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SrmColorKey> getSrmColorKeyList() {
        List<SrmColorKey> results = new Vector<>();
        try {
            QueryBuilder<SrmColorKey, Integer> queryBuilder = getSrmColorKeyDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.srmColorKey_SrmColorKeyPk, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Dao<SrmColorKey, Integer> getSrmColorKeyDataDao() {
        try {
            if (srmColorKey == null) {
                srmColorKey = getDao(SrmColorKey.class);
            }
            return srmColorKey;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    public SrmColorKey getSrmColorKey(String description) {
//        try {
//            QueryBuilder<SrmColorKey, Integer> queryBuilder = getSrmColorKeyDataDao().queryBuilder();
//            queryBuilder.where().eq(Constants.srmColorKey_ColorSrm, description);
//            return queryBuilder.query().get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    public SrmColorKey getSrmColorKey(int srmColorKeyPk) {
//        try {
//            QueryBuilder<SrmColorKey, Integer> queryBuilder = getSrmColorKeyDataDao().queryBuilder();
//            queryBuilder.where().eq(Constants.srmColorKey_SrmColorKeyPk, srmColorKeyPk);
//            return queryBuilder.query().get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    public int getPostion(int srmColorKeyPk) {
        try {

            List<SrmColorKey> srmColorKeyList = getSrmColorKeyList();
            SrmColorKey srmColorKey = null;
            for (SrmColorKey item : srmColorKeyList) {
                if (item.getSrmColorKeyPk() == srmColorKeyPk) {
                    srmColorKey = item;
                    break;
                }
            }

            return srmColorKeyList.indexOf(srmColorKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getPostion(int srmColorKeyPk, List<SrmColorKey> srmColorKeyList) {
        try {
            SrmColorKey srmColorKey = null;
            for (SrmColorKey item : srmColorKeyList) {
                if (item.getSrmColorKeyPk() == srmColorKeyPk) {
                    srmColorKey = item;
                    break;
                }
            }

            return srmColorKeyList.indexOf(srmColorKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getSrmColorKeyCount() {
        try {
            Dao<SrmColorKey, Integer> srmColorKey = getSrmColorKeyDataDao();
            List<SrmColorKey> list = srmColorKey.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
