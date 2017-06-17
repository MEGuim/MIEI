package Persistence;


import Model.Eleicao;
import Model.EleicaoListaCandidato;
import java.sql.*;
import java.util.*;

public class EleicaoListaCandidatoDAO implements Map<Integer, EleicaoListaCandidato> {

    private static final String INSERT_ELEICAO_LISTA_CANDIDATO = "INSERT INTO EleicaoListaCandidato (Eleicao_id_eleicao, Lista_id_lista, Cidadao_cartao_cidadao, hierarquia, partido) VALUES (?,?,?,?,?)";
    private static final String UPDATE_ELEICAO_LISTA_CANDIDATO = "UPDATE EleicaoListaCandidato SET hierarquia=?, partido=? WHERE Eleicao_id_eleicao=? AND Lista_id_lista = ? AND Cidadao_cartao_cidadao=?";

    private static final String SELECT_ELEICAO_LISTA_CANDIDATO = "SELECT Eleicao_id_eleicao, Lista_id_lista, Cidadao_cartao_cidadao, partido,hierarquia, Lista.nome_lista, Lista.resultado,Lista.local_voto, Cidadao.nome, Cidadao.nacionalidade, Cidadao.eleitor_id FROM EleicaoListaCandidato INNER JOIN Lista ON EleicaoListaCandidato.Lista_id_lista = Lista.id_lista INNER JOIN Cidadao ON EleicaoListaCandidato.Cidadao_cartao_cidadao = Cidadao.cartao_cidadao WHERE Eleicao_id_eleicao=? AND Lista_id_lista = ? AND Cidadao_cartao_cidadao =?";
    private static final String SELECT_ELEICOES_LISTAS_CANDIDATOS = "SELECT Eleicao_id_eleicao, Lista_id_lista, Cidadao_cartao_cidadao, partido,hierarquia, Lista.nome_lista, Lista.resultado,Lista.local_voto, Cidadao.nome, Cidadao.nacionalidade, Cidadao.eleitor_id FROM EleicaoListaCandidato INNER JOIN Lista ON EleicaoListaCandidato.Lista_id_lista = Lista.id_lista INNER JOIN Cidadao ON EleicaoListaCandidato.Cidadao_cartao_cidadao = Cidadao_cartao_cidadao";

    /*
    private static final String SELECT_ELEICAO_LISTA="SELECT Eleicao_id_eleicao, Lista_id_lista, Candidato_id_candidato, Lista.nome_lista, Lista.resultado,Lista.local_voto FROM EleicaoListaCandidato INNER JOIN Lista ON EleicaoListaCandidato.Lista_id_lista = Lista.id_lista WHERE Eleicao_id_eleicao =?";
    private static final String SELECT_ELEICAO_LISTAS="SELECT Eleicao_id_eleicao, Lista_id_lista, Candidato_id_candidato, Lista.nome_lista, Lista.resultado, Lista.local_voto FROM EleicaoListaCandiato INNER JOIN Lista ON EleicaoListaCandidato.Lista_id_lista = Lista.id_lista";

    private static final String SELECT_LISTA_CANDIDATOS="SELECT Eleicao_id_eleicao, Lista_id_lista, Candidato_id_candidato, Candidato.partido, Candidato.hierarquia FROM EleicaoListaCandidato INNER JOIN Candidato ON EleicaoListaCandidato.Candidato_id_candidato = Candidato.id_candidato WHERE Eleicao_id_eleicao =?";
    private static final String SELECT_LISTAS_CANDIDATOS="SELECT Eleicao_id_eleicao,Lista_id_lista,Candidato_id_candidato,Candidato.partido,Candidato.hierarquia FROM EleicaoListaCandidato INNER JOIN Candidato ON EleicaoListaCandidato.Candidato_id_candidato = Candidato.id_candidato";
    */

    private static final String DELETE_ELEICAO_LISTA_CANDIDATO = "DELETE FROM EleicaoListaCandidato WHERE Eleicao_id_eleicao = ? AND Lista_id_lista = ? AND Cidadao_cartao_cidadao= ?";
    private static final String DELETE_ELEICOES_LISTA_CANDIDATOS = "DELETE FROM EleicaoListaCandidato";

