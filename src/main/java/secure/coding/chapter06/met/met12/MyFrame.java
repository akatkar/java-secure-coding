package secure.coding.chapter06.met.met12;

import javax.swing.JFrame;

/**
 * @rule MET12-J. DO not use finalizers
 * 
 * @description The garbage collector invokes object finalizer methods after it
 *              determines that the object is unreachable but before it reclaims
 *              the object�s storage. Execution of the finalizer provides an
 *              opportunity to release resources such as open streams, files,
 *              and network connections that might not otherwise be released
 *              automatically through the normal action of the garbage
 *              collector. A sufficient number of problems are associated with
 *              finalizers to restrict their use to exceptional conditions:
 * 
 *              - There is no fixed time at which finalizers must be executed
 *              because this depends on the JVM.
 * 
 *              - The JVM may terminate without invoking the finalizer on some
 *              or all unreachable objects. Consequently, attempts to update
 *              critical persistent state from finalizer methods can fail
 *              without warning.
 *              
 * @category Noncompliant code
 *
 * @description Superclasses that use finalizers impose additional constraints
 *              on their extending classes. Consider an example from JDK 1.5 and
 *              earlier. The following noncompliant code example allocates a
 *              16MB buffer used to back a Swing JFrame object. Although the
 *              JFrame APIs lack finalize() methods, JFrame extends AWT.Frame,
 *              which does have a finalize() method. When a MyFrame object
 *              becomes unreachable, the garbage collector cannot reclaim the
 *              storage for the byte buffer because code in the inherited
 *              finalize() method might refer to it. Consequently, the byte
 *              buffer must persist at least until the inherited finalize()
 *              method for class MyFrame completes its execution and cannot be
 *              reclaimed until the following garbage-collection cycle.
 */
public final class MyFrame extends JFrame {
	private byte[] buffer = new byte[16 * 1024 * 1024];
	// persists for at least two GC cycles
}