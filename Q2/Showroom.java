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

  public Showroom()
  {
    mutex = new Semaphore(1);
    inParty = new AtomicBoolean(true);

  }

  public class Guest implements Runnable
  {

    public Guest()
    {

    }

    public void run()
    {
      try
      {
        while (inParty.get())
        {
          if (mutex.tryAcquire())
          {
            System.out.println("Visited");
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
    for (int i = 0; i < 100; i++)
    {
      Thread newGuest = new Thread(new Guest());
      guests.add(newGuest);
      newGuest.start();
    }
    try
    {
      Thread.sleep(3000);
    }
    catch (Exception e)
    {
        // Throwing an exception
        System.out.println(e);
    }
    inParty.getAndSet(false);
    return;
  }


}