    private static final String COUNT_ELEICAO_LISTA_CANDIDATO = "SELECT COUNT(*) as n FROM EleicaoListaCandidato";
    private static final String SELECT_IDS = "SELECT Eleicao_id_eleicao FROM EleicaoListaCandidato";
    private static final String SELECT_BY_LISTA = "SELECT * FROM EleicaoListaCandidato WHERE Lista_id_lista = ?";
    private static final String SELECT_BY_CANDIDATO = "SELECT * FROM EleicaoListaCandidato WHERE Cidadao_cartao_cidadao = ?";
    private static final String SELECT_BY_ELEICAO = "SELECT * FROM EleicaoListaCandidato WHERE Eleicao_id_eleicao = ?";


    private final String url;
    private final String user;
    private final String password;

    public EleicaoListaCandidatoDAO(String url,String user,String password){
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

            try(ResultSet resultSet = statement.executeQuery(COUNT_ELEICAO_LISTA_CANDIDATO)){
                if(resultSet.next())
                    count = resultSet.getInt("n");
                else
                    count = -1;
            }finally {
                statement.close();
                connection.close();
            }
            return count;
        }catch (SQLException ex){
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
        try{
            EleicaoListaCandidato e = (EleicaoListaCandidato) key;
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_ELEICAO_LISTA_CANDIDATO);

            statement.setInt(1, e.getEleicaoIdEleicao());
            statement.setInt(2, e.getListaIdLista());
            statement.setInt(3, e.getCidadaoCartaoCidadao());

            try(ResultSet result = statement.executeQuery()){
                return result.next();
            }finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsKey(value);
    }

