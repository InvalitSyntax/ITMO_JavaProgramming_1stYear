package objects.actions;

import java.util.Random;

public class Randoms {
    public static boolean isTrueWithChance(int chancePercent) {
        Random random = new Random();
        return random.nextInt(100) < chancePercent;
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz) {
        Random random = new Random();
        T[] enumConstants = clazz.getEnumConstants();
        return enumConstants[random.nextInt(enumConstants.length)];
    }

    public static int getRandomIntInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min; // Генерирует число в диапазоне [min, max)
    }
}
