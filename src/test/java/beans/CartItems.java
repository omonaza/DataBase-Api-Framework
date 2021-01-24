package beans;

/***
 *Create a Bean class CartItems that has following fields:
 *  id, quantity, total_price, food_id
 *  Override and Implement equals() , hashcode(), compareTo() methods,
 *  that will help you to perform comparison and sorting operations.
 */
public class CartItems implements Comparable< CartItems >{

    private Integer id;
    private Integer quantity;
    private Double total_price;
    private Integer food_id;

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public Integer getFood_id() {
        return food_id;
    }

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   @Override
    public int compareTo(CartItems o) {
        return this.getId().compareTo(o.getId());
    }

     @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CartItems items = (CartItems) o;
            return id.equals(items.id) &&
                    quantity.equals(items.quantity)
                    && total_price.equals(items.total_price)
                    && food_id.equals(items.food_id);
        }

        @Override
        public int hashCode() {
            return id;
        }

    @Override
    public String toString() {
        return "CartItems{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", total_price=" + total_price +
                ", food_id=" + food_id +
                '}';
    }
}
