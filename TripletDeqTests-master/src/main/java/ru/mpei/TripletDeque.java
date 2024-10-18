package ru.mpei;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TripletDeque<E> implements Deque<E>, Containerable {
    private ElementWrap<E> firstValues;
    private ElementWrap<E> lastValues;
    private int size = 0;

    private int maxSizeTriplet = 1000;


    public TripletDeque(int maxSizeTriplet) {
        this.maxSizeTriplet = maxSizeTriplet;
        ElementWrap<E> elementWrap = new ElementWrap<>();
        firstValues = elementWrap;
        lastValues = elementWrap;
    }
    //    вставляет указанный элемент в начало этого списка, если это возможно сделать немедленно, не нарушая ограничений по емкости,
//    вызывая исключение IllegalStateException, если в данный момент нет свободного места. При использовании deque с ограниченной пропускной способностью,
//    как правило, предпочтительнее сначала воспользоваться предложением метода.
//    Параметры:
//    e – элемент, который нужно добавить
//    Бросает:
//    Исключение IllegalStateException – если элемент не может быть добавлен в данный момент из-за ограничений емкости
//    ClassCastException – если класс указанного элемента препятствует его добавлению в этот список
//    Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null
//    Исключение IllegalArgumentException – если какое-либо свойство указанного элемента препятствует его добавлению в этот список
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
        //есть место слева
        if(firstValues.checkNullLeft()){
            firstValues.getValues()[firstValues.checkNullLeftInt() - 1] = e;
            //места слева нет, проверяем справа есть ли место -> смещаем элементы
        }else{
            createLeftTriplet(e);
            size++;

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
        //есть место справа
        if(lastValues.checkNullRight()){
            lastValues.getValues()[lastValues.checkNullRightInt() + 1] = e;
        }else{
            createRightTriplet(e);
            size++;
        }
        while (lastValues.getValues()[0] == null){
            movingToLeft(lastValues);
        }
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
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
            if (firstValues.checkOneElement()){
                temp = firstValues.getValues()[firstValues.checkNullLeftInt()];
//                lastValues = null;
//                firstValues = lastValues;
            }
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
            if (lastValues.checkOneElement()){
                temp = lastValues.getValues()[lastValues.checkNullRightInt()];
//                lastValues = null;
//                firstValues = lastValues;
            }
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
        return null;
    }

    @Override
    public E pollLast() {
        return null;
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
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (o == null){
            throw new NullPointerException();
        }
        if (o.getClass().equals(firstValues.getValues().getClass())){
            throw new ClassCastException();
        }
        ElementWrap<E> temp = firstValues;
        ElementWrap<E> tempNext = temp;
        boolean flag = false;
        if (!checkOneTriplet()){
            while (temp.getValues()[4] == null){
                movingToLeftAny(temp);
            }
            while (temp != lastValues && flag == false){
                flag = forRemoveOccuranceFirst(temp,o);
                if (flag){
                    temp.getValues()[4] = temp.getNextLink().getValues()[0];
                    if (temp.getNextLink() == lastValues){
                        if (flag == false){
                            flag = forRemoveOccuranceFirst(temp,o);
                        }else {
                            movingToLeftAny(temp.getNextLink());
                        }
                    }
                    break;
                }
                temp = temp.getNextLink();
            }

            if (temp.getNextLink() == null ){
                if(flag == false){
                    flag = forRemoveOccuranceFirst(temp,o);

                }else{
                    movingToLeftAny(temp);
                }
                temp.getValues()[4] = null;

            }else{
                temp = temp.getNextLink();
            }

            if (flag && temp.getNextLink() != null){
                while (temp.getNextLink() != null){
                    movingToLeftAny(temp);
                    temp.getValues()[4] = temp.getNextLink().getValues()[0];
                    temp = temp.getNextLink();
                }
                temp.getPrevLink().getValues()[4] = temp.getValues()[0];
                movingToLeftAny(temp);
            }
        }else{
            flag = forRemoveOccuranceFirst(temp,o);
            temp.getValues()[4] = null;
        }
        if (temp == firstValues && firstValues.getValues()[0]==null && firstValues.getValues()[4]==null){
            firstValues = firstValues.getNextLink();
            firstValues.setPrevLink(null);
        }if (temp == lastValues && lastValues.getValues()[0]==null && lastValues.getValues()[4]==null){
            lastValues = lastValues.getPrevLink();
            lastValues.setNextLink(null);
        }
        if (flag==false){
            return false;
        }
        return true;

    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null){
            throw new NullPointerException();
        }
        if (o.getClass().equals(firstValues.getValues().getClass())){
            throw new ClassCastException();
        }
        ElementWrap<E> temp = lastValues;
        boolean flag = false;
        if (!checkOneTriplet()){
//            while (temp.getValues()[0] == null){
//                movingToLeftAny(temp);
//            }
            while (temp != firstValues && flag == false){
                flag = forRemoveOccuranceLast(temp,o);
                if (flag){
                    if(temp.getNextLink() != null){
                        temp.getValues()[4] = temp.getNextLink().getValues()[0];
                    }
                    break;
                }
                temp = temp.getPrevLink();
                if (temp.getPrevLink() == null && flag == false){
                    flag = forRemoveOccuranceLast(temp,o);
                    temp.getValues()[4] = temp.getNextLink().getValues()[0];

                }

            }
            if (temp != lastValues && temp.getValues()[1] != null){
                temp.getValues()[0] = temp.getPrevLink().getValues()[4];
            }

            if (flag && temp.getNextLink() != null){
                temp = temp.getNextLink();

                while (temp.getNextLink() != null){
                    movingToLeftAny(temp);
                    temp.getValues()[4] = temp.getNextLink().getValues()[0];
                    temp = temp.getNextLink();
                }
                movingToLeftAny(temp);
                temp.getPrevLink().getValues()[4] = temp.getValues()[0];
            }
        }else{
            flag = forRemoveOccuranceLast(temp,o);
            if (flag){
                temp.getValues()[4] = null;
            }
        }
        if (temp == firstValues && firstValues.getValues()[0]==null && firstValues.getValues()[4]==null){
            firstValues = firstValues.getNextLink();
            firstValues.setPrevLink(null);
        }if (temp == lastValues && lastValues.getValues()[0]==null && lastValues.getValues()[4]==null){
            lastValues = lastValues.getPrevLink();
            lastValues.setNextLink(null);
        }
        if (flag==false){
            throw new NoSuchElementException();
        }
        return true;
    }

    private boolean forRemoveOccuranceLast(ElementWrap<E> temp, Object o) {
        boolean flag = false;
        for (int i = 4; i >= 0; i--){
            if (temp.getValues()[i] != null){
                if (temp.getValues()[i].equals(o)){
                    flag = true;
                    temp.getValues()[i] = null;
                    for(int j = i; j < temp.getValues().length-1; j++){
                        temp.getValues()[j] = temp.getValues()[j+1];
                    }
                    break;
                }
            }
        }
        return flag;
    }
    private boolean forRemoveOccuranceFirst(ElementWrap<E> temp, Object o){
        boolean flag = false;

        for (int i = 0; i < temp.getValues().length; i++){
            if (temp.getValues()[i] != null){
                if (temp.getValues()[i].equals(o)){
                    flag = true;
                    temp.getValues()[i] = null;
                    for(int j = i; j < temp.getValues().length-1; j++){
                        temp.getValues()[j] = temp.getValues()[j+1];
                    }
                    break;
                }
            }
        }
//        if (firstValues.checkNullRightInt() == 0){
//            firstValues = firstValues.getNextLink();
//            firstValues.setPrevLink(null);
//        }
        return flag;
    }

    //    add
