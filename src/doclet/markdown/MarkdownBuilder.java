package doclet.markdown;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.MemberDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import com.sun.javadoc.ThrowsTag;
import com.sun.javadoc.Type;

import doclet.counter.CountInfo;
import doclet.counter.Counter;

/**
 * Markdown 形式の Javadoc ドキュメントを作成する処理を提供します。
 */
public class MarkdownBuilder {

	/**
	 * Javadoc のルートドキュメント
	 */
	private RootDoc root;

	/**
	 * Markdown 出力
	 */
	private MarkdownWriter md;

	/**
	 * 出力済のパッケージを記憶するためのリスト
	 */
	private List<PackageDoc> packages;

	/**
	 * ステップカウント結果を記憶するためのマップ
	 */
	private Map<File, CountInfo> counts;

	/**
	 * ドキュメントを生成します。
	 *
	 * @param rootDoc
	 *            Javadoc のルートドキュメント
	 * @throws IOException
	 *             例外
	 */
	public void create(RootDoc rootDoc) throws IOException {

		// Javadoc のルートドキュメントを取得
		root = rootDoc;

		// Markdown 出力を初期化
		md = new MarkdownWriter();

		// 表紙を作成
		makeCoverPage();

		// 出力済パッケージリストを初期化
		packages = new ArrayList<PackageDoc>();

		// ステップカウント結果を初期化
		counts = new HashMap<File, CountInfo>();

		// 全てのクラスを出力
		makeClassPages();

		// ステップカウント結果を出力
		makeCountPage();

		// ファイルに保存
		md.save(Options.getOption("file", "document.md"));
	}

	/**
	 * 表紙を作成します。
	 */
	private void makeCoverPage() {

		// 題名の情報
		String title = Options.getOption("title");
		if (!Options.getOption("subtitle").isEmpty()) {
			if (!title.isEmpty()) {
				title += " ";
			}
			title += Options.getOption("subtitle");
		}
		if (!Options.getOption("version").isEmpty()) {
			if (!title.isEmpty()) {
				title += " ";
			}
			title += Options.getOption("version");
		}

		// 作成者の情報
		String company = Options.getOption("company");

		// 日付を取得
		Locale locale = new Locale("ja", "JP", "JP");
		Calendar cal = Calendar.getInstance(locale);
		DateFormat jformat = new SimpleDateFormat("GGGGy年M月d日", locale);
		String stamp = jformat.format(cal.getTime());

		// 表紙の情報を出力
		md.cover(title, company, stamp);
	}

	/**
	 * 実行メソッドの引数の書式を文字列で取得します。
	 *
	 * @param parameters
	 *            引数の情報
	 * @return 引数の書式を示した文字列
	 */
	private String getParamSignature(Parameter[] parameters) {
		StringBuilder sb = new StringBuilder();
		for (Parameter parameter : parameters) {
			if (0 < sb.length()) {
				sb.append(", ");
			}
			String type = parameter.type().toString();
			type = type.replaceAll("java\\.(lang|util|io|nio)\\.", "");
			sb.append(type);
			sb.append(" ");
			sb.append(parameter.name());
		}
		return sb.toString();
	}

	/**
	 * パラメタに設定されたコメントを取得します。
	 *
	 * @param tags
	 *            タグ情報
	 * @param name
	 *            パラメタ名
	 * @return コメント情報
	 */
	private String getParamComment(ParamTag[] tags, String name) {
		for (ParamTag tag : tags) {
			if (tag.parameterName().equals(name)) {
				return tag.parameterComment();
			}
		}
		return "";
	}

	/**
	 * 例外に設定されたコメントを取得します。
	 *
	 * @param tags
	 *            タグ情報
	 * @param name
	 *            例外クラスの名前
	 * @return コメント情報
	 */
	private String getThrowsComment(ThrowsTag[] tags, String name) {
		for (ThrowsTag tag : tags) {
			if (tag.exceptionName().equals(name)) {
				return tag.exceptionComment();
			}
		}
		return "";
	}

