using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Mobile_APP
{
    class Tarefa
    {
        public String descricao { get; set; }
        public bool estado { get; set; } // vai indicar se ja esta feita  ou nao
        public double latitude { get; set; }
        public double longitude { get; set; }

        public Tarefa(string x, bool est, double lat, double longe) {
            this.descricao = x;
            estado = est;
            this.latitude=lat;
            this.longitude = longe;
            
        }



        public String ToString()
        {
            return "Descricao= " + descricao + ";" + "Estado= " + estado + "; Latitude= " +latitude + "; Longitude= "+longitude;

        }

       

        
    }
}
