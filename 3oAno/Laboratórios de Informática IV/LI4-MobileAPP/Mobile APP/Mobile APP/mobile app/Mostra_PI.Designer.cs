namespace Mobile_APP
{
    partial class Mostra_PI
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
            this.interesse = new System.Windows.Forms.DataGridView();
            this.mostarPI = new System.Windows.Forms.Button();
            this.nav = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.interesse)).BeginInit();
            this.SuspendLayout();
            // 
            // interesse
            // 
            this.interesse.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.interesse.Location = new System.Drawing.Point(35, 45);
            this.interesse.Name = "interesse";
            this.interesse.Size = new System.Drawing.Size(586, 150);
            this.interesse.TabIndex = 0;
            this.interesse.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.interesse_CellClick);
            // 
            // mostarPI
            // 
            this.mostarPI.Location = new System.Drawing.Point(683, 58);
            this.mostarPI.Name = "mostarPI";
            this.mostarPI.Size = new System.Drawing.Size(75, 23);
            this.mostarPI.TabIndex = 1;
            this.mostarPI.Text = "Ver PI";
            this.mostarPI.UseVisualStyleBackColor = true;
            this.mostarPI.Click += new System.EventHandler(this.button1_Click_1);
            // 
            // nav
            // 
            this.nav.Location = new System.Drawing.Point(683, 139);
            this.nav.Name = "nav";
            this.nav.Size = new System.Drawing.Size(94, 23);
            this.nav.TabIndex = 2;
            this.nav.Text = "Ver no Mapa";
            this.nav.UseVisualStyleBackColor = true;
            this.nav.Click += new System.EventHandler(this.nav_Click);
            // 
            // Mostra_PI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(836, 261);
            this.Controls.Add(this.nav);
            this.Controls.Add(this.mostarPI);
            this.Controls.Add(this.interesse);
            this.Name = "Mostra_PI";
            this.Text = "Mostra_PI";
            ((System.ComponentModel.ISupportInitialize)(this.interesse)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView interesse;
        private System.Windows.Forms.Button mostarPI;
        private System.Windows.Forms.Button nav;
    }
}