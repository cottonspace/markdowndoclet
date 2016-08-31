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
	 * 有効行の数
	 */
	private int steps;

	/**
	 * 空白行の数
	 */
	private int branks;

	/**
	 * コンストラクタです。
	 *
	 * @param lines
	 *            行数
	 * @param steps
	 *            有効行の数
	 * @param branks
	 *            空白行の数
	 */
	public CountInfo(int lines, int steps, int branks) {
		this.lines = lines;
		this.steps = steps;
		this.branks = branks;
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
	 * 有効行の数を取得します。
	 *
	 * @return 有効行の数
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * 有効行の数を設定します。
	 *
	 * @param steps
	 *            有効行の数
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * 空白行の数を取得します。
	 *
	 * @return 空白行の数
	 */
	public int getBranks() {
		return branks;
	}

	/**
	 * 空白行の数を設定します。
	 *
	 * @param branks
	 *            空白行の数
	 */
	public void setBranks(int branks) {
		this.branks = branks;
	}
}