// Copyright (c) 2011 Cloudera, Inc. All rights reserved.

package com.cloudera.impala.catalog;

import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.MetaException;

/**
 * Internal representation of db-related metadata. Owned by Catalog instance.
 */
public class Db {
  private final String name;

  // map from table name to Table
  private final HashMap<String, Table> tables;

  private Db(String name) {
    this.name = name;
    this.tables = new HashMap<String, Table>();
  }

  public static Db loadDb(HiveMetaStoreClient client, String dbName) {
    try {
      Db db = new Db(dbName);
      List<String> tblNames = null;
      tblNames = client.getTables(dbName, "*");
      for (String tblName : tblNames) {
        Table table = Table.loadTable(client, dbName, tblName);
        if (table == null) {
          return null;
        }
        db.tables.put(tblName, table);
      }
      return db;
    } catch (MetaException e) {
      // turn into unchecked exception
      throw new UnsupportedOperationException(e.toString());
    }
  }

  public String getName() {
    return name;
  }

  public HashMap<String, Table> getTables() {
    return tables;
  }

  /**
   * Case-insensitive lookup
   */
  public Table getTable(String tbl) {
    return tables.get(tbl.toLowerCase());
  }
}
