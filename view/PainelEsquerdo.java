/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package view;

import camadas.AplicacaoTransmissora;
import camadas.MeioDeComunicacao;
import camadas.camada_aplicacao.CamadaDeAplicacaoTransmissora;

import util.Formatacao;
import util.Constantes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PainelEsquerdo extends JPanel {
  public static ArrayList<JTextArea> arrayCaixasDeTexto;

  public static JButton btnEnviar;
  public static ReduzirPermissoes mutexEnvio;
  public static JSlider sliderErro;

  private ArrayList<JPanel> arrayPaineis;
  private JTextArea txtLabelNumerosAscii;
  private JTextArea txtLabelQuadrosCodificados;
  private JTextArea txtLabelSliderErro;
  private JTextArea txtLabelCRCCodificado;
  private JTextField txtMensagem;

  /*
   * ************************************************************** 
   * Metodo: PainelEsquerdo 
   * Funcao: Construtor da classe PainelEsquerdo
   * Parametros: nulo
   * Retorno: void
   */
  public PainelEsquerdo() {
    this.arrayPaineis = new ArrayList<>();
    this.txtLabelNumerosAscii = new JTextArea("Numeros Ascii: ");
    this.txtLabelQuadrosCodificados = new JTextArea("Bits: ");
    this.txtLabelSliderErro = new JTextArea("Porcentagem de erros (bit a bit): ");
    this.txtLabelCRCCodificado = new JTextArea("CRC codificado: ");

    PainelEsquerdo.mutexEnvio = new ReduzirPermissoes(1);

    PainelEsquerdo.arrayCaixasDeTexto = Formatacao.inicializarCaixasDeTexto();

    for (int i = 0; i < Constantes.TAMANHO_ARRAY_PAINEIS; i++) {
      arrayPaineis.add(new JPanel());
      if (i != Constantes.TAMANHO_ARRAY_PAINEIS - 1) {
        arrayPaineis.get(i).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      }
    }

    this.initGUIComponents();
  }

  /*
   * ************************************************************** Metodo:
   * initGUIComponents Funcao: inicializar os componentes do painel. Parametros:
   * nulo Retorno: void
   */
  private void initGUIComponents() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.iniciarBotaoECaixaDeTextoEnviar();
    this.formatarLabels();
    this.adicionarComponentes();
  }

  /*
   * ************************************************************** Metodo:
   * iniciarBotaoECaixaDeTextoEnviar* Funcao: inicializa a caixa de texto de
   * entrada e o botao enviar.* Parametros: nulo* Retorno: void*
   */
  private void iniciarBotaoECaixaDeTextoEnviar() {
    txtMensagem = new JTextField("Digite sua mensagem") {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2);
      }

      {
        this.addFocusListener(new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {
            JTextField txt = (JTextField) e.getSource();
            txt.setText("");
            txt.setForeground(new Color(50, 50, 50));
          }

          @Override
          public void focusLost(FocusEvent e) {
            JTextField txt = (JTextField) e.getSource();
            if (txt.getText().length() == 0) {
              txt.setText("Digite sua mensagem");
              txt.setForeground(new Color(150, 150, 150));
            }
          }
        });
      }
    };

    btnEnviar = new JButton("Enviar") {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(90, Constantes.ALTURA_COMPONENTES - 10);
      }

      {
        this.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            AplicacaoTransmissora.aplicacaoTransmissora(txtMensagem.getText());
          }
        });

        this.addKeyListener(new KeyListener() {
          @Override
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
              if (txtMensagem.getText().equals("")) {
                System.out.println("msg nula");
              } else {
                CamadaDeAplicacaoTransmissora.camadaDeAplicacaoTransmissora(txtMensagem.getText());
                repaint();
              }
            }
          }

          @Override
          public void keyTyped(KeyEvent e) {
          }

          @Override
          public void keyReleased(KeyEvent e) {
          }
        });
      }
    };
  }

  /*
   * ************************************************************** Metodo:
   * formatarLabels* Funcao: formata os labels.* Parametros: nulo* Retorno: void*
   */
  private void formatarLabels() {
    this.txtLabelNumerosAscii.setBackground(this.getBackground());
    this.txtLabelQuadrosCodificados.setBackground(this.getBackground());
    this.txtLabelCRCCodificado.setBackground(this.getBackground());
    this.txtLabelSliderErro.setBackground(this.getBackground());

    Formatacao.inicializarLabels(txtLabelNumerosAscii, Constantes.LARGURA_LABELS_ESQUERDO * 0.41, Constantes.ALTURA_LABELS);
    Formatacao.inicializarLabels(txtLabelQuadrosCodificados, Constantes.LARGURA_LABELS_ESQUERDO * 0.2, Constantes.ALTURA_LABELS);
    Formatacao.inicializarLabels(txtLabelCRCCodificado, Constantes.LARGURA_LABELS_ESQUERDO * 0.425, Constantes.ALTURA_LABELS);
    Formatacao.inicializarLabels(txtLabelSliderErro, Constantes.LARGURA_LABELS_ESQUERDO, Constantes.ALTURA_LABELS);
  }

  /*
   * ************************************************************** 
   * Metodo: iniciarSliderDeErro
   * Funcao: cria o slider de erro.
   * Parametros: nulo
   * Retorno: JSlider sliderErro : slider de erro*
   */
  private JSlider iniciarSliderDeErro() {
    return sliderErro = new JSlider() {
      public Dimension getPreferredSize() {
        return new Dimension(400, 50);
      }
      {
        this.setMinimum(0);
        this.setValue(0);
        this.setMaximum(100);
        this.setMajorTickSpacing(20);
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.addChangeListener(e -> MeioDeComunicacao.porcentagemDeErro = this.getValue());
      }
    };
  }

  /*
   * ************************************************************** Metodo:
   * adicionarComponentes* Funcao: inicializa e adiciona os paineis os
   * componentes.* Parametros: nulo* Retorno: void*
   */
  private void adicionarComponentes() {
    arrayPaineis.get(0).add(txtMensagem);
    arrayPaineis.get(0).add(btnEnviar);

    arrayPaineis.get(1).add(txtLabelNumerosAscii);
    arrayPaineis.get(1).add(Formatacao.inicializarBarraDeRolagem(PainelEsquerdo.arrayCaixasDeTexto.get(0),
        Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));

    arrayPaineis.get(2).add(txtLabelQuadrosCodificados);
    arrayPaineis.get(2).add(Formatacao.inicializarBarraDeRolagem(PainelEsquerdo.arrayCaixasDeTexto.get(2),
        Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));
    
    arrayPaineis.get(3).add(txtLabelCRCCodificado);
    arrayPaineis.get(3).add(Formatacao.inicializarBarraDeRolagem(PainelEsquerdo.arrayCaixasDeTexto.get(3),
        Constantes.LARGURA_COMPONENTES, Constantes.ALTURA_COMPONENTES * 2));

    arrayPaineis.get(4).add(txtLabelSliderErro);
    arrayPaineis.get(4).add(this.iniciarSliderDeErro());

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
    return new Dimension(600, 400);
  }

  public class ReduzirPermissoes extends Semaphore {
    /*
     * ************************************************************** Metodo:
     * ReduzirPermissoes* Funcao: Construtor da classe reduzirPermissoes.*
     * Parametros: nulo* Retorno: void*
     */
    public ReduzirPermissoes(int permits) {
      super(permits);
    }

    /*
     * ************************************************************** Metodo:
     * callReducePermits* Funcao: Chamar o metodo protegido da classe Semaphore
     * reducePermits.* Parametros: nulo* Retorno: void*
     */
    public void callReducePermits(int reduction) {
      this.reducePermits(reduction);
    }
  }
}
