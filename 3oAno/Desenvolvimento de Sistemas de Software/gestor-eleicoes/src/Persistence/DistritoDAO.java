package Persistence;

import Model.Distrito;

import java.sql.*;
import java.util.*;


public class DistritoDAO implements Map<Integer, Distrito> {

    private static final String INSERT_DISTRITO = "INSERT INTO Distrito (designacao) VALUES (?)";
    private static final String UPDATE_DISTRITO = "UPDATE Distrito SET designacao WHERE id_distrito = ?";

    private static final String SELECT_DISTRITO = "SELECT designacao FROM Distrito WHERE id_distrito = ?";
    private static final String SELECT_DISTRITOS = "SELECT id_distrito,designacao FROM Distrito";

    private static final String DELETE_DISTRITO = "DELETE FROM Distrito WHERE id_distrito = ?";
    private static final String DELETE_DISTRITOS = "DELETE FROM Distrito";

    private static final String COUNT_DISTRITOS = "SELECT COUNT(*) as n FROM Distrito";
    private static final String SELECT_IDS = "SELECT id_distrito FROM Distrito";


    private String url;
    private String user;
    private String password;

    public DistritoDAO(String url, String user, String password) {
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

            try(ResultSet resultSet = statement.executeQuery(COUNT_DISTRITOS)){
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
    public boolean isEmpty() {return this.size() == 0;}

    @Override
    public boolean containsKey(Object key) {
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_DISTRITO);

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
        return containsKey(((Distrito) value).getIdDistrito());
    }

    @Override
    public Distrito get(Object key) {
        Distrito distrito = null;
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_DISTRITO);

            statement.setInt(1,(int) key);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    distrito = new Distrito();
                    distrito.setIdDistrito((int) key);
                    distrito.setNome(result.getString("designacao"));
                }
            } finally {
                statement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return distrito;
    }

    @Override
    public Distrito put(Integer key, Distrito value) {
        String query;
        int autoGeneratedKeys;
        boolean isUpdate;

        if(key < 0){
            isUpdate = false;
            query = INSERT_DISTRITO;
            autoGeneratedKeys = Statement.RETURN_GENERATED_KEYS;
        }
        else{
            isUpdate = true;
            query = UPDATE_DISTRITO;
            autoGeneratedKeys = Statement.NO_GENERATED_KEYS;
        }

        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(query,autoGeneratedKeys);


            statement.setString(1, (value.getNome()));

            if(isUpdate){
                statement.setInt(2,key);
            }

            statement.executeUpdate();

            try {
                if(autoGeneratedKeys == Statement.RETURN_GENERATED_KEYS){
                    ResultSet keys = statement.getGeneratedKeys();
                    if(keys != null && keys.next()){
                        value.setIdDistrito(keys.getInt(1));
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
    public Distrito remove(Object key) {
        Distrito distrito = get(key);
        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(DELETE_DISTRITO);

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

        return distrito;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Distrito> m) {
        for(Distrito c:m.values()){
            put(c.getIdDistrito(), c);
        }

    }

    @Override
    public void clear() {
        try {
            Connection connection = DriverManager.getConnection(url,user,password);

            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(DELETE_DISTRITOS);
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
    public Collection<Distrito> values() {
        ArrayList<Distrito> r = new ArrayList<>();
        try{
            Distrito distrito;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_DISTRITOS);

            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    distrito = new Distrito();

                    distrito.setIdDistrito(resultSet.getInt("id_concelho"));
                    distrito.setNome(resultSet.getString("designacao"));

                    r.add(distrito);
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
    public Set<Entry<Integer, Distrito>> entrySet() {
        return null;
    }
}