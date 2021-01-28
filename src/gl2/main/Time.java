package gl2.main;

/**
 * This enum contains multiple values for timing the tick() and render() calls
 * in {@link gl2.main.Game Game}, and for easy profiling of the overall
 * performance.
 * 
 * @author Heiko Ribberink
 * @see gl2.main.Game
 */

public enum Time {
	;
	/**
	 * Determines the <b> <u> minimal</u> </b> amount of time between the start of
	 * each frame and the start of the following frame. <br>
	 * The multiplicative inverse of this value is equal to the <b> <u> maximum</u>
	 * </b> amount of frames per second
	 * 
	 * @see gl2.main.Game
	 */
	public static double pursuedFrameDeltaTime = 1.0d / 120;
	/**
	 * Determines the <b> <u> minimal</u> </b> amount of time between the start of
	 * each tick() call and the start of the following tick() call. <br>
	 * The multiplicative inverse of this value is equal to the <b> <u> maximum</u>
	 * </b> amount of tick() calls per second.
	 * 
	 * @see gl2.main.Game
	 */
	public static double tickDeltaTime = 1.0d / 120;
	/**
	 * The actual amount of time between the start of the last render() call (or
	 * frame) and the start of the current render() call (or frame). The
	 * multiplicative inverse of this value is equal to the amount of frames per
	 * second.
	 * 
	 * @see gl2.main.Game
	 */
	public static double frameDeltaTime = 0d;
	/**
	 * Scales the amount of tick() calls per second without scaling the
	 * {@link #tickDeltaTime}, effectively scaling the game speed. Since it is not
	 * necessary to compute many more tick() calls per second, it is recommended to
	 * scale the {@link #tickDeltaTime} accordingly, or use
	 * {@link #scaleTime(double)}.
	 * 
	 * @see gl2.main.Game
	 */
	public static double timeScale = 1d;
	/**
	 * The amount of time since the start of the game.
	 */
	public static double timeElapsed = 0d;
	/**
	 * The amount of time since the start of the game. This may differ very slightly
	 * from timeElapsed, since this value is determined with the tick() calls.
	 */
	public static double tickTimeElapsed = 0d;
	/**
	 * The amount of frames that have been rendered since the start of the game.
	 */
	public static long frames = 0;
	/**
	 * The amount of tick() calls since the start of the game.
	 */
	public static long ticks = 0;
	/**
	 * Currently does nothing.
	 */
	public static int minimalFrames = 4;

	/**
	 * Use this function to scale the game speed. <br>
	 * Values above 1 will speed up the game, whilst values between 1 and 0 will
	 * slow down the game. If you wish to pause the game, use {@link #timeScale}
	 * instead.
	 * 
	 * @param scalar
	 */
	public static void scaleTime(double scalar) {
		timeScale *= scalar;
		tickDeltaTime *= scalar;
	}
}
