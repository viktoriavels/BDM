package sql;

import dto.MusicDto;
import dto.MusicianDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MusicSql {
    public static void update(MusicDto musicDto) {
        Connection connection = ConnectionHolder.getConnection();

        try (Statement statement = connection.createStatement()) {

            String sql = "UPDATE MUSIC SET ";
            sql += "NAME='" + musicDto.getName() + "'";
            sql += ",PERFORMANCE = '" + musicDto.getPerformance() + "'";

            if (musicDto.getMusicianDto() != null) {
                sql += ",EXECUTOR = " + musicDto.getMusicianDto().getId();
            } else {
                sql += ",EXECUTOR= NULL";
            }
            sql += " WHERE ID  = '" + musicDto.getId() + "'";
            statement.executeUpdate(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public static MusicDto get(int id) {
        Connection connection = ConnectionHolder.getConnection();
        String sql = "select MUSIC.ID ID,MUSIC.NAME NAME,MUSIC.PERFORMANCE PERFORMANCE," +
                "MUSIC.EXECUTOR EXECUTOR, MUSICIAN.NAME MUSICIAN_NAME " +
                " FROM MUSIC MUSIC LEFT JOIN MUSICIAN MUSICIAN ON MUSIC.EXECUTOR = MUSICIAN.ID  WHERE MUSIC.ID =" + id;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            MusicDto musicDto = null;

            while (resultSet.next()) {
                musicDto = new MusicDto();
                musicDto.setId(resultSet.getInt("id"));
                musicDto.setName(resultSet.getString("NAME"));
                musicDto.setPerformance(resultSet.getString("PERFORMANCE"));
                int musicianId = resultSet.getInt("EXECUTOR");

                if (musicianId != 0) {
                    MusicianDto musicianDto = new MusicianDto();
                    musicianDto.setId(musicianId);
                    musicianDto.setName(resultSet.getString("MUSICIAN_NAME"));

                    musicDto.setMusicianDto(musicianDto);
                }

            }
            return musicDto;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<MusicDto> search(String name, String executorName) {
        Connection connection = ConnectionHolder.getConnection();

        String sql = "select MUSIC.ID ID,MUSIC.NAME NAME,MUSIC.PERFORMANCE PERFORMANCE," +
                "MUSIC.EXECUTOR EXECUTOR, MUSICIAN.NAME MUSICIAN_NAME " +
                " FROM MUSIC MUSIC LEFT JOIN MUSICIAN MUSICIAN ON MUSIC.EXECUTOR = MUSICIAN.ID ";

        boolean isFirst = true;
        if ((name != null && !name.isEmpty())
                || (executorName != null && !executorName.isEmpty())) {
            sql += " where";
        }
        if (name != null && !name.isEmpty()) {
            sql += " MUSIC.NAME like '%" + name + "%'";
            isFirst = false;
        }

        if (executorName != null && !executorName.isEmpty()) {
            if (!isFirst) {
                sql += " AND ";
            }
            sql += " MUSICIAN.Name like '%" + executorName + "%'";
            isFirst = false;
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<MusicDto> musicDtos = new ArrayList<>();

            while (resultSet.next()) {
                MusicDto musicDto = new MusicDto();
                musicDto.setId(resultSet.getInt("id"));
                musicDto.setName(resultSet.getString("NAME"));
                musicDto.setPerformance(resultSet.getString("PERFORMANCE"));

                int musicianId = resultSet.getInt("EXECUTOR");
                if (musicianId != 0) {
                    MusicianDto musicianDto = new MusicianDto();
                    musicianDto.setId(musicianId);
                    musicianDto.setName(resultSet.getString("MUSICIAN_NAME"));
                    musicDto.setMusicianDto(musicianDto);
                }
                musicDtos.add(musicDto);
            }
            return musicDtos;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }


    }

    public static void insert(MusicDto musicDto) {
        Connection connection = ConnectionHolder.getConnection();
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO MUSIC(NAME,PERFORMANCE,EXECUTOR) VALUES ('"
                    + musicDto.getName() + "', '" + musicDto.getPerformance() + "' ";
            if (musicDto.getMusicianDto() != null) {
                sql += "," + musicDto.getMusicianDto().getId();
            } else {
                sql += ", NULL";
            }
            sql += ")";
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void delete(int id) {
        Connection connection = ConnectionHolder.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM MUSIC WHERE ID=" + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
