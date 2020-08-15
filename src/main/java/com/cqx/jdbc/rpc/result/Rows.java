package com.cqx.jdbc.rpc.result;

import java.util.*;
import java.util.function.Consumer;

public class Rows implements Iterable<Row> {
    /**
     * 实际存储row的对象
     */
    private List<Row> rows;

    /**
     * 列名数据
     */
    private Columns columns;

    /**
     * 数据size
     */
    private int size;

    /**
     * 当前位置
     */
    private int cursor = 0;

    public Rows(Columns columns, List<Row> rows) {
        this.rows = rows;
        this.columns = columns;
        this.size = rows.size();
    }

    public Rows(Columns columns) {
        this.columns = columns;
    }

    public void resetCursor(int cursor) {
        if (cursor < 0 || cursor > size - 1) {
            throw new IndexOutOfBoundsException("cursor out of bound!!!");
        }
        this.cursor = cursor;
    }

    public int currentCursor() {
        return this.cursor;
    }

    public Row current() {
        return rows.get(cursor);
    }

    public int size() {
        return this.size;
    }

    public boolean isFirst() {
        return cursor == 0;
    }

    public boolean isLast() {
        return cursor == size - 1;
    }

    public boolean hasNext() {
        return cursor < size - 1;
    }

    public Row next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("cursor out of bound!!!");
        }
        cursor++;
        return rows.get(cursor);
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super Row> action) {
        rows.forEach(action);
    }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator<Row> spliterator() {
        return rows.spliterator();
    }


    public List<Row> getRows() {
        return rows;
    }

    public Columns getColumns() {
        return columns;
    }

    public int getSize() {
        return size;
    }

    public int getCursor() {
        return cursor;
    }
}
