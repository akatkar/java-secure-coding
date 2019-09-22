package secure.coding.chapter11.err.err04;

/**
 * @rule ERR04-J. Do not exit abruptly from a finally block
 * 
 * @description Never use return, break, continue, or throw statements within a
 *              finally block. When program execution enters a try block that
 *              has a finally block, the finally block always executes,
 *              regardless of whether the try block (or any associated catch
 *              blocks) executes to completion. Statements that cause the
 *              finally block to terminate abruptly also cause the try block to
 *              terminate abruptly and consequently suppress any exception
 *              thrown from the try or catch blocks
 * 
 * @category Noncompliant code
 * 
 * @description In this noncompliant code example, the finally block completes
 *              abruptly because of a return statement in the block.
 */
class TryFinally {

	private static boolean doLogic() {
		try {
			throw new IllegalStateException();
		} finally {
			System.out.println("logic done");
			return true;
		}
	}

	public static void main(String[] args) {
		doLogic();
	}
}
