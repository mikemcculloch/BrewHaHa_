package brightseer.com.brewhaha.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.models.IngredientSelected;

public class DBHelper_IngredientSelected extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_IngredientSelected _helperInstance;
    private Context _context;
    private Dao<IngredientSelected, Integer> ingredientSelected = null;

    public static DBHelper_IngredientSelected getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_IngredientSelected(context);

        return _helperInstance;
    }

    public DBHelper_IngredientSelected(Context context) {
        super(context, Constants.DATABASE_NAME_IngredientSelected, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, IngredientSelected.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table IngredientSelected", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, IngredientSelected.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table IngredientSelected onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
//    public void insertIngredientSelectedList(List<IngredientSelected> ingredientSelectedList) {
//        try {
//            OrmLiteSqliteOpenHelper dbHelper = DBHelper_IngredientSelected.getInstance(_context);
//            Dao<IngredientSelected, Integer> daoIngredientSelected = dbHelper.getDao(IngredientSelected.class);
//            for (IngredientSelected item : ingredientSelectedList) {
//                daoIngredientSelected.create(item);
//            }
//            dbHelper.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            if (BuildConfig.DEBUG) {
//                Log.e(Constants.LOG, ex.getMessage());
//            }
//        }
//    }

    public void insertIngredientSelected(IngredientSelected ingredientSelected) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_IngredientSelected.getInstance(_context);
            Dao<IngredientSelected, Integer> daoIngredientSelected = dbHelper.getDao(IngredientSelected.class);

            daoIngredientSelected.create(ingredientSelected);

            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

//    public List<IngredientSelected> getIngredientSelectedByContentItemPk(int contentItemPk, String userToken) {
//        try {
//            QueryBuilder<IngredientSelected, Integer> queryBuilder = getIngredientSelectedDataDao().queryBuilder();
//            queryBuilder.where().eq(Constants.ingredientSelected_FeedKey, contentItemPk);
//            queryBuilder.where().eq(Constants.ingredientSelected_UserKey, userToken);
//            return queryBuilder.query();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        } catch (Exception ex) {
//            if (BuildConfig.DEBUG) {
//                Log.e(Constants.LOG, ex.getMessage());
//            }
//            return null;
//        }
//    }

    public Boolean isSelected(String FeedKey, String UserKey, String Key) {
        try {
            QueryBuilder<IngredientSelected, Integer> queryBuilder = getIngredientSelectedDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.ingredientSelected_FeedKey, FeedKey);
            queryBuilder.where().eq(Constants.ingredientSelected_UserKey, UserKey);
            queryBuilder.where().eq(Constants.ingredientSelected_Key, Key);

            return !queryBuilder.query().isEmpty();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            return false;
        }
    }


    private Dao<IngredientSelected, Integer> getIngredientSelectedDataDao() {
        try {
            if (ingredientSelected == null) {
                ingredientSelected = getDao(IngredientSelected.class);
            }

            return ingredientSelected;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
        return null;
    }

    public void deleteIngredientSelectedRecord(String key) {
        try {
            Dao<IngredientSelected, Integer> dao = getIngredientSelectedDataDao();
            DeleteBuilder<IngredientSelected, Integer> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq(Constants.ingredientSelected_Key, key);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

}
