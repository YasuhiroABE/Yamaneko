Yamaneko
========

Compatibility Packageを使用したAndroid 1.6以降の端末で稼働する、郵便番号検索アプリの簡易版です。

元々はCursorLoaderのテストを目的に作成したものがベースで、内部は android.support.v4 の次の要素を含んでいます。

* android.support.v4.app.Fragment;
* android.support.v4.app.FragmentActivity;
* android.support.v4.app.LoaderManager;
* android.support.v4.content.AsyncTaskLoader;
* android.support.v4.content.CursorLoader;
* android.support.v4.widget.SimpleCursorAdapter;

内部構造は別途解説用wikiを参照してください。

ターゲット
----------

サポートパッケージ v4を使用し、 Android 1.6 (Platform 4) 以降に対応しています。

開発環境としては、Tegra Android Developer Pack 1.0r8をベースに次の構成でテストされています。

* Eclipse: 3.7.2
* Plugin: ADT 18.0.0
* OS: Ubuntu 12.04LTS (MacOSX Lion (10.7), Windows Vistaで確認済み)

## セットアップ

git cloneを行なってディレクトリ全体をコピーした後に、Eclipse上から新規Androidプロジェクトの作成を選択します。その後、サポートv4ライブラリを追加し、ソースコードの文字コードをUTF-8にします。

1. メニューの中にある"Creating project from existing source"を選択します。
2. Package Exploerに表示されたプロジェクトを右クリックし、「Android Tools」メニューから「Add Support Library...」を選択します。
3. 同じくプロジェクトを右クリックし、「Properties」の「Resource」のText file encodingの欄で、Otherにチェックを入れ、UTF-8を選択します。

文字コードのUTF-8への変更はLinuxでは不要です。

## ライセンス

このリポジトリに含まれるコードはApache License 2.0に従って配布しています。

このライセンスのCopyright表示「Copyright (C) 2012 Yasuhiro ABE <yasu@yasundial.org>」を表示する限り、商用・非商用を問わず自由にコードの全体・一部を利用する事ができます。

ライセンス
----------
 
     Copyright (C) 2012 Yasuhiro ABE <yasu@yasundial.org>
     
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
     
          http://www.apache.org/licenses/LICENSE-2.0
     
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

