package sql;

import dto.EnsembleDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnsembleSql {


    public static void update(EnsembleDto ensembleDto) {
        Connection connection = ConnectionHolder.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE ENSEMBLE SET NAME='" + ensembleDto.getName()
                    + "' ,TYPE = '" + ensembleDto.getType()
                    + "' WHERE ID =" + ensembleDto.getId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static EnsembleDto get(int id) {
        Connection connection = ConnectionHolder.getConnection();

        String sql = "select * from ensemble WHERE ID =" + id;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            EnsembleDto ensembleDto = null;

            while (resultSet.next()) {
                ensembleDto = new EnsembleDto();
                ensembleDto.setId(resultSet.getInt("id"));
                ensembleDto.setName(resultSet.getString("NAME"));
                ensembleDto.setType(resultSet.getString("TYPE"));
            }
            return ensembleDto;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<EnsembleDto> search(String name, String type) {
        Connection connection = ConnectionHolder.getConnection();
        boolean isFirst = true;
        String sql = "select * from ensemble";
        if ((name != null && !name.isEmpty()) || (type != null && !type.isEmpty())) {
            sql += " where";
        }
        if (name != null && !name.isEmpty()) {

            sql += " NAME like '%" + name + "%'";
            isFirst = false;
        }
        if (type != null && !type.isEmpty()) {
            if (!isFirst) {
                sql += " AND ";
            }
            sql += " TYPE like '%" + type + "%'";
        }
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<EnsembleDto> ensembleDtos = new ArrayList<>();

            while (resultSet.next()) {

                EnsembleDto ensembleDto = new EnsembleDto();
                ensembleDto.setId(resultSet.getInt("id"));
                ensembleDto.setName(resultSet.getString("NAME"));
                ensembleDto.setType(resultSet.getString("TYPE"));
                ensembleDtos.add(ensembleDto);
            }
            return ensembleDtos;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static void insert(EnsembleDto ensembleDto) {
        Connection connection = ConnectionHolder.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO ENSEMBLE(NAME,TYPE) VALUES ('"
                    + ensembleDto.getName() + "', '" + ensembleDto.getType() + "')");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void delete(int id) {

        Connection connection = ConnectionHolder.getConnection();

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM ENSEMBLE WHERE ID=" + id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
