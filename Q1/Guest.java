import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Guest implements Runnable
{

  public boolean readyToEnter;
  public boolean hasEatenCupcake;
  public ReentrantLock lock;

  public Guest(ReentrantLock newLock)
  {
    this.readyToEnter = false;
    this.hasEatenCupcake = false;
    this.lock = newLock;
  }

  public void run()
  {

    try
    {
      while (Cupcake.getCupcakesEaten() < 100)
      {
        if (readyToEnter)
        {
          lock.lock();
          try
          {
            runMaze();
          }
          finally
          {
            lock.unlock();
          }
          runMaze();
        }
      }
      return;
    }
    catch (Exception e)
    {
        // Throwing an exception
        System.out.println(e);
    }
  }

    public void eatCupcake()
    {
      Cupcake.eatCupcake();
      hasEatenCupcake = true;
    }

    public void runMaze()
    {
      if (!hasEatenCupcake)
      {
        eatCupcake();
      }
      this.readyToEnter = false;
    }

}
