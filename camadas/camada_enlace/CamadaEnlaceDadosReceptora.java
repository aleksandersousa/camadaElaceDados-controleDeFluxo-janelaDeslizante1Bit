/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 09/11/2020*
Ultima alteracao: 13/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package camadas.camada_enlace;

import camadas.camada_enlace.controle_de_erro.CamadaEnlaceDadosReceptoraControleDeErro;
import camadas.camada_enlace.controle_de_fluxo.CamadaEnlaceDadosReceptoraControleFluxo;
import util.Constantes;
import util.Util;
import util.Conversao;

public class CamadaEnlaceDadosReceptora {
  public static void camadaEnlaceDadosReceptora(Quadro quadro) {
    Util.imprimirNaTela(Conversao.bitsParaString(quadro.bits), Constantes.BIT_RECEBIDO);

    try {
      quadro = CamadaEnlaceDadosReceptoraControleDeErro.camadaEnlaceDadosReceptoraControleDeErro(quadro);
    } catch(Exception e) {
      System.out.println("array bugado .-.");
    }
    
    CamadaEnlaceDadosReceptoraControleFluxo.camadaEnlaceDadosReceptoraControleFluxo(quadro);
  }
}
