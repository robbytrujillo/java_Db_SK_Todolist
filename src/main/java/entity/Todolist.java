package entity;

public class Todolist {

    private String todo;

    public Todolist() {
    }

    public Todolist(String todo) {
        this.todo = todo;
    }

    public String getTodo() { //getter untuk mengambil data
        return todo;
    }

    public void setTodo(String todo) { //setter untuk merubah data
        this.todo = todo;
    }
}
