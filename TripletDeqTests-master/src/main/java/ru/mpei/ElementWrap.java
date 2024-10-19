package ru.mpei;

public class ElementWrap <E>{

    private int maxSizeValues = 4;
    private ElementWrap<E> nextLink;
    private ElementWrap<E> prevLink;
    private Object[] values = new Object[]{null, null, null, null, null};

    public ElementWrap() {
    }

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

    public int checkNullLeftInt(){
        for (int i = 1; i < values.length; i++){
            if (values[i] != null){
                return i;
            }
        }
        return values.length - 1;
    }

    public int checkNullRightInt() {

        for (int i = values.length - 1; i >= 0; i--) {
            if (values[i] != null){
                return i;
            }
        }
        return 0;
    }

    public boolean checkNullLeft(){
        return (values[0] == null);
    }

    public boolean checkNullRight() {
        return (values[maxSizeValues] == null);
    }
}
