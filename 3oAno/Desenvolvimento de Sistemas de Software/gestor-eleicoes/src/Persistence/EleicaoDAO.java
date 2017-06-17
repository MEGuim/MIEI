package Persistence;

import Model.Eleicao;

import java.sql.*;
import java.util.*;

public class EleicaoDAO implements Map<Integer, Eleicao> {

    private static final String INSERT_ELEICAO = "INSERT INTO Eleicao (data_eleicao, tipo, activa,votos_nulos, votos_brancos) VALUES (?,?,?,?,?)";
    private static final String UPDATE_ELEICAO = "UPDATE Eleicao SET data_eleicao = ?, tipo = ?, activa = ? , votos_nulos = ?, votos_brancos = ? WHERE id_eleicao = ?";

    private static final String SELECT_ELEICAO = "SELECT data_eleicao, tipo, activa,votos_nulos, votos_brancos FROM Eleicao WHERE id_eleicao = ?";
    private static final String SELECT_ELEICOES = "SELECT id_eleicao, data_eleicao, tipo, activa,votos_nulos, votos_brancos FROM Eleicao";

    private static final String DELETE_ELEICAO = "CALL DELETE_ELEICAO_E_DADOS(?)";
    private static final String DELETE_ELEICOES = "DELETE FROM Eleicao";

    private static final String COUNT_ELEICOES = "SELECT COUNT(*) as n FROM Eleicao";
    private static final String SELECT_IDS = "SELECT id_eleicao FROM Eleicao";
    private static final String FINDACTIVES = "SELECT DISTINCT id_eleicao, data_eleicao,tipo, activa,votos_nulos, votos_brancos FROM Eleicao INNER JOIN CadernoVoto ON Eleicao_id_eleicao = id_eleicao INNER JOIN Cidadao ON Eleitor_id_eleitor = ? WHERE `votou?` = 0 AND Eleicao.activa=1";
    private static final String FINDHISTORIC ="SELECT DISTINCT id_eleicao, data_eleicao,tipo, activa,votos_nulos, votos_brancos FROM Eleicao INNER JOIN CadernoVoto ON Eleicao_id_eleicao = id_eleicao INNER JOIN Cidadao ON Eleitor_id_eleitor = ? WHERE `votou?` = 1";

    private String url;
    private String user;
    private String password;

    public EleicaoDAO(String url, String user, String password) {
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

            try (ResultSet resultSet = statement.executeQuery(COUNT_ELEICOES)) {
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
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_ELEICAO);

            statement.setInt(1, (int) key);

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
        return containsKey(((Eleicao) value).getIdEleicao());
    }

    @Override
    public Eleicao get(Object key) {
        Eleicao eleicao = null;
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_ELEICAO);

            statement.setInt(1,(int) key);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    eleicao = new Eleicao();
                    eleicao.setIdEleicao((int) key);
                    eleicao.setDataEleicao(result.getDate("data_eleicao"));
                    eleicao.setTipo(result.getString("tipo"));
                    eleicao.setActiva(result.getBoolean("activa"));
                    eleicao.setVotosNulos(result.getInt("votos_nulos"));
                    eleicao.setVotosBrancos(result.getInt("votos_brancos"));
                }
            } finally {
                statement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return eleicao;
    }

    @Override
    public Eleicao put(Integer key, Eleicao value) {
        String query;
        int autoGeneratedKeys;
        boolean isUpdate;
        if (key < 0) {
            isUpdate = false;
            query = INSERT_ELEICAO;
            autoGeneratedKeys = Statement.RETURN_GENERATED_KEYS;
        }
        else {
            isUpdate = true;
            query = UPDATE_ELEICAO;
            autoGeneratedKeys = Statement.NO_GENERATED_KEYS;
        }

        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(query,autoGeneratedKeys);

            statement.setDate(1,value.getDataEleicao());
            statement.setString(2,value.getTipo());
            statement.setBoolean(3, value.isActiva());
            statement.setInt(4, value.getVotosNulos());
            statement.setInt(5, value.getVotosBrancos());

            if (isUpdate) {
                statement.setInt(6,key);
            }

            statement.executeUpdate();

            try {
                if (autoGeneratedKeys == Statement.RETURN_GENERATED_KEYS) {
                    ResultSet keys = statement.getGeneratedKeys();
                    if (keys != null && keys.next()) {
                        value.setIdEleicao(keys.getInt(1));
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                statement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return value;
    }

    @Override
    public Eleicao remove(Object key) {
        Eleicao eleicao = get(key);
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(DELETE_ELEICAO);

            statement.setInt(1, (int) key);

            try {
                statement.executeUpdate();
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return eleicao;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Eleicao> m) {
        for (Eleicao eleicao: m.values()) {
            put(eleicao.getIdEleicao(), eleicao);
        }
    }

    @Override
    public void clear() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(DELETE_ELEICOES);
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
    public Collection<Eleicao> values() {
        ArrayList<Eleicao> arrayList = new ArrayList<>();
        try {
            Eleicao eleicao;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_ELEICOES);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    eleicao = new Eleicao();
                    eleicao.setIdEleicao(resultSet.getInt("id_eleicao"));
                    eleicao.setDataEleicao(resultSet.getDate("data_eleicao"));
                    eleicao.setTipo(resultSet.getString("tipo"));
                    eleicao.setActiva(resultSet.getBoolean("activa"));
                    eleicao.setVotosNulos(resultSet.getInt("votos_nulos"));
                    eleicao.setVotosBrancos(resultSet.getInt("votos_brancos"));

                    arrayList.add(eleicao);
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
    public Set<Entry<Integer, Eleicao>> entrySet() {
        return null;
    }

    public Collection<Eleicao> findbyActives(int id) {
        ArrayList<Eleicao> arrayList = new ArrayList<>();
        try {
            Eleicao eleicao;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(FINDACTIVES);
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    eleicao = new Eleicao();
                    eleicao.setIdEleicao(resultSet.getInt("id_eleicao"));
                    eleicao.setDataEleicao(resultSet.getDate("data_eleicao"));
                    eleicao.setTipo(resultSet.getString("tipo"));
                    eleicao.setActiva(resultSet.getBoolean("activa"));
                    eleicao.setVotosNulos(resultSet.getInt("votos_nulos"));
                    eleicao.setVotosBrancos(resultSet.getInt("votos_brancos"));

                    arrayList.add(eleicao);
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

    public Collection<Eleicao> findbyHistoric(int id) {
        ArrayList<Eleicao> arrayList = new ArrayList<>();
        try {
            Eleicao eleicao;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(FINDHISTORIC);
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    eleicao = new Eleicao();
                    eleicao.setIdEleicao(resultSet.getInt("id_eleicao"));
                    eleicao.setDataEleicao(resultSet.getDate("data_eleicao"));
                    eleicao.setTipo(resultSet.getString("tipo"));
                    eleicao.setActiva(resultSet.getBoolean("activa"));
                    eleicao.setVotosNulos(resultSet.getInt("votos_nulos"));
                    eleicao.setVotosBrancos(resultSet.getInt("votos_brancos"));

                    arrayList.add(eleicao);
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
}