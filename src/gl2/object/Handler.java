package gl2.object;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import gl2.main.Game;
import gl2.main.GameState;
import gl2.rendering.RenderManager;
import gl2.rendering.SceneRenderer;

/**
 * A class that handles all GameObjects in scene.
 * 
 * @author Heiko Ribberink
 * @see gl2.object.GameObject
 *
 */

public class Handler {

	protected ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	protected ArrayList<GameObject> enabledGameObjects = new ArrayList<GameObject>();

	protected ArrayList<RenderManager> renderManagers = new ArrayList<RenderManager>();
	private SceneRenderer mainRenderer;

	public Handler() {
		GameObject.handler = this;
	}

	public void removeAll() {
		gameObjects.clear();
		enabledGameObjects.clear();
	}

	/**
	 * Calls the GameObject.manageTick()
	 */

	public void tick() {
		for (GameObject o : enabledGameObjects) {
			for (GameState state : o.states) {
				if (state.equals(Game.getState())) {
					o.manageTick();
					break;
				}
			}
		}
	}

	public void render(Graphics2D g) {
		Collections.sort(renderManagers);
		for (RenderManager r : renderManagers) {
			r.manageRender(g);
		}
		if(mainRenderer != null) mainRenderer.render(g);
		for (GameObject o : enabledGameObjects) {
			for (GameState state : o.states) {
				if (state.equals(Game.getState())) {
					o.manageRender(g);
					break;
				}
			}
		}
	}

	public void addGameObject(GameObject o) {
		gameObjects.add(o);
		if (o.isEnabled()) {
			addGameObjectToEnabled(o);
		}
	}

	protected void addGameObjectToEnabled(GameObject o) {
		int index = enabledGameObjects.size();
		enabledGameObjects.add(o);
		o.onAddObjectToEnabled(index);
	}

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	public ArrayList<GameObject> getEnabledGameObjects() {
		return enabledGameObjects;
	}

	public SceneRenderer getMainRenderer() {
		return mainRenderer;
	}

	public void setMainRenderer(SceneRenderer mainRenderer) {
		this.mainRenderer = mainRenderer;
	}

	public ArrayList<RenderManager> getRenderManagers() {
		return renderManagers;
	}

}
