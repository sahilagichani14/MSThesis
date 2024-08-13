public class MemoryUsage
{
    public static void main (String [] args) throws Exception
    {
        run ();
        memoryUsed ();
        final int count = 100000;
        Object [] object = new Object [count];
        long heap1 = 0;
        for (int i = -1; i < count; ++ i)
        {
            Object obj = null;
            obj = new Object ();
            if (i >= 0)
                object [i] = obj;
            else
            {
                obj = null;
                run ();
                heap1 = memoryUsed ();
            }
        }
        run ();
        long heap2 = memoryUsed ();
        System.out.println ("'before' heap: " + heap1 +
                ", 'after' heap: " + heap2);
        System.out.println ("heap delta: " + (heap2 - heap1) );
        for (int i = 0; i < count; ++ i) object [i] = null;
        object = null;
    }
    static void run () throws Exception
    {
        for (int j = 0; j < 4; ++ j) _run ();
    }
    static void _run () throws Exception
    {
        long mem1 = memoryUsed (), mem2 = Long.MAX_VALUE;
        for (int i = 0; (mem1 < mem2) && (i < 500); ++ i)
        {
            runtime.runFinalization ();
            runtime.gc ();
            Thread.currentThread ().yield();
            mem2 = mem1;
            mem1 = memoryUsed ();
        }
    }
    static long memoryUsed ()
    {
        return runtime.totalMemory () - runtime.freeMemory ();
    }

    static final Runtime runtime = Runtime.getRuntime ();
}