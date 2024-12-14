package objects;

import java.util.Random;

public class Utils {
    public static String capitalized(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz) {
        Random random = new Random();
        T[] enumConstants = clazz.getEnumConstants();
        return enumConstants[random.nextInt(enumConstants.length)];
    }

    static boolean isTrueWithChance(int chancePercent) {
        Random random = new Random();
        return random.nextInt(100) < chancePercent;
    }

    static int getRandomIntInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min; // Генерирует число в диапазоне [min, max)
    }
}
