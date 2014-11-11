package jbse.bc;

import jbse.Type;
import jbse.bc.ClassFileArray.Visibility;
import jbse.exc.bc.ClassFileNotFoundException;
import jbse.exc.bc.NoArrayVisibilitySpecifiedException;
import jbse.exc.common.UnexpectedInternalException;

/**
 * Factory for {@link ClassFile}s.
 * 
 * @author Pietro Braione
 *
 */
public abstract class ClassFileFactory {
	/** Backlink to owner {@link ClassFileInterface}. */
	private ClassFileInterface cfi;
	protected Classpath cp;
	
	public ClassFileFactory(ClassFileInterface cfi, Classpath cp) {
		this.cfi = cfi;
		this.cp = cp;
	}
	
	protected abstract ClassFile newClassFileClass(String className) throws ClassFileNotFoundException;
	
	public ClassFile newClassFile(String className) 
	throws NoArrayVisibilitySpecifiedException, ClassFileNotFoundException, UnexpectedInternalException {
		if (Type.isArray(className)) {
        	//(recursively) gets the member class of an array
			final String memberType = Type.getArrayMemberType(className);
			final ClassFile classFileMember;
			if (Type.isPrimitive(memberType)) {
				classFileMember = this.cfi.getClassFilePrimitive(memberType);
			} else {
				final String memberClass = Type.className(memberType);
				classFileMember = this.cfi.getClassFile(memberClass);
			}

			//calculates package name
			//TODO couldn't find any specification for calculating this! Does it work for nested classes?
			final String packageName = classFileMember.getPackageName();
			
			//calculates visibility (JVM spec, 5.3.3, this
			//implementation exploits primitive class files)
			final Visibility visibility;
			if (classFileMember.isPublic()) {
				visibility = ClassFileArray.Visibility.PUBLIC;
			} else if (classFileMember.isPackage()) {
				visibility = ClassFileArray.Visibility.PACKAGE;
			} else {
				//TODO is this branch reachable for nested classes?
				throw new NoArrayVisibilitySpecifiedException();
			}
			return new ClassFileArray(className, packageName, visibility);
		} else {
			return newClassFileClass(className);
		}
	}	
}