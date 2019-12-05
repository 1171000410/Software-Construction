package strategy;

import crossing.Ladder;

public interface Strategy {

	/**
	 * Choose a suitable ladder
	 * @param direction of the monkey choosing ladder
	 * @return label of a ladder
	 */
	public Ladder chooseLadder(String direction);

}
