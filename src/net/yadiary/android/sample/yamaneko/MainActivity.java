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
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Void> {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		CommonUtils.logit("called");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// @see http://yuki312.blogspot.jp/2012/02/loadermanager.html
		getSupportLoaderManager();
	}

	public void doSearch(String codep, String codes) {
		CommonUtils.logit("called with codep=" + codep + ",codes=" + codes);
		ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMainList);
		Bundle bundle = new Bundle();
		bundle.putString(JPostalProvider.FIELD_CODEPREFIX, codep);
		bundle.putString(JPostalProvider.FIELD_CODESUFFIX, codes);
		bundle.putString(JPostalProvider.FIELD_QID, "" + new java.util.Date().getTime());
		
		getSupportLoaderManager().restartLoader(0, bundle, listFragment);
		Loader<Void> loader = getSupportLoaderManager().getLoader(1);
		if(loader != null) {
			loader.abandon();
		}
		getSupportLoaderManager().restartLoader(1, bundle, this);
	}

	/*
	 * ここから - LoaderManager.LoaderCallbacks<Void>の実装
	 */
	@Override
	public Loader<Void> onCreateLoader(int id, Bundle bundle) {
		CommonUtils.logit("onCreateLoader called with id=" + id);
		WebServiceAsyncTaskLoader loader = new WebServiceAsyncTaskLoader(this);
		loader.setQid(bundle.getString(JPostalProvider.FIELD_QID));
		loader.setCodeP(bundle.getString(JPostalProvider.FIELD_CODEPREFIX));
		loader.setCodeS(bundle.getString(JPostalProvider.FIELD_CODESUFFIX));
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Void> loader, Void arg) {
		CommonUtils.logit("called");
	}

	@Override
	public void onLoaderReset(Loader<Void> loader) {
		CommonUtils.logit("called");
	}
	/*
	 * ここまで - LoaderManager.LoaderCallbacks<Void>の実装
	 */

	/*
	 * ここから - メニュー用設定
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		CommonUtils.logit("called");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		CommonUtils.logit("called with item.getItemId=" + item.getItemId());
		if (item.getItemId() == R.id.menu_main_copyright) {
			Toast.makeText(this, R.string.menu_main_copyright, Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}
	/*
	 * ここまで - メニュー用設定
	 */
}