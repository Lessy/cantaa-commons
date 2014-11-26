package com.cantaa.util.comparator;

/**
 * @author Hans Lesmeister
 */
class ComparableComparator extends AbstractComparator<Comparable> {

    static final ComparableComparator ascendInstance = new ComparableComparator(false);
    static final ComparableComparator descendInstance = new ComparableComparator(true);

    ComparableComparator(boolean isDescend) {
        super(isDescend);
    }

    static ComparableComparator getInstance(boolean descend) {
        if (descend) {
            return descendInstance;
        }

        return ascendInstance;
    }

    @Override
    public Comparable getComparableValue(Comparable object) {
        return object;
    }
}
