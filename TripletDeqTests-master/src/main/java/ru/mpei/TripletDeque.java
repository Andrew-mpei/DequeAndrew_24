package ru.mpei;

import java.util.*;

public class TripletDeque<E> implements Deque<E>, Containerable {
    private ElementWrap<E> firstValues;
    private ElementWrap<E> lastValues;
    private int size = 0;

    private int maxSizeTriplet;


    public TripletDeque(int maxSizeTriplet) {
        this.maxSizeTriplet = maxSizeTriplet;
        ElementWrap<E> elementWrap = new ElementWrap<>();
        firstValues = elementWrap;
        lastValues = elementWrap;
        size = 1;
    }

    @Override
    public void addFirst(E e) {
        if (e == null){
            throw new NullPointerException();
        }
        if (size >= maxSizeTriplet){
            throw new IllegalStateException();
        }
        if (size >= maxSizeTriplet){
            throw new IllegalStateException();
        }

        if(firstValues.checkNullLeft()){
            firstValues.getValues()[firstValues.checkNullLeftInt() - 1] = e;
        }else{
            createLeftTriplet(e);

        }
        while (firstValues.getValues()[firstValues.getValues().length-1] == null){
            movingToRight(firstValues);
        }
    }


    @Override
    public void addLast(E e) {
        if (e == null){
            throw new NullPointerException();
        }
        if (size >= maxSizeTriplet){
            throw new IllegalStateException();
        }
        if(lastValues.checkNullRight()){
            lastValues.getValues()[lastValues.checkNullRightInt() + 1] = e;
        }else{
            createRightTriplet(e);
        }
        while (lastValues.getValues()[0] == null){
            movingToLeft(lastValues);
        }
    }

    @Override
    public boolean offerFirst(E e) {
        if (e == null){
            throw new NullPointerException();
        }else{
            if (size==maxSizeTriplet){
                throw new IllegalArgumentException();
            }else{
                addFirst(e);
                return true;
            }
        }
    }

    @Override
    public boolean offerLast(E e) {
        if (e == null){
            throw new NullPointerException();
        }else{
            if (size==maxSizeTriplet){
                throw new IllegalArgumentException();
            }else{
                addLast(e);
                return true;
            }
        }
    }

    @Override
    public E removeFirst() {
        Object temp;


        if (firstValues != lastValues){
            if(firstValues.getValues()[firstValues.getValues().length-2] == null){
                temp = firstValues.getValues()[firstValues.getValues().length-1];
                firstValues = firstValues.getNextLink();
                firstValues.setPrevLink(null);
                return (E) temp;
            }
            if (firstValues.checkNullLeft()){
                temp = firstValues.getValues()[firstValues.checkNullLeftInt()];
                firstValues.getValues()[firstValues.checkNullLeftInt()] = null;
            }else{
                temp = firstValues.getValues()[0];
                firstValues.getValues()[0] = null;
            }

        }else{//1 триплет
            if (firstValues.checkNullLeft()){
                temp = firstValues.getValues()[firstValues.checkNullLeftInt()];
                firstValues.getValues()[firstValues.checkNullLeftInt()] = null;
            }else{
                temp = firstValues.getValues()[0];
                firstValues.getValues()[0] = null;
            }
        }
        return (E) temp;
    }

    @Override
    public E removeLast() {
        Object temp;
        if (firstValues != lastValues){
            if(lastValues.getValues()[1] == null){
                temp = lastValues.getValues()[0];
                lastValues = lastValues.getPrevLink();
                lastValues.setNextLink(null);
                size--;
                return (E) temp;
            }
            if (lastValues.checkNullRight()){
                temp = lastValues.getValues()[lastValues.checkNullRightInt()];
                lastValues.getValues()[lastValues.checkNullRightInt()] = null;
            }else{
                temp = lastValues.getValues()[4];
                lastValues.getValues()[4] = null;
            }

        }else{//1 триплет
            if (lastValues.checkNullRight()){
                temp = lastValues.getValues()[lastValues.checkNullRightInt()];
                lastValues.getValues()[lastValues.checkNullRightInt()] = null;
            }else{
                temp = lastValues.getValues()[4];
                lastValues.getValues()[4] = null;
            }


        }
        return (E) temp;
    }

    @Override
    public E pollFirst() {
        if (this.checkOneTriplet() && firstValues.checkNullLeft() && firstValues.checkNullRight()){
            return null;
        }else{
            return removeFirst();
        }
    }

    @Override
    public E pollLast() {
        if (this.checkOneTriplet() && firstValues.checkNullLeft() && firstValues.checkNullRight()){
            return null;
        }else{
            return removeLast();
        }
    }

    @Override
    public E getFirst() {
        if (isEmpty()){
            throw  new NoSuchElementException();
        }
        if (!firstValues.checkNullLeft()){
            return (E) firstValues.getValues()[0];
        }
        return (E) firstValues.getValues()[firstValues.checkNullLeftInt()];

    }

    @Override
    public E getLast() {
        if (isEmpty()){
            throw  new NoSuchElementException();
        }else{
            return (E) lastValues.getValues()[lastValues.checkNullRightInt()];
        }
    }

    @Override
    public E peekFirst() {
        if (this.checkOneTriplet() && firstValues.checkNullLeft() && firstValues.checkNullRight()){
            return null;
        }else{
            return getFirst();
        }
    }

