//
//   Copyright (C) 2012 Yasuhiro ABE <yasu@yasundial.org>
//   
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//   
//        http://www.apache.org/licenses/LICENSE-2.0
//   
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//
package net.yadiary.android.sample.yamaneko;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_FILENAME = "cache";
	private static final int DATABASE_VERSION = 1;
	
	public static final String DATABASE_TABLENAME = "cache";
	
	public DBHelper(Context context) {
		super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
		CommonUtils.logit("called");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CommonUtils.logit("called");
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ");
		sql.append(DATABASE_TABLENAME);
		sql.append(" (");
		sql.append("_id INTEGER not null primary key,");
		sql.append("qid INTEGER not null,");
		sql.append("codep TEXT,");
		sql.append("codes TEXT,");
		sql.append("p TEXT,");
		sql.append("c TEXT,");
		sql.append("s TEXT");
		sql.append(");");
		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
