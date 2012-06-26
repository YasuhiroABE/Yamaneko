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

import android.util.Log;

/**
 * 特定のクラスに関連しない共通関数を定義
 */
public class CommonUtils {
	/**
	 * デバッグ用ログ出力：出所が分かるようにメソッドの出力位置を表示する
	 *
	 * @param message 出力するメッセージ文字列
	 */
	public static void logit(String message) {
		String prefLabel = new Throwable().getStackTrace()[1].toString();
		Log.v("logit", prefLabel + ": " + message);
	}
}
