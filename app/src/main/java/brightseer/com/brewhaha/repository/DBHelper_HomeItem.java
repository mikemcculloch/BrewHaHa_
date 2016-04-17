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

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.models.MainFeedItem;

public class DBHelper_HomeItem extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_HomeItem _helperInstance;
    private Context _context;
    private Dao<MainFeedItem, Integer> homeItem = null;

    public static DBHelper_HomeItem getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_HomeItem(context);

        return _helperInstance;
    }

    public DBHelper_HomeItem(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, MainFeedItem.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table HomeItemM", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, MainFeedItem.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table HomeItemM onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertHomeItemM(List<MainFeedItem> mainFeedItemList) throws SQLException {
        OrmLiteSqliteOpenHelper dbHelper = DBHelper_HomeItem.getInstance(_context);
        Dao<MainFeedItem, Integer> daoHomeItem = dbHelper.getDao(MainFeedItem.class);
        for (MainFeedItem item : mainFeedItemList) {
            daoHomeItem.create(item);
        }
        dbHelper.close();
    }

    public List<MainFeedItem> getHomeItemList() {
        try {
            QueryBuilder<MainFeedItem, Integer> queryBuilder = getHomeItemDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.field_ContentItemPk, false);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Dao<MainFeedItem, Integer> getHomeItemDataDao() throws SQLException {
        if (homeItem == null) {
            homeItem = getDao(MainFeedItem.class);
        }
        return homeItem;
    }

    public int getHomeItemCount() throws SQLException {
        Dao<MainFeedItem, Integer> homeItem = getHomeItemDataDao();
        List<MainFeedItem> list = homeItem.queryForAll();
        return list.size();
    }

}
