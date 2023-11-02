package repository;

import entity.Todolist;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TodoListRepositoryImpl implements TodoListRepository {

    public Todolist[] data = new Todolist[10];

    private DataSource dataSource;

    public TodoListRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Todolist[] getall() {
        return data;
    }

    // cek apakah model penuh?

    public boolean isFull() {
        var isFull = true;
        for (var i = 0; i < data.length; i++) {
            if (data[i] == null) {
                //model masih ada yang kosong
                isFull = false;
                break;
            }
        }
        return isFull;
    }

    public void resizeIsFull() {
        if (isFull()) {
            var temp = data;
            data = new Todolist[data.length * 2];

            for (var i = 0; i < temp.length; i++) {
                data[i] = temp[i];
            }
        }
    }

    @Override
    public void add(Todolist todolist) {
        // jika penuh kita resize ukuran array 2x lipat
//            resizeIsFull();

        // tambahkan ke posisi yang data arraynya NULL
//        for (var i = 0; i < data.length; i++) {
//            if (data[i] == null) {
//                data[i] = todolist;
//                break;
//            }
//        }

        String sql = "INSERT INTO todolist(todo) VALUES (?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, todolist.getTodo());
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }



    @Override
    public boolean remove(Integer number) {
        if ((number - 1) >= data.length){
            return false;
        }else if (data[number - 1] == null) {
            return false;
        }else {
            for (int i = (number - 1); i < data.length; i++) {
                if (i == (data.length - 1)) {
                    data[i] = null;
                } else {
                    data[i] = data[i + 1];
                }
            }
            return true;
        }
    }
}
