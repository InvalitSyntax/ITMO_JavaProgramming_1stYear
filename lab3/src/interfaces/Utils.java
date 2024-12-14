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

    static boolean isTrueWithChance(int chancePercent) {
        Random random = new Random();
        return random.nextInt(100) < chancePercent;
    }

    static int getRandomIntInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min; // Генерирует число в диапазоне [min, max)
    }
}
