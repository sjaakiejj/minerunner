package uk.co.jacobusmeulen.minerunner.core.levels;

import uk.co.jacobusmeulen.minerunner.core.states.levels.LevelCellState;
import uk.co.jacobusmeulen.minerunner.core.GameSettings;

// LibGDX
import com.badlogic.gdx.math.Vector2;  
// Box2D
import com.badlogic.gdx.physics.box2d.Body;  
import com.badlogic.gdx.physics.box2d.BodyDef;  
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;  
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;  
import com.badlogic.gdx.physics.box2d.CircleShape;  
import com.badlogic.gdx.physics.box2d.Fixture;  
import com.badlogic.gdx.physics.box2d.FixtureDef;  
import com.badlogic.gdx.physics.box2d.PolygonShape;  
import com.badlogic.gdx.physics.box2d.World;   

public class LevelCell
{
  private LevelCellState m_state;
  private static float m_cell_width = 0.0f;
  
  public LevelCell(LevelCellState state, World world, int offset)
  {
      if( m_cell_width == 0.0f )
        m_cell_width = (float)GameSettings.getInt("screen_width") / 20.0f;
  
      if( state != LevelCellState.ABYSS )
      {
	BodyDef groundBodyDef =new BodyDef();  
	// Set its world position
	Vector2 pos = new Vector2((int)m_cell_width * offset * 2, 10);
	
	if( state == LevelCellState.PLATFORM )
	  pos.y = 20;
	
	groundBodyDef.position.set(pos);  

	// Create a body from the defintion and add it to the world
	Body groundBody = world.createBody(groundBodyDef);  

	// Create a polygon shape
	PolygonShape groundBox = new PolygonShape();  
	groundBox.setAsBox(m_cell_width, 10.0f); 
	groundBody.createFixture(groundBox, 0.0f).setUserData("LevelCell:"+offset); 
	// Clean up after ourselves
	groundBox.dispose();
      }
      
      m_state = state;
  }
  
  public LevelCellState getState()
  {
    return m_state;
  }
  
  // Initial
  public void render(int index)
  {
  }
  
  // Use offset + index to determine location
  public void render(int index, int offset)
  {
    // Render at position offset + index * width
  }
}