	/**
	 * 全てのクラスの情報を出力します。
	 */
	private void makeClassPages() {

		// 全てのクラス
		for (ClassDoc classDoc : root.classes()) {

			// パッケージ
			PackageDoc packageDoc = classDoc.containingPackage();

			// 新たなパッケージの場合
			if (!packages.contains(packageDoc)) {

				// パッケージ名
				md.heading1(packageDoc.name() + " パッケージ");

				// パッケージ説明
				if (!packageDoc.commentText().isEmpty()) {
					print(packageDoc.commentText());
				}

				// 出力済パッケージに追加
				packages.add(packageDoc);
			}

			// 種類名
			String classType;
			if (classDoc.isInterface()) {
				classType = "インターフェイス";
			} else {
				if (classDoc.isAbstract()) {
					classType = "抽象クラス";
				} else {
					classType = "クラス";
				}
			}

			// クラス
			md.heading2(classDoc.modifiers() + " " + classDoc.name() + " " + classType);

			// パッケージ名
			md.heading3("パッケージ");
			md.unorderedList(classDoc.containingPackage().name());
			md.breakElement();

			// 継承階層
			List<ClassDoc> classDocs = new ArrayList<ClassDoc>();
			classDocs.add(classDoc);
			ClassDoc d = classDoc.superclass();
			while (d != null && !d.qualifiedName().equals("java.lang.Object")) {
				classDocs.add(d);
				d = d.superclass();
			}
			if (2 <= classDocs.size()) {
				md.heading3("すべての継承されたクラス階層");
				Collections.reverse(classDocs);
				for (int i = 0; i < classDocs.size(); i++) {
					md.orderedList(classDocs.get(i).qualifiedName());
				}
				md.breakElement();
			}

			// インターフェイス
			if (0 < classDoc.interfaces().length) {
				md.heading3("すべての実装されたインタフェース");
				for (int i = 0; i < classDoc.interfaces().length; i++) {
					md.unorderedList(classDoc.interfaces()[i].qualifiedName());
				}
				md.breakElement();
			}

			// クラス説明
			print(classDoc.commentText());

			// バージョン
			Tag[] versionTags = classDoc.tags("version");
			if (0 < versionTags.length) {
				md.heading3("バージョン");
				for (int i = 0; i < versionTags.length; i++) {
					md.unorderedList(versionTags[i].text());
				}
				md.breakElement();
			}

			// ソースコードファイル情報
			File source = classDoc.position().file();
			CountInfo ci = Counter.count(source);
			if (ci != null) {
				md.heading3("ファイル");
				md.unorderedList(source.getName() + " - " + ci.getSteps() + " ステップ " + ci.getLines() + " 行");
				md.breakElement();
			}
			counts.put(source, ci);

			// 作成者
			Tag[] authorTags = classDoc.tags("author");
			if (0 < authorTags.length) {
				md.heading3("作成者");
				for (int i = 0; i < authorTags.length; i++) {
					md.unorderedList(authorTags[i].text());
				}
				md.breakElement();
			}

			// 全ての定数
			if (0 < classDoc.enumConstants().length) {
				md.heading3("定数の詳細");
				for (int i = 0; i < classDoc.enumConstants().length; i++) {
					writeFieldDoc(classDoc.enumConstants()[i]);
				}
			}

			// 全てのフィールド
			if (0 < classDoc.fields().length) {
				md.heading3("フィールドの詳細");
				for (int i = 0; i < classDoc.fields().length; i++) {
					writeFieldDoc(classDoc.fields()[i]);
				}
			}

			// 全てのコンストラクタ
			if (0 < classDoc.constructors().length) {
				md.heading3("コンストラクタの詳細");
				for (int i = 0; i < classDoc.constructors().length; i++) {
					writeMemberDoc(classDoc.constructors()[i]);
				}
			}

			// 全てのメソッド
			if (0 < classDoc.methods().length) {
				md.heading3("メソッドの詳細");
				for (int i = 0; i < classDoc.methods().length; i++) {
					writeMemberDoc(classDoc.methods()[i]);
				}
			}
		}
	}

	/**
	 * 全てのフィールドの情報を出力します。
	 *
	 * @param doc
	 *            メンバ情報
	 */
	private void writeFieldDoc(MemberDoc doc) {

		// 種類名
		String fieldType;
		if (doc.isEnumConstant()) {
			fieldType = "列挙型定数";
		} else if (doc.isEnum()) {
			fieldType = "列挙型";
		} else {
			fieldType = "フィールド";
		}

		// フィールド情報
		md.heading4(doc.modifiers() + " " + doc.name() + " " + fieldType);
		if (!doc.commentText().isEmpty()) {
			print(doc.commentText());
		}
	}

