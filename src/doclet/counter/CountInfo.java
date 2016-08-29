package doclet.counter;

/**
 * ステップカウント結果を保持するクラスです。
 */
public class CountInfo {

	/**
	 * 行数
	 */
	private int lines;

	/**
	 * コメント行を除いたステップ数
	 */
	private int steps;

	/**
	 * コンストラクタです。
	 *
	 * @param lines
	 *            行数
	 * @param steps
	 *            ステップ数
	 */
	public CountInfo(int lines, int steps) {
		this.lines = lines;
		this.steps = steps;
	}

	/**
	 * 行数を取得します。
	 *
	 * @return 行数
	 */
	public int getLines() {
		return lines;
	}

	/**
	 * 行数を設定します。
	 *
	 * @param lines
	 *            行数
	 */
	public void setLines(int lines) {
		this.lines = lines;
	}

	/**
	 * ステップ数を取得します。
	 *
	 * @return ステップ数
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * ステップ数を設定します。
	 *
	 * @param steps
	 *            ステップ数
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}
}