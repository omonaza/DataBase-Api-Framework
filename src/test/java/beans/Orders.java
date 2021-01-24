package beans;

/***
 *Create a Bean class Orders that has following fields:
 *  id, order_placed_at, order_status, order_updated_at,custom_user_id
 *  Override and Implement equals() , hashcode(), compareTo() methods,
 *  that will help you to perform comparison and sorting operations.
 */
public class Orders implements Comparable<Orders> {

    private Integer id;
    private String  order_placed_at;
    private Integer order_status;
    private String order_updated_at;
    private Integer custom_user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_placed_at() {
        return order_placed_at;
    }

    public void setOrder_placed_at(String order_placed_at) {
        this.order_placed_at = order_placed_at;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public String getOrder_updated_at() {
        return order_updated_at;
    }

    public void setOrder_updated_at(String order_updated_at) {
        this.order_updated_at = order_updated_at;
    }

    public Integer getCustom_user_id() {
        return custom_user_id;
    }

    public void setCustom_user_id(Integer custom_user_id) {
        this.custom_user_id = custom_user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id.equals(orders.id) &&
                order_placed_at.equals(orders.order_placed_at)
                && order_status.equals(orders.order_status)
                && order_updated_at.equals(orders.order_updated_at)
                && custom_user_id.equals(orders.custom_user_id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Orders o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", order_placed_at='" + order_placed_at + '\'' +
                ", order_status=" + order_status +
                ", order_updated_at='" + order_updated_at + '\'' +
                ", custom_user_id=" + custom_user_id +
                '}';
    }
}
