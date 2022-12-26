package cs2030s.fp;

/**
 * The Transformer class.
 *
 * Contains methods before and after.
 *
 * CS2030S Lab 5
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */

// Suppose "this" is f(x)
public abstract class Transformer<R, P> implements Immutator<R, P> {

  // f(g(x))
  /**
   * 
   * @param <N>         Accepts transformer with generic N as arg
   * @param transformer Passing in "this" from outside
   * @return a new transformer that can be invoked.
   */
  public <N> Transformer<R, N> after(Transformer<P, N> transformer) {

    class Invoker {
      public Transformer<R, N> call(Transformer<R, P> t) {
        return new Transformer<R, N>() {
          public R invoke(N param) {
            return t.invoke(transformer.invoke(param));
          }
        };
      }
    }

    return new Invoker().call(this);
  }

  // g(f(x))
  /**
   * 
   * @param <N>         Accepts transformer with generic N as arg
   * @param transformer Passing in "this" from outside
   * @return a new transformer that can be invoked.
   */
  public <T> Transformer<T, P> before(Transformer<T, R> transformer) {

    class Invoker {
      public Transformer<T, P> call(Transformer<R, P> t) {
        return new Transformer<T, P>() {
          public T invoke(P param) {
            return transformer.invoke(t.invoke(param));
          }
        };
      }
    }

    return new Invoker().call(this);
  }
}