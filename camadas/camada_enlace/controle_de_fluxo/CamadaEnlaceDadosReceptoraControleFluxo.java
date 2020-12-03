/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas.camada_enlace.controle_de_fluxo;

import camadas.camada_aplicacao.CamadaDeAplicacaoReceptora;
import camadas.camada_enlace.Quadro;
import util.Constantes;
import util.Conversao;
import util.Util;

public class CamadaEnlaceDadosReceptoraControleFluxo {
  /*
   * *****************************************************************************
   * Metodo: camadaEnlaceDadosReceptoraControleFluxo* 
   * Funcao: Aplicar o algoritmo de recepcao de controle de fluxo
   * Parametros: Quadro quadro: quadro manipulado pelo algoritmo
   * Retorno: void*
  */
  public static void camadaEnlaceDadosReceptoraControleFluxo(Quadro quadro) {
    controleDeFluxoReceptoraJanelaDeslizante1Bit(quadro);
  }

  /*
   * *****************************************************************************
   * Metodo: controleDeFluxoReceptoraJanelaDeslizante1Bit* 
   * Funcao: Aplicar o algoritmo de recepcao da janela de deslizante de 1 bit
   * Parametros: Quadro quadro: quadro manipulado pelo algoritmo
   * Retorno: void*
  */
  public static void controleDeFluxoReceptoraJanelaDeslizante1Bit(Quadro quadro) {
    switch (CamadaEnlaceDadosTransmissoraControleFluxo.evento) {
      case CHEGOU:
        if (quadro.sequencia == CamadaEnlaceDadosTransmissoraControleFluxo.quadroEsperado) {
          // inverte o quadro esperado para receber o proximo quadro
          CamadaEnlaceDadosTransmissoraControleFluxo.quadroEsperado = 1
              - CamadaEnlaceDadosTransmissoraControleFluxo.quadroEsperado;

          // chama a proxima camada
          CamadaDeAplicacaoReceptora.camadaDeAplicacaoReceptora(quadro.bits);
        } 

        if (quadro.ack == CamadaEnlaceDadosTransmissoraControleFluxo.quadroEsperado) {
          String strAckRecebido = "Ack recebido, codigo: ";

          //imprime o ack recebido na tela
          strAckRecebido += quadro.ack;
          int[] asciiArray = Conversao.stringParaAscii(strAckRecebido);
          Util.imprimirNaTela(Conversao.asciiParaMensagem(asciiArray),  Constantes.ACK_RECEBIDO);

          // inverte o quadro na sequencia
          CamadaEnlaceDadosTransmissoraControleFluxo.proximoQuadroAEnviar = 1
              - CamadaEnlaceDadosTransmissoraControleFluxo.proximoQuadroAEnviar;

          // para o timer da carga util
          CamadaEnlaceDadosTransmissoraControleFluxo.temporizador.interromperTemporizador();

          // libera o envio do proximo quadro
          CamadaEnlaceDadosTransmissoraControleFluxo.mutexQuadro.release();
        }
        break;
      case ERRO: //descarta a informacao inutil
        break;
    }
  }
}
