package xadrez;

import pecasXadrez.Rei;
import pecasXadrez.Torre;
import tabuleiro.Tabuleiro;



public class PartidaXadrez {

    private Tabuleiro tabuleiro;

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8,8);
        initialSetup();
    }
    public PecaXadrez[][] getPecas(){
        PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for(int i=0; i< tabuleiro.getLinhas(); i++){
            for(int j=0; j<tabuleiro.getColunas(); j++){
                matriz[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
            }
        }
        return matriz;
    }

    private void colocanNovaPeca(char coluna, int linha, PecaXadrez pecaXadrez){
        tabuleiro.colocarPeca(pecaXadrez, new PosicaoXadrez(coluna, linha).toPosicao());
    }

    private void initialSetup(){
        colocanNovaPeca('b', 6, new Torre(tabuleiro, Cores.WHITE));
        colocanNovaPeca('e', 8, new Rei(tabuleiro, Cores.BLACK));
        colocanNovaPeca('e', 1, new Rei(tabuleiro, Cores.WHITE));

    }
}
