package nars;

import nars.core.NAR;
import nars.prolog.InvalidLibraryException;
import nars.prolog.InvalidTheoryException;
import nars.prolog.Prolog;
import nars.prolog.event.*;
import nars.prolog.lib.BasicLibrary;

/**
 * Wraps a Prolog instance loaded with nal.pl with some utility methods
 */
public class NARTuprolog extends Prolog implements OutputListener, WarningListener, TheoryListener, QueryListener {
    
    public final NAR nar;
    
    public NARTuprolog(NAR n)  {
        super();
        this.nar = n;
        
        addOutputListener(this);
        addTheoryListener(this);
        addWarningListener(this);
        addQueryListener(this);        
             
    }
    
    public NARTuprolog loadBasicLibrary() throws InvalidLibraryException {
        loadLibrary(new BasicLibrary());
        return this;
    }
    
    public NARTuprolog loadNAL() throws InvalidTheoryException {
    /*    addTheory(getNALTheory());
    private static Theory nalTheory;
    static {
        try {
            nalTheory = new Theory(PrologContext.class.getResourceAsStream("../nal.pl"));
        } catch (IOException ex) {
            nalTheory = null;
            Logger.getLogger(NARProlog.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }        
    }
    public static Theory getNALTheory() { return nalTheory; }
    */
        return this;
    }
    
    @Override public void onOutput(OutputEvent e) {        
        nar.emit(Prolog.class, e.getMsg());
    }
    
    @Override
    public void onWarning(WarningEvent e) {
        nar.emit(Prolog.class, e.getMsg() + ", from " + e.getSource());
    }

    @Override
    public void theoryChanged(TheoryEvent e) {
        nar.emit(Prolog.class, e.toString());
    }
    
    @Override
    public void newQueryResultAvailable(QueryEvent e) {
        /*
        output.output(Prolog.class, e.getSolveInfo());
        
        //TEMPORARY
        try {
            SolveInfo s = e.getSolveInfo();            
            System.out.println("Question:  " + s.getQuery() + " ?");
            System.out.println("  Answer:  " + s.getSolution());
        } catch (NoSolutionException ex) {
            //No solution
            System.out.println("  Answer: none.");
            //Logger.getLogger(NARProlog.class.getName()).log(Level.SEVERE, null, ex);
        }
                */
        
    }
    
//    public static void main(String[] args) throws Exception {
//        NAR nar = new DefaultNARBuilder().build();
//        new TextOutput(nar, System.out);
//        
//        Prolog prolog = new NARProlog(nar);
//        prolog.solve("revision([inheritance(bird, swimmer), [1, 0.8]], [inheritance(bird, swimmer), [0, 0.5]], R).");
//        prolog.solve("inference([inheritance(swan, bird), [0.9, 0.8]], [inheritance(bird, swan), T]).");
//        
//    }

    
}