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
import brightseer.com.brewhaha.objects.HopsForm;

public class DBHelper_HopsForm extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_HopsForm _helperInstance;
    private Context _context;
    private Dao<HopsForm, Integer> hopsForm = null;

    public static DBHelper_HopsForm getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_HopsForm(context);

        return _helperInstance;
    }

    public DBHelper_HopsForm(Context context) {
        super(context, Constants.DATABASE_NAME_HopsForm, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, HopsForm.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table HopsForm", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, HopsForm.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table HopsForm onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertHopsForm(List<HopsForm> hopsFormList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_HopsForm.getInstance(_context);
            Dao<HopsForm, Integer> daoHopsForm = dbHelper.getDao(HopsForm.class);

            for (HopsForm item : hopsFormList) {
                daoHopsForm.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HopsForm> getHopsFormList() {
        List<HopsForm> results = new Vector<>();
        try {
            QueryBuilder<HopsForm, Integer> queryBuilder = getHopsFormDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.hopsForm_HopsFormPk, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Dao<HopsForm, Integer> getHopsFormDataDao() {
        try {
            if (hopsForm == null) {
                hopsForm = getDao(HopsForm.class);
            }
            return hopsForm;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HopsForm getHopsForm(String description) {
        try {
            QueryBuilder<HopsForm, Integer> queryBuilder = getHopsFormDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.hopsForm_Description, description);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HopsForm getHopsForm(int hopsFormPk) {
        try {
            QueryBuilder<HopsForm, Integer> queryBuilder = getHopsFormDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.hopsForm_HopsFormPk, hopsFormPk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getPostion(int hopsFormPk) {
        try {

            List<HopsForm> hopsFormList = getHopsFormList();
            HopsForm hopsForm = null;
            for (HopsForm item : hopsFormList) {
                if (item.getHopsFormPk() == hopsFormPk) {
                    hopsForm = item;
                    break;
                }
            }

            return hopsFormList.indexOf(hopsForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getPostion(int hopsFormPk, List<HopsForm> hopsFormList) {
        try {
            HopsForm hopsForm = null;
            for (HopsForm item : hopsFormList) {
                if (item.getHopsFormPk() == hopsFormPk) {
                    hopsForm = item;
                    break;
                }
            }

            return hopsFormList.indexOf(hopsForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getHopsFormCount() {
        try {
            Dao<HopsForm, Integer> hopsForm = getHopsFormDataDao();
            List<HopsForm> list = hopsForm.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
