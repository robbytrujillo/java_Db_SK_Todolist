package repository;

import entity.Todolist;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class TodoListRepositoryImpl implements TodoListRepository {

    private DataSource dataSource;

    public TodoListRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Todolist[] getall() {
        String sql = "SELECT id, todo FROM todolist";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)
             ) {

            List<Todolist> list = new ArrayList<>();

            while (resultSet.next()) {
                Todolist todolist = new Todolist();

                list.add(todolist);

        }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
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

    private boolean isExists(Integer number) {
        String sql = "SELECT id FROM todolist WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, number);

        try (ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean remove(Integer number) {
//        if ((number - 1) >= data.length){
//            return false;
//        }else if (data[number - 1] == null) {
//            return false;
//        }else {
//            for (int i = (number - 1); i < data.length; i++) {
//                if (i == (data.length - 1)) {
//                    data[i] = null;
//                } else {
//                    data[i] = data[i + 1];
//                }
//            }
//            return true;
//        }
        if(isExists(number)) {
            String sql = "DELETE FROM todolist WHERE id = ?";

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, number);
                statement.executeUpdate();

                return true;
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        } else {
            return false;
        }
    }
}
