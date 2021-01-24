package beans;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TODO:
 * Add following fields to Food pojo class:
 *    Fields:
 *  - description
 *  - imageUrl
 *  - price
 *  - name
 *  - foodType
 * Implement getters and setters.
 * */

@Data
@EqualsAndHashCode
@ToString
@Builder
public class Food implements Comparable<Food> {
//    TODO: Implement here...

    private String description;
    private String imageUrl;
    private double price;
    private String name;
    private String foodType;

    @Override
    public int compareTo(Food o) {
        return this.getName().compareTo(o.getName());
    }


}
