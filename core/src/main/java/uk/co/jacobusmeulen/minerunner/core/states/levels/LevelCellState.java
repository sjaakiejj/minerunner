package uk.co.jacobusmeulen.minerunner.core.states.levels;

public enum LevelCellState
{
  ABYSS,
  PLATFORM,
  GROUND,
  PLATFORM_AND_GROUND;
  
  public static LevelCellState get(int index)
  {
    switch(index)
    {
      case 0: return ABYSS;
      case 1: return PLATFORM;
      case 2: return GROUND;
      case 3: return PLATFORM_AND_GROUND;
      default: return null;
    }
  }
  
  public static int get(LevelCellState state)
  {
    switch(state)
    {
      case ABYSS: return 0;
      case PLATFORM: return 1;
      case GROUND: return 2;
      case PLATFORM_AND_GROUND: return 3;
      default: return -1;
    }
  }
}
