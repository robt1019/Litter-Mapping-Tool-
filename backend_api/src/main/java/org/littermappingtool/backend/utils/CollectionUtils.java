package org.littermappingtool.backend.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Collection utils.
 */
public final class CollectionUtils {

    /**
     * Sort a map.
     *
     * @param orig the orig
     * @return the map
     */
    public static Map<String, Object> sortMap(final Map<String, Object> orig) {
        final Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(final String o1, final String o2) {
                final int sizeCompare = orig.size() - orig.size();
                return sizeCompare != 0 ? sizeCompare : o1.compareTo(o2);
            }
        };

        final Map<String, Object> ret = new TreeMap<String, Object>(c);
        ret.putAll(orig);
        return ret;
    }

    /**
     * Cast a list.
     *
     * @param <T>   the generic type
     * @param clazz the clazz
     * @param c     the c
     * @return the list
     */
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for (Object o : c)
            r.add(clazz.cast(o));
        return r;
    }
}

