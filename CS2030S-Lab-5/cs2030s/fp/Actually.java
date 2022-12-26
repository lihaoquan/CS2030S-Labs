package cs2030s.fp;

/**
 * The Actually interface.
 *
 *
 * CS2030S Lab 5
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */

public abstract class Actually<T> implements Immutatorable<T>, Actionable<T> {

  public static <T> Actually<T> ok(T res) {
    return new Success<T>(res);
  }

  @SuppressWarnings("unchecked")
  public static <T> Actually<T> err(Exception exception) {
    return (Actually<T>) new Failure(exception);
  }

  public abstract T unwrap() throws Exception;

  public abstract <U extends T> T except(Constant<? extends U> constant);

  public abstract void finish(Action<? super T> action);

  public abstract <R extends T> T unless(R param);

  public abstract <U> Actually<U> next(Immutator<? extends Actually<? extends U>, ? super T> param);

  private static class Success<T> extends Actually<T> {

    private T value;

    // Constructor private for immutability.
    private Success(T value) {
      this.value = value;
    };

    @Override
    public T unwrap() {
      return this.value;
    }

    @Override
    public <U extends T> T except(Constant<? extends U> constant) {
      return this.value;
    }

    @Override
    public void finish(Action<? super T> action) {
      action.call(this.value);
    }

    @Override
    public <R extends T> T unless(R param) {
      return this.value;
    }

    @Override
    public <R> Actually<R> transform(Immutator<? extends R, ? super T> param) {
      try {
        return new Success<R>(param.invoke(this.value));
      } catch (Exception e) {
        return Actually.err(e);
      }
    }

    @Override
    public void act(Action<? super T> action) {
      action.call(this.value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> Actually<U> next(Immutator<? extends Actually<? extends U>, ? super T> param) {
      try {
        return (Actually<U>) param.invoke(this.value);
      } catch (Exception e) {
        return Actually.err(e);
      }
    }

    @Override
    public String toString() {
      return "<" + this.value + ">";
    }

    /**
     * Check for equality between instances of Success<T>.
     *
     * @param obj The other value to be compared.
     * @return True if the two values are equal,
     *         false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj instanceof Success<?>) {
        Success<?> some = (Success<?>) obj;
        if (this.value == some.value) {
          return true;
        }
        if (this.value == null || some.value == null) {
          return false;
        }
        return this.value.equals(some.value);
      }
      return false;
    }
  }

  private static class Failure extends Actually<Object> {

    private Exception exc;

    // Constructor private for immutability.
    private Failure(Exception exception) {
      this.exc = exception;
    };

    @Override
    public Object unwrap() throws Exception {
      throw this.exc;
    }

    @Override
    public <U extends Object> Object except(Constant<? extends U> constant) {
      return constant.init();
    }

    @Override
    public void finish(Action<? super Object> action) {
    };

    @Override
    public <R extends Object> R unless(R param) {
      return param;
    }

    @Override
    public <R> Actually<R> transform(Immutator<? extends R, ? super Object> param) {
      return Actually.err(this.exc);
    }

    @Override
    public void act(Action<? super Object> action) {
    }

    @Override
    public <U> Actually<U> next(Immutator<? extends Actually<? extends U>, ? super Object> param) {
      return Actually.err(this.exc);
    }

    @Override
    public String toString() {
      return "[" + exc.getClass().getName() + "] " + exc.getMessage();
    }

    /**
     * Check for equality between instances of Failure.
     *
     * @param obj The other value to be compared.
     * @return True if the two values are equal,
     *         false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj instanceof Failure) {
        Failure some = (Failure) obj;

        if (this.exc.getMessage() == null || some.exc.getMessage() == null) {
          return false;
        }

        if (this.exc.getMessage() == some.exc.getMessage()) {
          return true;
        }

        return this.exc.getMessage().equals(some.exc.getMessage());
      }
      return false;
    }
  }
}