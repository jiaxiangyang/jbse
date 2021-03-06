package jbse.algo.meta;

import static jbse.algo.Util.exitFromAlgorithm;
import static jbse.algo.Util.throwVerifyError;

import java.util.function.Supplier;

import jbse.algo.Algo_INVOKEMETA_NONBRANCHING;
import jbse.algo.InterruptException;
import jbse.mem.Objekt;
import jbse.mem.State;
import jbse.mem.exc.ThreadStackEmptyException;
import jbse.val.Primitive;
import jbse.val.Reference;

public final class Algo_JAVA_SYSTEM_IDENTITYHASHCODE extends Algo_INVOKEMETA_NONBRANCHING {
    @Override
    protected Supplier<Integer> numOperands() {
        return () -> 1;
    }
    
    @Override
    protected void update(State state) throws ThreadStackEmptyException, InterruptException {
        try {
            final Reference thisReference = (Reference) this.data.operand(0);
            final Objekt thisObjekt = state.getObject(thisReference);

            //gets the hash code stored in the objekt and returns it
            final Primitive hashCode = state.getCalculator().valInt(thisObjekt.getObjektHashCode());
            state.pushOperand(hashCode);
        } catch (ClassCastException e) {
            throwVerifyError(state);
            exitFromAlgorithm();
        }
    }
}
