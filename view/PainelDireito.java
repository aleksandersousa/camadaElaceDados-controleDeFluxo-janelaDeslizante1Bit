/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package view;

import util.Constantes;
import util.Formatacao;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class PainelDireito extends JPanel {
  public static ArrayList<JTextArea> arrayCaixasDeTexto;
  private ArrayList<JPanel> arrayPaineis;
  private JTextArea txtLabelNumerosAciiDecodificados;
  private JTextArea txtLabelBitsRecebidos;
  private JTextArea txtLabelInstrucoes;
  private JTextArea txtLabelCRCRecebido;

  private String emoji = "\uD83D\uDE01";

  /*
   * ************************************************************** 
   * Metodo: PainelDireito
   * Funcao: Construtor da classe PainelDireito.
   * Parametros: nulo*
   * Retorno: void*
   */
  public PainelDireito() {
    PainelDireito.arrayCaixasDeTexto = Formatacao.inicializarCaixasDeTexto();
    this.arrayPaineis = new ArrayList<>();

    this.txtLabelNumerosAciiDecodificados = new JTextArea("Numero Ascii: ");
    this.txtLabelBitsRecebidos = new JTextArea("Bits recebidos: ");
    this.txtLabelInstrucoes = new JTextArea("Clique no botão ou aperte enter\n" + 
                                            "                para enviar " + emoji);
    this.txtLabelCRCRecebido = new JTextArea("CRC recebido: ");

    for (int i = 0; i < Constantes.TAMANHO_ARRAY_PAINEIS; i++) {
      arrayPaineis.add(new JPanel());
      if (i != Constantes.TAMANHO_ARRAY_PAINEIS - 1)
        arrayPaineis.get(i).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
    }

    this.initGUIComponents();
  }

  /*
   * ************************************************************** Metodo:
   * initGUIComponents* Funcao: inicializar os componentes do painel.* Parametros:
   * nulo* Retorno: void*
   */
  private void initGUIComponents() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.formatarLabels();
    this.adicionarComponentes();
  }

  /*
   * ************************************************************** Metodo:
   * formatarLabels* Funcao: formata os labels.* Parametros: nulo* Retorno: void*
   */
  private void formatarLabels() {
    this.txtLabelNumerosAciiDecodificados.setBackground(this.getBackground());
    this.txtLabelBitsRecebidos.setBackground(this.getBackground());
    this.txtLabelCRCRecebido.setBackground(this.getBackground());
    this.txtLabelInstrucoes.setBackground(this.getBackground());

    Formatacao.inicializarLabels(txtLabelNumerosAciiDecodificados, Constantes.LARGURA_LABELS_DIREITO * 0.56, Constantes.ALTURA_LABELS);
    Formatacao.inicializarLabels(txtLabelBitsRecebidos, Constantes.LARGURA_LABELS_DIREITO * 0.62, Constantes.ALTURA_LABELS);
    Formatacao.inicializarLabels(txtLabelCRCRecebido, Constantes.LARGURA_LABELS_DIREITO * 0.57, Constantes.ALTURA_LABELS);
    Formatacao.inicializarLabels(txtLabelInstrucoes, 450, 100);
    txtLabelInstrucoes.setForeground(Color.RED);
    txtLabelInstrucoes.setBounds(90, 27, 320, 50);
    txtLabelInstrucoes.setFont(new Font("txt", Font.BOLD, 15));
    txtLabelInstrucoes.setLineWrap(true);
    txtLabelInstrucoes.setWrapStyleWord(true);
  }

  /*
   * ************************************************************** Metodo:
   * adicionarComponentes* Funcao: inicializa e adiciona os componentes ao
   * painel.* Parametros: nulo* Retorno: void*
   */
  private void adicionarComponentes() {
    this.arrayPaineis.get(0).add(Formatacao.inicializarBarraDeRolagem(PainelDireito.arrayCaixasDeTexto.get(4), Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));

    this.arrayPaineis.get(1).add(txtLabelNumerosAciiDecodificados);
    this.arrayPaineis.get(1).add(Formatacao.inicializarBarraDeRolagem(PainelDireito.arrayCaixasDeTexto.get(1), Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));

    this.arrayPaineis.get(2).add(txtLabelBitsRecebidos);
    this.arrayPaineis.get(2).add(Formatacao.inicializarBarraDeRolagem(Formatacao.arrayCaixasDeTexto.get(5), Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));
    
    this.arrayPaineis.get(3).add(txtLabelCRCRecebido);
    this.arrayPaineis.get(3).add(Formatacao.inicializarBarraDeRolagem(Formatacao.arrayCaixasDeTexto.get(6), Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));
    
    this.arrayPaineis.get(4).setPreferredSize(new Dimension(320, 60));
    this.arrayPaineis.get(4).setLayout(null);
    this.arrayPaineis.get(4).add(txtLabelInstrucoes);

    for (int i = 0; i < Constantes.TAMANHO_ARRAY_PAINEIS; i++) {
      this.add(arrayPaineis.get(i));
    }
  }

  /*
   * ************************************************************** Metodo:
   * getPreferredSize* Funcao: seta o tamanho deste painel.* Parametros: nulo*
   * Retorno: void*
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 300);
  }
}
