package cs2030s.fp;

/**
 * The Immutator interface that can transform
 * to type T2, an object of type T1.
 *
 * Contains a single abstract method invoke.
 *
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */

// Takes in argument of type P
// and returns a data of type R.
public interface Immutator<R, P> {
	public abstract R invoke(P actually);
}