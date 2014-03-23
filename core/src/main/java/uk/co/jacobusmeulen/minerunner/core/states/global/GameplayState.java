package uk.co.jacobusmeulen.minerunner.core.states.global;

import java.util.List;
import java.util.ArrayList;

import uk.co.jacobusmeulen.minerunner.core.DebugLogger;
import uk.co.jacobusmeulen.minerunner.core.GameSettings;
import uk.co.jacobusmeulen.minerunner.core.states.State;
import uk.co.jacobusmeulen.minerunner.core.levels.LevelManager;
import uk.co.jacobusmeulen.minerunner.core.levels.LevelCell;
import uk.co.jacobusmeulen.minerunner.core.states.levels.LevelCellState;

// LibGDX
 import com.badlogic.gdx.Gdx;  
 import com.badlogic.gdx.graphics.GL10;  
 import com.badlogic.gdx.graphics.OrthographicCamera;  
 import com.badlogic.gdx.math.Vector2;  
 import com.badlogic.gdx.physics.box2d.Body;  
 import com.badlogic.gdx.physics.box2d.BodyDef;  
 import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;  
 import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;  
 import com.badlogic.gdx.physics.box2d.CircleShape;  
 import com.badlogic.gdx.physics.box2d.Fixture;  
 import com.badlogic.gdx.physics.box2d.FixtureDef;  
 import com.badlogic.gdx.physics.box2d.PolygonShape;  
 import com.badlogic.gdx.physics.box2d.World;   
 import com.badlogic.gdx.physics.box2d.ContactListener;  
 import com.badlogic.gdx.physics.box2d.ContactImpulse;  
 import com.badlogic.gdx.physics.box2d.Manifold;  
 import com.badlogic.gdx.physics.box2d.Contact;  
 import com.badlogic.gdx.Input;


public class GameplayState implements State
{
   private List<State> m_next_states;
   private State m_prev_state;
   
   // This is by default the state itself (identified as -1). 
   // If the state needs to change, it will be changed
   // to the appropriate id
   private int update_state_id;
   
   // Hero
   private boolean m_touch_down;
   private GameHero m_hero;
   
   // World
   private LevelCell[] m_view;
   private LevelCell[] m_next_view;
   private int m_view_index;
   private OrthographicCamera m_camera;
   
   // Physics
   private World m_physics_world; // Box2D representation of the world
   
   // Debugging
   Box2DDebugRenderer m_debug_renderer;
   private boolean m_debug_mode;
   
   public GameplayState(State prevState)
   {
      m_prev_state = prevState;
      m_next_states = new ArrayList<State>();
      update_state_id = -1;
      m_touch_down = false;
      m_debug_mode = false;
      
      m_camera = new OrthographicCamera(GameSettings.getInt("screen_width"),
      					GameSettings.getInt("screen_height"));
      m_camera.position.set(0, 0, 0); // This gets replaced by hero position
   }
   
   // State Management
   public List<State> getNextStates()
   {
      return m_next_states;
   }
   
   public State getPreviousState()
   {
      return m_prev_state;
   }
   
   // Ease up management of menus
   public State getNextStateById(String id)
   {
      return null; //TODO: implement 
   }
   
   //
   public void start()
   {
      // Add a gameover state
      //m_next_states.add(new GameplayState(this));
      m_physics_world = new World(new Vector2(0,GameSettings.getInt("gravity")), true);
      m_hero = new GameHero(m_physics_world);
      LevelManager.init(m_physics_world);
      
      // Only bother initialising if we're actually in debug mode
     // if( GameSettings.DEBUG ) 
     // {
        m_debug_renderer = new Box2DDebugRenderer();
     // }
      
      m_view = LevelManager.generateInitial();
      m_next_view = LevelManager.generateNextView( m_view[19].getState() );
   }
  
   public int update()
   {
      // Render if animated
      
      // Deal with inpu
      handleInput();
      
      // Physics and stuff
      handleGameworld();
      
      // Update Graphics
      handleGraphics();
      
      // Handle Sounds
      handleSounds();
      
      return update_state_id;
   }
   
   public void end()
   {
      // do nothing
   }
   
   
   public void handleInput()
   {
      // Only one action for our hero
      if( m_touch_down && !m_hero.isJumping() )
      {
        m_hero.jump();
      }
   }
   
   public void handleGameworld()
   {
      // Update Level
      if( m_view_index >= 9 )
      {
         // Move to the left
         for(int i = m_view_index; i < 20; i++)
	 {
	   m_view[i - m_view_index] = m_view[i];
	 }
	 
	 // Move cells from next view into current view
         for(int i = 0; i < 10; i++)
	 {
	   m_view[m_view_index + i] = m_next_view[i];
	 }
	 
	 // Generate new cells for the next view. This could be moved
	 // to then next frame if the performance is needed
	 m_next_view = LevelManager.generateNextView(m_view[19].getState());
	 m_view_index = 0;
      }
      
      // Follow the hero
      m_camera.position.x = m_hero.getPosition().x;
      m_camera.position.y = m_hero.getPosition().y;
      
      // Update Physics
      m_physics_world.step(1/45f, // Time Step - 1/45 for phones
      			   6, // Velocity Iterations (Default: 6)
			   2); // Position Iterations (Default: 2)
			   
      m_hero.update();
      m_camera.update();
   }
   
