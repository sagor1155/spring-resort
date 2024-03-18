package edu.miu.cs.cs544.domain;

import java.lang.reflect.Field;

public class AbstractEntity {
    public AbstractEntity mergeObject( AbstractEntity forUpdate)
        throws
        IllegalAccessException, NoSuchFieldException {
            for (Field field : forUpdate.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(forUpdate);

                //If it is a non null value copy to destination
                if (null != value) {

                    Field destField = this.getClass().getDeclaredField(name);
                    destField.setAccessible(true);

                    destField.set(this, value);
                }
            }
            return this;
    }

}
