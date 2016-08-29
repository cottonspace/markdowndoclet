package doclet.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * ステップカウント処理を提供します。
 */
public class Counter {

	/**
	 * ステップ数をカウントします。例外が発生した場合は null を返却します。
	 *
	 * @param file
	 *            対象ファイル
	 * @return カウント情報
	 */
	public static CountInfo count(File file) {

		// カウント実行
		BufferedReader br = null;
		try {

			// カウンタ初期化
			int lines = 0;
			int steps = 0;

			// 行文字列
			String line;

			// ブロックコメントフラグ初期化
			boolean comment = false;

			// ファイルオープン
			br = new BufferedReader(new FileReader(file));

			// 全行を読み込み
			while ((line = br.readLine()) != null) {

				// 行数加算
				lines++;

				// 空白除去
				line = line.trim();

				// 有効行のカウント
				if (0 < line.length()) {
					if (line.startsWith("/*") && line.endsWith("*/")) {
						comment = false;
					} else if (line.startsWith("/*")) {
						comment = true;
					} else if (line.endsWith("*/")) {
						comment = false;
					} else {
						if (!comment && !line.startsWith("//")) {
							steps++;
						}
					}
				}
			}

			// カウント結果を返却
			return new CountInfo(lines, steps);

		} catch (IOException e) {
		} finally {

			// ファイルクローズ
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}

		// エラーが発生した場合
		return null;
	}
}