    @Override
    public E peekLast() {
        if (this.checkOneTriplet() && firstValues.checkNullLeft() && firstValues.checkNullRight()){
            return null;
        }else{
            return getLast();
        }
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        ArrayList<E> tempList = new ArrayList<>();
        ElementWrap<E> tempTriplet = firstValues;
        int tempSize = size;
        boolean flag = true;
        int i = 0, count = 0;

        while (tempSize > 0){
            while (i < tempTriplet.getValues().length){
                while (tempTriplet.getValues()[i] == null && i < tempTriplet.getValues().length){
                    i ++;
                    if (i == 5){
                        i = 4;

                        break;
                    }
                }
                if (o.equals(tempTriplet.getValues()[i]) && count == 0){
                    count +=1 ;
                    flag = false;
                }else{
                    if (tempTriplet.getValues()[i] == null){
                        break;
                    }
                    tempList.add((E) tempTriplet.getValues()[i]);
                }
                i++;
            }
            i = 0;
            if (tempTriplet.getNextLink() != null){
                tempTriplet = tempTriplet.getNextLink();
            }
            tempSize --;
        }
        this.clear();
        for (Object obj : tempList){
            if (obj != null){
                this.addLast((E) obj);
            }
        }
        return !flag;

    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        ArrayList<E> tempList = new ArrayList<>();
        ElementWrap<E> tempTriplet = lastValues;
        int tempSize = size;
        boolean flag = true;
        int i = tempTriplet.getValues().length - 1, count = 0;

        while (tempSize > 0){
            while (i >= 0){
                while (tempTriplet.getValues()[i] == null && i >= 0){
                    i --;
                    if (i == -1){
                        i = 0;

                        break;
                    }
                }
                if (o.equals(tempTriplet.getValues()[i]) && count == 0){
                    count +=1 ;
                    flag = false;
                }else{
                    if (tempTriplet.getValues()[i] == null){
                        break;
                    }
                    tempList.add((E) tempTriplet.getValues()[i]);
                }
                i--;
            }
            i = tempTriplet.getValues().length - 1;
            if (tempTriplet.getPrevLink() != null){
                tempTriplet = tempTriplet.getPrevLink();
            }
            tempSize --;
        }
        this.clear();
        for (Object obj : tempList){
            if (obj != null){
                this.addFirst((E) obj);
            }
        }
        return !flag;
    }


    @Override
    public boolean add(E e) {
        addLast(e);
        return true;

    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        if (peekFirst() == null){
            throw new NoSuchElementException();
        }
        return peekFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E el : c){
            offerLast(el);
        }
        return true;
    }

    @Override
    public void clear() {
        while(this.iterator().hasNext()){
            this.removeFirst();
        }
        size = 1;
    }

    @Override
    public void push(E e) {
        if (e == null){
            throw new NullPointerException();
        }else{
            addFirst(e);
        }

    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }


    @Override
    public boolean contains(Object o) {
        if (o == null){
            throw new NullPointerException();
        }
        Iterator<E> iteration = iterator();
        while(iteration.hasNext()){
            if (iteration.next().equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }



    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {

            private ElementWrap<E> temp = firstValues;
            private ElementWrap<E> tempPrev = firstValues;
            private int indexElement = findFirstEl(temp);

            private int findFirstEl(ElementWrap<E> temp) {
                for (int i = 0; i < firstValues.getValues().length; i++) {
                    if (temp.getValues()[i] != null) {
                        return i;
                    }
                }
                return 0;
            }
            @Override
            public boolean hasNext() {
                if ((indexElement < temp.getValues().length && temp.getValues()[indexElement] != null)
                        || (indexElement == temp.getValues().length && temp.getNextLink() != null)){
                    return true;
                }
                return false;
            }

            @Override
            public E next() {
                if (hasNext()){
                    int tempInd = 0;
                    if (temp.getPrevLink() == tempPrev && indexElement != 4){
                        tempInd = indexElement;
                    }else if (indexElement <= 4){
                        tempPrev = temp;
                        tempInd =  indexElement;
                    }else{
                        temp = temp.getNextLink();
                        indexElement = 0;
                    }
                    indexElement++;
                    return (E) temp.getValues()[tempInd];
                }else{
                    throw new NoSuchElementException();
                }
            }
        };
        return iterator;
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] getContainerByIndex(int cIndex) {
        ElementWrap<E> temp = firstValues;
        int count = 0;
        while (count < cIndex && temp != null){
            temp = temp.getNextLink();
            count++;
        }
        if(temp == null){
            return null;
        }else{
            return temp.getValues();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean removeAll(Collection<?> c) {
        clear();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        if (checkOneTriplet()){
            return (firstValues.checkNullLeft() && firstValues.checkNullRight());
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }



    //проверка на существование только 1 триплета
    private boolean checkOneTriplet(){
        return firstValues.getPrevLink() == (lastValues.getPrevLink());
    }

    //смещение слева направо всех элементов на 1
    private void movingToRight(ElementWrap<E> any){
        int i = any.getValues().length-1;
        Object[] temp = any.getValues();
        while(i > 0){
            any.getValues()[i] = temp[i-1];
            i--;
        }
        any.getValues()[0] = null;

    }
    private void movingToLeft(ElementWrap<E> any){
        int i = 0;
        Object[] temp = any.getValues();
        while(i < any.getValues().length - 1){
            any.getValues()[i] = temp[i+1];
            i++;
        }
        any.getValues()[any.getMaxSizeValues()] = null;
    }

    private void createLeftTriplet(E e){
        ElementWrap<E> newtriplet = new ElementWrap<>();
        firstValues.setPrevLink(newtriplet);
        newtriplet.setNextLink(firstValues);
        firstValues = newtriplet;
        firstValues.getValues()[firstValues.checkNullRightInt()] = e;
        size++;
    }
    private void createRightTriplet(E e){
        ElementWrap<E> newtriplet = new ElementWrap<>();
        lastValues.setNextLink(newtriplet);
        newtriplet.setPrevLink(lastValues);
        lastValues = newtriplet;
        lastValues.getValues()[lastValues.checkNullRightInt()] = e;
        size++;
    }
}
