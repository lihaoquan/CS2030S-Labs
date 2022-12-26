/**
 * A non-generic Action to print the String
 * representation of the object.
 *
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Li Haoquan (Group 10A)
 */

// T will be type Object
// as all Objects have
// toString() method.
class Print implements Action<Object> {
  public void call(Object o) {
    System.out.println(o.toString());
  }
}