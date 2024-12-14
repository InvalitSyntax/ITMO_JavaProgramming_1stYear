package interfaces;

import java.util.ArrayList;
import java.util.function.Function;

@FunctionalInterface
public interface GetBooleanFromArrayAndFunc<T, E> {
    boolean apply(ArrayList<T> arrayList, Function<E, Boolean> function);

    static <T, E> boolean getBooleansFromArray(ArrayList<T> arrayList, Class<E> clazz, Function<E, Boolean> function, boolean reverse) {
        for (T t : arrayList) {
            if (clazz.isInstance(t)) {
                E e = clazz.cast(t);
                if (function.apply(e) != reverse) {
                    return false;
                }
            }
        }
        return true;
    }

    /* static boolean all(boolean ... bools) {
        return bools.allMatch
    }

     */
}
