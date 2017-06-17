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
        public Cordenadas coord { get; set; }
        public String nome { get; set; }
        public String descricao { get; set; }


        public PontoInteresee(Cordenadas cord, String n, String d) {
            coord = cord;
            nome = n;
            descricao = d;

          
        }

        public String toString() {
            return coord.toString() + "; Nota: " + nome + "; Descrição: " + descricao;
        }

        

       

    }
}
