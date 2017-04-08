package service;

import java.util.List;
import java.util.Set;

/**
 * Created by vladislav on 08.04.17.
 */
public interface IStorageManager {

    int SIZE = 100;

    Set<String> add(String page, Set<String> linksOnPage);

    boolean isExist(String page);

    boolean isParsed(String page);

    boolean isFull();

    void print();

    /**
     * Вершины, в которые попадем из текущей
     * @param index
     * @return
     */
    List<Integer> getTo(int index);

    /**
     * Вершины, из которых попадаем в текущую
     * @param index
     * @return
     */
    List<Integer> getFrom(int index);

    int getSize();

    String getPage(int index);
}
