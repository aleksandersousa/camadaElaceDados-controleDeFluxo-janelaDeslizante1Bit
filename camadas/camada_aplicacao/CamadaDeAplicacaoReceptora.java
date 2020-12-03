/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas.camada_aplicacao;

import camadas.AplicacaoReceptora;
import util.Constantes;
import util.Conversao;
import util.Util;
import view.PainelEsquerdo;

public class CamadaDeAplicacaoReceptora {

  /* *****************************************************************************
  Metodo: camadaDeAplicacaoReceptora*
  Funcao: Converter o array de numeros ascii em string*
  Parametros: int[] quadro: vetor com os numeros em ASCII*
  Retorno: void*
  ***************************************************************************** */
  public static void camadaDeAplicacaoReceptora(int[] quadro) {
    try {
      int[] asciiArray = Conversao.bitsParaAscii(quadro);

      Util.imprimirNaTela(
        Conversao.asciiParaString(asciiArray, Constantes.ASCII_DECODIFICADO),
        Constantes.ASCII_DECODIFICADO
      );

      String mensagem = Conversao.asciiParaMensagem(asciiArray);
      AplicacaoReceptora.aplicacaoReceptora(mensagem);

    } catch (Exception e) { //reinicia o programa em caso de erro
      PainelEsquerdo.sliderErro.setEnabled(true);
      PainelEsquerdo.mutexEnvio.release();
    }
  }
}