    @Override
    public EleicaoListaCandidato get(Object key) {
        EleicaoListaCandidato eleicaoListaCandidato = null;
        EleicaoListaCandidato newkey = (EleicaoListaCandidato) key;
        try {

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_ELEICAO_LISTA_CANDIDATO);

            statement.setInt(1, newkey.getEleicaoIdEleicao());
            statement.setInt(2, newkey.getListaIdLista());
            statement.setInt(3, newkey.getCidadaoCartaoCidadao());

            try (ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    eleicaoListaCandidato = new EleicaoListaCandidato();
                    eleicaoListaCandidato.setEleicaoIdEleicao(resultSet.getInt("Eleicao_id_eleicao"));
                    eleicaoListaCandidato.setListaIdLista(resultSet.getInt("Lista_id_lista"));
                    eleicaoListaCandidato.setCidadaoCartaoCidadao(resultSet.getInt("Cidadao_cartao_cidadao"));
                    eleicaoListaCandidato.setHierarquia("hierarquia");
                    eleicaoListaCandidato.setPartido("partido");
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return eleicaoListaCandidato;
    }

    @Override
    public EleicaoListaCandidato put(Integer key, EleicaoListaCandidato value) {
        String query= INSERT_ELEICAO_LISTA_CANDIDATO;
        try {

            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, value.getEleicaoIdEleicao());
            statement.setInt(2, value.getListaIdLista());
            statement.setInt(3, value.getCidadaoCartaoCidadao());
            statement.setString(4, value.getHierarquia());
            statement.setString(5, value.getPartido());

            try {
                statement.executeUpdate();
            } finally {
                statement.close();
                connection.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public EleicaoListaCandidato remove(Object key) {
        EleicaoListaCandidato eleicaoListaCandidato = get(key);

        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(DELETE_ELEICAO_LISTA_CANDIDATO);

            statement.setInt(1, eleicaoListaCandidato.getEleicaoIdEleicao());
            statement.setInt(2, eleicaoListaCandidato.getListaIdLista());
            statement.setInt(3, eleicaoListaCandidato.getCidadaoCartaoCidadao());

            try{
                statement.executeUpdate();
            }finally {
                statement.close();
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return eleicaoListaCandidato;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends EleicaoListaCandidato> m) {
        for(EleicaoListaCandidato e:m.values()){
            put(1,e);
        }
    }

    @Override
    public void clear() {
        try{
            Connection connection = DriverManager.getConnection(url,user,password);

            try (Statement statement = connection.createStatement()){
                statement.executeUpdate(DELETE_ELEICOES_LISTA_CANDIDATOS);
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
                while(resultSet.next()){
                    s.add(resultSet.getInt(1));
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public Collection<EleicaoListaCandidato> values() {
        ArrayList<EleicaoListaCandidato> r  = new ArrayList<>();
        try {
            EleicaoListaCandidato eleicaoListaCandidato;

            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_ELEICOES_LISTAS_CANDIDATOS);

            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    eleicaoListaCandidato = new EleicaoListaCandidato();

                    eleicaoListaCandidato.setEleicaoIdEleicao(resultSet.getInt("Eleicao_id_eleicao"));
                    eleicaoListaCandidato.setListaIdLista(resultSet.getInt("Lista_id_lista"));
                    eleicaoListaCandidato.setCidadaoCartaoCidadao(resultSet.getInt("Cidadao_cartao_cidadao"));
                    eleicaoListaCandidato.setHierarquia(resultSet.getString("hierarquia"));
                    eleicaoListaCandidato.setPartido(resultSet.getString("partido"));

                    r.add(eleicaoListaCandidato);
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
    public Set<Entry<Integer, EleicaoListaCandidato>> entrySet() {
        return null;
    }

    public List<EleicaoListaCandidato> findByLista(int id){
        List<EleicaoListaCandidato> lista = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_LISTA);

            statement.setInt(1,id);

            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    if(containsKey(resultSet.getInt("Eleicao_id_eleicao"))){
                        EleicaoListaCandidato eleicaoListaCandidato = get(resultSet.getInt("Eleicao_id_eleicao"));
                        lista.add(eleicaoListaCandidato);
                    }
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public List<EleicaoListaCandidato> findByCandidato(int id){
        List<EleicaoListaCandidato> lista = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_CANDIDATO);

            statement.setInt(1,id);

            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    if(containsKey(resultSet.getInt("Eleicao_id_eleicao"))){
                        EleicaoListaCandidato eleicaoListaCandidato = get(resultSet.getInt("Eleicao_id_eleicao"));
                        lista.add(eleicaoListaCandidato);
                    }
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public List<EleicaoListaCandidato> getByEleicao(int id){
        ArrayList<EleicaoListaCandidato> lista = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ELEICAO);

            statement.setInt(1,id);

            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    EleicaoListaCandidato eleicaoListaCandidato = new EleicaoListaCandidato();
                    eleicaoListaCandidato.setEleicaoIdEleicao(id);
                    eleicaoListaCandidato.setListaIdLista(resultSet.getInt("Lista_id_lista"));
                    eleicaoListaCandidato.setCidadaoCartaoCidadao(resultSet.getInt("Cidadao_cartao_cidadao"));
                    eleicaoListaCandidato.setHierarquia("hierarquia");
                    eleicaoListaCandidato.setPartido("partido");

                    lista.add(eleicaoListaCandidato);
                }
            } finally {
                statement.close();
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public EleicaoListaCandidato actualizaPartidoHierarquia(EleicaoListaCandidato value){
            String query= UPDATE_ELEICAO_LISTA_CANDIDATO;
            try {

                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, value.getHierarquia());
                statement.setString(2, value.getPartido());
                statement.setInt(3, value.getEleicaoIdEleicao());
                statement.setInt(4, value.getListaIdLista());
                statement.setInt(5, value.getCidadaoCartaoCidadao());


                statement.executeUpdate();
                try{
                    ResultSet keys = statement.getGeneratedKeys();
                    if(keys != null && keys.next()){
                        value.setEleicaoIdEleicao(keys.getInt("Eleicao_id_eleicao"));
                        value.setListaIdLista(keys.getInt("Lista_id_lista"));
                        value.setCidadaoCartaoCidadao(keys.getInt("Cidadao_cartao_cidadao"));
                        value.setHierarquia(keys.getString("hierarquia"));
                        value.setPartido(keys.getString("partido"));
                    }
                }finally {
                    statement.close();
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        return value;
    }
}
