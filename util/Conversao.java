/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 24/11/2020*
Ultima alteracao: 28/11/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */

package util;

public class Conversao {

  /* ***************************************************************
  Metodo: bitsParaAscii*
  Funcao: Transforma os bits da mensagem em caracteres ASCII*
  Parametros: int[] fluxoBrutoDeBits: bits a serem convertidos*
  Retorno: int[] quadro: vetor com os numeros ASCII*
  *************************************************************** */
  public static int[] bitsParaAscii(int[] fluxoBrutoDeBits) {
    int novoTamanho = 0;
    int numeroDeBits =
      32 - Integer.numberOfLeadingZeros(fluxoBrutoDeBits[fluxoBrutoDeBits.length-1]);

    if(numeroDeBits<=8){
      novoTamanho = (fluxoBrutoDeBits.length*4)-3;
    }else if(numeroDeBits<=16){
      novoTamanho = (fluxoBrutoDeBits.length*4)-2;
    }else if(numeroDeBits<=24){
      novoTamanho = (fluxoBrutoDeBits.length*4)-1;
    }else if(numeroDeBits<=32){
      novoTamanho = fluxoBrutoDeBits.length*4;
    }

    int[] quadro = new int[novoTamanho];
    int displayMask = 1 << 31;
    int valor = 0;

    for(int i=0, pos=0; i<fluxoBrutoDeBits.length; i++){
      int numero = fluxoBrutoDeBits[i];
      numeroDeBits = 32 - Integer.numberOfLeadingZeros(numero);
      numeroDeBits = arredondaBits(numeroDeBits);

      numero <<= 32-numeroDeBits;

      for(int j=1; j<=numeroDeBits; j++){
        if((numero & displayMask) == 0){
          valor <<= 1;
          valor = valor | 0;
        }else{
          valor <<= 1;
          valor = valor | 1;
        }
        numero <<= 1;

        if(j%8 == 0){
          quadro[pos] = valor;
          valor = 0;
          pos++;
        }
      }
    }

    return quadro;
  }

  /* ***************************************************************
  Metodo: asciiParaBits*
  Funcao: Transforma os numeros ASCII para bits*
  Parametros: int[] quadro: vetor com os numeros ASCII*
  Retorno: int[] bitsBrutos: vetor com os bits*
  *************************************************************** */
  public static int[] asciiParaBits(int[] quadro) {
    int novoTamanho = quadro.length%4 == 0 ? quadro.length/4 : quadro.length/4+1;

    int[] fluxoBrutoDeBits = new int[novoTamanho];
    int valor = 0;

    for(int i=0, pos=0; i<quadro.length; i++){
      valor <<= 8;
      valor = valor | quadro[i];

      if(i%4 >= 0 && i == quadro.length-1){
        fluxoBrutoDeBits[pos] = valor;
      }else if(i%4 == 3){
        fluxoBrutoDeBits[pos] = valor;
        valor=0;
        pos++;
      }
    }

    return fluxoBrutoDeBits;
  }

  /* ***************************************************************
  Metodo: asciiParaMensagem*
  Funcao: Transforma os numeros ASCII para Mensagem*
  Parametros: int[] quadro: vetor com os numeros ASCII*
  Retorno: String strMensagem: mensagem*
  *************************************************************** */
  public static String asciiParaMensagem(int[] quadro) {
    StringBuilder strMensagem = new StringBuilder();

    for(int i=0; i<quadro.length; i++){
       strMensagem.append(Character.toString((char)quadro[i]));
    }

    return strMensagem.toString();
  }

  /* ***************************************************************
  Metodo: bitsParaString*
  Funcao: Transforma os os bits da mensagem para o tipo String*
  Parametros: int[] bits: vetor com os bits*
  Retorno: String strBits: String com os bits*
  *************************************************************** */
  public static String bitsParaString(int[] bits) {
    StringBuilder strBits = new StringBuilder();

    int displayMask = 1 << 31;

    for(int i=0; i<bits.length; i++){
      int numero = bits[i];
      int numeroDeBits = 32 - Integer.numberOfLeadingZeros(numero);
      numeroDeBits = arredondaBits(numeroDeBits);

      numero <<= 32-numeroDeBits;

      for(int j=1; j<=numeroDeBits; j++){
        strBits.append((numero & displayMask) == 0 ? 0 : 1);
        numero <<= 1;

        if(j%8 == 0){
          strBits.append(" ");
        }
      }
    }

    return strBits.toString();
  }

