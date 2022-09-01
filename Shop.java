/**
 * Class that represents a Shop with multiple counters.
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class Shop {

    private final String shopName;
    private ShopCounter[] counters;

    /**
     * Constructor for a Shop
     * @param shopName name of the shop.
     * @param numberOfCounters number of counters the shop will have.
     */
    public Shop(String shopName, int numberOfCounters) {

        this.shopName = shopName;

        /**
         * Generate counters and designate a staff to each counter.
         */
        counters = new ShopCounter[numberOfCounters];
        for (int i = 0; i < numberOfCounters; i++) {
            counters[i] = new ShopCounter(new CounterStaff(Integer.toString((i)), "Staff " + (i + 1)), i, 1);
        }
    }

    /**
     * @return an array of ShopCounters that exists in a Shop.
     */
    public ShopCounter[] getCounters() {
        return counters;
    }
    
    /**
     * @return a String of the shop's name
     */
    public String getShopName() {
        return shopName;
    }
}
