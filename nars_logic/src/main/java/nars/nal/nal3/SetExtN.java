/*
 * SetExt.java
 *
 * Copyright (C) 2008  Pei Wang
 *
 * This file is part of Open-NARS.
 *
 * Open-NARS is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Open-NARS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Open-NARS.  If not, see <http://www.gnu.org/licenses/>.
 */
package nars.nal.nal3;

import nars.nal.NALOperator;
import nars.nal.term.Compound;
import nars.nal.term.Term;

import static nars.nal.NALOperator.SET_EXT_CLOSER;
import static nars.nal.NALOperator.SET_EXT_OPENER;

/**
 * An extensionally defined set, which contains one or more instances.
 */
public class SetExtN extends AbstractSetN implements SetExt {



    /**
     * Constructor with partial values, called by make
     * @param n The name of the term
     * @param arg The component list of the term - args must be unique and sorted
     */
    protected SetExtN(final Term... arg) {
        super(arg);
    }


    /**
     * Clone a SetExt
     * @return A new object, to be casted into a SetExt
     */
    @Override
    public SetExtN clone() {
        return new SetExtN(term);
    }
    
    @Override public Compound clone(Term[] replaced) {
        return SetExt.make(replaced);
    }

    /**
     * Get the operate of the term.
     * @return the operate of the term
     */
    @Override
    public NALOperator operator() {
        return NALOperator.SET_EXT;
    }


    /**
     * Make a String representation of the set, override the default.
     * @return true for communitative
     */
    @Override
    public CharSequence makeName() {
        return SetTensional.makeSetName(SET_EXT_OPENER.ch, SET_EXT_CLOSER.ch, term);
    }

    @Override
    protected byte[] makeKey() {
        return SetTensional.makeKey(NALOperator.SET_EXT_OPENER.ch, NALOperator.SET_EXT_CLOSER.ch, term);
    }
}