   public void handleGraphics()
   {
      for(int i = 0; i < 20; i++)
      {
        m_view[i].render(i);
      }
      
      m_hero.render();
      
      // Render the debug world if we're debugging
      if( m_debug_mode )
      {
        m_debug_renderer.render(m_physics_world, m_camera.combined);
      }
   }
   
   public void handleSounds()
   {
   }
   
   // Input
   public boolean touchDown(int x, int y, int p, int b)
   {
      // TODO: Extend to multiple buttons rather than full screen touch
      // update_state_id = 0;
      m_touch_down = true;
      return true;
   }
   
   // This state is not concerned with the following functionality
   public boolean touchUp(int x, int y, int p, int b)
   {
      m_touch_down = false;
      return true;
   }
   public boolean touchDragged(int x, int y, int p)
   {
      return false;
   }
   public boolean touchMoved(int x, int y)
   {
      return false;
   }
   public boolean scrolled(int amount)
   {
      return false;
   }
   
   public boolean keyDown(int keycode)
   {
      if( keycode == Input.Keys.D )
        m_debug_mode = !m_debug_mode;
      return true;
   }

   /*
    * GAME HERO
    */
   private class GameHero
   {
      private Body m_body;
      private double m_max_velocity;
      private int m_foot_contacts;

      private double m_jump_force;
      private double m_accelleration;
      public GameHero(World world)
      {
	BodyDef bodyDef = new BodyDef();
	
	bodyDef.type = BodyType.DynamicBody;
	bodyDef.position.set(0,100); // TODO: Should depend on level
	bodyDef.fixedRotation = true;


	PolygonShape heroBox = new PolygonShape();
	heroBox.setAsBox( (float)GameSettings.getDouble("hero_width"), 
     			  (float)GameSettings.getDouble("hero_height") );

	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.shape = heroBox;
	fixtureDef.density = (float)GameSettings.getDouble("hero_density");
	fixtureDef.friction = (float)GameSettings.getDouble("hero_friction");
	fixtureDef.restitution = (float)GameSettings.getDouble("hero_restitution"); 


	m_body = world.createBody(bodyDef);

	m_body.createFixture(fixtureDef);

	heroBox.setAsBox(0.3f, 0.3f, new Vector2(0,(int)-GameSettings.getDouble("hero_height")), 0);
	fixtureDef.isSensor = true;
	m_body.createFixture(fixtureDef).setUserData( 3 );

	heroBox.dispose();


	// Setup final things - maximum velocity, listeners
	m_max_velocity = GameSettings.getDouble("hero_max_velocity");
	m_foot_contacts = 0;
	m_jump_force = GameSettings.getDouble("hero_jump_force");
	m_accelleration = GameSettings.getDouble("hero_accelleration");

	world.setContactListener(new ContactListener(){
     	   @Override
	   public void beginContact(Contact contact) {
                   Fixture fixtureA = contact.getFixtureA();
                   Fixture fixtureB = contact.getFixtureB();
                  // Gdx.app.log("beginContact", "between " + fixtureA.getUserData() + " and " + fixtureB.getUserData());
		   
		   String uData = (""+fixtureA.getUserData());
		   String uData2 = (""+fixtureB.getUserData());
		   if( uData.equals("3") )
			   m_foot_contacts ++;
		   if( uData2.length() > 10 && uData2.substring(0,10).equals("LevelCell:"))
		   {
		   	m_view_index = 1+Integer.parseInt(uData2.substring(10)) % 10;
			DebugLogger.log("info","GameplayState","Currently on level cell " + m_view_index);
		   }
               }

               @Override
               public void endContact(Contact contact) {
                   Fixture fixtureA = contact.getFixtureA();
                   Fixture fixtureB = contact.getFixtureB();
                   
		   if( (""+fixtureA.getUserData()).equals("3") )
			   m_foot_contacts --;
               }

               @Override
               public void preSolve(Contact contact, Manifold oldManifold) {
               }

               @Override
               public void postSolve(Contact contact, ContactImpulse impulse) {
               }

	});
      }

      public void update()
      { 
	Vector2 vel = m_body.getLinearVelocity();
	Vector2 pos = m_body.getPosition();

	// Speed up as long as max velocity has not been reached.
	if( vel.x < m_max_velocity  )
	{
           m_body.setLinearVelocity(new Vector2((int)m_accelleration, 
	   				vel.y));
	   //vel.x = (int)m_max_velocity;
	 // m_body.setLinearVelocity(vel);
	}
      }

      public void jump()
      { 
	Vector2 vel = m_body.getLinearVelocity();
	Vector2 pos = m_body.getPosition(); 

	if(m_foot_contacts > 0)
	{
	  m_body.setLinearVelocity( vel.x, (float)m_jump_force );
	 /* m_body.applyLinearImpulse( 0,
	  			     (int)m_jump_force,
	  			     pos.x,
				     pos.y, true );
	  m_body.applyLinearImpulse( (float)m_accelleration,
	  			     0,
	  			     pos.x,
				     pos.y, true );*/
	}
      }
      
      public Vector2 getPosition()
      {
        return m_body.getPosition();
      }
      
      public void render()
      {
      }

      public boolean isJumping()
      {
	return false;
      }
   }}

