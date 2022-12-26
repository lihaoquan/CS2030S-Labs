/**
 * A generic Immutator that takes in an object
 * that is T and returns an object that is probably T.
 *
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */

class Improbable<T> implements Immutator<Probably<T>, T> {

  // Takes in argument of type T
  // and returns data of type Probably<T>
  // wraps supplied value as a Probably value.
  @Override
  public Probably<T> invoke(T param) {
    return Probably.just(param);
  }
}