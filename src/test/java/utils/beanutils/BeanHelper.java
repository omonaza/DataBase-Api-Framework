package utils.beanutils;

import java.lang.reflect.Field;

/***
 * Create a method that will return String [] property names of the Class.
 * (returns property names, not values)
 */
public class BeanHelper {

     public static <T> String [] getBeanPropertyNames(Class<T> object){
       //TODO: IMPLEMENT HERE

        Field[] field = object.getDeclaredFields();
        String [] properties= new String[field.length];

        for (int i = 0;i<field.length;i++){
            properties[i]=field[i].getName();
        }
        return properties;
    }
}
