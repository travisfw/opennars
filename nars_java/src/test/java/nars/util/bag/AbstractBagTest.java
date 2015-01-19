package nars.util.bag;

import nars.core.Memory;
import nars.perf.BagPerf;
import nars.util.sort.SortedIndex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by me on 1/18/15.
 */
public class AbstractBagTest {
    public static int[] testRemovalPriorityDistribution(int capacity, boolean random, SortedIndex<BagPerf.NullItem> items) {
        CurveBag<BagPerf.NullItem,CharSequence> f = new CurveBag(capacity, CurveBagTest.curve, random, items);
        return testRemovalPriorityDistribution(8, capacity, 0.2f, 0.2f, f);
    }

    public static int[] testRemovalPriorityDistribution(int loops, int insertsPerLoop, float fractionToAdjust, float fractionToRemove, Bag<BagPerf.NullItem,CharSequence> f) {
        return testRemovalPriorityDistribution(loops, insertsPerLoop, fractionToAdjust, fractionToRemove, f, true);
    }

    public static int[] testRemovalPriorityDistribution(int loops, int insertsPerLoop, float fractionToAdjust, float fractionToRemove, Bag<BagPerf.NullItem,CharSequence> f, boolean requireOrder) {

        int levels = 9;
        int count[] = new int[levels];

        float adjustFraction = fractionToAdjust;
        float removeFraction = fractionToRemove;



        for (int l = 0; l < loops; l++) {
            //fill with random items
            for (int i= 0; i < insertsPerLoop; i++) {
                BagPerf.NullItem ni = new BagPerf.NullItem();
                ni.key = "" + (int)(Memory.randomNumber.nextFloat() * insertsPerLoop * 1.2f);
                f.PUT(ni);
            }


            int preadjustCount = f.size();
            //assertEquals(items.size(), f.size());

            //remove some, adjust their priority, and re-insert
            for (int i= 0; i < insertsPerLoop * adjustFraction; i++) {
                BagPerf.NullItem t = f.TAKENEXT();
                if (i % 2 == 0)
                    t.budget.setPriority(t.budget.getPriority()*0.99f);
                else
                    t.budget.setPriority(Math.min(1.0f,t.budget.getPriority()*1.01f));
                f.PUT(t);
            }

            int postadjustCount = f.size();
            //assertEquals(items.size(), f.size());

            assertEquals(preadjustCount, postadjustCount);

            float min = f.getMinPriority();
            float max = f.getMaxPriority();
            if (requireOrder) {
                assertTrue(max > min);
            }


            int nRemoved = 0;
            //remove last than was inserted so the bag never gets empty
            for (int i= 0; i < insertsPerLoop * removeFraction; i++) {
                int sizeBefore = f.size();

                BagPerf.NullItem t = f.TAKENEXT();

                int sizeAfter = f.size();

                if (t == null) {
                    assertTrue(sizeAfter == 0);
                    assertEquals(sizeAfter, sizeBefore);
                    continue;
                }
                else {
                    assertEquals(sizeAfter, sizeBefore-1);
                }

                float p = t.getPriority();

                if (requireOrder) {
                    assertTrue(p >= min);
                    assertTrue(p <= max);
                }

                int level = (int)Math.floor(p * levels);
                if (level >= count.length) level=count.length-1;
                count[level]++;
                nRemoved++;
            }

            assertEquals(postadjustCount-nRemoved, f.size());

            //nametable and itemtable consistent size
            //assertEquals(items.size(), f.size());
            //System.out.printMeaning("  "); items.reportPriority();

        }


        //System.out.println(items.getClass().getSimpleName() + "," + random + "," + capacity + ": " + Arrays.toString(count));

        return count;


        //removal rates are approximately monotonically increasing function
        //assert(count[0] <= count[N-2]);
        //assert(count[N/2] <= count[N-2]);

        //System.out.println(random + " " + Arrays.toString(count));
        //System.out.println(count[0] + " " + count[1] + " " + count[2] + " " + count[3]);

    }


    public static int[] testRetaining(int loops, int insertsPerLoop, Bag<BagPerf.NullItem,CharSequence> f) {

        int levels = 9;
        int count[] = new int[levels];


        for (int l = 0; l < loops; l++) {
            //fill with random items
            for (int i= 0; i < insertsPerLoop; i++) {
                BagPerf.NullItem ni = new BagPerf.NullItem(Memory.randomNumber.nextFloat());
                f.PUT(ni);
            }



            //nametable and itemtable consistent size
            //assertEquals(items.size(), f.size());
            //System.out.printMeaning("  "); items.reportPriority();

        }


        return count;
    }


    /** removal rates are approximately monotonically increasing function; tests first, mid and last for this  ordering */
    public static boolean semiMonotonicallyIncreasing(int[] count) {

        int cl = count.length;
        return
                (count[0] <= count[cl-1]) &&
                (count[cl/2] <= count[cl-1]);
    }
}