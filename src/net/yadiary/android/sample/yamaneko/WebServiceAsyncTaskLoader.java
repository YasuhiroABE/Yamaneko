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

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

public class WebServiceAsyncTaskLoader extends AsyncTaskLoader<Void> {

	private String qid = "";

	public void setQid(String qid) {
		this.qid = qid;
	}

	private String codep = "";

	public void setCodeP(String codep) {
		this.codep = codep;
	}

	private String codes = "";

	public void setCodeS(String codes) {
		this.codes = codes;
	}

	public WebServiceAsyncTaskLoader(Context context) {
		super(context);
		CommonUtils.logit("called");
	}

	@Override
	protected void onStartLoading() {
		CommonUtils.logit("called");
		super.onStartLoading();

		getContext().getContentResolver().delete(JPostalProvider.CONTENT_URI, null, null);
	}

	@Override
	public Void loadInBackground() {
		CommonUtils.logit("called");
		int page = 1;
		int pageTotal = 1;
		while (page <= pageTotal) {
			if(isAbandoned()) {
				break;
			}
			JsonBean bean = SearchEngine.search(codep, codes, page);
			pageTotal = Integer.parseInt(bean.getPageTotal());
			ArrayList<HashMap<String, String>> rows = bean.getRows();

			for (int i = 0; i < rows.size(); i++) {
				if(isAbandoned()) {
					break;
				}
				ContentValues values = new ContentValues();
				HashMap<String, String> row = rows.get(i);
				for (String label : new String[] { JPostalProvider.FIELD_CODEPREFIX, JPostalProvider.FIELD_CODESUFFIX,
						JPostalProvider.FIELD_PREF, JPostalProvider.FIELD_CITY, JPostalProvider.FIELD_STREET }) {
					values.put(label, row.get(label));
				}
				values.put(JPostalProvider.FIELD_QID, qid);
				getContext().getContentResolver().insert(
						Uri.parse(JPostalProvider.CONTENT_URI + "?" + JPostalProvider.FIELD_QID + "=" + qid + "&"
								+ JPostalProvider.FIELD_CODEPREFIX + "=" + codep + "&"
								+ JPostalProvider.FIELD_CODESUFFIX + "=" + codes), values);
			}
			page++;
		}
		return null;
	}

}
