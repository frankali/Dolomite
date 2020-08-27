package entities;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import org.threeten.bp.OffsetDateTime;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AdminDAO_Impl implements AdminDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfStock;

  private final TiviTypeConverters __tiviTypeConverters = new TiviTypeConverters();

  private final EntityInsertionAdapter __insertionAdapterOfPortfolio;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPortfolio;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfStock;

  private final SharedSQLiteStatement __preparedStmtOfSetStockPriceByStockId;

  private final SharedSQLiteStatement __preparedStmtOfRenamePortfolio;

  public AdminDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStock = new EntityInsertionAdapter<Stock>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Stock`(`stock_id`,`portfolio_id`,`stock_price`,`bought_datetime`,`sold_datetime`,`ticker`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Stock value) {
        stmt.bindLong(1, value.stock_id);
        stmt.bindLong(2, value.portfolio_id);
        stmt.bindDouble(3, value.stock_price);
        final String _tmp;
        _tmp = __tiviTypeConverters.fromOffsetDateTime(value.bought_datetime);
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = __tiviTypeConverters.fromOffsetDateTime(value.sold_datetime);
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp_1);
        }
        if (value.ticker == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.ticker);
        }
      }
    };
    this.__insertionAdapterOfPortfolio = new EntityInsertionAdapter<Portfolio>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Portfolio`(`portfolio_id`,`portfolio_name`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Portfolio value) {
        stmt.bindLong(1, value.portfolio_id);
        if (value.portfolio_name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.portfolio_name);
        }
      }
    };
    this.__deletionAdapterOfPortfolio = new EntityDeletionOrUpdateAdapter<Portfolio>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Portfolio` WHERE `portfolio_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Portfolio value) {
        stmt.bindLong(1, value.portfolio_id);
      }
    };
    this.__deletionAdapterOfStock = new EntityDeletionOrUpdateAdapter<Stock>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Stock` WHERE `stock_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Stock value) {
        stmt.bindLong(1, value.stock_id);
      }
    };
    this.__preparedStmtOfSetStockPriceByStockId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "update Stock set stock_price = ? where stock_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfRenamePortfolio = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = " update Portfolio set portfolio_name = ? where portfolio_id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertStock(final Stock stock) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfStock.insert(stock);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertPortfolio(final Portfolio portfolio) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPortfolio.insert(portfolio);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePortfolio(final Portfolio portfolio) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPortfolio.handle(portfolio);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteStock(final Stock stock) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfStock.handle(stock);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void setStockPriceByStockId(final int stockId, final float stockPrice) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetStockPriceByStockId.acquire();
    int _argIndex = 1;
    _stmt.bindDouble(_argIndex, stockPrice);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, stockId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfSetStockPriceByStockId.release(_stmt);
    }
  }

  @Override
  public int renamePortfolio(final int portfolio_id, final String name) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfRenamePortfolio.acquire();
    int _argIndex = 1;
    if (name == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, name);
    }
    _argIndex = 2;
    _stmt.bindLong(_argIndex, portfolio_id);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfRenamePortfolio.release(_stmt);
    }
  }

  @Override
  public Portfolio[] loadPortfolioByPortfolioName(final String portfolio_name) {
    final String _sql = "select * from portfolio where Portfolio.portfolio_name = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (portfolio_name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, portfolio_name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfPortfolioId = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_id");
      final int _cursorIndexOfPortfolioName = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_name");
      final Portfolio[] _result = new Portfolio[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final Portfolio _item;
        final String _tmpPortfolio_name;
        _tmpPortfolio_name = _cursor.getString(_cursorIndexOfPortfolioName);
        _item = new Portfolio(_tmpPortfolio_name);
        _item.portfolio_id = _cursor.getInt(_cursorIndexOfPortfolioId);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Portfolio loadPortfolioByPortfolioId(final int portfolio_id) {
    final String _sql = "select * from portfolio where Portfolio.portfolio_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, portfolio_id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfPortfolioId = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_id");
      final int _cursorIndexOfPortfolioName = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_name");
      final Portfolio _result;
      if(_cursor.moveToFirst()) {
        final String _tmpPortfolio_name;
        _tmpPortfolio_name = _cursor.getString(_cursorIndexOfPortfolioName);
        _result = new Portfolio(_tmpPortfolio_name);
        _result.portfolio_id = _cursor.getInt(_cursorIndexOfPortfolioId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Portfolio[] loadAllPortfolios() {
    final String _sql = "select * from  Portfolio";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfPortfolioId = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_id");
      final int _cursorIndexOfPortfolioName = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_name");
      final Portfolio[] _result = new Portfolio[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final Portfolio _item;
        final String _tmpPortfolio_name;
        _tmpPortfolio_name = _cursor.getString(_cursorIndexOfPortfolioName);
        _item = new Portfolio(_tmpPortfolio_name);
        _item.portfolio_id = _cursor.getInt(_cursorIndexOfPortfolioId);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Stock[] loadStockByPortfolioId(final int portfolio_id) {
    final String _sql = "select * from Stock inner join portfolio on stock.portfolio_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, portfolio_id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfStockId = CursorUtil.getColumnIndexOrThrow(_cursor, "stock_id");
      final int _cursorIndexOfPortfolioId = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_id");
      final int _cursorIndexOfStockPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "stock_price");
      final int _cursorIndexOfBoughtDatetime = CursorUtil.getColumnIndexOrThrow(_cursor, "bought_datetime");
      final int _cursorIndexOfSoldDatetime = CursorUtil.getColumnIndexOrThrow(_cursor, "sold_datetime");
      final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
      final int _cursorIndexOfPortfolioId_1 = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_id");
      final Stock[] _result = new Stock[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final Stock _item;
        final int _tmpPortfolio_id;
        _tmpPortfolio_id = _cursor.getInt(_cursorIndexOfPortfolioId);
        final OffsetDateTime _tmpBought_datetime;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfBoughtDatetime);
        _tmpBought_datetime = __tiviTypeConverters.toOffsetDateTime(_tmp);
        final String _tmpTicker;
        _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
        final int _tmpPortfolio_id_1;
        _tmpPortfolio_id_1 = _cursor.getInt(_cursorIndexOfPortfolioId_1);
        _item = new Stock(_tmpPortfolio_id,_tmpBought_datetime,_tmpTicker);
        _item.stock_id = _cursor.getInt(_cursorIndexOfStockId);
        _item.stock_price = _cursor.getFloat(_cursorIndexOfStockPrice);
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfSoldDatetime);
        _item.sold_datetime = __tiviTypeConverters.toOffsetDateTime(_tmp_1);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Stock loadStockByStockId(final int stock_id) {
    final String _sql = "select * from Stock where stock_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, stock_id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfStockId = CursorUtil.getColumnIndexOrThrow(_cursor, "stock_id");
      final int _cursorIndexOfPortfolioId = CursorUtil.getColumnIndexOrThrow(_cursor, "portfolio_id");
      final int _cursorIndexOfStockPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "stock_price");
      final int _cursorIndexOfBoughtDatetime = CursorUtil.getColumnIndexOrThrow(_cursor, "bought_datetime");
      final int _cursorIndexOfSoldDatetime = CursorUtil.getColumnIndexOrThrow(_cursor, "sold_datetime");
      final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
      final Stock _result;
      if(_cursor.moveToFirst()) {
        final int _tmpPortfolio_id;
        _tmpPortfolio_id = _cursor.getInt(_cursorIndexOfPortfolioId);
        final OffsetDateTime _tmpBought_datetime;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfBoughtDatetime);
        _tmpBought_datetime = __tiviTypeConverters.toOffsetDateTime(_tmp);
        final String _tmpTicker;
        _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
        _result = new Stock(_tmpPortfolio_id,_tmpBought_datetime,_tmpTicker);
        _result.stock_id = _cursor.getInt(_cursorIndexOfStockId);
        _result.stock_price = _cursor.getFloat(_cursorIndexOfStockPrice);
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfSoldDatetime);
        _result.sold_datetime = __tiviTypeConverters.toOffsetDateTime(_tmp_1);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
