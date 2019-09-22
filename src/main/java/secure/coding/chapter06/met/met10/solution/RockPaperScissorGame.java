package secure.coding.chapter06.met.met10.solution;

import secure.coding.chapter06.met.met10.RockPaperScissorGame.GameEntry;

/**
 * @rule MET10-J. Follow the general contract when implementing the compareTo()
 *       method
 * 
 * @description Choosing to implement the Comparable interface represents a
 *              commitment that the implementation of the compareTo() method
 *              adheres to the general usage contract for that method. Library
 *              classes such as TreeSet and TreeMap accept Comparable objects
 *              and use the associated compareTo() methods to sort the objects.
 *              However, a class that implements the compareTo() method in an
 *              unexpected way can cause undesirable results.
 * 
 *              The general usage contract for compareTo() from Java SE 6 API [
 *              API 2006 ] (numbering added) states that
 * 
 *              1. The implementor must ensure sgn(x.compareTo(y)) ==
 *              -sgn(y.compareTo(x)) for all x and y. (This implies that
 *              x.compareTo(y) must throw an exception if y.compareTo(x) throws
 *              an exception.)
 * 
 *              2. The implementor must also ensure that the relation is
 *              transitive: (x.compareTo(y) > 0 && y.compareTo(z) > 0) implies
 *              x.compareTo(z) > 0.
 * 
 *              3. Finally, the implementor must ensure that x.compareTo(y) == 0
 *              implies that sgn(x. compareTo(z)) == sgn(y.compareTo(z)) for all
 *              z.
 * 
 *              4. It is strongly recommended, but not strictly required, that
 *              (x.compareTo(y) == 0) == x.equals(y). Generally speaking, any
 *              class that implements the Comparable interface and violates this
 *              condition should clearly indicate this fact. The recommended
 *              language is �Note: this class has a natural ordering that is
 *              inconsistent with equals.�
 * 
 * @category Compliant solution
 */
public final class RockPaperScissorGame {
	public enum GameEntry {
		ROCK, PAPER, SCISSORS
	}

	private GameEntry value;

	public RockPaperScissorGame(GameEntry value) {
		this.value = value;
	}

	/**
	 * @description This program implements the classic game of rock-paper-scissors,
	 *              using the compareTo() operator to determine the winner of a
	 *              game. However, this game violates the required transitivity
	 *              property because rock beats scissors, and scissors beats paper,
	 *              but rock does not beat paper.
	 * 
	 *              This compliant solution implements the same game without using
	 *              the Comparable interface.
	 */
	public int beats(RockPaperScissorGame other) {
		return (value == other.value) ? 0
				: (value == GameEntry.ROCK && other.value == GameEntry.PAPER) ? -1
						: (value == GameEntry.PAPER && other.value == GameEntry.SCISSORS) ? -1
								: (value == GameEntry.SCISSORS && other.value == GameEntry.ROCK) ? -1 : 1;
	}
}