import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.Semaphore;

public class Showroom
{

  public Semaphore mutex;
  public AtomicBoolean inParty;
  public int guestTotal;
  public AtomicInteger visitCount;

  public Showroom(int size)
  {
    mutex = new Semaphore(1);
    inParty = new AtomicBoolean(true);
    guestTotal = size;
    visitCount = new AtomicInteger();
  }

  public class Guest implements Runnable
  {
    public boolean hasVisited;

    public Guest()
    {
      hasVisited = false;
    }

    public void run()
    {
      try
      {
        while (inParty.get())
        {
          if (mutex.tryAcquire())
          {
            // System.out.println("Visited");
            if (hasVisited == false)
            {
              visitCount.getAndIncrement();
              hasVisited = true;
            }
            mutex.release();
          }
        }
      }
      catch (Exception e)
      {
          // Throwing an exception
          System.out.println(e);
      }
    }

  }

  public void partyTime()
  {
    ArrayList<Thread> guests = new ArrayList<Thread>();
    for (int i = 0; i < guestTotal; i++)
    {
      Thread newGuest = new Thread(new Guest());
      guests.add(newGuest);
      newGuest.start();
    }
    /*
    try
    {
      Thread.sleep(3000);
    }
    catch (Exception e)
    {
        // Throwing an exception
        System.out.println(e);
    }
    */

    while (visitCount.get() < guestTotal)
    {
      continue;
    }

    inParty.getAndSet(false);
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


}
