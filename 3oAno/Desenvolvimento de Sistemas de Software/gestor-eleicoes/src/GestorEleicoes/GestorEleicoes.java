package GestorEleicoes;

import Persistence.*;
import Model.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class GestorEleicoes {
	public EleicaoDAO _eleicaoDAO = RepositoryFactory.getEleicaoDAO();
	public CidadaoDAO _cidadaoDAO = RepositoryFactory.getCidadaoDAO();
	public ListaDAO _listaDAO = RepositoryFactory.getListaDAO();
	public CadernoVotoDAO _cadernoVotoDAO = RepositoryFactory.getCadernoVotoDAO();
	public EleitorDAO _eleitorDAO = RepositoryFactory.getEleitorDAO();
    public EleicaoListaCandidatoDAO _eleicaoListaCandidatoDAO = RepositoryFactory.getEleicaoListaCandidatoDAO();

	public Map<String, Map<Integer, String>> distritos = new HashMap<>();

    public List<Eleicao> eleicoes = new ArrayList<>();

	public void agendarEleicao(List<EleicaoListaCandidato> candidatos) {

        for (EleicaoListaCandidato e : candidatos)
            this._eleicaoListaCandidatoDAO.put(-1, e);
	}

	public void cancelarEleicao(int aEleicaoID) {
        Eleicao eleicao = this._eleicaoDAO.remove(aEleicaoID);
        this.eleicoes.remove(eleicao);
        
	}

    public void marcarEleicaoActiva(Eleicao eleicao) throws EleicaoActivaException{
        if (eleicao.isActiva()) throw new EleicaoActivaException("Esta eleicao já está em curso");
        eleicao.setActiva(true);
        this.eleicoes.add(eleicao);
        if (this._eleicaoDAO.containsKey(eleicao.getIdEleicao())) {
            this._eleicaoDAO.put(eleicao.getIdEleicao(), eleicao);

            ArrayList<Eleitor> eleitores = (ArrayList<Eleitor>) _eleitorDAO.values();
            for (Eleitor eleitor : eleitores) {
                CadernoVoto cadernoVoto = new CadernoVoto();
                cadernoVoto.setEleicao(eleicao.getIdEleicao());
                cadernoVoto.setEleitor(eleitor.getNumero());
                cadernoVoto.setVotou(false);

                _cadernoVotoDAO.put(-1, cadernoVoto);
            }
        }

    }

	public void registarCidadao(Cidadao cidadao, boolean update) throws CidadaoJaRegistadoException {
        if (this._cidadaoDAO.containsKey(cidadao.getCartaoCidadao()) && !update)
            throw new CidadaoJaRegistadoException("Cidadao já se encontra registado no sistema");
        this._cidadaoDAO.put(cidadao.getCartaoCidadao(), cidadao);
	}

    public void loadDistritos() {

            InputStream stream = GestorEleicoes.class.getResourceAsStream("/municipalities.json");
            InputStreamReader inputStreamReader = new InputStreamReader(stream);

            JsonArray elements = new JsonParser().parse(inputStreamReader).getAsJsonArray();

            for (JsonElement e : elements) {
                JsonObject o = (JsonObject) e;
                String district = o.get("district").getAsString();
                String concelho = o.get("name").getAsString();
                Integer nif = o.get("NIF").getAsInt();

                if (distritos.containsKey(district)) {
                    Map<Integer, String> concelhos = distritos.get(district);
                    concelhos.putIfAbsent(nif,concelho);
                    distritos.put(district, concelhos);

                } else {
                    Map<Integer, String> concelhos = new HashMap<>();
                    concelhos.put(nif, concelho);
                    distritos.put(district,concelhos);
                }
            }

    }

	public int login(Integer codigo, String password) throws NullPointerException{
		try{
            Eleitor eleitor = this._eleitorDAO.get(codigo);
            if (eleitor.getPassword().equals(password)) {
                return 1;
            }
            else {
                return 0;
            }
        }catch (NullPointerException e){
            throw new NullPointerException();
        }
    }

	public ObservableList<Eleicao> getObservableListEleicoes() {
		ObservableList<Eleicao> res = FXCollections.observableArrayList();
		res.addAll(this._eleicaoDAO.values().stream().collect(Collectors.toList()));
		return res;
	}

	public ObservableList<Lista> getObservableListListas() {
        ObservableList<Lista> res = FXCollections.observableArrayList();
		res.addAll(this._listaDAO.values().stream().collect(Collectors.toList()));
        return res;
    }

    public ObservableList<String> getObservableListDistritos() {
        List<String> list = new ArrayList<>(this.distritos.keySet());
        Collections.sort(list);
        return FXCollections.observableArrayList(list);
    }

    public ObservableList<String> getObservableListConcelhos(String distrito) {
        List<String> list = new ArrayList<>(this.distritos.get(distrito).values());
        Collections.sort(list);
        return FXCollections.observableArrayList(list);
    }

    public ObservableList<Cidadao> getObservableListCidadaos() {
        ObservableList<Cidadao> res = FXCollections.observableArrayList();
        res.addAll(this._cidadaoDAO.values().stream().collect(Collectors.toList()));
        return res;
    }

    public ObservableList<Cidadao> getObservableListCandidatos() {
        ObservableList<Cidadao> res = FXCollections.observableArrayList();
        res.addAll(this._cidadaoDAO.findAllCandidatos().stream().collect(Collectors.toList()));
        return res;
    }

    public Cidadao getUtilizador(int codigo){
        return this._cidadaoDAO.getByEleitor(codigo);
    }

    public Eleitor getEleitor(int codigo){
        return this._eleitorDAO.get(codigo);
    }

    public ObservableList<Eleicao> getObservableListEleicoesActivas(int id){
        ObservableList<Eleicao> res = FXCollections.observableArrayList();
        res.addAll(this._eleicaoDAO.findbyActives(id).stream().collect(Collectors.toList()));
        return res;
    }

    public ObservableList<Eleicao> getObservableListHistoricoEleicao(int id){
        ObservableList<Eleicao> res = FXCollections.observableArrayList();
        res.addAll(this._eleicaoDAO.findbyHistoric(id).stream().collect(Collectors.toList()));
        return res;
    }

    public void updatePasswordEleitor(Integer key, Eleitor eleitor){
        this._eleitorDAO.put(key,eleitor);
    }

    public void marcarEleicaoFinalizada(Eleicao eleicao) throws EleicaoFinalizadaException {
        if (!eleicao.isActiva()) throw new EleicaoFinalizadaException("Esta eleição já terminou!");
        eleicao.setActiva(false);
        this.eleicoes.add(eleicao);
        if (this._eleicaoDAO.containsKey(eleicao.getIdEleicao())) {
            this._eleicaoDAO.put(eleicao.getIdEleicao(), eleicao);
            }
    }



    public class CidadaoJaRegistadoException extends Throwable {
        public CidadaoJaRegistadoException(String s) {
            super(s);
        }
    }

    public class EleicaoActivaException extends Throwable {
        public EleicaoActivaException(String s) {
            super(s);
        }
    }


    public class EleicaoFinalizadaException extends Throwable {
        public EleicaoFinalizadaException(String s){super(s);}
    }
}