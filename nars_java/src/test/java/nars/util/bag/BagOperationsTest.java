/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package nars.util.bag;

import nars.logic.entity.BudgetValue;
import nars.logic.entity.Concept;
import nars.logic.entity.Item;
import nars.logic.entity.Term;
import nars.util.bag.impl.CurveBag;
import nars.util.bag.impl.LevelBag;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author me
 */
public class BagOperationsTest {

    public static class NullConcept extends Concept {

        public NullConcept(String id, float priority) {
            super(new BudgetValue(priority, priority, priority), new Term(id), null, null, null);
        }    

        @Override
        public float getQuality() {
            return 0;
        }
        
    }

    @Test public void testLevelBag() {
        testBagSequence(new LevelBag(2, 2));
    }
    
    
    @Test public void testCurveBag() {
        testBagSequence(new CurveBag(2, new CurveBag.FairPriorityProbabilityCurve(), true));
    }
    
    void testBagSequence(Bag b) {
        
        //different id, different priority
        b.PUT(new NullConcept("a", 0.1f));
        b.PUT(new NullConcept("b", 0.15f));
        assertEquals(2, b.size());
        b.clear();
        
        //same priority, different id
        b.PUT(new NullConcept("a", 0.1f));
        b.PUT(new NullConcept("b", 0.1f));
        assertEquals(2, b.size());
        
        b.PUT(new NullConcept("c", 0.2f));
        assertEquals(2, b.size());
        assertEquals(0.1f, b.getMinPriority(),0.001f);
        assertEquals(0.2f, b.getMaxPriority(),0.001f);
        
        //if (b instanceof GearBag()) return;
        
        
        b.PUT(new NullConcept("b", 0.4f));
        
        
        assertEquals(2, b.size());
        assertEquals(0.2f, b.getMinPriority(),0.001f);
        assertEquals(0.4f, b.getMaxPriority(),0.001f);
        
        
        Item tb = b.TAKE(new Term("b"));
        assertTrue(tb!=null);
        assertEquals(1, b.size());
        assertEquals(0.4f, tb.getPriority(), 0.001f);
        
        Item tc = b.TAKENEXT();
        assertEquals(0, b.size());
        assertEquals(0.2f, tc.getPriority(), 0.001f);
        
        assertEquals(null, b.PUT(new NullConcept("a", 0.2f)));
        b.PUT(new NullConcept("b", 0.3f));

        if (b instanceof LevelBag) {
            assertEquals("a", b.PUT(new NullConcept("c", 0.1f)).name().toString()); //replaces item on level
        }
        else if (b instanceof CurveBag) {
            assertEquals("c", b.PUT(new NullConcept("c", 0.1f)).name().toString()); //could not insert, so got the object returned as result
            assertEquals(2, b.size());
        
            //same id, different priority (lower, so budget will not be affected)
            assertEquals(null, b.PUT(new NullConcept("b", 0.1f)));
            assertEquals(0.2f, b.getMinPriority(),0.001f); //unaffected, 0.2 still lowest

            //same id, higher priority
            assertEquals(0.3f, b.getMaxPriority(),0.001f); //affected, 0.4 highest

            //increasing b's priority should not cause 'a' to be removed
            Item zzz = b.PUT(new NullConcept("b", 0.4f));
            assertNull(null, zzz);

            assertEquals(0.4f, b.getMaxPriority(),0.001f); //affected, 0.4 highest

        }
        
    }
}