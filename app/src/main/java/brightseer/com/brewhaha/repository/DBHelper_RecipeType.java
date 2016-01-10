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
import brightseer.com.brewhaha.objects.RecipeType;

public class DBHelper_RecipeType extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_RecipeType _helperInstance;
    private Context _context;
    private Dao<RecipeType, Integer> recipeType = null;

    public static DBHelper_RecipeType getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_RecipeType(context);

        return _helperInstance;
    }

    public DBHelper_RecipeType(Context context) {
        super(context, Constants.DATABASE_NAME_RecipeType, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RecipeType.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table RecipeType", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, RecipeType.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table RecipeType onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertRecipeType(List<RecipeType> recipeTypes) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_RecipeType.getInstance(_context);
            Dao<RecipeType, Integer> daoRecipeType = dbHelper.getDao(RecipeType.class);
            for (RecipeType item : recipeTypes) {
                daoRecipeType.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //GET ALL
    public List<RecipeType> getRecipeTypeList() {
        List<RecipeType> recipeTypeList = new Vector<>();
        try {
            QueryBuilder<RecipeType, Integer> queryBuilder = getRecipeTypeDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.recipeType_RecipeTypePk, true);
            return queryBuilder.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeTypeList;
    }

    public Dao<RecipeType, Integer> getRecipeTypeDataDao() {
        try {
            if (recipeType == null) {
                recipeType = getDao(RecipeType.class);
            }
            return recipeType;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public RecipeType getRecipeTypeByDescription(String description) {
        try {
            QueryBuilder<RecipeType, Integer> queryBuilder = getRecipeTypeDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.recipeType_Description, description);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getRecipeTypeCount() {
        try {
            Dao<RecipeType, Integer> recipeType = getRecipeTypeDataDao();
            List<RecipeType> list = recipeType.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