	/**
	 * 全ての実行可能メンバの情報を出力します。
	 *
	 * @param doc
	 *            実行可能メンバの情報
	 */
	private void writeMemberDoc(ExecutableMemberDoc doc) {

		// 出力文字
		String str;

		// 種類名
		String memberType;
		if (doc.isConstructor()) {
			memberType = "コンストラクタ";
		} else if (doc.isMethod()) {
			memberType = "メソッド";
		} else {
			memberType = "メンバ";
		}

		// メソッド情報
		md.heading4(doc.name() + " " + memberType);
		if (!doc.commentText().isEmpty()) {
			print(doc.commentText());
		}
		md.heading5("形式");
		str = doc.modifiers();
		if (doc instanceof MethodDoc) {
			MethodDoc method = (MethodDoc) doc;
			str += " " + method.returnType().simpleTypeName();
		}
		str += " " + doc.name();
		str += " (" + getParamSignature(doc.parameters()) + ")";
		print(str);

		// パラメータ
		Parameter[] parameters = doc.parameters();
		if (0 < parameters.length) {
			md.heading5("パラメータ");
			for (int i = 0; i < parameters.length; i++) {
				str = parameters[i].name();
				md.definition(str, getParamComment(doc.paramTags(), parameters[i].name()));
			}
		}

		// 戻り値
		if (doc instanceof MethodDoc) {
			MethodDoc method = (MethodDoc) doc;
			if (0 < method.tags("return").length) {
				md.heading5("戻り値");
				str = method.returnType().simpleTypeName();
				md.definition(str, method.tags("return")[0].text());
			}
		}

		// 例外
		Type[] exceptions = doc.thrownExceptionTypes();
		if (0 < exceptions.length) {
			md.heading5("例外");
			for (int i = 0; i < exceptions.length; i++) {
				str = exceptions[i].simpleTypeName();
				md.definition(str, getThrowsComment(doc.throwsTags(), exceptions[i].typeName()));
			}
		}
	}

	/**
	 * Javadoc の内容を Markdown 形式で出力します。
	 *
	 * @param str
	 *            Javadoc の内容
	 */
	private void print(String str) {

		// 段落ごとに処理
		String[] paragraphs = str.split("\\s*<(p|P)>\\s*");
		for (int i = 0; i < paragraphs.length; i++) {

			// 改行の結合
			paragraphs[i] = paragraphs[i].replaceAll("\\s*[\\r\\n]+\\s*", " ");

			// 行ごとに改行を挿入
			paragraphs[i] = paragraphs[i].replaceAll("\\.\\s+", ".\n");
			paragraphs[i] = paragraphs[i].replaceAll("。\\s*", "。\n");

			// 改行ごとに処理
			String[] lines = paragraphs[i].split("\n");
			for (int j = 0; j < lines.length; j++) {
				md.line(lines[j]);
			}

			// Markdown 要素の終了
			md.breakElement();
		}
	}

	/**
	 * ステップカウント結果ページを出力します。
	 */
	private void makeCountPage() {

		// 合計値の初期化
		int count = 0;
		long sumSize = 0;
		int sumSteps = 0;
		int sumBranks = 0;
		int sumLines = 0;

		// パッケージ名
		md.heading1("ファイル一覧");

		// テーブルヘッダ
		md.columns("ファイル", "bytes", "ステップ", "空白行", "行数");
		md.columns(":-----", "-----:", "-----:", "-----:", "-----:");

		// 対象ファイルが存在する場合
		if (0 < counts.size()) {

			// ファイルリスト生成 (ファイル名順)
			List<File> files = new ArrayList<File>(counts.keySet());
			Collections.sort(files, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});

			// ファイル一覧の出力
			for (File file : files) {

				// ファイル情報
				long size = file.length();
				int steps = counts.get(file).getSteps();
				int branks = counts.get(file).getBranks();
				int lines = counts.get(file).getLines();

				// 合計値の加算
				count++;
				sumSize += size;
				sumSteps += steps;
				sumBranks += branks;
				sumLines += lines;

				// ファイル情報
				md.columns(file.getName(), // ファイル
						String.format("%,d", size), // サイズ
						String.format("%,d", steps), // ステップ
						String.format("%,d", branks), // 空白行
						String.format("%,d", lines) // 行数
				);
			}
		}

		// 合計行
		md.columns(String.format("計 %,d ファイル", count), // ファイル数
				String.format("%,d", sumSize), // サイズ
				String.format("%,d", sumSteps), // ステップ
				String.format("%,d", sumBranks), // 空白行
				String.format("%,d", sumLines) // 行数
		);
		md.breakElement();
	}
}