package uk.co.jacobusmeulen.minerunner.core.states.global;

import java.util.List;
import java.util.ArrayList;

import uk.co.jacobusmeulen.minerunner.core.states.State;

public class GameplayState implements State
{
   public List<State> m_next_states;
   public State m_prev_state;
   
   // This is by default the state itself (identified as -1). 
   // If the state needs to change, it will be changed
   // to the appropriate id
   public int update_state_id;
   
   public MainMenuState(State prevState)
   {
      m_prev_state = prevState;
      m_next_states = new ArrayList<State>();
      update_state_id = -1:
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
   }
  
   public int update()
   {
      // Render if animated
      
      // Deal with inpu
      
      return update_state_id;
   }
   
   public void end()
   {
      // do nothing
   }
   
   
   public void handleInput()
   {
      
   }
   
   public void handleGameworld()
   {
   }
   
   public void handleGraphics()
   {
   }
   
   
   // Input
   public boolean touchDown(int x, int y, int p, int b)
   {
      // TODO: Extend to multiple buttons rather than full screen touch
      // update_state_id = 0;
   }
   
   // This state is not concerned with the following functionality
   public boolean touchUp(int x, int y, int p, int b)
   {
      return false;
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
}
