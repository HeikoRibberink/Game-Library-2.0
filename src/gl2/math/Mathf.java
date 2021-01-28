package gl2.math;

/**
 * This class contains similar methods to {@link java.lang.Math Math}, but are
 * much faster in trade for being less precise.
 * 
 * @author Heiko Ribberink
 *
 */

public class Mathf {

	public static final double DOUBLEPI = Math.PI * 2;
	public static final double HALFPI = Math.PI * 0.5;

	public static double invSqrt(double x) {
		double xhalf = 0.5d * x;
		long i = Double.doubleToLongBits(x);
		i = 0x5fe6ec85e7de30daL - (i >> 1);
		x = Double.longBitsToDouble(i);
		x *= (1.5d - xhalf * x * x);
		return x;
	}

	private static final int sineSize = 36000;
	private static final double[] sineLookupTable = new double[sineSize];
	private static final double multiplier = (double) sineSize / DOUBLEPI;

	static {
		double inverseMultiplier = 1 / multiplier;
		for(int i = 0; i < sineSize; i++) {
			sineLookupTable[i] = Math.sin(i * inverseMultiplier);
		}
	}

	public static double sin(double a) {
		a = a % DOUBLEPI;
		if(a < 0) {
			a += DOUBLEPI;
		}
		return sineLookupTable[(int) (a * multiplier)];
	}

	public static double cos(double a) {
		a += HALFPI;
		a = a % DOUBLEPI;
		if(a < 0) {
			a += DOUBLEPI;
		}
		return sineLookupTable[(int) (a * multiplier)];
	}

}
