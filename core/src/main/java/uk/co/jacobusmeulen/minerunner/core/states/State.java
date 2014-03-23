package uk.co.jacobusmeulen.minerunner.core.states;

import java.util.List;

public interface State
{
   // State Management
   public List<State> getNextStates();
   public State getPreviousState();
   
   // Ease up management of menus
   public State getNextStateById(String id);
   
   //
   
   //
   public void start();
   public int update();
   public void end();
   
   // Input
   public boolean touchDown(int x, int y, int p, int b);
   public boolean touchUp(int x, int y, int p, int b);
   public boolean touchDragged(int x, int y, int p);
   public boolean touchMoved(int x, int y);
   public boolean scrolled(int amount);
   public boolean keyDown(int keycode);
}
