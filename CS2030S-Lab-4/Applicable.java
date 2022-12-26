/**
 * The Applicable interface that can probably
 * transform if given something that is
 * probably an Immutator.
 *
 * Contains a single abstract method apply.
 *
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */
interface Applicable<T> {
  public abstract <R> Probably<R> apply(Probably<Immutator<R, ? super T>> param);
}