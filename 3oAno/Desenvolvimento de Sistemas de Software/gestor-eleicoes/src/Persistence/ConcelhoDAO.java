package Persistence;

import Model.Concelho;
import java.sql.*;
import java.util.*;


public class ConcelhoDAO implements Map<Integer, Concelho> {

    private static final String INSERT_CONCELHO = "INSERT INTO Concelho (designacao,Distrito_id_distrito) VALUES (?,?)";
    private static final String UPDATE_CONCELHO = "UPDATE Concelho SET designacao = ?, Distrito_id_distrito = ? WHERE id_concelho = ?";

    private static final String SELECT_CONCELHO = "SELECT designacao FROM Concelho WHERE id_concelho = ?";
    private static final String SELECT_CONCELHOS = "SELECT id_concelho, designacao FROM Concelho" ;
    private static final String SELECT_BY_DISTRITO = "SELECT id_concelho,Concelho.designacao FROM Concelho INNER JOIN Distrito ON Concelho.Distrito_id_distrito=Distrito.id_distrito WHERE id_distrito = ?";


    private static final String DELETE_CONCELHO = "DELETE FROM Concelho WHERE id_concelho = ?";
    private static final String DELETE_CONCELHOS = "DELETE FROM Concelho";

    private static final String COUNT_CONCELHOS = "SELECT COUNT(*) as n FROM Concelho";
    private static final String SELECT_IDS = "SELECT id_concelho FROM Concelho";

    private final String url;
    private final String user;
    private final String password;

    public ConcelhoDAO(String url,String user,String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }


    @Override
    public int size() {
        try{
            int count;

            Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();

            try(ResultSet resultSet = statement.executeQuery(COUNT_CONCELHOS)){
                if(resultSet.next()) count=resultSet.getInt("n");
                else count = -1;
            } finally {
                statement.close();
                connection.close();
            }
            return count;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CONCELHO);

            statement.setInt(1, (int) key);

            try(ResultSet resultSet = statement.executeQuery()){
                return resultSet.next();
            }finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsKey(((Concelho) value).getIdConcelho());
    }

    @Override
    public Concelho get(Object key) {
        Concelho concelho = null;
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CONCELHO);

            statement.setInt(1, (int) key);

            try (ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    concelho = new Concelho();
                    concelho.setIdConcelho((int) key);
                    concelho.setNome(resultSet.getString("designacao"));
                    concelho.setDistrito(resultSet.getInt("Distrito_id_distrito"));
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return concelho;
    }

    @Override
    public Concelho put(Integer key, Concelho value) {
        String query;
        int autoGeneratedKeys;
        boolean isUpdate;

        if(key < 0){
            isUpdate = false;
            query = INSERT_CONCELHO;
            autoGeneratedKeys = Statement.RETURN_GENERATED_KEYS;
        }
        else{
            isUpdate = true;
            query = UPDATE_CONCELHO;
            autoGeneratedKeys = Statement.NO_GENERATED_KEYS;
        }

        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(query,autoGeneratedKeys);


            statement.setString(1, (value.getNome()));
            statement.setObject(2, (value.getDistrito()));

            if(isUpdate){
                statement.setInt(3,key);
            }

            statement.executeUpdate();

            try {
                if(autoGeneratedKeys == Statement.RETURN_GENERATED_KEYS){
                    ResultSet keys = statement.getGeneratedKeys();
                    if(keys != null && keys.next()){
                        value.setIdConcelho(keys.getInt(1));
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }

    @Override
    public Concelho remove(Object key) {
        Concelho concelho = get(key);
        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(DELETE_CONCELHO);

            statement.setInt(1, (int) key);

            try {
                statement.executeUpdate();
            }finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return concelho;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Concelho> m) {
        for(Concelho c:m.values()){
            put(c.getIdConcelho(), c);
        }

    }

    @Override
    public void clear() {
        try {
            Connection connection = DriverManager.getConnection(url,user,password);

            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(DELETE_CONCELHOS);
            }finally {
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> s = new HashSet<>();

        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_IDS);

            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    s.add(resultSet.getInt(1));
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public Collection<Concelho> values() {
        ArrayList<Concelho> r = new ArrayList<>();
        try{
            Concelho concelho;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_CONCELHOS);

            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    concelho = new Concelho();

                    concelho.setIdConcelho(resultSet.getInt("id_concelho"));
                    concelho.setNome(resultSet.getString("designacao"));
                    concelho.setDistrito(resultSet.getInt("Distrito_id_distrito"));

                    r.add(concelho);
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return r;
    }

    @Override
    public Set<Entry<Integer, Concelho>> entrySet() {
        return null;
    }

    public Iterable<Concelho> findByDistrito(int id){
        List<Concelho> concelhos = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_DISTRITO);

            statement.setInt(1,id);

            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    Concelho c = get(resultSet.getInt("id_concelho"));
                    c.setNome(resultSet.getString("designacao"));
                    concelhos.add(c);
                }
            }finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return concelhos;
    }
}