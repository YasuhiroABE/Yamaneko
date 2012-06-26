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

import net.yadiary.android.sample.yamaneko.DBHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * URL例）
 *   "content://net.yadiary.android.sample.yamaneko/search?&codep=xxx&codes=xxxx"
 */
public class JPostalProvider extends ContentProvider {
	private static final String AUTHORITY = "net.yadiary.android.sample.yamaneko.provider";
	private static final String URI_PATH = "search";
	private static final int URI_PATTERN_ALL = 1;
	private static final int URI_PATTERN_ITEM = 2;
	private static final String MIME_TYPE_ALL = "vnd.android.cursor.dir/" + AUTHORITY + ".item";
	private static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/" + AUTHORITY + ".item";
	private static UriMatcher uriMatcher;
	private DBHelper helper = null;
	/*
	 * 公開するURL等
	 */
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + URI_PATH);
	public static final String FIELD_PREF = "p";
	public static final String FIELD_CITY = "c";
	public static final String FIELD_STREET = "s";
	public static final String FIELD_CODEPREFIX = "codep";
	public static final String FIELD_CODESUFFIX = "codes";
	public static final String FIELD_ID = "_id";
	public static final String FIELD_QID = "qid";

	@Override
	public boolean onCreate() {
		CommonUtils.logit("called");
		helper = new DBHelper(getContext());
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, URI_PATH, URI_PATTERN_ALL);
		uriMatcher.addURI(AUTHORITY, URI_PATH + "/#", URI_PATTERN_ITEM);

		SQLiteDatabase db = null;
		try {
			db = helper.getReadableDatabase();
			db.delete(DBHelper.DATABASE_TABLENAME, null, null);
		} catch (Exception e) {
			Log.e("provider", "e=" + e.getMessage());
			return false;
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return true;
	}

	@Override
	public String getType(Uri uri) {
		CommonUtils.logit("called");
		switch (uriMatcher.match(uri)) {
		case URI_PATTERN_ALL:
			return MIME_TYPE_ALL;
		case URI_PATTERN_ITEM:
			return MIME_TYPE_ITEM;
		default:
			throw new IllegalArgumentException("unknown URI " + uri);
		}
	}

	/**
	 * @param projection
	 *            FIELD_*クラス定数を使い、new String[]{FIELD_CODEPREFIX,...}の形式で任意に指定可能
	 * @param selection
	 *            無視
	 * @param selectionArgs
	 *            無視
	 * @param sortOrder
	 *            無視
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		CommonUtils.logit("called with uri=" + uri);

		String queryId = uri.getQueryParameter(FIELD_QID);
		if (queryId == null) {
			queryId = "";
		}
		String queryCodeP = uri.getQueryParameter(FIELD_CODEPREFIX);
		if (queryCodeP == null) {
			queryCodeP = "";
		}
		String queryCodeS = uri.getQueryParameter(FIELD_CODESUFFIX);
		if (queryCodeS == null) {
			queryCodeS = "";
		}

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.DATABASE_TABLENAME, projection, FIELD_QID + " = ? AND " + FIELD_CODEPREFIX
				+ " LIKE ? AND " + FIELD_CODESUFFIX + " LIKE ?", new String[] { queryId, queryCodeP + "%",
				queryCodeS + "%" }, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		CommonUtils.logit("called with uri=" + uri + ",values=" + values);
		Uri ret = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		long id = db.insert(DBHelper.DATABASE_TABLENAME, null, values);
		if (id >= 0) {
			ret = ContentUris.withAppendedId(uri, id);
			getContext().getContentResolver().notifyChange(ret, null);
		}
		return ret;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		CommonUtils.logit("called with uri=" + uri);
		SQLiteDatabase db = helper.getReadableDatabase();
		int deleteCount = db.delete(DBHelper.DATABASE_TABLENAME, null, null);
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		CommonUtils.logit("called");
		// 現時点では処理を行なわない
		return 0;
	}
}
