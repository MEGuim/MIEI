package Persistence;

import Model.Candidato;
import Model.Eleicao;

import java.util.*;
import java.sql.*;


public class CandidatoDAO implements Map<Integer, Candidato> {

    private static final String INSERT_CANDIDATO = "INSERT INTO Candidato (id_candidato,partido,hierarquia) VALUES (?,?,?) ";
    private static final String UPDATE_CANDIDATO = "UPDATE Candidato SET partido = ?, hierarquia = ? WHERE id_candidato = ?";

    private static final String SELECT_CANDIDATO = "SELECT partido,hierarquia FROM Candidato WHERE id_candidato = ?";
    private static final String SELECT_CANDIDATOS = "SELECT id_candidato, partido, hierarquia FROM Candidato";

    private static final String DELETE_CANDIDATO = "DELETE FROM Candidato WHERE id_candidato = ?";
    private static final String DELETE_CANDIDATOS = "DELETE FROM Candidato";

    private static final String COUNT_CANDIDATOS = "SELECT COUNT(*) as n FROM Candidato";
    private static final String SELECT_IDS = "SELECT id_candidato FROM Candidato";

    private String url;
    private String user;
    private String password;

    public CandidatoDAO(String url, String user, String password) {
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

            try (ResultSet resultSet = statement.executeQuery(COUNT_CANDIDATOS)) {
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
        return this.size()==0;
    }

    @Override
    public boolean containsKey(Object key) {
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CANDIDATO);

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
    public boolean containsValue(Object value) {return containsKey(((Eleicao) value).getIdEleicao());    }

    @Override
    public Candidato get(Object key) {
        Candidato candidato = null;
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CANDIDATO);

            statement.setInt(1,(int) key);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    candidato = new Candidato();
                    candidato.setNumero((int) key);
                    candidato.setPartido(result.getString("partido"));
                    candidato.setHierarquia(result.getString("hierarquia"));
                }
            } finally {
                statement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return candidato;
    }

    @Override
    public Candidato put(Integer key, Candidato value) {
            String query;
            int autoGeneratedKeys;
            boolean isUpdate;
            if (key < 0) {
                isUpdate = false;
                query = INSERT_CANDIDATO;
                autoGeneratedKeys = Statement.RETURN_GENERATED_KEYS;
            }
            else {
                isUpdate = true;
                query = UPDATE_CANDIDATO;
                autoGeneratedKeys = Statement.NO_GENERATED_KEYS;
            }

            try {
                Connection connection = DriverManager.getConnection(url,user,password);
                PreparedStatement statement = connection.prepareStatement(query,autoGeneratedKeys);

                statement.setString(1,value.getPartido());
                statement.setString(2,value.getHierarquia());

                if (isUpdate) {
                    statement.setInt(3,key);
                }

                statement.executeUpdate();

                try {
                    if (autoGeneratedKeys == Statement.RETURN_GENERATED_KEYS) {
                        ResultSet keys = statement.getGeneratedKeys();
                        if (keys != null && keys.next()) {
                            value.setNumero(keys.getInt(1));
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
    public Candidato remove(Object key) {

        Candidato candidato = get(key);
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(DELETE_CANDIDATO);

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
        return candidato;
    }


    @Override
    public void putAll(Map<? extends Integer, ? extends Candidato> m) {
        for (Candidato candidato: m.values()) {
            put(candidato.getNumero(), candidato);
        }

    }

    @Override
    public void clear() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(DELETE_CANDIDATOS);
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
    public Collection<Candidato> values() {
        ArrayList<Candidato> arrayList = new ArrayList<>();
        try {
            Candidato candidato;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CANDIDATOS);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    candidato = new Candidato();
                    candidato.setNumero(resultSet.getInt("id_candidato"));
                    candidato.setPartido(resultSet.getString("partido"));
                    candidato.setHierarquia(resultSet.getString("hierarquia"));

                    arrayList.add(candidato);
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
    public Set<Entry<Integer, Candidato>> entrySet() {
        return null;
    }
}