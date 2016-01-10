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
import brightseer.com.brewhaha.objects.Country;

public class DBHelper_Country extends OrmLiteSqliteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static DBHelper_Country _helperInstance;
    private Context _context;
    private Dao<Country, Integer> country = null;

    public static DBHelper_Country getInstance(Context context) {
        if (_helperInstance == null)
            _helperInstance = new DBHelper_Country(context);

        return _helperInstance;
    }

    public DBHelper_Country(Context context) {
        super(context, Constants.DATABASE_NAME_Country, null, Constants.DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Country.class);
        } catch (Exception e) {
            Log.e(TAG, "could not create table Country", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Country.class, false);
        } catch (SQLException e) {
            Log.e(TAG, "could not drop table Country onUpgrade", e);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        _helperInstance = null;
    }

    ///INSERT
    public void insertCountry(List<Country> countryList) {
        try {
            OrmLiteSqliteOpenHelper dbHelper = DBHelper_Country.getInstance(_context);
            Dao<Country, Integer> daoCountry = dbHelper.getDao(Country.class);

            for (Country item : countryList) {
                daoCountry.create(item);
            }
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Country> getCountryList() {
        List<Country> results = new Vector<>();
        try {
            QueryBuilder<Country, Integer> queryBuilder = getCountryDataDao().queryBuilder();
            queryBuilder.orderBy(Constants.country_CountryPk, true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Dao<Country, Integer> getCountryDataDao() {
        try {
            if (country == null) {
                country = getDao(Country.class);
            }
            return country;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    public Country getCountry(String description) {
//        try {
//            QueryBuilder<Country, Integer> queryBuilder = getCountryDataDao().queryBuilder();
//            queryBuilder.where().eq(Constants.country_Description, description);
//            return queryBuilder.query().get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    public Country getCountry(int countryPk) {
        try {
            QueryBuilder<Country, Integer> queryBuilder = getCountryDataDao().queryBuilder();
            queryBuilder.where().eq(Constants.country_CountryPk, countryPk);
            return queryBuilder.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getPostion(int countryPk) {
        try {

            List<Country> countryList = getCountryList();
            Country country = null;
            for (Country item : countryList) {
                if (item.getCountryPk() == countryPk) {
                    country = item;
                    break;
                }
            }

            return countryList.indexOf(country);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getPostion(int countryPk, List<Country> countryList) {
        try {
            Country country = null;
            for (Country item : countryList) {
                if (item.getCountryPk() == countryPk) {
                    country = item;
                    break;
                }
            }

            return countryList.indexOf(country);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getCountryCount() {
        try {
            Dao<Country, Integer> country = getCountryDataDao();
            List<Country> list = country.queryForAll();
            return list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
