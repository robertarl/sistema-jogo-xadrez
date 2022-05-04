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
        colocanNovaPeca('c', 1, new Torre(tabuleiro, Cores.WHITE));
        colocanNovaPeca('c', 2, new Torre(tabuleiro, Cores.WHITE));
        colocanNovaPeca('d', 2, new Torre(tabuleiro, Cores.WHITE));
        colocanNovaPeca('e', 2, new Torre(tabuleiro, Cores.WHITE));
        colocanNovaPeca('e', 1, new Torre(tabuleiro, Cores.WHITE));
        colocanNovaPeca('d', 1, new Rei(tabuleiro, Cores.WHITE));

        colocanNovaPeca('c', 7, new Torre(tabuleiro, Cores.BLACK));
        colocanNovaPeca('c', 8, new Torre(tabuleiro, Cores.BLACK));
        colocanNovaPeca('d', 7, new Torre(tabuleiro, Cores.BLACK));
        colocanNovaPeca('e', 7, new Torre(tabuleiro, Cores.BLACK));
        colocanNovaPeca('e', 8, new Torre(tabuleiro, Cores.BLACK));
        colocanNovaPeca('d', 8, new Rei(tabuleiro, Cores.BLACK));

    }
}
