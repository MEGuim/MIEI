using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Mobile_APP
{
    
    public partial class Mostra_PI : Form
    {
        Navegacao nv;
        List<PontoInteresee> pi;
        double c1;
        double c2;

        public Mostra_PI()
        {
            InitializeComponent();
            pi = new List<PontoInteresee>();
            c1 = 0.0f;
            c2 = 0.0f;
        }

       



        //Mostrar Mapa (vamos passar como argumento as coordenadas do ponto de interesse selecionado)
        private void nav_Click(object sender, EventArgs e)
        {
            nv = new Navegacao(c1,c2);
            nv.Show();
        }


        private void button1_Click_1(object sender, EventArgs e)
        {
            PontoInteresee pt = new PontoInteresee(41.585773, -8.359406,"Ourense","Possivel localização de forte inimigo");
            pi.Add(pt);
            PontoInteresee pt1 = new PontoInteresee(41.585773, -8.359406, "SitioFixe", "Possivel localização de agua potavel");
            pi.Add(pt1);
            PontoInteresee pt2 = new PontoInteresee(41.585773, -8.359406, "Vigo Ouviedo", "Possivel localização de forte inimigo");
            pi.Add(pt2);
            PontoInteresee pt3 = new PontoInteresee(41.585773, -8.359406, "Ourense", "Possivel localização de forte inimigo");
            pi.Add(pt3);
            interesse.DataSource = pi;
        }

        private void interesse_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            var item = interesse.Rows[e.RowIndex].Cells[0].Value;
            var item2 = interesse.Rows[e.RowIndex].Cells[1].Value;
            c1 = float.Parse(item.ToString());
            c2 = float.Parse(item2.ToString());
        }
    }
}
