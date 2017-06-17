using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows.Forms;

namespace Mobile_APP
{
    public partial class Notepad : Form
    {
        String n { get; set; }
        List<String> notas;
        public Notepad()
        {
            InitializeComponent();
            notas = new List<string>();
            
        }

        private void cancelar_Click(object sender, EventArgs e)
        {
            nota.Text = "";
        }

        private void save_Click(object sender, EventArgs e)
        {
            n = nota.Text;
            notas.Add(n);

        }

        public List<String> getNotas() {

            return notas;
        }

        
    }
}
