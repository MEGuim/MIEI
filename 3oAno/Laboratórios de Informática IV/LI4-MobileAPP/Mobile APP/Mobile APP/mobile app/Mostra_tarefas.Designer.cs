namespace Mobile_APP
{
    partial class Mosta_tarefas
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.tfs = new System.Windows.Forms.DataGridView();
            this.verMapa = new System.Windows.Forms.Button();
            this.Mostar = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.tfs)).BeginInit();
            this.SuspendLayout();
            // 
            // tfs
            // 
            this.tfs.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.tfs.Location = new System.Drawing.Point(27, 24);
            this.tfs.Name = "tfs";
            this.tfs.Size = new System.Drawing.Size(439, 178);
            this.tfs.TabIndex = 0;
            this.tfs.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.tfs_CellClick);
            // 
            // verMapa
            // 
            this.verMapa.Location = new System.Drawing.Point(498, 56);
            this.verMapa.Name = "verMapa";
            this.verMapa.Size = new System.Drawing.Size(96, 25);
            this.verMapa.TabIndex = 1;
            this.verMapa.Text = "Ver no Mapa";
            this.verMapa.UseVisualStyleBackColor = true;
            this.verMapa.Click += new System.EventHandler(this.verMapa_Click);
            // 
            // Mostar
            // 
            this.Mostar.Location = new System.Drawing.Point(498, 151);
            this.Mostar.Name = "Mostar";
            this.Mostar.Size = new System.Drawing.Size(96, 23);
            this.Mostar.TabIndex = 2;
            this.Mostar.Text = "Mostar";
            this.Mostar.UseVisualStyleBackColor = true;
            this.Mostar.Click += new System.EventHandler(this.button1_Click);
            // 
            // Mosta_tarefas
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(637, 246);
            this.Controls.Add(this.Mostar);
            this.Controls.Add(this.verMapa);
            this.Controls.Add(this.tfs);
            this.Name = "Mosta_tarefas";
            this.Text = "Tarefas";
            ((System.ComponentModel.ISupportInitialize)(this.tfs)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView tfs;
        private System.Windows.Forms.Button verMapa;
        private System.Windows.Forms.Button Mostar;
    }
}