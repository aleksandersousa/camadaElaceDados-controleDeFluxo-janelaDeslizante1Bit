/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas;

import camadas.camada_aplicacao.CamadaDeAplicacaoTransmissora;
import view.Erro;
import view.PainelEsquerdo;

public class AplicacaoTransmissora {
  public static String buffer; //buffer para reenviar a mensagem, caso necessario

  /* **************************************************************
  Metodo: aplicacaoTransmissora*
  Funcao: chama a camada de aplicacao transmissora.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public static void aplicacaoTransmissora(String txtMensagem) {
    buffer = txtMensagem;

    new Thread(new Runnable() {
      @Override
      public void run() {
        if (PainelEsquerdo.mutexEnvio.tryAcquire()){
          PainelEsquerdo.sliderErro.setEnabled(false);
          PainelEsquerdo.sliderErro.update(PainelEsquerdo.sliderErro.getGraphics());

          if (buffer.equals("")) {
            Erro.tratarErroMensagemVazia();
          } else {
            CamadaDeAplicacaoTransmissora.camadaDeAplicacaoTransmissora(buffer);
          }
        } else {
         Erro.tratarErroMensagemEmAdamento();
        }
      }
    }).start();
  }
}
