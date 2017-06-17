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
        public Cordenadas coord { get; set; } // Coordenada de cada Tarefa

        public Tarefa(string x, bool est, Cordenadas c) {
            this.descricao = x;
            estado = est;
            this.coord = c;
        }

        

        public String toString()
        {
            return "Descricao= "+descricao + ";" + "Estado= " + estado + ";" + coord.toString();
                  
        }

        
    }
}
