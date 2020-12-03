package camadas.camada_enlace;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextArea;

import camadas.MeioDeComunicacao;
import camadas.camada_enlace.controle_de_fluxo.CamadaEnlaceDadosTransmissoraControleFluxo;
import view.PainelDoMeio;
import view.TelaPrincipal;

public class Temporizador extends Timer {
  public Quadro quadro;

  private int periodo = 1000;
  public static int duracao = 20;
  public int intervalo;

  private JTextArea txtTimer = new JTextArea();

  public Temporizador(Quadro quadro) {
    this.quadro = quadro;

    intervalo = duracao;

    // adiciona o timer a tela
    txtTimer.setBackground(PainelDoMeio.painelTimer.getBackground());
    txtTimer.setFont(new Font("font", Font.BOLD, 24));
    PainelDoMeio.painelTimer.add(txtTimer);
    PainelDoMeio.painelTimer.setAlignmentY(Component.CENTER_ALIGNMENT);
    PainelDoMeio.painelTimer.update(PainelDoMeio.painelTimer.getGraphics());

    this.iniciarTemporizador();
  }

  private void iniciarTemporizador() {
    //desabilita o slider de duracao do temporizador
    PainelDoMeio.sliderTemporizador.setEnabled(false);

    this.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        setInterval();
      }
    }, 0, periodo);
  }

  private int setInterval() {
    long sec = intervalo % 60L;

    String strTimer = String.format("Timer: %02d s", sec);
    txtTimer.setText(strTimer);

    if (intervalo >= duracao * 0.7) {
      txtTimer.setForeground(Color.GREEN);
    } else if (intervalo >= duracao * 0.4 && intervalo <= duracao * 0.7) {
      txtTimer.setForeground(Color.YELLOW);
    } else {
      txtTimer.setForeground(Color.RED);
    }

    txtTimer.revalidate();
    txtTimer.repaint();
    txtTimer.update(txtTimer.getGraphics());

    if (intervalo == 0) {
      this.cancel();
      this.purge();

      // remove o timer da tela
      PainelDoMeio.painelTimer.removeAll();
      PainelDoMeio.painelTimer.revalidate();
      PainelDoMeio.painelTimer.update(PainelDoMeio.painelTimer.getGraphics());

      this.reenviarQuadro();
    }

    return intervalo--;
  }

  public void interromperTemporizador() {
    this.cancel();
    this.purge();

    //reablilita o slider de duracao do temporizador
    PainelDoMeio.sliderTemporizador.setEnabled(true);
    TelaPrincipal.painelDoMeio.revalidate();
    TelaPrincipal.painelDoMeio.repaint();

    // remove o timer da tela
    PainelDoMeio.painelTimer.removeAll();
    PainelDoMeio.painelTimer.revalidate();
    PainelDoMeio.painelTimer.update(PainelDoMeio.painelTimer.getGraphics());
  }

  private void reenviarQuadro() {
    //pegando buffer
    quadro.bits = quadro.buffer;

    //reinicia o timer
    CamadaEnlaceDadosTransmissoraControleFluxo.temporizador = new Temporizador(quadro);

    //reenviando quadro
    MeioDeComunicacao.meioDeComunicacao(quadro);
  }
}