  /* **************************************************************
  Metodo: asciiParaString*
  Funcao: Transformar os elementos do vetor quadro em bits e colocar
          os bits em uma string seguido da letra ou numero rspectivo
          para imprimir na tela*
  Parametros: int[] quadro: vetor com os numeros em ASCII
              int tipoDeImpressao: em qual caixa de texto sera impresso*
  Retorno: void*
  *************************************************************** */
  public static String asciiParaString(int[] quadro, int tipoDeFormato) {
    StringBuilder strAscii = new StringBuilder();

    if(tipoDeFormato == Constantes.ASCII){
      for(int i=0; i<quadro.length; i++){
        if(i == quadro.length-1){
          strAscii.append(Character.toString((char)quadro[i])+"["+quadro[i]+"]");
        }
        else{
          strAscii.append(Character.toString((char)quadro[i])+"["+quadro[i]+"]\n");
        }
      }
    }
    else if(tipoDeFormato == Constantes.ASCII_DECODIFICADO){
      for(int i=0; i<quadro.length; i++){
        if(i == quadro.length-1){
          strAscii.append(quadro[i]+"->"+Character.toString((char)quadro[i]));
        }
        else{
          strAscii.append(quadro[i]+"->"+Character.toString((char)quadro[i])+" ");
        }
      }
    }

    return strAscii.toString();
  }

  /* **************************************************************
  Metodo: stringParaAscii*
  Funcao: Transformar string em ascii*
  Parametros: String mensagem: string a ser transformada*
  Retorno: int[] arrayAscii: codigo ascii de cada char da string*
  *************************************************************** */
  public static int[] stringParaAscii (String mensagem) {
    int[] arrayAscii = new int[mensagem.length()];
    for(int i=0; i<mensagem.length(); i++){
      arrayAscii[i] = mensagem.charAt(i);
    }

    return arrayAscii;
  }

  /* **************************************************************
  Metodo: arredondaBits*
  Funcao: Arrendonda o numero de bits do numero*
  Parametros: int numeroDeBits: numero a ser arredondado
  Retorno: int: numero arrendondado*
  *************************************************************** */
  public static int arredondaBits(int numeroDeBits){
    if(numeroDeBits <= 8){
      return numeroDeBits = 8;
    }else if(numeroDeBits <= 16){
      return numeroDeBits = 16;
    }else if(numeroDeBits <= 24){
      return numeroDeBits = 24;
    }else{
      return numeroDeBits = 32;
    }
  }

  /* **************************************************************
  Metodo: vetorBits*
  Funcao: pegar cada bit de cada posicao do vetor e passar para uma
          posicao do vetor de bits*
  Parametros: int[] quadro: array com os bits comprimidos*
  Retorno: int[] vetorBits: array de bits descomprimidos*
  *************************************************************** */
  public static int[] descomprimeBits (int[] quadro) {
    int numeroDeBits = 
      Conversao.arredondaBits(Integer.toBinaryString(quadro[quadro.length-1]).length());
    int tamanho = (quadro.length-1)*32 + numeroDeBits;
    int[] vetorBits = new int[tamanho];

    int displayMask = 1 << 31;
    int indexBits = 0;

    for (int numero : quadro) {
      numeroDeBits = 32 - Integer.numberOfLeadingZeros(numero);
      numeroDeBits = Conversao.arredondaBits(numeroDeBits);
      
      numero <<=  32 - numeroDeBits;

      for (int i=0; i<numeroDeBits; i++) {
        vetorBits[indexBits++] = (numero & displayMask) == 0 ? 0 : 1;
        numero <<= 1;
      }
    }

    return vetorBits;
  }
  
  /* **************************************************************
  Metodo: comprimeBits*
  Funcao: pega um vetor de bits descomprimidos e comprime em um vetor
          de inteiros com os bits comprimidos*
  Parametros: int[] bits: array com os bits descomprimidos*
  Retorno: int[] bitsComprimidos: array de bits descomprimidos*
  *************************************************************** */
  public static int[] comprimeBits (int[] bits) {
    int tamanho = bits.length%32 == 0 ? bits.length/32 : bits.length/32 + 1;
    int[] bitsComprimidos = new int[tamanho];

    int valor = 0;

    for (int i=1, indexBitsComprimidos = 0; i<=bits.length; i++) {
      valor <<= 1;
      valor |= bits[i - 1];

      if (i%32 == 0 || i == bits.length) {
        bitsComprimidos[indexBitsComprimidos++] = valor;
        valor = 0;
      }
    }
    
    return bitsComprimidos;
  }
}
