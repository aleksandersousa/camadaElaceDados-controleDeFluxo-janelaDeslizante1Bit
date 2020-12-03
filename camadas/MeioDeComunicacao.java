/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas;

import java.util.Random;

import camadas.camada_enlace.CamadaEnlaceDadosReceptora;
import camadas.camada_enlace.Quadro;
import view.Canvas;
import util.Conversao;
import util.Util;

public class MeioDeComunicacao {
  public static double porcentagemDeErro;

  /*
   * *****************************************************************************
   * Metodo: meioDeComunicacao* 
   * Funcao: Enviar o bits recebidos da camada fisica transmissora para a camada 
             fisica receptora* 
   * Parametros: Quadro quadro
   * fluxoBrutoDeBits: vetor com os os bits* 
   * Retorno: void*
   */
  public static void meioDeComunicacao(Quadro quadro) {
    int[] fluxoBrutoDeBits = quadro.bits;

    int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
    int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBitsPontoA.length];

    new Thread(new Runnable() {
      int valor = 0;
      int displayMask = 1 << 31;

      @Override
      public void run() {
        // passando bit a bit de um vetor para outro
        for (int i = 0; i < fluxoBrutoDeBitsPontoA.length; i++) {
          int numero = fluxoBrutoDeBitsPontoA[i];

          Random random = new Random();

          int erro = random.nextInt(100);
          int indexErro = random.nextInt(33);

          for (int j = 1; j <= 32; j++) {
            valor <<= 1;

            if (erro < porcentagemDeErro) { // deu erro
              if (j == indexErro) { // altera apenas um bit aleatorio
                valor |= (numero & displayMask) == 0 ? 1 : 0;
              } else {
                valor |= (numero & displayMask) == 0 ? 0 : 1;
              }
            } else {
              valor |= (numero & displayMask) == 0 ? 0 : 1;
            }
            numero <<= 1;
          }

          fluxoBrutoDeBitsPontoB[i] = valor;
          valor = 0;
        }

        quadro.bits = fluxoBrutoDeBitsPontoB;

        // passando os bits para o canvas
        String strBits = Conversao.bitsParaString(quadro.bits);
        for (int i = 0; i < strBits.length(); i++) {
          if (Character.getNumericValue(strBits.charAt(i)) != -1) { // nao pega o espaco
            Canvas.fluxoDeBits.add(Character.getNumericValue(strBits.charAt(i)));
          }
        }

        try {
          // inciando animacao
          Canvas.iniciarListaDeImagens();
          Canvas.flag = true;
          Util.repintarCanvas();

          Canvas.trava.acquire(); // trava essa thread ate o canvas terminar a animacao
        } catch (InterruptedException e) {
          System.out.println("Erro no acquire do semaforo trava!");
        }

        CamadaEnlaceDadosReceptora.camadaEnlaceDadosReceptora(quadro);
      }
    }).start();
  }
}