//    Вставляет указанный элемент в очередь, представленную этим deque (другими словами, в хвост этого deque),
//    если это возможно сделать немедленно, не нарушая ограничений емкости, возвращая true в случае успеха и вызывая исключение IllegalStateException,
//    если в данный момент нет свободного места. При использовании deque с ограниченной вместимостью, как правило, предпочтительнее использовать offer.
//    Этот метод эквивалентен добавлению последнего.
//            Параметры:
//    e – элемент, который нужно добавить
//    Возвращается:
//            true (как указано в Collection.add)
//    Бросает:
//    Исключение IllegalStateException – если элемент не может быть добавлен в данный момент из-за ограничений емкости
//    ClassCastException – если класс указанного элемента препятствует его добавлению в этот список
//    Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null
//    Исключение IllegalArgumentException – если какое-либо свойство указанного элемента препятствует его добавлению в этот список
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;

    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

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
    public boolean containsAll(Collection<?> c) {
        return false;
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
        return firstValues.getValues().length;
    }

    @Override
    public boolean isEmpty() {
        if (checkOneTriplet()){
            return (firstValues.checkNullLeft() && firstValues.checkNullRight());
        }
        return false;
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
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public Object[] getContainerByIndex(int cIndex) {
        ElementWrap<E> temp = firstValues;
        int count = 0;
        while (count< cIndex){
            temp = temp.getNextLink();
            count++;
        }
        if(temp == null){
            return null;
        }else{
            return temp.getValues();
        }
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
    private void movingToLeftAny(ElementWrap<E> any){
        int i = 0;
        while(i < any.getValues().length - 1 && any.getValues()[i] != null){
            any.getValues()[i] = any.getValues()[i+1];
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


    public void print(){
        ElementWrap<E> newtriplet = firstValues;
        boolean fl = true;
        int ii = 0;
        while(ii < 100){
            for (int i = 0; i <= firstValues.getMaxSizeValues(); i++){
                System.out.print(newtriplet.getValues()[i] + " ");
            }
            System.out.println();
            newtriplet = newtriplet.getNextLink();
            ii++;

        }



    }
}
