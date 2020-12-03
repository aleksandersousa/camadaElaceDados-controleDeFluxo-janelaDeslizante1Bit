/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas;

import view.PainelEsquerdo;

import util.Constantes;
import util.Util;

public class AplicacaoReceptora {
  /* *****************************************************************************
  Metodo: aplicacaoReceptora*
  Funcao: Imprimir a mensagem decodificada na tela*
  Parametros: String mensagem: mensagem a ser impressa*
  Retorno: void*
  ***************************************************************************** */
  public static void aplicacaoReceptora(String mensagem) {
    Util.imprimirNaTela(mensagem, Constantes.MENSAGEM_DECODIFICADA);

    PainelEsquerdo.sliderErro.setEnabled(true);
    PainelEsquerdo.mutexEnvio.release();
  }
}
