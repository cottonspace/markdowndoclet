package doclet.markdown;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Markdown 形式のデータを出力します。
 */
public class MarkdownWriter {

	/**
	 * Markdown の出力行の情報
	 */
	List<String> lines = new ArrayList<String>();

	/**
	 * 表紙の情報を出力します。
	 *
	 * @param title
	 *            題名
	 * @param author
	 *            作成者
	 * @param date
	 *            日付
	 */
	public void cover(String title, String author, String date) {
		lines.add(0, "% " + markdown(date));
		lines.add(0, "% " + markdown(author));
		lines.add(0, "% " + markdown(title));
		breakElement();
	}

	/**
	 * レベル 1 の見出しを出力します。
	 *
	 * @param str
	 *            見出しの文字列
	 */
	public void heading1(String str) {
		lines.add("# " + markdown(str));
		breakElement();
	}

	/**
	 * レベル 2 の見出しを出力します。
	 *
	 * @param str
	 *            見出しの文字列
	 */
	public void heading2(String str) {
		lines.add("## " + markdown(str));
		breakElement();
	}

	/**
	 * レベル 3 の見出しを出力します。
	 *
	 * @param str
	 *            見出しの文字列
	 */
	public void heading3(String str) {
		lines.add("### " + markdown(str));
		breakElement();
	}

	/**
	 * レベル 4 の見出しを出力します。
	 *
	 * @param str
	 *            見出しの文字列
	 */
	public void heading4(String str) {
		lines.add("#### " + markdown(str));
		breakElement();
	}

	/**
	 * レベル 5 の見出しを出力します。
	 *
	 * @param str
	 *            見出しの文字列
	 */
	public void heading5(String str) {
		lines.add("##### " + markdown(str));
		breakElement();
	}

	/**
	 * 順番なしリストを出力します。
	 *
	 * @param str
	 *            リストの内容
	 */
	public void unorderedList(String str) {
		lines.add("* " + markdown(str));
	}

	/**
	 * 番号付きリストを出力します。
	 *
	 * @param str
	 *            リストの内容
	 */
	public void orderedList(String str) {
		lines.add("1. " + markdown(str));
	}

	/**
	 * 定義リストを出力します。
	 *
	 * @param item
	 *            定義の名前
	 * @param term
	 *            定義の内容
	 */
	public void definition(String item, String term) {
		lines.add(markdown(item));
		if (!term.isEmpty()) {
			lines.add(":   " + markdown(term));
		} else {
			lines.add(":   " + "(undef)");
		}
		breakElement();
	}

	/**
	 * 行単位の情報を出力します。
	 *
	 * @param str
	 *            行単位の情報
	 */
	public void line(String str) {
		lines.add(markdown(str) + "  ");
	}

	/**
	 * Markdown 要素の終了
	 */
	public void breakElement() {
		lines.add("");
	}

	/**
	 * Javadoc 情報を Markdown 形式の文字列に変換します。
	 *
	 * @param str
	 *            Javadoc 形式の文字列
	 * @return Markdown 形式の文字列
	 */
	private String markdown(String str) {

		// Javadoc インラインタグの Markdown 変換
		str = Pattern.compile("<code>(.*?)</code>").matcher(str).replaceAll("`$1`");
		str = Pattern.compile("<i>(.*?)</i>").matcher(str).replaceAll("_$1_");
		str = Pattern.compile("<em>(.*?)</em>").matcher(str).replaceAll("_$1_");
		str = Pattern.compile("<b>(.*?)</b>").matcher(str).replaceAll("__$1__");
		str = Pattern.compile("<strong>(.*?)</strong>").matcher(str).replaceAll("__$1__");
		str = Pattern.compile("<a href=\"(https?://.+?)\">(.+?)</a>").matcher(str).replaceAll("[$2]($1)");
		str = Pattern.compile("\\{@link +(.+?) +(.+?)\\}").matcher(str).replaceAll("[$2]($1)");
		str = Pattern.compile("\\{@link +(.+?)\\}").matcher(str).replaceAll("[$1]($1)");
		str = Pattern.compile("\\{@code +(.+?)\\}").matcher(str).replaceAll("`$1`");

		// エンティティ参照の復元
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&apos;", "'");
		str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&amp;", "&");

		// 結果の返却
		return str;
	}

	/**
	 * Markdown ファイルを保存します。
	 *
	 * @param file
	 *            ファイル名
	 * @throws IOException
	 *             例外
	 */
	public void save(String file) throws IOException {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			for (String line : lines) {
				writer.write(line);
				writer.write(System.lineSeparator());
			}
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
	}
}