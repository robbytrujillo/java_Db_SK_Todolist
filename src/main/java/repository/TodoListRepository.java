package repository;

import entity.Todolist;

public interface TodoListRepository {

    Todolist[] getall();

    //menambah todolist
    void add(Todolist todolist);

    boolean remove(Integer number);


}
