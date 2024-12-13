package interfaces;

import java.util.Random;

public interface Utils {
    static String capitalized(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    static <T extends Enum<?>> T getRandomEnum(Class<T> clazz) {
        Random random = new Random();
        T[] enumConstants = clazz.getEnumConstants();
        return enumConstants[random.nextInt(enumConstants.length)];
    }
}
