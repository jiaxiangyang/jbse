package jbse.apps.run;

import java.util.Map;
import java.util.function.Supplier;

import jbse.common.exc.UnexpectedInternalException;
import jbse.dec.DecisionProcedure;
import jbse.dec.DecisionProcedureChainOfResponsibility;
import jbse.dec.exc.DecisionException;
import jbse.jvm.RunnerParameters;
import jbse.mem.Objekt;
import jbse.mem.State;
import jbse.mem.exc.ContradictionException;
import jbse.meta.annotations.ConservativeRepOk;
import jbse.rewr.CalculatorRewriting;
import jbse.val.ReferenceSymbolic;
import jbse.val.exc.InvalidTypeException;

/**
 * A decision procedure based on the execution of conservative repOk methods;
 * it validates a path condition by constructing the corresponding initial 
 * state, and running the methods annotated with the {@link ConservativeRepOk}
 * annotation on all the object of the corresponding classes that are present
 * in the refined initial state's heap.  
 * 
 * @author Pietro Braione
 *
 */
public class DecisionProcedureConservativeRepOk extends DecisionProcedureChainOfResponsibility {
    private final InitialHeapChecker checker;
    
    public DecisionProcedureConservativeRepOk(DecisionProcedure next, CalculatorRewriting calc, 
    RunnerParameters checkerParameters, Map<String, String> checkMethods) {
        super(next, calc);
        this.checker = new InitialHeapChecker(checkerParameters, ConservativeRepOk.class, checkMethods);
    }
    
    public void setInitialStateSupplier(Supplier<State> initialStateSupplier) {
        this.checker.setInitialStateSupplier(initialStateSupplier);
    }
    
    public void setCurrentStateSupplier(Supplier<State> currentStateSupplier) {
        this.checker.setCurrentStateSupplier(currentStateSupplier);
    }
    
    @Override
	protected boolean isSatExpandsLocal(ReferenceSymbolic r, String className)
	throws DecisionException {
		final State sIni = this.checker.makeInitialState();
		try {
			sIni.assumeExpands(r, className);
		} catch (InvalidTypeException | ContradictionException e) {
			//this should not happen
			throw new UnexpectedInternalException(e);
		}
        return this.checker.checkHeap(sIni, true);
	}
	
	@Override
	protected boolean isSatAliasesLocal(ReferenceSymbolic r, long heapPosition, Objekt o) 
	throws DecisionException {
		final State sIni = this.checker.makeInitialState();
		try {
			sIni.assumeAliases(r, heapPosition, o);
		} catch (ContradictionException e) {
			//this should not happen
			throw new UnexpectedInternalException(e);
		}
        return this.checker.checkHeap(sIni, true);
	}
	
	@Override
	protected boolean isSatNullLocal(ReferenceSymbolic r)
	throws DecisionException {
		final State sIni = this.checker.makeInitialState();
		try {
			sIni.assumeNull(r);
		} catch (ContradictionException e) {
			//this should not happen
			throw new UnexpectedInternalException(e);
		}
        return this.checker.checkHeap(sIni, true);
	}
}
