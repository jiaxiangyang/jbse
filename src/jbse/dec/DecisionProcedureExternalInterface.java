package jbse.dec;

import java.io.IOException;

import jbse.exc.common.UnexpectedInternalException;
import jbse.exc.dec.ExternalProtocolInterfaceException;
import jbse.mem.Objekt;
import jbse.mem.Primitive;
import jbse.mem.ReferenceSymbolic;

public abstract class DecisionProcedureExternalInterface {
    /**
     * Checks whether the external decision procedure works.
     *  
     * @return {@code true} iff the external decision procedure
     *         is working, and therefore can be used.
     */
	public abstract boolean isWorking();

	/**
	 * Sends a clause to the external decision procedure; 
	 * the clause is just transmitted, without checking or 
	 * assuming it, and becomes the current clause to work on.
	 * This method sends numeric clauses.
	 * 
	 * @param predicate the clause to put. It is a {@code Primitive}.
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when a current predicate already exists.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public abstract void sendClauseAssume(Primitive predicate) 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;
	
	/**
	 * Sends a clause to the external decision procedure; 
	 * the clause is just transmitted, without checking or 
	 * assuming it, and becomes the current clause to work on.
	 * This method sends an aliasing clause for a symbolic 
	 * reference.
	 * 
     * @param r a {@link ReferenceSymbolic}.
     * @param heapPos a {@code long} value, the position of {@code o} in the heap.
     * @param o an {@link Objekt}, the object to which {@code r} refers.
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when a current predicate already exists.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public abstract void sendClauseAssumeAliases(ReferenceSymbolic r, long heapPos, Objekt o) 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;
	
	/**
	 * Sends a clause to the external decision procedure; 
	 * the clause is just transmitted, without checking or 
	 * assuming it, and becomes the current clause to work on.
	 * This method sends an expansion clause for a symbolic 
	 * reference.
	 * 
     * @param r a {@link ReferenceSymbolic}.
     * @param className a {@link String}, the name of the class
     *        to which {@code r} is expanded.
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when a current predicate already exists.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public abstract void sendClauseAssumeExpands(ReferenceSymbolic r, String className) 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;
	
	/**
	 * Sends a clause to the external decision procedure; 
	 * the clause is just transmitted, without checking or 
	 * assuming it, and becomes the current clause to work on.
	 * This method sends a null clause for a symbolic 
	 * reference.
	 * 
     * @param r a {@link ReferenceSymbolic}, the reference that
     *        is assumed to be null.
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when a current predicate already exists.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public abstract void sendClauseAssumeNull(ReferenceSymbolic r) 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;
	
	/**
	 * Sends a clause to the external decision procedure; 
	 * the clause is just transmitted, without checking or 
	 * assuming it, and becomes the current clause to work on.
	 * This method sends a class initialization clause.
	 * 
     * @param className a {@link String}, the name of the class
     *        that is initialized at the start of symbolic execution.
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when a current predicate already exists.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public abstract void sendClauseAssumeClassInitialized(String className) 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;
	
	/**
	 * Sends a clause to the external decision procedure; 
	 * the clause is just transmitted, without checking or 
	 * assuming it, and becomes the current clause to work on.
	 * This method sends a class not-initialization clause.
	 * 
     * @param className a {@link String}, the name of the class
     *        that is not initialized at the start of symbolic execution.
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when a current predicate already exists.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public abstract void sendClauseAssumeClassNotInitialized(String className) 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;

	/**
	 * Retracts the current predicate, setting the decision procedure 
	 * to a "no current predicate" state.
	 * 
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when there is no current predicate.
	 * @throws IOException
	 * @throws UnexpectedInternalException 
	 */
	public abstract void retractClause() 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;

	/**
	 * Verifies whether the current assumption is satisfiable 
	 * when put in logical and with the (possibly negated) 
	 * current predicate.
	 * 
	 * @param positive if {@code false} the current predicate must 
	 *        be negated before checking satisfiability, otherwise not.
	 * @return {@code false} if the decision procedure proves that the 
	 *         current assumption and the (possibly negate) current 
	 *         predicate are not satisfiable, {@code true} otherwise. 
	 * @throws ExternalProtocolInterfaceException if this method is 
	 *         invoked when there is no current predicate.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public abstract boolean checkSat(boolean positive)
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;

	/**
	 * Pushes the (possibly negated) current clauses to the current
	 * assumptions. 
	 * 
	 * @param positive if {@code false} the current predicate is negated
	 *        before pushing, otherwise not.
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when there is no current predicate.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public abstract void pushAssumption(boolean positive)
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;
	
	/**
	 * Pops the last clause added to the current assumption by 
	 * a call to {@link #pushAssumption(boolean)}. It can be unimplemented.
	 * 
	 * @throws ExternalProtocolInterfaceException if this method
	 *         is invoked when there is a current predicate, 
	 *         or if the method is unimplemented.
	 * @throws IOException if communication with the external 
	 *         decision procedure fails.
	 * @throws UnexpectedInternalException 
	 */
	public void popAssumption()
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException {
		throw new ExternalProtocolInterfaceException("popping assumptions is not implemented for external decision procedure interface of class " + this.getClass().getName());
	}

	/**
	 * Deletes the whole assumption set.
	 * 
	 * @throws ExternalProtocolInterfaceException if it is
	 *         invoked after {@link #quit()}.
	 * @throws IOException
	 * @throws UnexpectedInternalException 
	 */
	public abstract void clear() 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;

	/**
	 * Quits the decision procedure.
	 * 
	 * @throws ExternalProtocolInterfaceException
	 * @throws IOException
	 * @throws UnexpectedInternalException 
	 */
	public abstract void quit() 
	throws ExternalProtocolInterfaceException, IOException, UnexpectedInternalException;

	/**
	 * To be invoked when the decision procedure fails.
	 */
	public abstract void fail();
}