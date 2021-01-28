package gl2.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.text.DecimalFormat;

import debug.TestObject;
import gl2.input.FocusManager;
import gl2.input.KeyManager;
import gl2.input.MouseManager;
import gl2.math.Transform2D;
import gl2.object.GameObject;
import gl2.object.Handler;
import gl2.object.component.camera.Camera;
import gl2.object.component.texture.SpriteTexture;
import gl2.object.instances.DebugCamera;
import gl2.rendering.SceneRenderer;
import gl2.rendering.Sprite;

public class Game implements Runnable {

	protected Window window;
	protected Handler handler;
	protected Thread thread;

	private boolean running = false;

	protected static Game GAME;
	protected static GameState STATE = GameState.Game;

	public static void main(String[] args) {
		GAME = new Game();
	}

	/**
	 * Creates a new Game
	 */

	public Game() {
		thread = new Thread(this);
		start();
	}

	/**
	 * This function will start the execution of the main loop.
	 */
	public void start() {
		running = true;
		thread.start();
	}

	/**
	 * This function will stop the execution of the main loop. All resources that
	 * should be released, threads that should be stopped, etc. should be handled
	 * here. The main loop should <b> NEVER </b> be ended in another way.
	 */
	public void stop() {
		running = false;
		window.dispose();
	}

	/**
	 * This function should be used to initialize all required resources.
	 */
	protected void init() {
		// Standard stuff, should not be modified (begin)
		window = new Window("Game Library 2.0", 1920 / 2, 1080 / 2);
		handler = new Handler();
		// Add Swing components
		window.getCanvas().addKeyListener(new KeyManager());
		MouseManager m = new MouseManager();
		window.getCanvas().addMouseListener(m);
		window.getCanvas().addMouseWheelListener(m);
		window.getCanvas().addFocusListener(new FocusManager());

		setup();
	}

	/**
	 * Create objects, components, etc. used at the start of the game here. Be sure
	 * to set the MainRenderer field in {@link gl2.object.Handler Handler}, if you
	 * wish to render the scene view.
	 */

