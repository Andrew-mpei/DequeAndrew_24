package ru.mpei;

/**
 * Interface which allows getting access to inner data for ru.mpei.TripletDeque
 */
public interface Containerable{

    Object[] getContainerByIndex(int cIndex);

}
