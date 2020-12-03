/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package util;

import camadas.camada_enlace.Quadro;
import view.PainelDireito;
import view.PainelDoMeio;
import view.PainelEsquerdo;
import view.TelaPrincipal;

public class Util {
  public static StringBuilder bufferMensagemDecodificada = new StringBuilder();
  public static StringBuilder bufferAsciiDecodificado = new StringBuilder();
  /*
   * ************************************************************** Metodo:
   * imprimirNaTela* Funcao: Imprimir as informacoes na tela* Parametros: String
   * strMensagem: texto a ser impresso int tipoDeImpressao: em qual caixa de texto
   * sera impresso* Retorno: void*
   */
  public static void imprimirNaTela(String strMensagem, int tipoDeImpressao) {
    try {
      switch (tipoDeImpressao) {
        case Constantes.ASCII:
          PainelEsquerdo.arrayCaixasDeTexto.get(0).setText(strMensagem);
          PainelEsquerdo.arrayCaixasDeTexto.get(0).update(PainelEsquerdo.arrayCaixasDeTexto.get(0).getGraphics());
          break;
        case Constantes.ASCII_DECODIFICADO:
          bufferAsciiDecodificado.append(strMensagem + ", ");
          PainelDireito.arrayCaixasDeTexto.get(1).setText(bufferAsciiDecodificado.toString());
          PainelDireito.arrayCaixasDeTexto.get(1).update(PainelDireito.arrayCaixasDeTexto.get(1).getGraphics());
          break;
        case Constantes.MENSAGEM_DECODIFICADA:
          bufferMensagemDecodificada.append(strMensagem);
          PainelDireito.arrayCaixasDeTexto.get(4).setText(bufferMensagemDecodificada.toString());
          PainelDireito.arrayCaixasDeTexto.get(4).update(PainelDireito.arrayCaixasDeTexto.get(4).getGraphics());
          break;
        case Constantes.BIT_BRUTO:
          PainelEsquerdo.arrayCaixasDeTexto.get(2).setText(strMensagem);
          PainelEsquerdo.arrayCaixasDeTexto.get(2).update(PainelEsquerdo.arrayCaixasDeTexto.get(2).getGraphics());
          break;
        case Constantes.CRC_DECODIFICADO:
          PainelDireito.arrayCaixasDeTexto.get(5).setText(strMensagem);
          PainelDireito.arrayCaixasDeTexto.get(5).update(PainelDireito.arrayCaixasDeTexto.get(5).getGraphics());
          break;        
        case Constantes.CRC_CODIFICADO: 
          PainelEsquerdo.arrayCaixasDeTexto.get(3).setText(strMensagem);
          PainelEsquerdo.arrayCaixasDeTexto.get(3).update(PainelEsquerdo.arrayCaixasDeTexto.get(3).getGraphics());
          break;
        case Constantes.BIT_RECEBIDO:
          PainelDireito.arrayCaixasDeTexto.get(6).setText(strMensagem);
          PainelDireito.arrayCaixasDeTexto.get(6).update(PainelDireito.arrayCaixasDeTexto.get(6).getGraphics());
          break;
        case Constantes.ACK_ENVIADO:
          PainelDoMeio.arrayCaixasDeTexto.get(7).setText(strMensagem);
          PainelDoMeio.arrayCaixasDeTexto.get(7).update(PainelDoMeio.arrayCaixasDeTexto.get(7).getGraphics());
          break;
        case Constantes.ACK_RECEBIDO:
          PainelDoMeio.arrayCaixasDeTexto.get(8).setText(strMensagem);
          PainelDoMeio.arrayCaixasDeTexto.get(8).update(PainelDoMeio.arrayCaixasDeTexto.get(8).getGraphics());
          break;
      }

      Thread.sleep(600);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * ************************************************************** Metodo:
   * repintarPainel* Funcao: inicializa o thread que repinta o painel canvas*
   * Parametros: nulo* Retorno: void*
   */
  public static void repintarCanvas() {
    try {
      TelaPrincipal.canvas.repintar();
      Thread.sleep(600);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * ************************************************************** 
   * Metodo: bitsErroParaString
   * Funcao: concatena os bits do array de quadros em uma unica string
   * Parametros: nulo
   * Retorno: void
   */
  public static String bitsErroParaString(Quadro[] quadros) {
    StringBuilder strBuilder = new StringBuilder();
    
    for (Quadro quadro : quadros) {
      strBuilder.append(Conversao.bitsParaString(quadro.bits) + "\n");
    }

    return strBuilder.toString();
  }
}
