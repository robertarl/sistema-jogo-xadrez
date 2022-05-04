package xadrez;

import pecasXadrez.Rei;
import pecasXadrez.Torre;
import tabuleiro.Posicao;
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
    private void initialSetup(){
        tabuleiro.lugarPeca(new Torre(tabuleiro, Cores.WHITE), new Posicao(2, 1));
        tabuleiro.lugarPeca(new Rei(tabuleiro, Cores.BLACK), new Posicao(0, 4));
        tabuleiro.lugarPeca(new Rei(tabuleiro, Cores.WHITE), new Posicao(7, 4));
    }
}
