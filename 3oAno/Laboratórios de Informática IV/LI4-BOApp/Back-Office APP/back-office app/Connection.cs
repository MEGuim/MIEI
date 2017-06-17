using System;
using System.Data.SqlClient;
//using System.Windows;
using System.IO;
using System.Windows.Forms;

namespace WpfApplication1
{
    public partial class Connection
    {
        public SqlConnection cnn;
        public Connection() {

            this.cnn = new SqlConnection();

        }
        // Returna um conexão com o sql Server
        public SqlConnection ligar()
        {
            string connetionString = null;
            connetionString = "Data Source=VAREJATOR\\SQLEXPRESS;Integrated Security=True;Connect Timeout=15;Encrypt=False;TrustServerCertificate=False;ApplicationIntent=ReadWrite;MultiSubnetFailover=False";
            
            cnn = new SqlConnection(connetionString);
            try
            {
                cnn.Open();
                
            }
            catch (Exception)
            {
                MessageBox.Show("Não foi possivel estabelecer ligação! ");
            }
            return cnn;
        }
        // Desliga a conexão com o sqlServer
        public void desligar() {
            cnn.Close();
        }
    }
}
