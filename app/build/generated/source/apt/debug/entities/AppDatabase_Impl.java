package entities;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AdminDAO _adminDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Portfolio` (`portfolio_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `portfolio_name` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Stock` (`stock_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `portfolio_id` INTEGER NOT NULL, `stock_price` REAL NOT NULL, `bought_datetime` TEXT, `sold_datetime` TEXT, `ticker` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `StockData` (`ticker` TEXT NOT NULL, PRIMARY KEY(`ticker`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'deea6b78868add28d242ae8e68b78789')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Portfolio`");
        _db.execSQL("DROP TABLE IF EXISTS `Stock`");
        _db.execSQL("DROP TABLE IF EXISTS `StockData`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPortfolio = new HashMap<String, TableInfo.Column>(2);
        _columnsPortfolio.put("portfolio_id", new TableInfo.Column("portfolio_id", "INTEGER", true, 1));
        _columnsPortfolio.put("portfolio_name", new TableInfo.Column("portfolio_name", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPortfolio = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPortfolio = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPortfolio = new TableInfo("Portfolio", _columnsPortfolio, _foreignKeysPortfolio, _indicesPortfolio);
        final TableInfo _existingPortfolio = TableInfo.read(_db, "Portfolio");
        if (! _infoPortfolio.equals(_existingPortfolio)) {
          throw new IllegalStateException("Migration didn't properly handle Portfolio(entities.Portfolio).\n"
                  + " Expected:\n" + _infoPortfolio + "\n"
                  + " Found:\n" + _existingPortfolio);
        }
        final HashMap<String, TableInfo.Column> _columnsStock = new HashMap<String, TableInfo.Column>(6);
        _columnsStock.put("stock_id", new TableInfo.Column("stock_id", "INTEGER", true, 1));
        _columnsStock.put("portfolio_id", new TableInfo.Column("portfolio_id", "INTEGER", true, 0));
        _columnsStock.put("stock_price", new TableInfo.Column("stock_price", "REAL", true, 0));
        _columnsStock.put("bought_datetime", new TableInfo.Column("bought_datetime", "TEXT", false, 0));
        _columnsStock.put("sold_datetime", new TableInfo.Column("sold_datetime", "TEXT", false, 0));
        _columnsStock.put("ticker", new TableInfo.Column("ticker", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStock = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStock = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStock = new TableInfo("Stock", _columnsStock, _foreignKeysStock, _indicesStock);
        final TableInfo _existingStock = TableInfo.read(_db, "Stock");
        if (! _infoStock.equals(_existingStock)) {
          throw new IllegalStateException("Migration didn't properly handle Stock(entities.Stock).\n"
                  + " Expected:\n" + _infoStock + "\n"
                  + " Found:\n" + _existingStock);
        }
        final HashMap<String, TableInfo.Column> _columnsStockData = new HashMap<String, TableInfo.Column>(1);
        _columnsStockData.put("ticker", new TableInfo.Column("ticker", "TEXT", true, 1));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStockData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStockData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStockData = new TableInfo("StockData", _columnsStockData, _foreignKeysStockData, _indicesStockData);
        final TableInfo _existingStockData = TableInfo.read(_db, "StockData");
        if (! _infoStockData.equals(_existingStockData)) {
          throw new IllegalStateException("Migration didn't properly handle StockData(entities.StockData).\n"
                  + " Expected:\n" + _infoStockData + "\n"
                  + " Found:\n" + _existingStockData);
        }
      }
    }, "deea6b78868add28d242ae8e68b78789", "4ea4b5cb6f3e1f50abe59abfd290c950");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Portfolio","Stock","StockData");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Portfolio`");
      _db.execSQL("DELETE FROM `Stock`");
      _db.execSQL("DELETE FROM `StockData`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public AdminDAO userDao() {
    if (_adminDAO != null) {
      return _adminDAO;
    } else {
      synchronized(this) {
        if(_adminDAO == null) {
          _adminDAO = new AdminDAO_Impl(this);
        }
        return _adminDAO;
      }
    }
  }
}
