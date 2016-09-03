% MarkdownDoclet 詳細設計書 1.0
% sample
% 平成28年9月3日

# doclet.counter パッケージ

ステップカウント処理のクラスを格納するパッケージです。  

## public CountInfo クラス

ステップカウント結果を保持するクラスです。  

### パッケージ

* doclet.counter

### ファイル

* CountInfo.java - 29 ステップ 95 行

#### フィールドの詳細

##### private int lines フィールド

行数  

##### private int steps フィールド

有効行の数  

##### private int branks フィールド

空白行の数  

#### public CountInfo (lines, steps, branks) コンストラクタ

コンストラクタです。  

##### int lines パラメータ

行数  

##### int steps パラメータ

有効行の数  

##### int branks パラメータ

空白行の数  

#### public int getLines () メソッド

行数を取得します。  

##### 戻り値

行数  

#### public void setLines (lines) メソッド

行数を設定します。  

##### int lines パラメータ

行数  

#### public int getSteps () メソッド

有効行の数を取得します。  

##### 戻り値

有効行の数  

#### public void setSteps (steps) メソッド

有効行の数を設定します。  

##### int steps パラメータ

有効行の数  

#### public int getBranks () メソッド

空白行の数を取得します。  

##### 戻り値

空白行の数  

#### public void setBranks (branks) メソッド

空白行の数を設定します。  

##### int branks パラメータ

空白行の数  

## public Counter クラス

ステップカウント処理を提供します。  

### パッケージ

* doclet.counter

### ファイル

* Counter.java - 47 ステップ 85 行

#### public Counter () コンストラクタ

説明がありません  

#### public static CountInfo count (file) メソッド

ステップ数をカウントします。  
例外が発生した場合は null を返却します。  

##### File file パラメータ

対象ファイル  

##### 戻り値

カウント情報  

# doclet.markdown パッケージ

MarkdownDoclet のクラスを格納するパッケージです。  

## public Options クラス

実行時オプションを格納するクラスです。  

### パッケージ

* doclet.markdown

### ファイル

* Options.java - 27 ステップ 65 行

#### フィールドの詳細

##### public static String[][] options フィールド

Javadoc オプションの配列  

#### public Options () コンストラクタ

説明がありません  

#### public static String getOption (name) メソッド

オプション文字列を取得します。  

該当するオプションが指定されていない場合は空文字列を返却します。  

##### String name パラメータ

オプション名  

##### 戻り値

オプションの値  

#### public static String getOption (name, defaultValue) メソッド

オプション文字列を取得します。  

該当するオプションが指定されていない場合はデフォルト値を返却します。  

##### String name パラメータ

オプション名  

##### String defaultValue パラメータ

オプションが指定されていない場合に使用する値  

##### 戻り値

オプションの値  

#### public static boolean isSupportedOption (option) メソッド

対応しているオプション名であるか判定します。  

##### String option パラメータ

オプション名  

##### 戻り値

対応しているオプション名の場合は true を返却します。  

## public MarkdownWriter クラス

Markdown 形式のデータを出力します。  

### パッケージ

* doclet.markdown

### ファイル

* MarkdownWriter.java - 105 ステップ 226 行

#### フィールドの詳細

#####  List\<String\> lines フィールド

Markdown の出力行の情報  

#### public MarkdownWriter () コンストラクタ

説明がありません  

#### public void cover (title, author, date) メソッド

表紙の情報を出力します。  

##### String title パラメータ

題名  

##### String author パラメータ

作成者  

##### String date パラメータ

日付  

#### public void heading1 (str) メソッド

レベル 1 の見出しを出力します。  

##### String str パラメータ

見出しの文字列  

#### public void heading2 (str) メソッド

レベル 2 の見出しを出力します。  

##### String str パラメータ

見出しの文字列  

#### public void heading3 (str) メソッド

レベル 3 の見出しを出力します。  

##### String str パラメータ

見出しの文字列  

#### public void heading4 (str) メソッド

レベル 4 の見出しを出力します。  

##### String str パラメータ

見出しの文字列  

#### public void heading5 (str) メソッド

レベル 5 の見出しを出力します。  

##### String str パラメータ

見出しの文字列  

#### public void unorderedList (str) メソッド

順番なしリストを出力します。  

##### String str パラメータ

リストの内容  

#### public void orderedList (str) メソッド

番号付きリストを出力します。  

##### String str パラメータ

リストの内容  

#### public void definition (item, term) メソッド

定義リストを出力します。  

##### String item パラメータ

定義の名前  

##### String term パラメータ

定義の内容  

#### public void line (str) メソッド

行単位の情報を出力します。  

##### String str パラメータ

行単位の情報  

#### public void columns (cols) メソッド

テーブル行を出力します。  

##### String[] cols パラメータ

列の情報  

#### public void breakElement () メソッド

Markdown 要素の終了  

