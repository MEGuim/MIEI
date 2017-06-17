using Mobile_APP;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace Mobile_APP
{
    class PontoInteresee
    {
        public double latitude { get; set; }
        public double longitude { get; set; }
        public String nome { get; set; }
        public String descricao { get; set; }


        public PontoInteresee(double lat, double longe, String n, String d) {
            latitude = lat;
            longitude = longe;
            nome = n;
            descricao = d;

          
        }

        public String toString() {
            return "Latitude= "+latitude+ "; Longitude="+longitude+ "; Nota: " + nome + "; Descrição: " + descricao;
        }

        

       

    }
}
