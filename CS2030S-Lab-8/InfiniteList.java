package cs2030s.fp;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * An InfiniteList that is memoized.
 */
public class InfiniteList<T> {

  /**
   * Head of the list.
   */
  private Memo<Actually<T>> head;

  /**
   * Tail of the list.
   */
  private Memo<InfiniteList<T>> tail;

  /**
   * Represents ending of a list.
   */
  private static End END = new End();

  /**
   * Constructor for an infinite list.
   * 
   * @param head Head of the list.
   * @param tail Tail of the list.
   */
  private InfiniteList(Memo<Actually<T>> head, Memo<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  /**
   * Generate an infinite list using a value provider.
   * 
   * @param <T>  The data type of the list.
   * @param prod The value provider.
   * @return An {@code InfiniteList<T>} with memoized values
   *         produced from a provider.
   */
  public static <T> InfiniteList<T> generate(Constant<T> prod) {
    return new InfiniteList<T>(Memo.from(() -> Actually.ok(prod.init())),
        Memo.from(() -> generate(prod)));
  }

  /**
   * Generate a list by applying an immutator on the seed repeatedly.
   * 
   * @param <T>  The data type of the list.
   * @param seed The initial seed used.
   * @param func The function to be applied onto the seed.
   * @return An {@code InfiniteList<T>} with memoized values
   *         produced from applying function onto seed repeatedly.
   */
  public static <T> InfiniteList<T> iterate(T seed, Immutator<T, T> func) {
    return new InfiniteList<>(Memo.from(Actually.ok(seed)),
        Memo.from(() -> iterate(func.invoke(seed), func)));
  }

  /**
   * Get the head of the InfiniteList.
   * If head is null, get the next head down the chain.
   * 
   * @return The non-null head of the infinite list.
   */
  public T head() {
    return this.head.get().except(() -> this.tail.get().head());
  }

  /**
   * Get the tail of the InfiniteList.
   * If tail is null, get the next tail down the chain.
   * 
   * @return The non-null tail of the infinite list.
   */
  public InfiniteList<T> tail() {
    return this.head.get().transform(t -> this.tail.get())
        .except(() -> this.tail.get().tail());
  }

  /**
   * Transforms values inside the list and returns it.
   * 
   * @param <R> Data type of the new transformed list.
   * @param f   Function to be applied onto the data
   *            in the list.
   * @return A new list with transformed values by
   *         applying f onto them.
   */
  public <R> InfiniteList<R> map(Immutator<? extends R, ? super T> f) {
    return new InfiniteList<>(Memo.from(() -> this.head.get().transform(f)),
        this.tail.transform(x -> x.map(f)));
  }

  /**
   * Filters and only collect values that passes
   * the predicates.
   * 
   * @param pred Predicate to check values against.
   * @return A new filtered list with values that passes
   *         the predicate check.
   */
  public InfiniteList<T> filter(Immutator<Boolean, ? super T> pred) {
    return new InfiniteList<T>(Memo.from(() -> this.head.get().check(pred)),
        this.tail.transform(x -> x.filter(pred)));
  }

  /**
   * Returns a truncated infinite list.
   * 
   * @param n Retain how many values in the list.
   * @return A truncated list based on specified limit.
   */
  public InfiniteList<T> limit(long n) {
    if (n <= 0) {
      return end();
    }
    return new InfiniteList<T>(this.head, Memo.from(() -> this.tail.get()
        .limit(n - this.head.get().transform(x -> 1).except(() -> 0))));
  }

  /**
   * Continue taking elements in the list until predicate fails.
   * If fail, truncate.
   * 
   * @param pred Predicate to test values against.
   * @return A truncated list of elements that passes the predicate
   *         and stops taking when it first encounters a fail. Any
   *         elements that passes the predicate but succeeds the first
   *         fail will not be taken.
   */
  public InfiniteList<T> takeWhile(Immutator<Boolean, ? super T> pred) {

    // Check that head passes, then check if tail passes recursively.

    Memo<Actually<T>> head = Memo.from(
        () -> Actually.ok(this.head())
            .check(pred));
    Memo<InfiniteList<T>> tail = Memo.from(
        () -> {
          return head.get()
              .transform(h -> this.tail().takeWhile(pred))
              .unless(end());
        });

    return new InfiniteList<>(head, tail);
  }

  /**
   * Converts infinite list into type List.
   * 
   * @return List version of the infinite list.
   */
  public List<T> toList() {

    // New array list to store result.
    List<T> arrayList = new java.util.ArrayList<T>();
    InfiniteList<T> list = this;

    while (!list.isEnd()) {
      list.head.get().finish(item -> arrayList.add(item));
      list = list.tail.get();
    }

    return arrayList;
  }

  /**
   * Reduction operation that will accumulate
   * values, then combine the values in the list.
   * 
   * @param <U> type of return item.
   * @param id  initial identity value.
   * @param acc Combiner that combines the accumulated value.
   * @return value after reduce is performed onto the stream.
   */
  public <U> U reduce(U id, Combiner<? extends U, ? super U, ? super T> acc) {
    return this.head.get()
        .transform(item -> this.tail.get().reduce(acc.combine(id, item), acc))
        .except(() -> this.tail.get().reduce(id, acc));
  }

  /**
   * Get count of items in InfiniteList.
   * 
   * @return the amount of items in the list.
   */
  public long count() {
    return reduce(0L, (x, y) -> x + 1L);
  }

  /**
   * String representation of the list comprising
   * of head and tail.
   * 
   * @return String representation of the list.
   */
  @Override
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }

  /**
   * If instance of list is End.
   * 
   * @return false all the time as list contains
   *         value.
   */
  public boolean isEnd() {
    return false;
  }

  /**
   * Returns a presentation of the end of a list.
   * 
   * @param <T> data type of InfiniteList.
   * @return a list that represents ending of the list.
   */
  @SuppressWarnings("unchecked")
  public static <T> InfiniteList<T> end() {
    return (InfiniteList<T>) END;
  }

  /**
   * A class that represents the ending of a infinite list.
   */
  private static class End extends InfiniteList<Object> {

    /**
     * Constructor for End.
     */
    End() {
      super(Memo.from(() -> Actually.ok(null)), Memo.from(
          () -> new InfiniteList<>(null, null)));
    }

    /**
     * Check if list is End instance.
     * 
     * @return whether instance of list is End.
     */
    @Override
    public boolean isEnd() {
      return true;
    }

    /**
     * Overriden method for End list when
     * accessing head.
     * 
     * @return throw error for End list.
     */
    @Override
    public Object head() {
      throw new NoSuchElementException();
    }

    /**
     * Overriden method for End list when
     * accessing tail.
     * 
     * @return throw error for End list.
     */
    @Override
    public InfiniteList<Object> tail() {
      throw new NoSuchElementException();
    }

    /**
     * Overriden method for End list when
     * accessing map.
     * 
     * @return return an instance of End.
     */
    @Override
    public <R> InfiniteList<R> map(Immutator<? extends R, ? super Object> f) {
      return End.end();
    }

    /**
     * Overriden method for End list when
     * accessing filter.
     * 
     * @return return an instance of End.
     */
    @Override
    public InfiniteList<Object> filter(Immutator<Boolean, ? super Object> pred) {
      return this;
    }

    /**
     * Overriden method for End list when
     * accessing limit.
     * 
     * @return return an instance of End.
     */
    @Override
    public InfiniteList<Object> limit(long n) {
      return end();
    }

    /**
     * Overriden method for End list when
     * accessing takeWhile.
     * 
     * @return return an instance of End.
     */
    @Override
    public InfiniteList<Object> takeWhile(Immutator<Boolean, ? super Object> pred) {
      return end();
    }

    /**
     * Overriden method for End list when
     * accessing reduce.
     * 
     * @return returns the identity value.
     */
    @Override
    public <U> U reduce(U id, Combiner<? extends U, ? super U, ? super Object> acc) {
      return id;
    }

    /**
     * Overriden method for End list when
     * accessing toList.
     * 
     * @return return an empty List.
     */
    @Override
    public List<Object> toList() {
      return List.of();
    }

    /**
     * String representation of End.
     * 
     * @return String representation of End
     *         hard coded to "-".
     */
    @Override
    public String toString() {
      return "-";
    }
  }
}