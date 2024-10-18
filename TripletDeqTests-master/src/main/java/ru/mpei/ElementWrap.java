package ru.mpei;

public class ElementWrap <E>{

    private int maxSizeValues = 4;
    private ElementWrap<E> nextLink;
    private ElementWrap<E> prevLink;
    private Object[] values = new Object[]{null, null, null, null, null};

    public ElementWrap() {
    }

//    public void setValues(Object[] values) {
//        this.values = values;
//    }

    public int getMaxSizeValues() {
        return maxSizeValues;
    }

    public void setNextLink(ElementWrap<E> nextLink) {
        this.nextLink = nextLink;
    }

    public void setPrevLink(ElementWrap<E> prevLink) {
        this.prevLink = prevLink;
    }

    public ElementWrap<E> getNextLink() {
        return nextLink;
    }

    public ElementWrap<E> getPrevLink() {
        return prevLink;
    }

    public Object[] getValues() {
        return values;
    }

    //номера несвободного элемента в левой части массива, вызывается после boolean checkNullLeft()
    public int checkNullLeftInt(){
        for (int i = 1; i < values.length; i++){
            if (values[i] != null){
                return i;
            }
        }
        return values.length - 1;
    }
    //номера несвободного элемента в правой части массива, вызывается после boolean checkNullRight()
    public int checkNullRightInt() {
//        if (values[values.length - 1] != null) {
//            return values.length - 1;
//        }
        for (int i = values.length - 1; i >= 0; i--) {
            if (values[i] != null){
                return i;
            }
        }
        return 0;
    }
    //проверка наличия свободных элементов в левой части массива
    public boolean checkNullLeft(){
        return (values[0] == null);
    }
    //проверка наличия свободных элементов в правой части массива
    public boolean checkNullRight() {
        return (values[maxSizeValues] == null);
    }
    public boolean checkOneElement(){
        int count = 0;
        for (int i = 0; i < values.length; i++){
            if (values[i] == null){
                count ++;
            }
        }
        if (count == 1){
            return true;
        }
        return false;
    }
}
