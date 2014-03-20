package uk.co.jacobusmeulen.minerunner.core.states.global;

import java.util.List;
import java.util.ArrayList;

import uk.co.jacobusmeulen.minerunner.core.states.State;

public class MainMenuState implements State
{
   public List<State> m_next_states;
   public State m_prev_state;
   
   public MainMenuState(State prevState)
   {
      m_prev_state = prevState;
      m_next_states = new ArrayList<State>();
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
      m_next_states.add(new GameplayState(this));
   }
  
   public void update()
   {
      // Render if animated
   }
   
   public void end()
   {
      // do nothing
   }
   
   // Input
   public boolean touchDown(int x, int y, int p, int b)
   {
      // TODO: Extend to multiple buttons rather than full screen to
   }
   
   public boolean touchUp(int x, int y, int p, int b);
   public boolean touchDragged(int x, int y, int p);
   public boolean touchMoved(int x, int y);
   public boolean scrolled(int amount);
}