	protected void setup() {
		// Create objects, components, etc. used at the beginning of the game.

		// An object to hold the camera for the main scene renderer must always be
		// created, so this will be the
		// default.
		GameObject go = new DebugCamera(new Transform2D(0, 0, 1, 1, 0), GameState.values());
		Camera cam = new Camera();
		go.addComponents(cam);
		handler.addGameObject(go);
		handler.setMainRenderer(new SceneRenderer(cam, 0));
		try {
			Sprite.loadSprite("debug/res/TestObject.png", "TestObject");
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.addGameObject(new TestObject(new Transform2D(1, 0, 1, 1, 0), 0, GameState.Game,
				new SpriteTexture(0, Sprite.getSpriteByName("TestObject"), new Transform2D())));
	}

	private final void render() {
		BufferStrategy bs = window.getCanvas().getBufferStrategy();
		if (bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, window.getWidth(), window.getHeight());
		g.setColor(Color.white);

		AffineTransform old = g.getTransform();
		// Translates the window to make the origin (0, 0) the center of the screen, and
		// then scales the window to make the virtual positive-y-direction up in the
		// real world. This is not necessary, but fixes a few counterintuitive things.
		g.translate(window.getWidth() >> 1, window.getHeight() >> 1);
		g.scale(1, -1);

		handler.render(g);

		g.setTransform(old);

		g.setColor(Color.white);

		// Visualise debug values
		renderDebugInfo(g);

		// finish
		g.dispose();
		bs.show();
	}

	protected void renderDebugInfo(Graphics2D g) {
		DecimalFormat format = new DecimalFormat("#0.####");
		g.drawString("#Objects: " + handler.getGameObjects().size(), 10, 20);
		g.drawString("#EnabledObjects: " + handler.getEnabledGameObjects().size(), 10, 40);
		g.drawString("TickDelta: " + format.format(Time.tickDeltaTime), 10, 60);
		g.drawString("RenderDelta: " + format.format(Time.frameDeltaTime), 10, 80);
		g.drawString("PursuedRenderDelta: " + format.format(Time.pursuedFrameDeltaTime), 10, 100);
		g.drawString("TimeElapsed: " + format.format(Time.timeElapsed), 10, 120);
		g.drawString("#Ticks: " + Time.ticks + " (" + format.format((double) Time.ticks / Time.timeElapsed) + " per second)", 10, 140);
		g.drawString("#Frames: " + Time.frames + " (" + format.format((double) Time.frames / Time.timeElapsed) + " per second)", 10, 160);
	}

	private final void tick() {
		handler.tick();
	}

	private double[] tickHistory;
	private double[] renderHistory;

	@Override
	public void run() {
		init();

		double ns = 1 / 1000000000d;
		double lastTime = (double) System.nanoTime();
		double lastFrameTime = lastTime;
		double tickTimeLeft = Time.tickDeltaTime;
		double renderTimeLeft = Time.pursuedFrameDeltaTime;

		// Render time history for averaging and debugging
		int tickHistoryLength = 30;
		int renderHistoryLength = 30;
		tickHistory = new double[tickHistoryLength];
		renderHistory = new double[renderHistoryLength];
		int tickHistoryPointer = 0;
		int renderHistoryPointer = 0;

		double debugTimeLeft = 0;
		double debugDeltaTime = 1d / 2d;

		while (running) {

			long now = System.nanoTime();
			double delta = (double) (now - lastTime) * ns;
			lastTime = now;

			// Update Time variables
			Time.timeElapsed += delta;

			tickTimeLeft += delta * Time.timeScale;
			
			// Tick loop
			while (tickTimeLeft >= Time.tickDeltaTime) {
				long startNanos = System.nanoTime();

				tick();

				tickTimeLeft -= Time.tickDeltaTime;

				// Update Time variables
				Time.ticks += 1;
				Time.tickTimeElapsed += Time.tickDeltaTime;

				// Update debug values
				double tickTime = (double) (System.nanoTime() - startNanos) * ns;
				tickHistory[tickHistoryPointer] = tickTime;
				tickHistoryPointer++;
				if (tickHistoryPointer >= tickHistoryLength)
					tickHistoryPointer = 0;
			}

			// Render loop
			renderTimeLeft += delta;
			if (running && (renderTimeLeft >= Time.pursuedFrameDeltaTime)) {
				long startNanos = System.nanoTime();

				now = System.nanoTime();

				render();

				renderTimeLeft -= Time.pursuedFrameDeltaTime;
				if (renderTimeLeft > 1)
					renderTimeLeft = 1;

				// Update Time variables
				Time.frames += 1;
				double frameDelta = (double) (now - lastFrameTime) * ns;
				lastFrameTime = now;
				Time.frameDeltaTime = frameDelta;

				// Update debug values
				double renderTime = (double) (System.nanoTime() - startNanos) * ns;
				renderHistory[renderHistoryPointer] = renderTime;
				renderHistoryPointer++;
				if (renderHistoryPointer >= renderHistoryLength)
					renderHistoryPointer = 0;
			}

			// Print debug values
			debugTimeLeft += delta;
			if (running && (debugTimeLeft >= debugDeltaTime)) {
				double sum = 0;
				for (int i = 0; i < tickHistoryLength; i++) {
					if (tickHistory[i] == 0d) {
						sum += (sum / (i + 1)) * tickHistoryLength;
						break;
					}
					sum += tickHistory[i];
				}
				double tickCost = sum / tickHistoryLength;
				sum = 0;
				for (int i = 0; i < renderHistoryLength; i++) {
					if (renderHistory[i] == 0d) {
						sum += (sum / (i + 1)) * renderHistoryLength;
						break;
					}
					sum += renderHistory[i];
				}
				double renderCost = sum / renderHistoryLength;
				System.out.println("TickCost: " + tickCost + "; RenderCost: " + renderCost);

				debugTimeLeft -= debugDeltaTime;
				if (debugTimeLeft > 1)
					debugTimeLeft = 1;
			}
		}
	}

	public static GameState getState() {
		return STATE;
	}

	public static void setState(GameState state) {
		STATE = state;
	}

	public static Game getGame() {
		return GAME;
	}

	public Window getWindow() {
		return window;
	}

	public Handler getHandler() {
		return handler;
	}

	public Thread getThread() {
		return thread;
	}

	public boolean isRunning() {
		return running;
	}
}
