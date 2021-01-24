package beans;

import lombok.Data;

import java.util.List;

@Data
public class CachedFoodResponse {

    private List<Food> foodCached;

}
