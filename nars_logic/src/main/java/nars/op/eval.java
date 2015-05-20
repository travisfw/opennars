package nars.op;

import nars.Memory;
import nars.nal.nal4.Product;
import nars.nal.nal8.Operation;
import nars.nal.nal8.TermFunction;
import nars.nal.term.Atom;
import nars.nal.term.Compound;
import nars.nal.term.Term;
import nars.util.event.Reaction;

import java.util.List;

/**
 * Created by me on 5/19/15.
 */
public class eval extends TermFunction {

    public final static Term term = Atom.the("eval");

    public static Term eval(final Term x, final Memory m) {


        if (x instanceof Operation) {
            final Operation o = (Operation)x;
            final Term op = o.getOperator();
            TermFunction tf = getTheTermFunction(op, m);
            if (tf instanceof TermFunction) {
                Object result = ((TermFunction) tf).function(o.arg(m, true));
                if (result != null)
                    return term(result);
            }
        }

        if (x instanceof Compound) {
            Compound ct = (Compound)x;
            Term[] r = new Term[ct.length()];
            boolean modified = false;
            int j = 0;
            for (final Term w : ct.term) {
                Term v = eval(w, m);
                if ((v!=null) && (v!=w)) {
                    r[j] = v;
                    modified = true;
                }
                else {
                    r[j] = w;
                }
                j++;
            }
            if (modified)
                return ct.clone(r);
        }

        return x; //return as-is
    }

    public Term eval(final Term x) {
        return eval(x, nar.memory);
    }

    /** gets the first available term function if it exists for the given term */
    static TermFunction getTheTermFunction(Term op, Memory m) {
        List<Reaction<Term>> r = m.exe.all(op);
        int s = r.size();
        for (Reaction<Term> rr : r) {
            if (rr instanceof TermFunction)
                return ((TermFunction)rr);
        }
        return null;
    }

    private static Term term(Object o) {

        if (o instanceof Term) return ((Term)o);
        else if (o instanceof String) {
            return Atom.the((String) o);
        }
        else if (o instanceof Number) {
            return Atom.the((Number) o);
        }
        else {
            return Atom.the(o.toString());
        }
    }



    @Override
    public Object function(Term... x) {
        if (x.length == 1) {
            return eval(x[0]);
        }
        else {
            Term[] y = new Term[x.length];
            for (int j = 0; j < y.length; j++)
                y[j] = eval(x[j]);

            return Product.make(y);
        }
    }
}
