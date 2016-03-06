package com.dictionary.hckhanh.pearsondictionary;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public final class DictDaoGenerator {
  private static final int DB_VERSION = 1;
  private static final String PACKAGE_NAME = "com.dictionary.hckhanh.pearsondictionary.history.dao";
  public static final String APP_SRC_MAIN = "app/src/main/java";

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
