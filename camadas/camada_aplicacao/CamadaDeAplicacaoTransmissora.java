/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package camadas.camada_aplicacao;

import java.util.concurrent.Semaphore;

import camadas.camada_enlace.CamadaEnlaceDadosTransmissora;
import camadas.camada_enlace.controle_de_fluxo.CamadaEnlaceDadosTransmissoraControleFluxo;
import util.Conversao;
import util.Constantes;
import util.Util;
import view.PainelDireito;
import view.PainelDoMeio;

public class CamadaDeAplicacaoTransmissora {
  /* *****************************************************************************
  Metodo: camadaDeAplicacaoTransmissora*
  Funcao: Tranformar a mensagem em caracteres ASCII e enviar para a camada fisica 
          transmissora*
  Parametros: String mensagem: mensagem a ser enviada*
  Retorno: void*
  ***************************************************************************** */
  public static void camadaDeAplicacaoTransmissora(String mensagem) {
    CamadaDeAplicacaoTransmissora.resetarPrograma();    

    //pega os codigos ascii
    int[] arrayAscii = Conversao.stringParaAscii(mensagem);

    Util.imprimirNaTela(Conversao.asciiParaString(arrayAscii, Constantes.ASCII), Constantes.ASCII);

    int[] quadro = Conversao.asciiParaBits(arrayAscii);

    CamadaEnlaceDadosTransmissora.camadaEnlaceDadosTransmissora(quadro);
  }

  /* *****************************************************************************
  Metodo: resetarPrograma*
  Funcao: reseta todas a variaveis para um reenvio de mensagem*
  Parametros: String mensagem: mensagem a ser enviada*
  Retorno: void*
  ***************************************************************************** */
  public static void resetarPrograma() {
    //reseta texto na tela
    //ascii decodificado
    PainelDireito.arrayCaixasDeTexto.get(1).setText("");
    PainelDireito.arrayCaixasDeTexto.get(1).update(PainelDireito.arrayCaixasDeTexto.get(1).getGraphics());

    //mensagem decodficada
    PainelDireito.arrayCaixasDeTexto.get(4).setText("");
    PainelDireito.arrayCaixasDeTexto.get(4).update(PainelDireito.arrayCaixasDeTexto.get(4).getGraphics());

    //ack enviado
    PainelDoMeio.arrayCaixasDeTexto.get(7).setText("");
    PainelDoMeio.arrayCaixasDeTexto.get(7).update(PainelDoMeio.arrayCaixasDeTexto.get(7).getGraphics());

    //ack recebido
    PainelDoMeio.arrayCaixasDeTexto.get(8).setText("");
    PainelDoMeio.arrayCaixasDeTexto.get(8).update(PainelDoMeio.arrayCaixasDeTexto.get(8).getGraphics());

    //reseta buffer de mensagem decodificadas
    Util.bufferAsciiDecodificado = new StringBuilder();
    Util.bufferMensagemDecodificada = new StringBuilder();

    // reseta as janelas
    CamadaEnlaceDadosTransmissoraControleFluxo.proximoQuadroAEnviar = 0;
    CamadaEnlaceDadosTransmissoraControleFluxo.quadroEsperado = 0;

    // reseta o semaforo
    CamadaEnlaceDadosTransmissoraControleFluxo.mutexQuadro = new Semaphore(1);
  }
}