#### private String markdown (str) メソッド

Javadoc 情報を Markdown 形式の文字列に変換します。  

##### String str パラメータ

Javadoc 形式の文字列  

##### 戻り値

Markdown 形式の文字列  

#### public void save (file) メソッド

Markdown ファイルを保存します。  

##### String file パラメータ

ファイル名  

##### 例外

IOException
:   例外

## public MarkdownDoclet クラス

Markdown 形式の Javadoc ドキュメントを作成するドックレットです。  

### パッケージ

* doclet.markdown

### ファイル

* MarkdownDoclet.java - 26 ステップ 55 行

### すべての継承されたクラス階層

1. com.sun.javadoc.Doclet
1. doclet.markdown.MarkdownDoclet

#### public MarkdownDoclet () コンストラクタ

説明がありません  

#### public static boolean start (rootDoc) メソッド

Javadoc 生成処理を実行します。  

実行すると Javadoc 情報を Markdown ファイルとして生成します。  
既に同名のファイルが存在する場合は上書きされます。  

##### RootDoc rootDoc パラメータ

Javadoc のルートドキュメント  

##### 戻り値

実行結果を真偽値で返却します。  

#### public static int optionLength (option) メソッド

オプションの引数の個数を返却します。  

##### String option パラメータ

オプション名  

##### 戻り値

対応する引数自身を含むパラメタの個数  

#### public static LanguageVersion languageVersion () メソッド

対応する Java バージョンを指定します。  

##### 戻り値

対応する Java バージョン  

## public MarkdownBuilder クラス

Markdown 形式の Javadoc ドキュメントを作成する処理を提供します。  

### パッケージ

* doclet.markdown

### ファイル

* MarkdownBuilder.java - 302 ステップ 525 行

#### フィールドの詳細

##### private RootDoc root フィールド

Javadoc のルートドキュメント  

##### private MarkdownWriter md フィールド

Markdown 出力  

##### private List\<PackageDoc\> packages フィールド

出力済のパッケージを記憶するためのリスト  

##### private Map\<File, CountInfo\> counts フィールド

ステップカウント結果を記憶するためのマップ  

##### private static final String NO_COMMENT フィールド

コメントが指定されていない場合の表示文字列  

#### public MarkdownBuilder () コンストラクタ

説明がありません  

#### public void create (rootDoc) メソッド

ドキュメントを生成します。  

##### RootDoc rootDoc パラメータ

Javadoc のルートドキュメント  

##### 例外

IOException
:   例外

#### private void makeCoverPage () メソッド

表紙を作成します。  

#### private String getParamSignature (parameters, type) メソッド

実行メソッドの引数の書式を文字列で取得します。  

##### Parameter[] parameters パラメータ

引数の情報  

##### boolean type パラメータ

クラス名を表示する場合  

##### 戻り値

引数の書式を示した文字列  

#### private String getParamComment (tags, name) メソッド

パラメタに設定されたコメントを取得します。  

##### ParamTag[] tags パラメータ

タグ情報  

##### String name パラメータ

パラメタ名  

##### 戻り値

コメント情報  

#### private String getThrowsComment (tags, name) メソッド

例外に設定されたコメントを取得します。  

##### ThrowsTag[] tags パラメータ

タグ情報  

##### String name パラメータ

例外クラスの名前  

##### 戻り値

コメント情報  

#### private void makeClassPages () メソッド

全てのクラスの情報を出力します。  

#### private void writeFieldDoc (doc) メソッド

全てのフィールドの情報を出力します。  

##### MemberDoc doc パラメータ

メンバ情報  

#### private void writeMemberDoc (doc) メソッド

全ての実行可能メンバの情報を出力します。  

##### ExecutableMemberDoc doc パラメータ

実行可能メンバの情報  

#### private void print (str) メソッド

Javadoc の内容を Markdown 形式で出力します。  

##### String str パラメータ

Javadoc の内容  

#### private String getText (str, def) メソッド

指定された文字列が空の場合はデフォルト文字列を返します。  

##### String str パラメータ

文字列  

##### String def パラメータ

デフォルト文字列  

##### 戻り値

選択された文字列  

#### private String getShortName (type) メソッド

クラス名からパッケージ名を除去します。  

##### Type type パラメータ

クラス  

##### 戻り値

省略したクラス名  

#### private void makeCountPage () メソッド

ステップカウント結果ページを出力します。  

# ファイル一覧

|ファイル|bytes|ステップ|空白行|行数|
|:-----|-----:|-----:|-----:|-----:|
|CountInfo.java|1,523|29|11|95|
|Counter.java|1,746|47|16|85|
|MarkdownBuilder.java|14,066|302|74|525|
|MarkdownDoclet.java|1,482|26|5|55|
|MarkdownWriter.java|5,463|105|21|226|
|Options.java|1,641|27|5|65|
|計 6 ファイル|25,921|536|132|1,051|

