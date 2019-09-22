package secure.coding.chapter06.met.met12.solution;

import javax.swing.JFrame;

/**
 * @rule MET12-J. DO not use finalizers
 * 
 * @description The garbage collector invokes object finalizer methods after it
 *              determines that the object is unreachable but before it reclaims
 *              the object’s storage. Execution of the finalizer provides an
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
 * @category Compliant solution
 *
 * @description When a superclass defines a finalize() method, make sure to
 *              decouple the objects that can be immediately garbage-collected
 *              from those that must depend on the finalizer. This compliant
 *              solution ensures that the buffer can be reclaimed as soon as the
 *              object becomes unreachable.
 */

public final class MyFrame {
	private JFrame frame;
	private byte[] buffer = new byte[16 * 1024 * 1024]; // now decoupled
}