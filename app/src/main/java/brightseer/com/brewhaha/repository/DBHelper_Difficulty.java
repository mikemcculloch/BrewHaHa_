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
import brightseer.com.brewhaha.objects.Difficulty;

public class DBHelper_Difficulty extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_Difficulty _helperInstance;
    private Context _context;
    private Dao<Difficulty, Integer> difficulty = null;

    public static DBHelper_Difficulty getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_Difficulty(context);

        return _helperInstance;
    }

    public DBHelper_Difficulty(Context context) {
        super(context, Constants.DATABASE_NAME_Difficulty, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Difficulty.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table Difficulty", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Difficulty.class, false);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
                Log.e(TAG, "could not drop table Difficulty onUpgrade", e);
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
    public void insertDifficulty(List<Difficulty> difficultyList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_Difficulty.getInstance(_context);
            Dao<Difficulty, Integer> daoDifficulty = dbHelper.getDao(Difficulty.class);

            for (Difficulty item : difficultyList) {
                daoDifficulty.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public List<Difficulty> getDifficultyList() {
        try {
            QueryBuilder<Difficulty, Integer> queryBuilder = getDifficultyDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.difficulty_DifficultyPk, true);
            return queryBuilder.query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return null;
        }
    }

    public Dao<Difficulty, Integer> getDifficultyDataDao() {
        try {
            if (difficulty == null) {
                difficulty = getDao(Difficulty.class);
            }
            return difficulty;
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

    public Difficulty getDifficultyByDescription(String description) {
        try {
            QueryBuilder<Difficulty, Integer> queryBuilder = getDifficultyDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.difficulty_Description, description);
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

    public int getDifficultyCount() {
        try {
            Dao<Difficulty, Integer> difficulty = getDifficultyDataDao();
            List<Difficulty> list = difficulty.queryForAll();
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

}
