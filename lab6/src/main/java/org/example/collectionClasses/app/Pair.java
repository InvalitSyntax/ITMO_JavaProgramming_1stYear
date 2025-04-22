package org.example.collectionClasses.app;

/**
 * Класс для хранения пары значений.
 *
 * @param <E> тип первого значения
 * @param <T> тип второго значения
 * @author ISyntax
 * @version 1.0
 */
public record Pair<E, T>(E first, T second) {
}