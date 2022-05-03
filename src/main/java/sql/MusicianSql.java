package sql;

import dto.EnsembleDto;
import dto.MusicianDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MusicianSql {
    public static void update(MusicianDto musicianDto) {
        Connection connection = ConnectionHolder.getConnection();

        try (Statement statement = connection.createStatement()) {

            String sql = "UPDATE MUSICIAN SET ";
            sql += "NAME='" + musicianDto.getName() + "'";
            sql += ",GENRE = '" + musicianDto.getGenre() + "'";
            sql += " ,MUSICAL_INSTRUMENT = '" + musicianDto.getInstrument() + "'";
            if (musicianDto.getEnsembleDto() != null) {
                sql += ",ENSEMBLE_ID = " + musicianDto.getEnsembleDto().getId();
            } else {
                sql += ",ENSEMBLE_ID = NULL";
            }
            sql += ",ROLE = '" + musicianDto.getGenre() + "'";
            sql += "WHERE ID  = '" + musicianDto.getId() + "'";
            statement.executeUpdate(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static MusicianDto get(int id) {
        Connection connection = ConnectionHolder.getConnection();

        String sql = "select MUSICIAN.ID ID,MUSICIAN.NAME NAME,MUSICIAN.GENRE GENRE," +
                "MUSICIAN.MUSICAL_INSTRUMENT MUSICAL_INSTRUMENT,MUSICIAN.ENSEMBLE_ID ENSEMBLE_ID," +
                "MUSICIAN.ROLE ROLE,ENSEMBLE.NAME ENSEMBLE_NAME, ENSEMBLE.TYPE ENSEMBLE_TYPE" +
                " FROM MUSICIAN MUSICIAN LEFT JOIN ENSEMBLE ENSEMBLE ON MUSICIAN.ENSEMBLE_ID = ENSEMBLE.ID  WHERE MUSICIAN.ID =" + id;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            MusicianDto musicianDto = null;

            while (resultSet.next()) {

                musicianDto = new MusicianDto();
                musicianDto.setId(resultSet.getInt("id"));
                musicianDto.setName(resultSet.getString("NAME"));
                musicianDto.setGenre(resultSet.getString("GENRE"));
                musicianDto.setInstrument(resultSet.getString("MUSICAL_INSTRUMENT"));
                musicianDto.setRole(resultSet.getString("ROLE"));
                int ensembleId = resultSet.getInt("ENSEMBLE_ID");
                if (ensembleId != 0) {
                    EnsembleDto ensembleDto = new EnsembleDto();
                    ensembleDto.setId(ensembleId);
                    ensembleDto.setName(resultSet.getString("ENSEMBLE_NAME"));
                    ensembleDto.setType(resultSet.getString("ENSEMBLE_TYPE"));
                    musicianDto.setEnsembleDto(ensembleDto);
                }
            }
            return musicianDto;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<MusicianDto> search(String name, String instrument, String genre) {
        Connection connection = ConnectionHolder.getConnection();

        String sql = "select MUSICIAN.ID ID,MUSICIAN.NAME NAME,MUSICIAN.GENRE GENRE," +
                "MUSICIAN.MUSICAL_INSTRUMENT MUSICAL_INSTRUMENT,MUSICIAN.ENSEMBLE_ID ENSEMBLE_ID," +
                "MUSICIAN.ROLE ROLE,ENSEMBLE.NAME ENSEMBLE_NAME, ENSEMBLE.TYPE ENSEMBLE_TYPE" +
                " FROM MUSICIAN MUSICIAN LEFT JOIN ENSEMBLE ENSEMBLE ON MUSICIAN.ENSEMBLE_ID = ENSEMBLE.ID";

        boolean isFirst = true;
        if ((name != null && !name.isEmpty())
                || (instrument != null && !instrument.isEmpty()) ||
                (genre != null && !genre.isEmpty())) {
            sql += " where";

        }
        if (name != null && !name.isEmpty()) {
            sql += " MUSICIAN.NAME like '%" + name + "%'";
            isFirst = false;
        }
        if (genre != null && !genre.isEmpty()) {
            if (!isFirst) {
                sql += " AND ";
            }
            sql += "  MUSICIAN.GENRE like '%" + genre + "%'";
            isFirst = false;
        }
        if (instrument != null && !instrument.isEmpty()) {
            if (!isFirst) {
                sql += " AND ";
            }
            sql += " MUSICIAN.MUSICAL_INSTRUMENT like '%" + instrument + "%'";
            isFirst = false;
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<MusicianDto> musicianDtos = new ArrayList<>();

            while (resultSet.next()) {

                MusicianDto musicianDto = new MusicianDto();
                musicianDto.setId(resultSet.getInt("id"));
                musicianDto.setName(resultSet.getString("NAME"));
                musicianDto.setGenre(resultSet.getString("GENRE"));
                musicianDto.setInstrument(resultSet.getString("MUSICAL_INSTRUMENT"));
                musicianDto.setRole(resultSet.getString("ROLE"));
                int ensembleId = resultSet.getInt("ENSEMBLE_ID");
                if (ensembleId != 0) {
                    EnsembleDto ensembleDto = new EnsembleDto();
                    ensembleDto.setId(ensembleId);
                    ensembleDto.setName(resultSet.getString("ENSEMBLE_NAME"));
                    ensembleDto.setType(resultSet.getString("ENSEMBLE_TYPE"));
                    musicianDto.setEnsembleDto(ensembleDto);
                }
                musicianDtos.add(musicianDto);
            }
            return musicianDtos;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static void insert(MusicianDto musicianDto) {
        Connection connection = ConnectionHolder.getConnection();

        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO MUSICIAN(NAME,MUSICAL_INSTRUMENT,ENSEMBLE_ID,ROLE,GENRE) VALUES ('"
                    + musicianDto.getName() + "', '" + musicianDto.getInstrument() + "' ";
            if (musicianDto.getEnsembleDto() != null) {
                sql += "," + musicianDto.getEnsembleDto().getId();
            } else {
                sql += ", NULL";
            }
            sql += ", '" + musicianDto.getRole() + "','" + musicianDto.getGenre() + "')";
            statement.executeUpdate(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void delete(int id) {
        Connection connection = ConnectionHolder.getConnection();

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM MUSICIAN WHERE ID=" + id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
