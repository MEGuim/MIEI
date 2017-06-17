package Persistence;

import java.util.Properties;



public class RepositoryFactory {

    private static String DB_TYPE = "mysql";
    private static String HOST = "localhost";
    private static String PORT = "3306";
    private static String USER = "USER";
    private static String PASSWORD = "123";
    private static String DATABASE = "gestor_eleicoes";

    private static CadernoVotoDAO cadernoVotoDAO;
    private static CidadaoDAO cidadaoDAO;
    private static EleicaoDAO eleicaoDAO;
    private static EleitorDAO eleitorDAO;
    private static EleicaoListaCandidatoDAO eleicaoListaCandidatoDAO;
    private static ListaDAO listaDAO;


    public RepositoryFactory() { }

    public static CadernoVotoDAO getCadernoVotoDAO() {
        if (cadernoVotoDAO == null)
            cadernoVotoDAO = new CadernoVotoDAO(getURL(), USER, PASSWORD);
        return cadernoVotoDAO;
    }

    public static CidadaoDAO getCidadaoDAO() {
        if (cidadaoDAO == null) {
            cidadaoDAO = new CidadaoDAO(getURL(),USER,PASSWORD);
        }
        return cidadaoDAO;
    }

    public static EleicaoDAO getEleicaoDAO() {
        if (eleicaoDAO == null) {
            eleicaoDAO = new EleicaoDAO(getURL(),USER,PASSWORD);
        }
        return eleicaoDAO;
    }

    public static EleitorDAO getEleitorDAO() {
        if (eleitorDAO == null)
            eleitorDAO = new EleitorDAO(getURL(), USER, PASSWORD);
        return eleitorDAO;
    }

    public static EleicaoListaCandidatoDAO getEleicaoListaCandidatoDAO() {
        if (eleicaoListaCandidatoDAO == null)
            eleicaoListaCandidatoDAO = new EleicaoListaCandidatoDAO(getURL(), USER, PASSWORD);
        return eleicaoListaCandidatoDAO;
    }

    public static ListaDAO getListaDAO() {
        if (listaDAO == null) {
            listaDAO = new ListaDAO(getURL(),USER,PASSWORD);
        }
        return listaDAO;
    }

    public static void setProperties(Properties props) {
        DB_TYPE = props.getOrDefault("db_type", DB_TYPE).toString();
        HOST = props.getOrDefault("host", HOST).toString();
        PORT = props.getOrDefault("port", PORT).toString();
        USER = props.getOrDefault("user", USER).toString();
        PASSWORD = props.getOrDefault("password", PASSWORD).toString();
        DATABASE = props.getOrDefault("database", DATABASE).toString();
    }

    private static String getURL() {
        return "jdbc:" + DB_TYPE + "://" + HOST + ":" + PORT + "/" + DATABASE;
    }

}
