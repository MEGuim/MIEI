package Persistence;

import Model.Cidadao;

import java.sql.*;
import java.util.*;

public class CidadaoDAO implements Map<Integer, Cidadao> {

    private static final String INSERT_CIDADAO = "INSERT INTO Cidadao (nome,data_nascimento,nacionalidade,concelho, distrito,eleitor_id,candidato,cartao_cidadao) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE_CIDADAO = "UPDATE Cidadao SET nome = ?, data_nascimento = ?, nacionalidade = ?, concelho= ?, distrito = ?, eleitor_id = ?, candidato = ? WHERE cartao_cidadao = ?";

    private static final String SELECT_CIDADAO = "SELECT nome,data_nascimento,nacionalidade,concelho, distrito, eleitor_id,candidato FROM Cidadao WHERE cartao_cidadao = ?";
    private static final String SELECT_CIDADAOS = "SELECT nome,cartao_cidadao,data_nascimento,nacionalidade,concelho, distrito, eleitor_id,candidato FROM Cidadao";

    public static final String SELECT_CIDADAOS_CANDIDATOS = "SELECT * FROM Cidadao WHERE candidato = TRUE";
    public static final String SELECT_BY_ELEITOR = "SELECT * FROM Cidadao WHERE eleitor_id = ?";
    /*
    public static final String SELECT_BY_CANDIDATO = "SELECT * FROM Cidadao WHERE candidato_id = ?";
    */

    private static final String DELETE_CIDADAO = "DELETE FROM Cidadao WHERE cartao_cidadao = ?";
    private static final String DELETE_CIDADAOS = "DELETE FROM Cidadao";

    private static final String COUNT_CIDADAOS = "SELECT COUNT(*) as n FROM Cidadao";
    private static final String SELECT_IDS = "SELECT cartao_cidadao FROM Cidadao";

    private final String url;
    private final String user;
    private final String password;

    public CidadaoDAO(String url, String user, String password) {
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

            try (ResultSet resultSet = statement.executeQuery(COUNT_CIDADAOS)) {
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
    public boolean containsKey(Object key){
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CIDADAO);

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
        return containsKey(((Cidadao) value).getCartaoCidadao());
    }

    @Override
    public Cidadao get(Object key) {
        Cidadao cidadao = null;
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CIDADAO);

            statement.setInt(1,(int) key);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cidadao = new Cidadao();
                    cidadao.setCartaoCidadao((int) key);
                    cidadao.setNome(result.getString("nome"));
                    cidadao.setDataNascimento(result.getDate("data_nascimento"));
                    cidadao.setNacionalidade(result.getString("nacionalidade"));
                    cidadao.setConcelho(result.getString("concelho"));
                    cidadao.setDistrito(result.getString("distrito"));
                    cidadao.setEleitor(result.getInt("eleitor_id"));
                    cidadao.setCandidato(result.getBoolean("candidato"));

                }
            } finally {
                statement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cidadao;
    }

    @Override
    public Cidadao put(Integer key, Cidadao value) {
        String query;
        query = INSERT_CIDADAO;

        if (this.containsKey(key)) {
            query = UPDATE_CIDADAO;
        }

        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,value.getNome());
            statement.setDate(2,value.getDataNascimento());
            statement.setString(3,value.getNacionalidade());
            statement.setString(4,value.getConcelho());
            statement.setString(5,value.getDistrito());
            statement.setInt(6,value.getEleitor());
            statement.setBoolean(7,value.getCandidato());

            statement.setInt(8,key);

            try {
                statement.executeUpdate();
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
    public Cidadao remove(Object key) {
        Cidadao cidadao = get(key);
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(DELETE_CIDADAO);

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
        return cidadao;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Cidadao> m) {
        for (Cidadao cidadao: m.values()) {
            put(cidadao.getCartaoCidadao(), cidadao);
        }
    }

    @Override
    public void clear() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(DELETE_CIDADAOS);
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
    public Collection<Cidadao> values() {
        ArrayList<Cidadao> arrayList = new ArrayList<>();
        try {
            Cidadao cidadao;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CIDADAOS);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cidadao = new Cidadao();
                    cidadao.setCartaoCidadao(resultSet.getInt("cartao_cidadao"));
                    cidadao.setNome(resultSet.getString("nome"));
                    cidadao.setDataNascimento(resultSet.getDate("data_nascimento"));
                    cidadao.setNacionalidade(resultSet.getString("nacionalidade"));
                    cidadao.setConcelho(resultSet.getString("concelho"));
                    cidadao.setDistrito(resultSet.getString("distrito"));
                    cidadao.setEleitor(resultSet.getInt("eleitor_id"));
                    cidadao.setCandidato(resultSet.getBoolean("candidato"));

                    arrayList.add(cidadao);
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
    public Set<Entry<Integer, Cidadao>> entrySet() {
        return null;
    }

    public List<Cidadao> findAllCandidatos() {
        List<Cidadao> res = new ArrayList<>();
        try {
            Cidadao cidadao;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CIDADAOS_CANDIDATOS);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cidadao = new Cidadao();
                    cidadao.setCartaoCidadao(resultSet.getInt("cartao_cidadao"));
                    cidadao.setNome(resultSet.getString("nome"));
                    cidadao.setDataNascimento(resultSet.getDate("data_nascimento"));
                    cidadao.setNacionalidade(resultSet.getString("nacionalidade"));
                    cidadao.setConcelho(resultSet.getString("concelho"));
                    cidadao.setDistrito(resultSet.getString("distrito"));
                    cidadao.setEleitor(resultSet.getInt("eleitor_id"));
                    cidadao.setCandidato(resultSet.getBoolean("candidato"));

                    res.add(cidadao);
                }
            } finally {
                statement.close();
                connection.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return res;
    }

    public Cidadao getByEleitor(int eleitor) {
        Cidadao cidadao = null;
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ELEITOR);

            statement.setInt(1,(int) eleitor);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cidadao = new Cidadao();
                    cidadao.setCartaoCidadao(result.getInt("cartao_cidadao"));
                    cidadao.setNome(result.getString("nome"));
                    cidadao.setDataNascimento(result.getDate("data_nascimento"));
                    cidadao.setNacionalidade(result.getString("nacionalidade"));
                    cidadao.setConcelho(result.getString("concelho"));
                    cidadao.setDistrito(result.getString("distrito"));
                    cidadao.setEleitor(eleitor);
                    cidadao.setCandidato(result.getBoolean("candidato"));
                }
            } finally {
                statement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cidadao;
    }

}
