using Mobile_APP;
using System;
using System.Collections.Generic;
using System.IO.Ports;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;



namespace WpfApplication1
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        Query q;
        String password;
        List<Tarefa> tarefas;
        List<PontoInteresee> pinteresse;
        static SerialPort porta;
        String data;
        String simagem, saudio;


        public MainWindow()

        {
            InitializeComponent();
            q = new Query();
            password = "password";
            data = "";
            porta = new SerialPort("COM6", 9600, Parity.None, 8, StopBits.One);
            tarefas = new List<Tarefa>();
            pinteresse = new List<PontoInteresee>();
            
            string[,] posto = new String[100, 2];
            posto = q.showPostos();
            for (int i = 0; i < 100 && posto[i,0]!= null; i++)
            {
                postos.Items.Add(posto[i, 0] + "-" + posto[i, 1]);

            }

            //fazer disable de todas as tabs exceto login

        }

        private void TabControl_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        

        private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
        {

        }



        private void login_Click(object sender, RoutedEventArgs e)
        {
            string id = ident.Text;
            string passw = pass.Password;

            if (password.Equals(passw))
            {
                MessageBox.Show("k"); //fazer enable do resto das tabs e fazer disable da tab login
            }
            
        }

        private void sImagem_Click(object sender, RoutedEventArgs e)
        {
            Microsoft.Win32.OpenFileDialog dlg = new Microsoft.Win32.OpenFileDialog();
            

            // Show open file dialog box
            Nullable<bool> result = dlg.ShowDialog();

            // Process open file dialog box results
            if (result == true)
            {
                simagem = dlg.FileName;
                l1PI.Content = simagem;
            }
        }

        private void sAudio_Click_1(object sender, RoutedEventArgs e)
        {
            Microsoft.Win32.OpenFileDialog dlg = new Microsoft.Win32.OpenFileDialog();


            // Show open file dialog box
            Nullable<bool> result = dlg.ShowDialog();

            // Process open file dialog box results
            if (result == true)
            {
                saudio = dlg.FileName;
                l1PI.Content = simagem;
            }
        }

        //-------------------------------------- INTERFACE TAREFA ------------------------------------------

        // Quando clica em confimar insere tarefa
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            q = new Query();
            string idt = id.Text;
            string tf = tarefa.Text;
            q.insertTarefas(Int32.Parse(idt), tf);
        }

        // ao cancelar Tarefa mete os campos em branco

        private void cancela_Click(object sender, RoutedEventArgs e)
        {
            id.Text = "";
            tarefa.Text = "";
        }


        //-------------------------------------- INTERFACE UTILIZADOR ------------------------------------------


        private void confirUser_Click(object sender, RoutedEventArgs e)
        {
            q = new Query();
            string ident = idU.Text;
            string name = nomeU.Text;
            string dataNasc = dataN.SelectedDate.Value.ToString("MM / dd / yyyy");
            string dataIns = dataI.SelectedDate.Value.ToString("MM / dd / yyyy");
            string var = postos.Text;

            
            
            string[] aux= var.Split('-');
            
            
            
            q.insereUtilizador(Int32.Parse(ident), name, dataNasc, dataIns, Int32.Parse(aux[0]));

        }

        private void cancelaU_Click(object sender, RoutedEventArgs e)
        {
            idU.Text = "";
            nomeU.Text = "";
            dataN.DataContext = null;
            dataI.DataContext = null;
            postos.Text = "";
        }

        //-------------------------------------- INTERFACE PontoInteresse ------------------------------------------

        private void confirmaPI_Click(Object sender, RoutedEventArgs e)
        {
            q = new Query();
            int id = Int32.Parse(idPI.Text);
            float latitude = float.Parse(laPI.Text);
            float longitude = float.Parse(loPI.Text);
            String txt = txtPI.Text;

            q.inserePontoInteresse(id,latitude,longitude,simagem ,saudio ,txt);

            idPI.Text = "";
            laPI.Text = "";
            loPI.Text = "";
            l1PI.BeginInit();
            L2PI.BeginInit();
            txtPI.Text = "";

        }

        private void cancelaPI_Click(Object sender, RoutedEventArgs e)
        {
            idPI.Text = "";
            laPI.Text = "";
            loPI.Text = "";
            l1PI.BeginInit();
            L2PI.BeginInit();
            txtPI.Text = "";
        }


        //-------------------------------------- BUTOES LOGOUT ------------------------------------------

        private void logout1_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();  
        }

        private void logout2_Click(Object sender, RoutedEventArgs e)
        {
           
        }

        private void logout3_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void logout4_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void logout5_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void logout6_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void logout7_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void logout8_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void logout9_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void logout10_Click(Object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        //-------------------------------------- INTERFACE INSERIR MISSAO ------------------------------------------

        private void okMi_Click(Object sender, RoutedEventArgs e)
        {
            q = new Query();
            String idMissao = idmissao.Text;
            String[,] aux = new String[100, 2];
            q = new Query();
            
            //saca todas as tarefas da base de dados
            aux = q.showTarefas();
            

            tabelaTarefas.Items.Add(aux[0, 0]+"-"+ aux[0,1]);

            //insere na tabela tarefas
            //bloqueia outras tabs ate confirmar ou cancelar
            
        }


        // Enviar Dados para a APPMovel
        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            porta.Open();
            // Vai enviar o tamanho da lista de tarefas que vai ser enviada;
            data = tarefas.Count.ToString();
            porta.Write(data);
            foreach (Tarefa t in tarefas){

                data = t.descricao + "," + t.coord.latitude + "," + t.coord.latitude;
                porta.Write(data);


            }
            data = pinteresse.Count.ToString();
            porta.Write(data);
            foreach (PontoInteresee p in pinteresse) {
                data= p.coord.latitude+","+p.coord.longitude+","+p.nome+","+p.descricao;
                porta.Write(data);
            }
            MessageBox.Show("Transferencia feita");

        }
    }



   
}
