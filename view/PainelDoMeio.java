/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import camadas.camada_enlace.Temporizador;
import util.Constantes;
import util.Formatacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class PainelDoMeio extends JPanel {
  public static ArrayList<JTextArea> arrayCaixasDeTexto;
  
  private JTextArea txtLabelAckEnviado;
  private JTextArea txtLabelAckRecebido;
  private JTextArea txtLabelSliderTemporizador;
  
  private JPanel painelAckEnviado;
  private JPanel painelAckRecebido;
  private JPanel painelSliderTemporizador;
  private JPanel painelInferior;
  public static JPanel painelTimer;

  public static JSlider sliderTemporizador;

  /*
   * ************************************************************** 
   * Metodo: PainelDoMeio 
   * Funcao: Construtor da classe PainelDoMeio
   * Parametros: nulo
   * Retorno: void
   */
  public PainelDoMeio() {
    PainelDoMeio.arrayCaixasDeTexto = Formatacao.inicializarCaixasDeTexto();

    this.txtLabelAckEnviado = new JTextArea("ACK enviado: ");
    this.txtLabelAckRecebido = new JTextArea("ACK recebido: ");
    this.txtLabelSliderTemporizador = new JTextArea("Duracao do temporizador(s): ");

    this.painelAckEnviado = new JPanel();
    this.painelAckRecebido = new JPanel();
    this.painelSliderTemporizador = new JPanel();
    PainelDoMeio.painelTimer = new JPanel();

    this.initGUIComponents();
    this.formatarLabels();
    this.incializarComponentes();
  }

  /*
   * ************************************************************** 
   * Metodo: initGUIComponents 
   * Funcao: inicializar os componentes do painel. 
   * Parametros: nulo 
   * Retorno: void
   */
  private void initGUIComponents() {
    this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
    this.setLayout(new BorderLayout());
  }

  /*
   * ************************************************************** 
   * Metodo: inicializarComponentes 
   * Funcao: inicializar os paineis de suporte. 
   * Parametros: nulo 
   * Retorno: void
   */
  private void incializarComponentes() {
    painelAckEnviado.setLayout(new BoxLayout(painelAckEnviado, BoxLayout.Y_AXIS));
    painelAckEnviado.add(txtLabelAckEnviado);
    painelAckEnviado.add(Formatacao.inicializarBarraDeRolagem(PainelDoMeio.arrayCaixasDeTexto.get(7), Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));

    painelAckRecebido.setLayout(new BoxLayout(painelAckRecebido, BoxLayout.Y_AXIS));
    painelAckRecebido.add(txtLabelAckRecebido);
    painelAckRecebido.add(Formatacao.inicializarBarraDeRolagem(PainelDoMeio.arrayCaixasDeTexto.get(8), Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));

    painelSliderTemporizador.setLayout(new BoxLayout(painelSliderTemporizador, BoxLayout.Y_AXIS));
    painelSliderTemporizador.add(txtLabelSliderTemporizador);
    painelSliderTemporizador.add(this.inicializarSliderTemporizador());

    // painelTimer.setLayout(new BoxLayout(target, axis)));
    
    painelInferior = new JPanel(){
      JPanel painel1 = new JPanel();
      JPanel painel2 = new JPanel();
      JPanel painel3 = new JPanel();

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(0, 280);
      }

      private void iniciarPainel1() {
        painel1.setLayout(new BoxLayout(painel1, BoxLayout.Y_AXIS));
        painel1.add(painelAckEnviado);
      }

      private void iniciarPainel2() {
        painel2.setLayout(new BoxLayout(painel2, BoxLayout.Y_AXIS));
        painel2.add(painelAckRecebido);
      }

      private void inicializarPainel3() {
        painel3.add(painelSliderTemporizador);
      }

      {
        this.iniciarPainel1();
        this.iniciarPainel2();
        this.inicializarPainel3();
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        this.add(painel3);
        this.add(painel1);
        this.add(painel2);
      }
    };

    this.add(painelInferior, BorderLayout.SOUTH);
    this.add(painelTimer);
  }

  /*
   * ************************************************************** 
   * Metodo: inicializarSliderTemporizador 
   * Funcao: Criar slider de duracao do temporizador 
   * Parametros: nulo 
   * Retorno: JSlider sliderTemporizador: slider de duracao do temporizador
   */
  private JSlider inicializarSliderTemporizador() {
    sliderTemporizador = new JSlider() {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(400, 50);
      }

      {
        this.setMinimum(5);
        this.setValue(20);
        this.setMaximum(30);
        this.setMajorTickSpacing(5);
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.addChangeListener(e -> Temporizador.duracao = this.getValue());
      }
    };

    return sliderTemporizador;
  }

  /*
   * ************************************************************** 
   * Metodo: formatarLabels
   * Funcao: formata os labels.
   * Parametros: nulo
   * Retorno: void
   */
  private void formatarLabels() {
    this.txtLabelAckEnviado.setBackground(this.getBackground());
    this.txtLabelAckRecebido.setBackground(this.getBackground());
    this.txtLabelSliderTemporizador.setBackground(this.getBackground());

    Formatacao.inicializarLabels(txtLabelAckEnviado, Constantes.LARGURA_LABELS_DIREITO * 0.55, Constantes.ALTURA_LABELS);
    Formatacao.inicializarLabels(txtLabelAckRecebido, Constantes.LARGURA_LABELS_DIREITO * 0.55, Constantes.ALTURA_LABELS);
    Formatacao.inicializarLabels(txtLabelSliderTemporizador, Constantes.LARGURA_LABELS_DIREITO * 0.2, Constantes.ALTURA_LABELS);
  }

  /*
   * ************************************************************** 
   * Metodo: getPreferredSize 
   * Funcao: seta o tamanho deste painel. 
   * Parametros: nulo
   * Retorno: void
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(0, 350);
  }
}
