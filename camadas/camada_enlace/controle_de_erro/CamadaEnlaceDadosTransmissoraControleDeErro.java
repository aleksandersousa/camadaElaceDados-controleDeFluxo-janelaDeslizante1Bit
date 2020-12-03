/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas.camada_enlace.controle_de_erro;

import camadas.camada_enlace.Quadro;
import util.Constantes;
import util.Conversao;
import util.Util;

public class CamadaEnlaceDadosTransmissoraControleDeErro {
  /*
   * *****************************************************************************
   * Metodo: camadaEnlaceDadosTransmissoraControleDeErro
   * Funcao: Aplicar o algoritmo de transmissao de controle de erro
   * Parametros: Quadro[] quadros: vetor com os quadros
   * Retorno: Quadro[] quadrosControleErro: vetor de quadros com o controle de erro
  */
  public static Quadro[] camadaEnlaceDadosTransmissoraControleDeErro(Quadro[] quadro) {
    Quadro[] quadrosControleErro = camadaEnlaceDadosTransmissoraControleDeErroCRC(quadro);

    //imprime todos os quadros
    String strConcatenada = Util.bitsErroParaString(quadrosControleErro);
    Util.imprimirNaTela(strConcatenada, Constantes.CRC_CODIFICADO);

    return quadrosControleErro;
  }

  /*
   * *****************************************************************************
   * Metodo: camadaEnlaceDadosTransmissoraControleDeErroCRC
   * Funcao: Aplicar o algoritmo de transmissao de controle de erro CRC-32
   * Parametros: Quadro[] quadros: vetor com os quadros
   * Retorno: Quadro[] quadro: vetor de quadros com o controle de erro
  */
  public static Quadro[] camadaEnlaceDadosTransmissoraControleDeErroCRC(Quadro[] quadro) {
    int[] bitsPolinomioCRC = 
      {1,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,1,0,0,0,1,1,1,0,1,1,0,1,1,0,1,1,1};

    for (int indexQuadro = 0; indexQuadro<quadro.length; indexQuadro++) {
      int[] quadroBits = Conversao.descomprimeBits(quadro[indexQuadro].bits);
      int tamanho = bitsPolinomioCRC.length + quadroBits.length - 1;
      
      int[] dividendo = new int[tamanho];
      int[] resto = new int[tamanho];

      for (int i=0; i<quadroBits.length; i++) {
        dividendo[i] = quadroBits[i];
        resto[i] = quadroBits[i];  
      }

      boolean zerado = false;
      for (int i=1; i<resto.length; i++) { //encontra o CRC-32
        if (!zerado) {
          for (int j=0; j<bitsPolinomioCRC.length; j++, i++) {
            resto[i] = resto[i] ^ bitsPolinomioCRC[j];
          }

          for (int j=0; j<quadroBits.length; j++) {
            if (resto[j] == 1) { //o dividendo ainda nao foi zerado
              i = j - 1; //comeca a comparar a partir do proximo 1
              break;
            } else if (j == quadroBits.length - 1){
              zerado = true;
            }
          }
        } else {
          break;
        }
      }

      for (int i=0; i<dividendo.length; i++) { //concatena a mensagem com a informacao de controle
        dividendo[i] = dividendo[i] ^ resto[i];
      }

      int[] quadroControleErro = Conversao.comprimeBits(dividendo);
      
      quadro[indexQuadro].bits = quadroControleErro;
    }

    return quadro;
  }
}
