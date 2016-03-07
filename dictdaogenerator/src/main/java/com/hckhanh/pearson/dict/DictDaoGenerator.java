package com.hckhanh.pearson.dict;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public final class DictDaoGenerator {
  public static final String APP_SRC_MAIN = "app/src/main/java";
  private static final int DB_VERSION = 2;
  private static final String PACKAGE_NAME =
      "com.hckhanh.pearson.dict.history.dao";

  public static void main(String[] args) throws Exception {
    Schema schema = new Schema(DB_VERSION, PACKAGE_NAME);
    generateHistory(schema);

    new DaoGenerator().generateAll(schema, APP_SRC_MAIN);

    System.out.print("Dictionary Dao is generated...");
  }

  private static void generateHistory(Schema schema) {
    Entity history = schema.addEntity("History");
    history.addIdProperty().autoincrement();
    history.addStringProperty("word").notNull().unique();
  }
}
