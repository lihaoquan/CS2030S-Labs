/**
 * The Immutatorable interface that can
 * transform when given something that is
 * Immutator.
 *
 * Contains a single abstract method transform.
 *
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */
interface Immutatorable<T> {

  // Transforms a value of type T to value of type R
  public abstract <R> Immutatorable<R> transform(Immutator<R, ? super T> param);
}