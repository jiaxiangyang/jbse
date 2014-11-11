package jbse.apps;

import java.util.Collection;

import jbse.dec.DecisionProcedure;
import jbse.dec.DecisionProcedureDecorator;
import jbse.exc.common.UnexpectedInternalException;
import jbse.exc.dec.DecisionException;
import jbse.mem.Clause;
import jbse.mem.Expression;
import jbse.mem.Objekt;
import jbse.mem.ReferenceSymbolic;

/**
 * A {@link DecisionProcedureDecorator} that accounts the time spent 
 * by its component.
 *  
 * @author Pietro Braione
 */
public class DecisionProcedureDecoratorTimer extends DecisionProcedureDecorator implements Timer {
	private long time;
	private long start;
	
	private void startTimer() {
		this.start = System.currentTimeMillis();
	}

	private void stopTimer() {
        this.time += System.currentTimeMillis() - start;
	}

	public DecisionProcedureDecoratorTimer(DecisionProcedure component) {
		super(component);
    	this.time = 0L;
	}

	@Override
	public long getTime() {
		return this.time;
	}
	
	@Override
	public void pushAssumption(Clause c) 
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		super.pushAssumption(c);
		this.stopTimer();
	}
	
	@Override
	public void clearAssumptions() 
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		super.clearAssumptions();
		this.stopTimer();
	}
	
	@Override
	public void setAssumptions(Collection<Clause> newAssumptions) 
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		super.setAssumptions(newAssumptions);
		this.stopTimer();
	}
	
	@Override
	public Collection<Clause> getAssumptions() 
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		Collection<Clause> result = super.getAssumptions();
		this.stopTimer();
		return result;
	}

	@Override
	public boolean isSat(Expression exp) 
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		boolean result = super.isSat(exp);
		this.stopTimer();
        return result;
	}
	
	@Override
	public boolean isSatAliases(ReferenceSymbolic r, long heapPos, Objekt o)
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		boolean result = super.isSatAliases(r, heapPos, o);
		this.stopTimer();
        return result;
	}
	
	@Override
	public boolean isSatExpands(ReferenceSymbolic r, String className)
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		boolean result = super.isSatExpands(r, className);
		this.stopTimer();
        return result;
	}
	
	@Override
	public boolean isSatNull(ReferenceSymbolic r) 
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		boolean result = super.isSatNull(r);
		this.stopTimer();
        return result;
	}
	
	@Override
	public boolean isSatInitialized(String className) 
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		boolean result = super.isSatInitialized(className);
		this.stopTimer();
        return result;
	}
	
	@Override
	public boolean isSatNotInitialized(String className)
	throws DecisionException, UnexpectedInternalException {
		this.startTimer();
		boolean result = super.isSatNotInitialized(className);
		this.stopTimer();
        return result;
	}
}