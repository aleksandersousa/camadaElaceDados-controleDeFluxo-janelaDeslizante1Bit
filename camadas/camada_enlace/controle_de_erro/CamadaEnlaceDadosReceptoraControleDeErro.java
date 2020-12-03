/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas.camada_enlace.controle_de_erro;

import java.util.concurrent.Semaphore;

import camadas.camada_enlace.Quadro;
import camadas.camada_enlace.controle_de_fluxo.CamadaEnlaceDadosTransmissoraControleFluxo;
import util.Constantes;
import util.Conversao;
import util.Evento;
import util.Util;
import view.Erro;

public class CamadaEnlaceDadosReceptoraControleDeErro {
  public static Semaphore mutex = new Semaphore(0);

  /*
   * *****************************************************************************
   * Metodo: camadaEnlaceDadosTransmissoraControleDeErro
   * Funcao: Aplicar o algoritmo de recepcao de controle de erro
   * Parametros: Quadro quadro: quadro manipulado pelo algoritmo
   * Retorno: Quadro quadroControleErro: quadro sem a informacao de controle de erro
  */
  public static Quadro camadaEnlaceDadosReceptoraControleDeErro(Quadro quadro) {
    Quadro quadroControleErro = camadaEnlaceDadosReceptoraControleDeErroCRC(quadro);

    Util.imprimirNaTela(Conversao.bitsParaString(quadroControleErro.bits), Constantes.CRC_DECODIFICADO);

    return quadroControleErro;
  }
  
  /*
   * *****************************************************************************
   * Metodo: camadaEnlaceDadosReceptoraControleDeErroCRC
   * Funcao: Aplicar o algoritmo de recepcao de controle de erro CRC-32
   * Parametros: Quadro quadro: quadro manipulado pelo algoritmo
   * Retorno: Quadro quadro: quadro sem a informacao de controle de erro
  */
  public static Quadro camadaEnlaceDadosReceptoraControleDeErroCRC(Quadro quadro) {
    int[] bitsPolinomioCRC = 
      {1,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,1,0,0,0,1,1,1,0,1,1,0,1,1,0,1,1,1};
    
    int[] resto = Conversao.descomprimeBits(quadro.bits);
    int tamanhoBitsQuadro = resto.length - bitsPolinomioCRC.length + 1;

    boolean zerado = false;
    for (int i=1; i<resto.length; i++) { //encontra o CRC-32
      if (!zerado) {
        for (int j=0; j<bitsPolinomioCRC.length; j++, i++) {
          resto[i] = resto[i] ^ bitsPolinomioCRC[j];
        }

        for (int j=0; j<tamanhoBitsQuadro; j++) {
          if (resto[j] == 1) { //o dividendo ainda nao foi zerado
            i = j - 1; //comeca a comparar a partir do proximo 1
            break;
          } else if (j == tamanhoBitsQuadro - 1){
            zerado = true;
          }
        }
      } else {
        break;
      }
    }

    for (int i=0; i<resto.length; i++) {
      if (resto[i] != 0) { //deu erro
        Erro.tratarErroCRC();
        CamadaEnlaceDadosTransmissoraControleFluxo.evento = Evento.ERRO;
        break;
      } else {
        CamadaEnlaceDadosTransmissoraControleFluxo.evento = Evento.CHEGOU;
      }
    }

    //tirando o resto da divisao
    resto = Conversao.descomprimeBits(quadro.bits);
    int[] bitsQuadro = new int[tamanhoBitsQuadro];
    for (int i=0; i<bitsQuadro.length; i++) {
      bitsQuadro[i] = resto[i];
    }

    int[] quadroControleErro = Conversao.comprimeBits(bitsQuadro);

    quadro.bits = quadroControleErro;

    return quadro;
  }
}
