package uk.co.jacobusmeulen.minerunner.core.levels;

import uk.co.jacobusmeulen.minerunner.core.states.levels.LevelCellState;
import uk.co.jacobusmeulen.minerunner.core.levels.LevelCell;
import uk.co.jacobusmeulen.minerunner.core.DebugLogger;
import uk.co.jacobusmeulen.minerunner.core.GameSettings;
// Java Imports
import java.util.Random;

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

public class LevelManager
{
  private static Random m_rand;
  private static int m_cell_count;
  
  private static double[][] m_state_probabilities;
  private static World m_world;
  
  public static void init(World world)
  {
    m_world = world;
    m_rand = new Random();
    m_state_probabilities = new double[4][];
    for(int i = 0; i < 4; i++)
     m_state_probabilities[i] = new double[4];
     
    m_cell_count = 0;
    String [] stateString = new String[]{"abyss","platform","ground","ground_and_platform"};
    
    for(int i = 0; i < 4; i++){
     for(int j = 0; j < 4; j++){
       DebugLogger.log("INFO", "LevelManager", "Getting " + stateString[i] + "_to_" + stateString[j]);
       m_state_probabilities[i][j] = GameSettings.getDouble(stateString[i] + "_to_" + stateString[j]);
     }
    }
  }

  public static LevelCell[] generateInitial()
  {
    LevelCell [] cells = new LevelCell[20];
    
    // Give the player a little bit of time to get ready
    for (int i = 0; i < 10; i++)
    { 
       cells[i] = new LevelCell( LevelCellState.GROUND, m_world, m_cell_count );
       m_cell_count++;
    }
    
    LevelCell [] nextCells = generateNextView( LevelCellState.GROUND );
    
    for (int i = 0; i < 10; i++)
    {
       cells[10+i] = nextCells[i];
    }
    
    return cells;
  }

  public static LevelCell[] generateNextView( LevelCellState state )
  {
    return generateFrom(state,10);
  }
  
  public static LevelCell[] generateFrom( LevelCellState state, int amount )
  {
    DebugLogger.log("info","LevelManager", "Generating new level cells");
    LevelCellState current = state;
    LevelCell [] generatedStates = new LevelCell[amount];
  
    //TODO: Setup for varying heights as well
    for(int i = 0; i < amount; i++)
    {
       double nextProb = m_rand.nextDouble();
       // TODO: Generate level here instead of just ground
       int potentialNext = LevelCellState.get(LevelCellState.ABYSS);
       
       while( nextProb > getProbability( LevelCellState.get(current),
       					 potentialNext ) )
       {
	 nextProb -= getProbability( LevelCellState.get(current), 
	 				potentialNext );
         potentialNext ++; 
       }
       
       generatedStates[i] = new LevelCell(LevelCellState.get(potentialNext),
       						 m_world, m_cell_count);
       current = generatedStates[i].getState();
       m_cell_count++;
    }
    
    return generatedStates;
  }
  
  private static double getProbability( int state, 
  					int next )
  {
    return m_state_probabilities[state][next];
  }
}

