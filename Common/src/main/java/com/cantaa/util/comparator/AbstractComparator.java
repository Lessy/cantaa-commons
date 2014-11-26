package com.cantaa.util.comparator;

import java.util.Comparator;

/**
 * Convenience Comporator. Makes it easy to sort lists that contain beans must sorting must be done
 * on a property of that bean.
 * <pre>
 *     // Sort list with cars on car's name:
 *     List&lt;Car> carList = createCarlist();
 *     Collections.sort(carList, CarComparator.getInstance());
 *
 *     class CarComparator extends AbstractComparator&lt;Car> {
 *        public Comparable<?> getComparableValue(Car car) {
 *            return car.getName();
 *        }
 *     }
 * </pre>
 *
 * @author Hans Lesmeister
 */
public abstract class AbstractComparator<T> implements Comparator<T> {

    private final boolean descend;

    public AbstractComparator() {
        this(false);
    }

    public AbstractComparator(boolean isDescend) {
        super();
        descend = isDescend;
    }

    @Override
    public int compare(T o1, T o2) {
        int smaller = -1;
        int bigger = 1;

        if (descend) {
            int temp = smaller;
            smaller = bigger;
            bigger = temp;
        }

        // Both are null => equal
        if ((o1 == null) && (o2 == null)) {
            return 0;
        }

        if (o1 == null) {
            return smaller;
        }

        if (o2 == null) {
            return bigger;
        }

        if ((o1 instanceof Comparable) && (o2 instanceof Comparable)) {
            int retVal = ((Comparable) o1).compareTo(o2);
            if (retVal < 0) {
                return smaller;
            }
            else if (retVal > 0) {
                return bigger;
            }
            else {
                return 0;
            }
        }
        else {
            Comparable<?> c1 = getComparableValue(o1);
            Comparable<?> c2 = getComparableValue(o2);
            return ComparableComparator.getInstance(descend).compare(c1, c2);  // Recursion
        }
    }

    /**
     * Determine the value to sort on
     * @param object The object eg. from the list that is being sorted. This value is never null
     * @return The value/poperty that must be sorted on. Can be null but must be comparable
     */
    abstract public Comparable<?> getComparableValue(T object);
}
