using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WpfApplication1
{

    class Query
    {

        public Connection cs;
        public SqlConnection cnn;
        string query;

        public Query()
        {
            this.cs = new Connection();
            this.cnn = cs.ligar();
            this.query = null;
        }


        /*   INSERTS BASE DE DADOS   */

        public void inserePostos(int a, String b)
        {
            query = "use LI4_f; INSERT INTO Posto (idPosto,descricao) values(" + a + "," + "'" + b + "'" + ");";

            SqlCommand comando = new SqlCommand(query, cnn);

            try
            {

                int recordsAffected = comando.ExecuteNonQuery();
                cs.desligar();
            }
            catch (SqlException)
            {

                MessageBox.Show("Inserção falhada!");
            }
        }

        public void inserePontoInteresse(int id, float latitude, float longitude, String a, String b, String c)
        {
            query = "use LI4_f; Insert into Pontos_Interesse values(" + id + "," + latitude + "," + longitude + "," +
                "'" + a + "'" + "," + "'" + b + "'" + "," + "'" + c + "'" + ");";

            SqlCommand comando = new SqlCommand(query, cnn);

            try
            {

                int recordsAffected = comando.ExecuteNonQuery();
                cs.desligar();
            }
            catch (SqlException)
            {

                MessageBox.Show("Inserção falhada!");
            }
        }

        public void insertEstado(int id, String estado)
        {

            query = "use LI4_f; INSERT INTO Estado(idEstado,descricao) values (" + id + "," + "'" + estado + "'" + ")";

            SqlCommand comando = new SqlCommand(query, cnn);

            try
            {

                int recordsAffected = comando.ExecuteNonQuery();
                cs.desligar();
            }
            catch (SqlException)
            {

                MessageBox.Show("Inserção falhada!");
            }

        }

        public void insereMissao(int id, String relatorio, String voz, int utilizador, int estado)
        {

            query = "use LI4_f; INSERT INTO Estado(idEstado,descricao) values (" + id + "," + "'" + relatorio + "'" + "," +
                "'" + voz + "'" + "," + utilizador + "," + estado + ")";

            SqlCommand comando = new SqlCommand(query, cnn);

            try
            {

                int recordsAffected = comando.ExecuteNonQuery();
                cs.desligar();
            }
            catch (SqlException)
            {

                MessageBox.Show("Inserção falhada!");
            }
        }

        public void insereMissao(int id, int utilizador, int estado)
        {
            query = "use LI4_f; INSERT INTO Missao(idMissao,Utilizador_idUtilizador,Estado_idEstado) values (" + id + "," + utilizador + "," + estado + ")";

            SqlCommand comando = new SqlCommand(query, cnn);

            try
            {

                int recordsAffected = comando.ExecuteNonQuery();
                cs.desligar();
            }
            catch (SqlException)
            {

                MessageBox.Show("Inserção falhada!");
            }
        }

        public void insertMissaoHasTarefa(int idM, int idT, int idPI)
        {
            query = "use LI4_f; INSERT INTO Missao_has_Tarefa values (" + idM + "," + idT + "," + idPI + ")";

            SqlCommand comando = new SqlCommand(query, cnn);

            try
            {

                int recordsAffected = comando.ExecuteNonQuery();
                cs.desligar();
            }
            catch (SqlException)
            {

                MessageBox.Show("Inserção falhada!");
            }

        }

        public void insertTarefas(int id, String tarefa)
        {

            query = "use LI4_f; INSERT INTO Tarefa(idTarefa,descricao) values (" + id + "," + "'" + tarefa + "'" + ")";

            SqlCommand comando = new SqlCommand(query, cnn);

            try
            {

                int recordsAffected = comando.ExecuteNonQuery();
                cs.desligar();
            }
            catch (SqlException)
            {

                MessageBox.Show("Inserção falhada!");
            }

        }


        public void insereUtilizador(int id, String nome, String dataNas, String dataIns, int posto)
        {

            query =
                "using LI4_f; insert into Utilizador values(" + id + "," + "'" + nome + "'" + "," + "'" + dataNas + "'" +
                "," + "'" + dataIns + "'" + "," + posto + ")";

            SqlCommand comando = new SqlCommand(query, cnn);

            try
            {

                int recordsAffected = comando.ExecuteNonQuery();
                cs.desligar();
            }
            catch (SqlException)
            {
                MessageBox.Show("Inserção falhada!");
            }
        }


                                                /*   SELECTS BASE DE DADOS    */
        public String[,] showPostos()
        {

            query = "use LI4_f; Select * From Posto;";
            String[,] f = new String[100, 2]; int i = 0; int j = 0;

            using (SqlCommand myCommand = new SqlCommand(query, cnn))
            {
                try
                {
                    using (SqlDataReader reader = myCommand.ExecuteReader())
                    {
                        while (reader.Read())
                        {

                            f[i, j + 1] = reader[1].ToString();
                            f[i, j] = reader[0].ToString();
                            j = 0;
                            i++;
                        }

                    }

                }
                catch (SqlException a)
                {
                    MessageBox.Show("" + a);
                    return null;
                }
            }
            cs.desligar();
            return f;
        }

        public String[,] showTarefas()
        {

            query = "use LI4_f; Select * From Tarefa;";
            String[,] f = new String[100, 2]; int i = 0; int j = 0;

            using (SqlCommand myCommand = new SqlCommand(query, cnn))
            {
                try
                {
                    using (SqlDataReader reader = myCommand.ExecuteReader())
                    {
                        while (reader.Read())
                        {

                            f[i, j + 1] = reader[1].ToString();
                            f[i, j] = reader[0].ToString();
                            j = 0;
                            i++;
                        }

                    }

                }
                catch (SqlException a)
                {
                    MessageBox.Show("" + a);
                    return null;
                }
            }
            cs.desligar();
            return f;
        }


        public String[,] showMissoes()
        {
            query = "use LI4_f; Select * From Mssao;";
            String[,] f = new String[100, 2]; int i = 0; int j = 0;

            using (SqlCommand myCommand = new SqlCommand(query, cnn))
            {
                try
                {
                    using (SqlDataReader reader = myCommand.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            f[i, j] = reader[0].ToString();
                            f[i, j + 1] = reader[1].ToString();
                            f[i, j + 2] = reader[2].ToString();
                            f[i, j + 3] = reader[3].ToString();
                            f[i, j + 4] = reader[4].ToString();
                            j = 0;
                            i++;
                        }

                    }

                }
                catch (SqlException a)
                {
                    MessageBox.Show("" + a);
                    return null;
                }
            }
            cs.desligar();
            return f;
        }

        public String[,] showPontosInteresse()
        {
            query = "use LI4_f; Select * From Pontos_Interesse;";
            String[,] f = new String[100, 2]; int i = 0; int j = 0;

            using (SqlCommand myCommand = new SqlCommand(query, cnn))
            {
                try
                {
                    using (SqlDataReader reader = myCommand.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            f[i, j] = reader[0].ToString();
                            f[i, j + 1] = reader[1].ToString();
                            f[i, j + 2] = reader[2].ToString();
                            f[i, j + 3] = reader[3].ToString();
                            f[i, j + 4] = reader[4].ToString();
                            f[i, j + 5] = reader[5].ToString();
                            j = 0;
                            i++;
                        }

                    }

                }
                catch (SqlException a)
                {
                    MessageBox.Show("" + a);
                    return null;
                }
            }
            cs.desligar();
            return f;
        }

        public String[,] showUtilizadores()
        {
            query = "use LI4_f; Select * From Utilizadores;";
            String[,] f = new String[100, 2]; int i = 0; int j = 0;

            using (SqlCommand myCommand = new SqlCommand(query, cnn))
            {
                try
                {
                    using (SqlDataReader reader = myCommand.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            f[i, j] = reader[0].ToString();
                            f[i, j + 1] = reader[1].ToString();
                            f[i, j + 2] = reader[2].ToString();
                            f[i, j + 3] = reader[3].ToString();
                            f[i, j + 4] = reader[4].ToString();
   
                            j = 0;
                            i++;
                        }

                    }

                }
                catch (SqlException a)
                {
                    MessageBox.Show("" + a);
                    return null;
                }
            }
            cs.desligar();
            return f;
        }

        public String[,] showMissoesTarefas(String s)
        {
            query = "use LI4_f; Select * From Missao_has_Tarefa where Missao_idMissao="+s+";";
            String[,] f = new String[100, 2]; int i = 0; int j = 0;

            using (SqlCommand myCommand = new SqlCommand(query, cnn))
            {
                try
                {
                    using (SqlDataReader reader = myCommand.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            f[i, j] = reader[0].ToString();
                            f[i, j + 1] = reader[1].ToString();
                            f[i, j + 2] = reader[2].ToString();
                            j = 0;
                            i++;
                        }

                    }

                }
                catch (SqlException a)
                {
                    MessageBox.Show("" + a);
                    return null;
                }
            }
            cs.desligar();
            return f;
        }

    }




    }

