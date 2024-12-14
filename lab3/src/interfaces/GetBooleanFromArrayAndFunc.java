package interfaces;

import java.util.ArrayList;
import java.util.function.Function;

@FunctionalInterface
public interface GetBooleanFromArrayAndFunc<T> {
    boolean apply(ArrayList<T> arrayList, Function<T, Boolean> function);

    static <T> boolean getBooleansFromArray(ArrayList<T> arrayList, Function<T, Boolean> function, boolean reverse) {
        for (T t : arrayList) {
            if (function.apply(t) != reverse) {
                return false;
            }
        }
        return true;
    }

    /* static boolean all(boolean ... bools) {
        return bools.allMatch
    }

     */
}
