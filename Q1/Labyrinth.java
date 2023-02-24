import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.Semaphore;

public class Labyrinth {

  public ReentrantLock lock;
  public AtomicInteger cupcakesEaten;
  public AtomicIntegerArray guestsInvited;
  public AtomicInteger currentGuestInvited;
  public int max;
  public Semaphore mutex;
  public AtomicBoolean cupcake;

  public Labyrinth(int threadNum)
  {
    lock = new ReentrantLock();
    cupcakesEaten = new AtomicInteger();
    max = threadNum;
    currentGuestInvited = new AtomicInteger(max);
    guestsInvited = new AtomicIntegerArray(max);
    mutex = new Semaphore(1);
    cupcake = new AtomicBoolean(true);
  }

  public void startMaze() {
      ArrayList<Thread> guests = new ArrayList<Thread>();
      Random random = new Random();
      int nextGuest;

      for (int i = 0; i < max; i++)
      {
        if (i == 0)
        {
          Thread newGuest = new Thread(new CountingGuest(i));
          guests.add(newGuest);
          newGuest.start();
        }
        else
        {
          Thread newGuest = new Thread(new Guest(i));
          guests.add(newGuest);
          newGuest.start();
        }
      }

      while (cupcakesEaten.get() < max)
      {
        nextGuest = random.nextInt(max);
        // currentGuestInvited.compareAndSet(max, nextGuest);
        // System.out.println(nextGuest);
        guestsInvited.compareAndSet(nextGuest, 0, 1);
      }

      for (Thread t : guests)
      {
        try
        {
          t.join();
        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println(e);
        }
      }

      return;
  }

  public class CountingGuest implements Runnable
  {
    public boolean readyToEnter;
    public boolean hasEatenCupcake;
    public int guestNum;

    public CountingGuest(int num)
    {
      this.readyToEnter = false;
      this.hasEatenCupcake = false;
      this.guestNum = num;
    }

    public void run()
    {

      try
      {
        while (cupcakesEaten.get() < max)
        {
          if (guestsInvited.compareAndSet(guestNum, 1, 0))
          {
            /*
            lock.lock();
            try
            {
              runMaze();
            }
            finally
            {
              lock.unlock();
            }
            */

            mutex.acquire();
            runMaze();
            mutex.release();
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
        // int temp = cupcakesEaten.incrementAndGet();
        // System.out.println(temp + " " + guestNum);
        hasEatenCupcake = true;
        cupcake.getAndSet(false);
      }

      public void replaceCupcake()
      {
        int temp = cupcakesEaten.incrementAndGet();
        // System.out.println(temp + " " + guestNum);
        cupcake.getAndSet(true);
      }

      public void runMaze()
      {
        if (!hasEatenCupcake && cupcake.get())
        {
          eatCupcake();
        }
        else if (!cupcake.get())
        {
          replaceCupcake();
        }
      }
  }

  public class Guest implements Runnable
  {

    public boolean readyToEnter;
    public boolean hasEatenCupcake;
    public int guestNum;

    public Guest(int num)
    {
      this.readyToEnter = false;
      this.hasEatenCupcake = false;
      this.guestNum = num;
    }

    public void run()
    {

      try
      {
        while (cupcakesEaten.get() < max)
        {
          if (guestsInvited.compareAndSet(guestNum, 1, 0))
          {
            /*
            lock.lock();
            try
            {
              runMaze();
            }
            finally
            {
              lock.unlock();
            }
            */

            mutex.acquire();
            runMaze();
            mutex.release();
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
        // System.out.println(temp + " " + guestNum);
        hasEatenCupcake = true;
        cupcake.getAndSet(false);
      }

      public void runMaze()
      {
        if (!hasEatenCupcake && cupcake.get())
        {
          eatCupcake();
        }
      }

  }


}
