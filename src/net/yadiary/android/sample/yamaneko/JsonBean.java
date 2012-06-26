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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Webサーバーから返されたJSONの処理結果をカプセル化するクラス。
 * 便宜的にBeanと呼ぶが、SerializableではないためJavaBeansではない。
 */
public class JsonBean {
	private ArrayList<HashMap<String, String>> rows = null;
	private String rows_total;
	private String unit;
	private String page;
	private String page_total;
	private String total;

	public JsonBean() {
		CommonUtils.logit("called");
		rows = new ArrayList<HashMap<String, String>>();
	}

	public JsonBean(InputStream is) {
		this();
		CommonUtils.logit("called");
		init(is);
	}

	private void init(InputStream is) {
		CommonUtils.logit("called");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonTextBuilder = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				jsonTextBuilder.append(line);
			}
		} catch (IOException e) {
			CommonUtils.logit("e.getMessage=" + e.getMessage());
		}
		try {
			JSONObject j = (JSONObject) new JSONTokener(jsonTextBuilder.toString()).nextValue();
			total = j.getString("total");
			page = j.getString("page");
			page_total = j.getString("page_total");
			rows_total = j.getString("rows_total");
			unit = j.getString("unit");
			JSONArray jArray = j.getJSONArray("rows");
			for (int i = 0; i < jArray.length(); i++) {
				HashMap<String, String> rowsVal = new HashMap<String, String>();
				JSONObject jobj = jArray.getJSONObject(i);
				@SuppressWarnings("unchecked")
				Iterator<String> it = jobj.keys();
				while (it.hasNext()) {
					String k = it.next();
					rowsVal.put(k, jobj.getString(k));
				}
				rows.add(rowsVal);
			}
		} catch (JSONException e) {
			CommonUtils.logit("e.getMessage=" + e.getMessage());
		}
	}

	public ArrayList<HashMap<String, String>> getRows() {
		return rows;
	}

	public void setRows(ArrayList<HashMap<String, String>> rows) {
		this.rows = rows;
	}

	public String getRowsTotal() {
		return rows_total;
	}

	public void setRowsTotal(String rows_total) {
		this.rows_total = rows_total;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageTotal() {
		return page_total;
	}

	public void setPagTotal(String page_total) {
		this.page_total = page_total;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
