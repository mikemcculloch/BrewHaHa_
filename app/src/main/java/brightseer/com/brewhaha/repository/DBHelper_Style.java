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
import brightseer.com.brewhaha.objects.Style;

public class DBHelper_Style extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_Style _helperInstance;
    private Context _context;
    private Dao<Style, Integer> style = null;

    public static DBHelper_Style getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_Style(context);

        return _helperInstance;
    }

    public DBHelper_Style(Context context) {
        super(context, Constants.DATABASE_NAME_Style, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Style.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table Style", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Style.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table Style onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertStyle(List<Style> styleList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_Style.getInstance(_context);
            Dao<Style, Integer> daoStyle = dbHelper.getDao(Style.class);

            for (Style item : styleList) {
                daoStyle.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Style> getStyleList() {
        List<Style> results = new Vector<>();
        try {
            QueryBuilder<Style, Integer> queryBuilder = getStyleDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.style_Description, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Dao<Style, Integer> getStyleDataDao() {
        try {
            if (style == null) {
                style = getDao(Style.class);
            }
            return style;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Style getStyle(String description) {
        try {
            QueryBuilder<Style, Integer> queryBuilder = getStyleDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.style_Description, description);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Style getStyle(int stylePk) {
        try {
            QueryBuilder<Style, Integer> queryBuilder = getStyleDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.style_StylePk, stylePk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getPostion(int stylePk) {
        try {

            List<Style> styleList = getStyleList();
            Style style = null;
            for (Style item : styleList) {
                if (item.getStylePk() == stylePk) {
                    style = item;
                    break;
                }
            }

            return styleList.indexOf(style);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getPostion(int stylePk, List<Style> styleList) {
        try {
            Style style = null;
            for (Style item : styleList) {
                if (item.getStylePk() == stylePk) {
                    style = item;
                    break;
                }
            }

            return styleList.indexOf(style);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getStyleCount() {
        try {
            Dao<Style, Integer> style = getStyleDataDao();
            List<Style> list = style.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
