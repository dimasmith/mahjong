package net.anatolich.mahjong.game.impl;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Dmytro Kovalchuk
 */
public final class CyclicalIterator<T> implements Iterator<T> {

    static <T> CyclicalIterator<T> forList(List<T> hintList) {
        return new CyclicalIterator<>(hintList);
    }
    private final List<T> itemList;
    private Iterator<T> iterator;

    private CyclicalIterator(List<T> hintList) {
        this.itemList = hintList;
        iterator = hintList.iterator();
    }

    @Override
    public boolean hasNext() {
        return !itemList.isEmpty();
    }

    @Override
    public T next() {
        if (!iterator.hasNext()) {
            iterator = itemList.iterator();
        }
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
