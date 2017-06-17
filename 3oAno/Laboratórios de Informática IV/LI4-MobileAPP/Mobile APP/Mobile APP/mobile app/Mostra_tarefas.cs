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
    public partial class Mosta_tarefas : Form
    {
        
        double c1;
        double c2;
        Navegacao nv;

        public Mosta_tarefas()
        {
            InitializeComponent();
            c1 = 0.0f;
            c2 = 0.0f;
        }


        

        private void button1_Click(object sender, EventArgs e)
        {
            

            List<Tarefa> tf = new List<Tarefa>();

            
            Tarefa tf1 = new Tarefa("Matar um general Islamico", false, 41.567773, -8.359406);
            tf.Add(tf1);
            Tarefa tf2 = new Tarefa("Resgatar refens ", false, 41.585773, -8.459406);
            tf.Add(tf2);
            Tarefa tf3 = new Tarefa("Proteger civilização", false, 41.585773, -8.759406);
            tf.Add(tf3);
            Tarefa tf4 = new Tarefa("Escoltar comida para pobres", false, 41.285773, -8.359406);
            tf.Add(tf4);
            Tarefa tf5 = new Tarefa("Matar um general Islamico", false, 42.525773, -8.459306);
            tf.Add(tf5);
          

            tfs.DataSource = tf;
        }

        private void verMapa_Click(object sender, EventArgs e)
        {
            nv = new Navegacao(c1,c2);
            nv.Show();

        }

        private void tfs_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            var item = tfs.Rows[e.RowIndex].Cells[2].Value;
            var item2 = tfs.Rows[e.RowIndex].Cells[3].Value;
             c1 = float.Parse(item.ToString());
             c2 = float.Parse(item2.ToString());
           

        }
    }
    }

