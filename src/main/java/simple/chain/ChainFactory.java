package simple.chain;

public final class ChainFactory {

	public static final Chain get(int difficulty, double rewardCoefficient) {
		return new Chain(difficulty, rewardCoefficient);
	}
	
}
