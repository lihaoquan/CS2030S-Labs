import cs2030s.fp.Combiner;
import cs2030s.fp.Immutator;
import cs2030s.fp.Memo;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper around a lazily evaluated and memoized
 * list that can be generated with a lambda expression.
 *
 * @author Adi Yoga S. Prabawa
 * @version CS2030S AY 22/23 Sem 1
 */
class MemoList<T> {
  /** The wrapped java.util.List object */
  private List<Memo<T>> list;

  /**
   * A private constructor to initialize the list to the given one.
   *
   * @param list The given java.util.List to wrap around.
   */
  private MemoList(List<Memo<T>> list) {
    this.list = list;
  }

  /**
   * Generate the content of the list. Given x and a lambda f,
   * generate the list of n elements as [x, f(x), f(f(x)), f(f(f(x))),
   * ... ]
   *
   * @param <T>  The type of the elements in the list.
   * @param n    The number of elements.
   * @param seed The first element.
   * @param f    The immutator function on the elements.
   * @return The created list.
   */
  public static <T> MemoList<T> generate(int n, T seed, Immutator<? extends T, ? super T> f) {
    MemoList<T> memoList = new MemoList<>(new ArrayList<>());
    Memo<T> curr = Memo.from(seed);
    for (int i = 0; i < n; i++) {
      memoList.list.add(curr);
      curr = curr.transform(f);
    }
    return memoList;
  }

  /**
   * Generates a list based on the combination of its components.
   * 
   * @param <T>   The datatype of items in the list.
   * @param n     Number of elements in the list.
   * @param first First element in the list.
   * @param snd   Second element in the list.
   * @param f     Combiner used to combine two elements in the list.
   * @return A new {@code MemoList<T>} with combiner applied to its elements in
   *         pairs.
   */
  public static <T> MemoList<T> generate(int n, T first, T snd,
      Combiner<? extends T, ? super T, ? super T> f) {
    MemoList<T> memoList = new MemoList<>(new ArrayList<>());

    memoList.list.add(Memo.from(first));
    memoList.list.add(Memo.from(snd));

    for (int i = 0; i < n - 2; i++) {
      Memo<T> item = memoList.list.get(i);
      memoList.list.add(item.combine(memoList.list.get(i + 1), f));
    }

    return memoList;
  }

  /**
   * Transforms each item in the list using an immutator.
   * 
   * @param <R> The datatype of items in the list.
   * @param f   The immutator used to transform items in the list.
   * @return A new {@code MemoList<R>} with immutator applied onto its elements.
   */
  public <R> MemoList<R> map(Immutator<? extends R, ? super T> f) {
    MemoList<R> memoList = new MemoList<>(new ArrayList<>());
    for (int i = 0; i < this.list.size(); i++) {
      memoList.list.add(i, this.list.get(i).transform(f));
    }
    return memoList;
  }

  /**
   * Flattens nested list into a single dimension list.
   * 
   * @param <R> The datatype of items in the list.
   * @param f   The immutator that is used to generate nested {@code MemoList<R>}.
   * @return A flattened {@code MemoList<R>}.
   */
  public <R> MemoList<R> flatMap(Immutator<? extends MemoList<R>, ? super T> f) {

    MemoList<R> memoList = new MemoList<>(new ArrayList<>());

    for (int i = 0; i < this.list.size(); i++) {
      MemoList<R> nestedList = f.invoke(this.list.get(i).get());
      for (int j = 0; j < nestedList.list.size(); j++) {
        memoList.list.add(nestedList.list.get(j));
      }
    }
    return memoList;
  }

  /**
   * Return the element at index i of the list.
   *
   * @param i The index of the element to retrieved (0 for the 1st element).
   * @return The element at index i.
   */
  public T get(int i) {
    return this.list.get(i).get();
  }

  /**
   * Find the index of a given element.
   *
   * @param v The value of the element to look for.
   * @return The index of the element in the list. -1 is element is not in the
   *         list.
   */
  public int indexOf(T v) {
    for (int i = 0; i < this.list.size(); i++) {
      if (this.list.get(i).equals(Memo.from(v))) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Return the string representation of the list.
   *
   * @return The string representation of the list.
   */
  @Override
  public String toString() {
    return this.list.toString();
  }
}
