@Override
public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    schemaGenerator.doUpgrade(sqLiteDatabase, oldVersion, newVersion);
}