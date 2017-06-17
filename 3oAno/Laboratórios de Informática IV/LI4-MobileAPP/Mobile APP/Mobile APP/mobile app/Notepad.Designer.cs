namespace Mobile_APP
{
    partial class Notepad
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
            this.save = new System.Windows.Forms.Button();
            this.cancelar = new System.Windows.Forms.Button();
            this.nota = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // save
            // 
            this.save.Location = new System.Drawing.Point(373, 55);
            this.save.Name = "save";
            this.save.Size = new System.Drawing.Size(82, 31);
            this.save.TabIndex = 0;
            this.save.Text = "Guardar Nota";
            this.save.UseVisualStyleBackColor = true;
            this.save.Click += new System.EventHandler(this.save_Click);
            // 
            // cancelar
            // 
            this.cancelar.Location = new System.Drawing.Point(373, 146);
            this.cancelar.Name = "cancelar";
            this.cancelar.Size = new System.Drawing.Size(82, 28);
            this.cancelar.TabIndex = 1;
            this.cancelar.Text = "Cancelar";
            this.cancelar.UseVisualStyleBackColor = true;
            this.cancelar.Click += new System.EventHandler(this.cancelar_Click);
            // 
            // nota
            // 
            this.nota.Location = new System.Drawing.Point(12, 101);
            this.nota.Name = "nota";
            this.nota.Size = new System.Drawing.Size(339, 20);
            this.nota.TabIndex = 2;
            // 
            // Notepad
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(480, 261);
            this.Controls.Add(this.nota);
            this.Controls.Add(this.cancelar);
            this.Controls.Add(this.save);
            this.Name = "Notepad";
            this.Text = "Notepad";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button save;
        private System.Windows.Forms.Button cancelar;
        private System.Windows.Forms.TextBox nota;
    }
}