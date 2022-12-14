/**
 * The Generic Array for CS2030S.
 *
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 2
 */
class Array<T extends Comparable<T>> {
  
  private T[] array;

  /**
   * We can only put an object into array
   * through set() and we only put object
   * of type T inside. So it is safe to 
   * suppress the warning for checking
   * type in this case.
   * 
   * @param size Size of the array of type T
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  Array(int size) {
    this.array = (T[]) new Comparable[size];
  }
  
  // Set an element of the array to be of object type T.
  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public int size() {
    return this.array.length;
  }

  // Returns the smallest item based on CompareTo.
  public T min() {
    T currentMin = this.array[0];
    for (int i = 1; i < this.array.length; i++) {
      if (this.array[i].compareTo(currentMin) < 0) {
        currentMin = this.array[i];
      }
    }

    return currentMin;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < this.array.length; i++) {
      s.append(i + ":" + this.array[i]);
      if (i != this.array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}
