package secure.coding.chapter11.err.err04.solution;

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
 * @category Compliant Solution
 * 
 * @description This compliant solution removes the return statement from the
 *              finally block.
 */
class TryFinally {

	private static boolean doLogic() {
		try {
			throw new IllegalStateException();
		} finally {
			System.out.println("logic done");
		}
		// Any return statements must go here;
		// applicable only when exception is thrown conditionally
		// return true;
	}
	
	public static void main(String[] args) {
		doLogic();
	}
}
