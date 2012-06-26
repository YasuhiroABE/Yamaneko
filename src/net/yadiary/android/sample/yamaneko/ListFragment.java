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

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private final String[] projection = new String[] { 
			JPostalProvider.FIELD_ID, JPostalProvider.FIELD_CODEPREFIX,	JPostalProvider.FIELD_CODESUFFIX,
			JPostalProvider.FIELD_PREF, JPostalProvider.FIELD_CITY, JPostalProvider.FIELD_STREET, };

	private SimpleCursorAdapter adapter;

	private TextView textView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		CommonUtils.logit("called");
		View view = inflater.inflate(R.layout.list, container);

		String[] from = new String[] { 
				JPostalProvider.FIELD_CODEPREFIX,
				JPostalProvider.FIELD_CODESUFFIX,
				JPostalProvider.FIELD_PREF,
				JPostalProvider.FIELD_CITY,
				JPostalProvider.FIELD_STREET, };
		int[] to = new int[] { 
				R.id.textViewListItemCodeP, 
				R.id.textViewListItemCodeS, 
				R.id.textViewListItemPref,
				R.id.textViewListItemCity, 
				R.id.textViewListItemStreet, };
		adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, null, from, to,
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		ListView listView = (ListView) view.findViewById(R.id.listViewList);
		listView.setAdapter(adapter);

		textView = (TextView) view.findViewById(R.id.textViewList);
		updateStatus(0);
		return view;
	}

	private void updateStatus(int count) {
		StringBuilder text = new StringBuilder(getText(R.string.list_textview_prefix));
		text.append(count);
		textView.setText(text.toString());
	}

	/*
	 * ここから - LoaderCallback<Cursor>の実装
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		CommonUtils.logit("called with id=" + id);
		Uri uri = Uri.parse(JPostalProvider.CONTENT_URI + "?" + JPostalProvider.FIELD_QID + "="
				+ bundle.getString(JPostalProvider.FIELD_QID) + "&" + JPostalProvider.FIELD_CODEPREFIX + "="
				+ bundle.getString(JPostalProvider.FIELD_CODEPREFIX) + "&" + JPostalProvider.FIELD_CODESUFFIX + "="
				+ bundle.getString(JPostalProvider.FIELD_CODESUFFIX));
		return new CursorLoader(this.getActivity(), uri, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		CommonUtils.logit("called with cursor.getCount=" + cursor.getCount());
		Cursor oldCursor = adapter.swapCursor(cursor);
		CommonUtils.logit("newCurosr=" + cursor);
		CommonUtils.logit("oldCurosr=" + oldCursor);

		updateStatus(cursor.getCount());
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		CommonUtils.logit("called");
		adapter.swapCursor(null);
	}
	/*
	 * ここまで - LoaderCallback<Cursor>の実装
	 */
}
