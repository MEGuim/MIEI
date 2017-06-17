package Model;


import javafx.beans.property.SimpleStringProperty;

public class EleicaoListaCandidato {
    private int _Eleicao_id_eleicao = -1;
    private int _Lista_id_lista = -1;
    private int _Cidadao_cartao_cidadao = -1;
    private SimpleStringProperty _hierarquia;
    private SimpleStringProperty _partido;

    public EleicaoListaCandidato(){
        this._hierarquia = new SimpleStringProperty();
        this._partido = new SimpleStringProperty();
    }

    public int getEleicaoIdEleicao(){return this._Eleicao_id_eleicao;}

    public int getListaIdLista(){return this._Lista_id_lista;}

    public int getCidadaoCartaoCidadao(){return this._Cidadao_cartao_cidadao;}

    public void setEleicaoIdEleicao(int Eleicao_id_eleicao){this._Eleicao_id_eleicao=Eleicao_id_eleicao;}

    public void setListaIdLista(int Lista_id_lista){this._Lista_id_lista=Lista_id_lista;}

    public void setCidadaoCartaoCidadao(int _Cidadao_cartao_cidadao){this._Cidadao_cartao_cidadao=_Cidadao_cartao_cidadao;}

    public String getHierarquia(){return this._hierarquia.get();}

    public SimpleStringProperty getHierarquiaProperty(){return this._hierarquia;}

    public void setHierarquia(String hierarquia){this._hierarquia.set(hierarquia);}

    public void setPartido(String partido){this._partido.set(partido);}

    public String getPartido(){return this._partido.get();}

    public SimpleStringProperty getPartidoProperty(){return this._partido;}

    @Override
    public String toString() {
        return "Eleicao: " + this._Eleicao_id_eleicao + "\nLista: " + this._Lista_id_lista + "\nCandidato: " + this._Cidadao_cartao_cidadao;
    }
}
