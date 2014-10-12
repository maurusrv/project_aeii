package com.toyknight.aeii.gui.screen;

import com.toyknight.aeii.core.Game;
import com.toyknight.aeii.core.manager.GameManager;
import com.toyknight.aeii.core.manager.LocalGameManager;
import com.toyknight.aeii.core.manager.ManagerStateListener;
import com.toyknight.aeii.gui.AEIIApplet;
import com.toyknight.aeii.gui.Screen;
import com.toyknight.aeii.gui.animation.SwingAnimatingProvider;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author toyknight
 */
public class GameScreen extends Screen implements ManagerStateListener {

	private Game game;
	private LocalGameManager manager;
	private SwingAnimatingProvider animation_provider;

	private MapCanvas map_canvas;
	private TilePanel tile_panel;
	private StatusPanel status_panel;
	private ActionPanel action_panel;

	public GameScreen(Dimension size, AEIIApplet context) {
		super(size, context);
	}

	@Override
	public void initComponents() {
		this.setLayout(null);
		int apw = ts * 3 + ts / 4; //action panel width
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;
		this.tile_panel = new TilePanel(this, ts);
		this.tile_panel.setBounds(0, height - ts, ts, ts);
		this.add(tile_panel);
		this.status_panel = new StatusPanel(ts);
		this.status_panel.setBounds(ts, height - ts, width - apw - ts, ts);
		this.add(status_panel);
		Dimension canvas_size = new Dimension(width - apw, height - ts);
		this.map_canvas = new MapCanvas(canvas_size, getContext(), this);
		this.map_canvas.setPreferredSize(canvas_size);
		this.map_canvas.setBounds(0, 0, width - apw, height - ts);
		this.map_canvas.init();
		this.add(map_canvas);
		this.action_panel = new ActionPanel(this, ts);
		this.action_panel.setBounds(width - apw, 0, apw, height);
		this.action_panel.initComponents(ts);
		this.add(action_panel);
		this.animation_provider = new SwingAnimatingProvider(this, ts);
	}

	public void setGame(Game game) {
		this.game = game;
		this.manager = new LocalGameManager(game, animation_provider);
		this.manager.setStateListener(this);
		this.animation_provider.setGameManager(manager);
		this.map_canvas.setGameManager(manager);
		this.action_panel.setGameManager(manager);
		this.status_panel.setGameManager(manager);
		this.tile_panel.setGameManager(manager);
		this.action_panel.update();
		this.tile_panel.update();
		this.game.startTurn();
	}

	public Game getGame() {
		return game;
	}
	
	@Override
	public void managerStateChanged(GameManager manager) {
		action_panel.update();
		map_canvas.updateActionBar();
	}

	public ActionPanel getActionPanel() {
		return action_panel;
	}

	public TilePanel getTilePanel() {
		return tile_panel;
	}

	public MapCanvas getCanvas() {
		return map_canvas;
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		map_canvas.onKeyPress(e);
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		map_canvas.onKeyRelease(e);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	public void update() {
		map_canvas.update();
		tile_panel.update();
		manager.updateAnimation();
	}

}
