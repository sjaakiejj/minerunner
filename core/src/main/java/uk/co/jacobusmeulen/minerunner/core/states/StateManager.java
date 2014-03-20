package uk.co.jacobusmeulen.minerunner.core.states;


public class StateManager implements InputProcessor
{
   private State m_current_state;

   public StateManager(State initialState)
   {
     m_current_state = initialState;
   }

   public void start()
   {
     m_current_state.start();
   }
   
   public void update()
   {
     int nextState = m_current_state.update();
     
     if(nextState >= 0)
     {
        m_current_state.end();
	m_current_state = m_current_state.getNextStateById(nextState);
	m_current_state.start();
     }
   }
   
   // ******************************************************************************* 
   // INPUT 
   // *******************************************************************************
   @Override
   public boolean touchDown (int x, int y, int pointer, int button) {
      return m_current_state.touchDown(x,y,pointer,button);
   }

   @Override
   public boolean touchUp (int x, int y, int pointer, int button) {
      return m_current_state.touchUp(x,y,pointer,button);
   }

   @Override
   public boolean touchDragged (int x, int y, int pointer) {
      return m_current_state.touchDragged(x,y,pointer);
   }

   @Override
   public boolean touchMoved (int x, int y) {
      return m_current_state.touchMoved(x,y);
   }

   @Override
   public boolean scrolled (int amount) {
      return m_current_state.scrolled(amount);
   }
   
   
   // State Manager only deals with touch. Any keys are debug controls.
   @Override
   public boolean keyDown (int keycode) {
      return false;
   }

   @Override
   public boolean keyUp (int keycode) {
      return false;
   }

   @Override
   public boolean keyTyped (char character) {
      return false; 
   }

}
