package Persistence;

import Model.CadernoVoto;

import java.util.*;
import java.sql.*;

public class CadernoVotoDAO implements Map<Integer, CadernoVoto> {

    private static final String INSERT_CADERNOVOTO = "INSERT INTO CadernoVoto (`votou?`,Eleicao_id_eleicao,Eleitor_id_eleitor) VALUES (?,?,?)";
    private static final String UPDATE_CADERNOVOTO = "UPDATE CadernoVoto SET `votou?` = ? WHERE Eleicao_id_eleicao = ? AND Eleitor_id_eleitor = ?";

    private static final String SELECT_CADERNOVOTO = "SELECT `votou?` FROM CadernoVoto WHERE Eleicao_id_eleicao = ? AND Eleitor_id_eleitor = ?";
    private static final String SELECT_CADERNOSVOTO = "SELECT Eleicao_id_eleicao, Eleitor_id_eleitor, `votou?` FROM CadernoVoto";

    private static final String DELETE_CADERNOVOTO = "DELETE FROM CadernoVoto WHERE Eleicao_id_eleicao = ? AND Eleitor_id_eleitor = ?";
    private static final String DELETE_CADERNOSVOTO = "DELETE FROM CadernoVoto";

    private static final String COUNT_CADERNOSVOTO = "SELECT COUNT(*) as n FROM CadernoVoto";
    private static final String SELECT_IDS = "SELECT Eleicao_id_eleicao, Eleitor_id_eleitor FROM CadernoVoto";

    private static final String SELECT_BY_ELEITOR_AND_ELEICAO = "SELECT  Eleicao_id_eleicao, Eleitor_id_eleitor, `votou?` FROM CadernoVoto WHERE Eleitor_id_eleitor = ? AND Eleicao_id_eleicao = ?";

    private String url;
    private String user;
    private String password;

    public CadernoVotoDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public int size() {
        try {
            int count;

            Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();

            try (ResultSet resultSet = statement.executeQuery(COUNT_CADERNOSVOTO)) {
                if (resultSet.next())
                    count = resultSet.getInt("n");
                else
                    count = -1;
            } finally {
                statement.close();
                connection.close();
            }

            return count;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() { return this.size() == 0;}

    @Override
    public boolean containsKey(Object key) {
        try {
            CadernoVoto newkey = (CadernoVoto) key;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CADERNOVOTO);

            statement.setInt(1, newkey.getEleicao());
            statement.setInt(2, newkey.getEleitor());

            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            } finally {
                statement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsKey(value);
    }

    @Override
    public CadernoVoto get(Object key) {
        CadernoVoto cadernoVoto = null;
        CadernoVoto newkey = (CadernoVoto) key;
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CADERNOVOTO);

            statement.setInt(1,newkey.getEleicao());
            statement.setInt(2,newkey.getEleitor());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cadernoVoto= new CadernoVoto();
                    cadernoVoto.setEleicao(result.getInt("Eleicao_id_eleicao"));
                    cadernoVoto.setEleitor(result.getInt("Eleitor_id_eleitor"));
                    cadernoVoto.setVotou(result.getBoolean("votou?"));
                }
            } finally {
                statement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cadernoVoto;
    }

    @Override
    public CadernoVoto put(Integer key, CadernoVoto value) {
        String query;
        if (key == 1)
            query= UPDATE_CADERNOVOTO;
        else
            query= INSERT_CADERNOVOTO;
        try {

            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setBoolean(1, value.getVotou());
            statement.setInt(2, value.getEleicao());
            statement.setInt(3, value.getEleitor());

            try{
                statement.executeUpdate();
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public CadernoVoto remove(Object key) {
        CadernoVoto cadernoVoto = get(key);
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(DELETE_CADERNOVOTO);

            statement.setInt(1, cadernoVoto.getEleicao());
            statement.setInt(2, cadernoVoto.getEleitor());

            try {
                statement.executeUpdate();
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cadernoVoto;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends CadernoVoto> m) {
        for(CadernoVoto c: m.values()){
            put(1,c);
        }
    }

    @Override
    public void clear() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(DELETE_CADERNOSVOTO);
            } finally {
                connection.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> s = new HashSet<>();

        try {

            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(SELECT_IDS);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    s.add(result.getInt(1));
                }
            } finally {
                statement.close();
                connection.close();
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return s;
    }

    @Override
    public Collection<CadernoVoto> values() {
        ArrayList<CadernoVoto> arrayList = new ArrayList<>();
        try {
            CadernoVoto cadernoVoto;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CADERNOSVOTO);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cadernoVoto = new CadernoVoto();
                    cadernoVoto.setEleicao(resultSet.getInt("Eleicao_id_eleicao"));
                    cadernoVoto.setEleitor(resultSet.getInt("Eleitor_id_eleitor"));
                    cadernoVoto.setVotou(resultSet.getBoolean("`votou?`"));

                    arrayList.add(cadernoVoto);
                }
            }  finally {
                statement.close();
                connection.close();
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return arrayList;
    }

    @Override
    public Set<Entry<Integer, CadernoVoto>> entrySet() {
        return null;
    }

    public CadernoVoto getByEleitorAndEleicao(int idEleitor, int idEleicao) {
        CadernoVoto cadernoVoto = null;

        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ELEITOR_AND_ELEICAO);

            statement.setInt(1, idEleitor);
            statement.setInt(2, idEleicao);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cadernoVoto = new CadernoVoto();
                    cadernoVoto.setEleicao(result.getInt("Eleicao_id_eleicao"));
                    cadernoVoto.setEleitor(result.getInt("Eleitor_id_eleitor"));
                    cadernoVoto.setVotou(result.getBoolean("votou?"));
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cadernoVoto;
    }
}