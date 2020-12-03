/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
  public static Canvas canvas;

  private JPanel painelBackground;
  private JPanel painelInferior;
  public static JPanel painelDoMeio;
  private PainelEsquerdo painelEsquerdo;
  private PainelDireito painelDireito;

  /* **************************************************************
  Metodo: TelaPrincipal*
  Funcao: Construtor da classe TelaPrincipal.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public TelaPrincipal(){
    TelaPrincipal.canvas = new Canvas();

    this.painelEsquerdo = new PainelEsquerdo();
    TelaPrincipal.painelDoMeio = new PainelDoMeio();
    this.painelDireito = new PainelDireito();

    this.initGUIComponents();
  }

  /* **************************************************************
  Metodo: initGUIComponents*
  Funcao: inicializar os componentes da tela.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void initGUIComponents() {
    this.setTitle("Simulador de Redes, controle de erro - CRC-32");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1200, 700);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.getRootPane().setDefaultButton(PainelEsquerdo.btnEnviar);
    this.setVisible(true);
    this.inicializarComponentes();
    this.add(painelBackground);
    this.add(painelInferior);
  }

  /* **************************************************************
  Metodo: inicializarComponentes*
  Funcao: inicializar os paineis que compoem a tela.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void inicializarComponentes() {
    this.painelBackground = new JPanel(){
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(0, 400);
      }
      {
        this.setLayout(new GridLayout(0, 3));
        this.add(painelEsquerdo);
        this.add(painelDoMeio);
        this.add(painelDireito);
      }
    };

    this.painelInferior = new JPanel(){
      JPanel painel1 = new JPanel();
      JLabel labelBarraDeVelocidade  = new JLabel("Barra de velocidade");
      JSlider barraDeVelocidade = new JSlider(){
        @Override
        public Dimension getPreferredSize() {
          return new Dimension(400, 0);
        }
        {
          this.setBackground(Color.CYAN);
          this.setMinimum(1);
          this.setValue(10);
          this.setMaximum(10);
          this.setMajorTickSpacing(1);
          this.setPaintTicks(true);
          this.setPaintLabels(true);
          this.addChangeListener( e -> Canvas.velocidade = this.getValue());
        }
      };

      private void iniciarPainel1(){
        labelBarraDeVelocidade.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel1.setLayout(new BoxLayout(painel1, BoxLayout.Y_AXIS));
        painel1.setBackground(Color.CYAN);
        painel1.add(labelBarraDeVelocidade);
        painel1.add(barraDeVelocidade);
      }
      {
        this.iniciarPainel1();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.CYAN);
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        this.add(painel1);
        this.add(canvas);
      }
    };
  }
}
