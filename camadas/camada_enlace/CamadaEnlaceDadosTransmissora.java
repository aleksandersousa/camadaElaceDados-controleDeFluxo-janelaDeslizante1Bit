/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 09/11/2020*
Ultima alteracao: 13/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package camadas.camada_enlace;

import camadas.camada_enlace.controle_de_erro.CamadaEnlaceDadosTransmissoraControleDeErro;
import camadas.camada_enlace.controle_de_fluxo.CamadaEnlaceDadosTransmissoraControleFluxo;
import util.Constantes;
import util.Conversao;
import util.Util;

public class CamadaEnlaceDadosTransmissora {
  public static int tamanhoMensagem;

  public static void camadaEnlaceDadosTransmissora(int[] quadro) {
    Util.imprimirNaTela(util.Conversao.bitsParaString(quadro), Constantes.BIT_BRUTO);

    Quadro[] quadros = camadaEnladeTransmissoraEnquadramento(quadro);

    tamanhoMensagem = quadros.length;

    quadros = CamadaEnlaceDadosTransmissoraControleDeErro.camadaEnlaceDadosTransmissoraControleDeErro(quadros);
    
    CamadaEnlaceDadosTransmissoraControleFluxo.camadaEnlaceDadosTransmissoraControleFluxo(quadros);
  }

  public static Quadro[] camadaEnladeTransmissoraEnquadramento(int[] quadro) {
    quadro = Conversao.bitsParaAscii(quadro);

    Quadro[] quadros = new Quadro[quadro.length];

    for (int i = 0; i<quadro.length; i++) {
      quadros[i] = new Quadro();
      int[] temp = {quadro[i]};
      quadros[i].bits = temp;
    }

    return quadros;
  }
}
