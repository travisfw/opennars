package nars.perf;

import java.util.Collection;
import nars.core.NAR;
import nars.core.build.CurveBagNARBuilder;
import static nars.perf.NALStressMeasure.perfNAL;
import nars.test.core.NALTest;

/**
 * Runs NALTestPerf continuously, for profiling
 */
public class NALPerfLoop {
    
    public static void main(String[] args) {
       
        int repeats = 2;
        int warmups = 1;
        int extraCycles = 512;
        int maxConcepts = 2000;
        int randomExtraCycles = 512;
          
        NAR n = new CurveBagNARBuilder().setConceptBagSize(maxConcepts).build();
        //NAR n = new DefaultNARBuilder().setConceptBagSize(maxConcepts).build();
        //NAR n = new DiscretinuousBagNARBuilder().setConceptBagSize(maxConcepts).build();
        
        Collection c = NALTest.params();
        while (true) {
            for (Object o : c) {
                String examplePath = (String)((Object[])o)[0];
                perfNAL(n, examplePath,extraCycles+ (int)(Math.random()*randomExtraCycles),repeats,warmups,true);
            }
        }        
    }
}
