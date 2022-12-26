package cs2030s.fp;

/**
 * The Memo class.
 * CS2030S Lab 6
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */

public class Memo<T> extends Lazy<T> {

  /**
   * The value stored in Memo.
   */
  private Actually<T> value;

  /**
   * Constructor for creating Memo.
   * @param constant The provider that we will initialize our value from.
   * @param value The actual pre-initialized value.
   */
  private Memo(Constant<? extends T> constant, Actually<T> value) {
    super(constant);
    this.value = value;
  }

  /**
   * Construct a {@code Memo<T>} with a pre-initialized value.
   * 
   * @param <T> Type of value stored in Memo.
   * @param v   Value to be stored.
   * @return a new Memo with pre-initialized value.
   */
  public static <T> Memo<T> from(T v) {
    return new Memo<T>(null, Actually.ok(v));
  }

  /**
   * Construct a {@code Memo<T>} that is uninitialized.
   * 
   * @param <T>      Type of value stored in Memo.
   * @param constant Provider of the value.
   * @return a new Memo that is uninitialized.
   */
  public static <T> Memo<T> from(Constant<? extends T> constant) {
    return new Memo<T>(constant, Actually.err(new Exception()));
  }

  /**
   * Getter for value stored in Memo.
   * 
   * @return value stored in Memo.
   */
  @Override
  public T get() {
    this.value = Actually.ok(this.value.except(() -> super.get()));
    return this.value.except(null);
  }

  /**
   * If success, except will simply return the inner value.
   * If failure, except will return the constant that is supplied to it.
   * In this case, we want failure to return "?".
   * 
   * @return String representation of stored value.
   */
  @Override
  public String toString() {

    /*
     * When returning, the current Actually that we have is {@code Actually<T>}.
     * We need to transform it into a {@code Actually<String>}.
     * This is to remove the need of using unchecked cast.
     */

    Actually<String> str = (Actually<String>) this.value.transform(p -> String.valueOf(p));
    return str.except(() -> "?");
  }

  /**
   * Transforms currently stored value using supplied Immutator.
   * 
   * @return a new {@code Memo<R>} whose value is transformed by Immutator.
   */
  @Override
  public <R> Memo<R> transform(Immutator<? extends R, ? super T> f) {
    return Memo.from(() -> f.invoke(this.get()));
  }

  /**
   * Transforms currently stored value using supplied Immutator.
   * Does multiple {@code get()} to get the value of nested {@code Memo<T>}.
   * 
   * @param f Immutator that will be used to transform the value.
   * @return a new {@code Memo<T>} whose value is transformed by Immutator.
   */
  @Override
  public Memo<T> next(Immutator<? extends Lazy<? extends T>, T> f) {

    /*
     * Unwraps nested Memo, double get(), first get returns a Memo.
     * Second get() returns nested Memo's value.
     *
     * Memo<Integer> step3 = step2.next(i -> Memo.from(i + 10));
     *
     * Line above is equivalent to:
     *
     * Memo<Integer> step3 = step2.next(new Immutator<Lazy<Integer>, Integer>() {
     *
     * @Override
     * public Lazy<Integer> invoke(Integer p) {
     * return Memo.from(p);
     * }
     * });
     *
     * In the above, we use a Immutator to wrap Integer (from get()) into a Lazy.
     */

    return Memo.from(() -> f.invoke(get()).get());
  }

  /**
   * Combines two Memo object's value into one based on rules of the combiner.
   * 
   * @param <R>      Memo type that we are going to assign combine result to.
   * @param <S>      The type of Memo we are combining with.
   * @param other    {@code Memo<S>} that we are going to combine with.
   * @param combiner Combiner that defines how the two Memo should combine.
   * @return Combined value of two Memo processed by combiner.
   */
  public <R, S> Memo<R> combine(Memo<? extends S> other,
      Combiner<? extends R, T, ? super S> combiner) {
    /*
     * Combiner<RETURN_MEMO, MEMO1_TYPE, MEMO2_TYPE>.
     * Combiner<? extends R, T, ? extends S>.
     * 1. Combiner's return type needs to be <: Memo<R>, hence extends R.
     * 2. T is "this" Memo that will combine with the other Memo.
     * 3. Third param is supplied by Memo<S>, which contains value of type S.
     * 4. Memo<S> is producer of S, hence ? extend S.
     * 5. Combiner<..., ..., ? super S> - Combiner consumes S, hence ? super S.
     */

    return Memo.from(() -> combiner.combine(this.get(), other.get()));
  }

  // Below is an expanded example of how Combine is called.
  // Memo<Integer> modInt = Memo.from(() -> 20).combine(Memo.from(() -> 30), new
  // Combiner<Integer,Integer,Integer>() {
  // @Override
  // public Integer combine(Integer s, Integer t) {
  // return s * 100 + t;
  // }
  // });
}