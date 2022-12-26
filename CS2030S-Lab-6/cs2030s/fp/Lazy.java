package cs2030s.fp;

/**
 * The Lazy class.
 * CS2030S Lab 6
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */

public class Lazy<T> implements Immutatorable<T> {

  /**
   * The constant that we will initialize our value from.
   */
  private Constant<? extends T> init;

  /**
   * Set the provider of the value stored in Lazy.
   * @param constant Provider of the value.
   */
  protected Lazy(Constant<? extends T> constant) {
    this.init = constant;
  }

  /**
   * Create a {@code Lazy<T>} that is initialized.
   * 
   * @param <T> Type of value stored in Lazy.
   * @param v   Value to be stored.
   * @return a new Lazy with a pre-initialized value.
   */
  public static <T> Lazy<T> from(T v) {
    return new Lazy<T>(new Constant<T>() {
      @Override
      public T init() {
        return v;
      }
    });
  }

  /**
   * Create a {@code Lazy<T>} that is uninitialized.
   * 
   * @param <T>      Type of value stored in Lazy.
   * @param constant Provider of the value.
   * @return a new Lazy that is uninitialized.
   */
  public static <T> Lazy<T> from(Constant<? extends T> constant) {
    return new Lazy<T>(constant);
  }

  /**
   * Getter for value stored in Lazy.
   * 
   * @return value stored in Lazy.
   */
  public T get() {
    return this.init.init();
  }

  /**
   * Get String representation of stored value.
   * 
   * @return String representation of the value.
   *         This is calculated on the fly via get().
   */
  @Override
  public String toString() {
    return get().toString();
  }

  /**
   * Transforms currently stored value using supplied Immutator.
   * 
   * @param f Immutator that will be used to transform the value.
   * @return a new {@code Lazy<R>} whose value is transformed by Immutator.
   */
  @Override
  public <R> Lazy<R> transform(Immutator<? extends R, ? super T> f) {
    return Lazy.from(() -> f.invoke(get()));
  }

  /**
   * Transforms currently stored value using supplied Immutator.
   * Does multiple get() to get the value of nested {@code Lazy<T>}.
   * 
   * @param f Immutator that will be used to transform the value.
   * @return a new {@code Lazy<T>} whose value is transformed by Immutator.
   */
  public Lazy<T> next(Immutator<? extends Lazy<? extends T>, T> f) {
    // Unwraps nested Lazy, double get(), first get returns a Lazy.
    // Second get() returns nested Lazy's value.
    return Lazy.from(() -> f.invoke(get()).get());
  }
}