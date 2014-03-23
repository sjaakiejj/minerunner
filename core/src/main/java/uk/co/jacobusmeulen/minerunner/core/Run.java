package uk.co.jacobusmeulen.minerunner.core;

// MineRunner
import uk.co.jacobusmeulen.minerunner.core.states.StateManager;
import uk.co.jacobusmeulen.minerunner.core.states.global.GameplayState;


// LibGDX
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Run implements ApplicationListener {
	Texture texture;
	SpriteBatch batch;
	float elapsed;
	StateManager m_state_manager;

	@Override
	public void create () {
		//texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
		GameSettings.load(); // Load settings from file
		
		batch = new SpriteBatch();
		
		
		m_state_manager = new StateManager(new GameplayState(null));
		m_state_manager.start();
		
		// TODO: This should be a multiplexer to deal with menus
		Gdx.input.setInputProcessor(m_state_manager);
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
	//	batch.draw(texture, 100+100*(float)Math.cos(elapsed), 100+25*(float)Math.sin(elapsed));
		m_state_manager.update(); // Update the game, render etc.
		batch.end();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
