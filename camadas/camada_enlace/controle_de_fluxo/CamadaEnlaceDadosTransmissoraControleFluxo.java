/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas.camada_enlace.controle_de_fluxo;

import java.util.concurrent.Semaphore;

import camadas.MeioDeComunicacao;
import camadas.camada_enlace.Quadro;
import camadas.camada_enlace.Temporizador;
import util.Constantes;
import util.Conversao;
import util.Evento;
import util.Util;

public class CamadaEnlaceDadosTransmissoraControleFluxo {
  public static int proximoQuadroAEnviar; // janela do transmissor
  public static int quadroEsperado; // janela do receptor
  public static Evento evento;
  public static Temporizador temporizador;
  public static Semaphore mutexQuadro;

  /*
   * *****************************************************************************
   * Metodo: camadaEnlaceDadosTransmissoraControleFluxo* 
   * Funcao: Aplicar o algoritmo de transmissao controle de fluxo
   * Parametros: Quadro[] quadros: vetor com os quadros
   * Retorno: void*
  */
  public static void camadaEnlaceDadosTransmissoraControleFluxo(Quadro[] quadros) {
    controleDeFluxoTransmissoraJanelaDeslizante1Bit(quadros);
  }

  /*
   * *****************************************************************************
   * Metodo: controleDeFluxoTransmissoraJanelaDeslizante1Bit* 
   * Funcao: Aplicar o algoritmo de janela deslizante de 1 bit
   * Parametros: Quadro[] quadros: vetor com os quadros
   * Retorno: void*
   */
  public static void controleDeFluxoTransmissoraJanelaDeslizante1Bit(Quadro[] quadros) {
    for (int i = 0; i < quadros.length; i++) {
      String strAckEnviado = "Ack enviado, codigo: ";
      
      try {
        // bloqueia o envio do proximo do quadro ate receber o ack
        CamadaEnlaceDadosTransmissoraControleFluxo.mutexQuadro.acquire();

        quadros[i].buffer = quadros[i].bits;
        quadros[i].sequencia = proximoQuadroAEnviar;
        quadros[i].ack = 1 - quadroEsperado;

        //imprime o ack enviado na tela
        strAckEnviado += quadros[i].ack;
        int[] asciiArray = Conversao.stringParaAscii(strAckEnviado);
        Util.imprimirNaTela(Conversao.asciiParaMensagem(asciiArray),  Constantes.ACK_ENVIADO);

        //starta o timer
        temporizador = new Temporizador(quadros[i]);

        // chama o meio de comunicacao
        MeioDeComunicacao.meioDeComunicacao(quadros[i]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
