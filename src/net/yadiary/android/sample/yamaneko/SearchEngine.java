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

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SearchEngine {
	
	private static final String SEARCH_ENGINE_URL = "http://www.yadiary.net/postal/main.fcgi?";
	
	public static JsonBean search(String codep, String codes, int page) {
		CommonUtils.logit("called with codep=" + codep + ",codes=" + codes + ",page=" + page);
		if (codep == null) {
			codep = "";
		}
		if (codes == null) {
			codes = "";
		}
		JsonBean ret = null;
		URL url = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {
			url = new URL(SEARCH_ENGINE_URL + "mode=search_json&code_prefix=" + codep + "&code_suffix="
					+ codes + "&page=" + page);
			URLConnection urlconn = url.openConnection();
			urlconn.setConnectTimeout(200);
			urlconn.setReadTimeout(5000);
			is = urlconn.getInputStream();
			bis = new BufferedInputStream(is);
			ret = new JsonBean(bis);
		} catch (Exception e) {
			CommonUtils.logit("e.getMessage=" + e.getMessage());
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
					CommonUtils.logit("e.getMessage=" + e.getMessage());
				}
			}
		}
		return ret;
	}
}
