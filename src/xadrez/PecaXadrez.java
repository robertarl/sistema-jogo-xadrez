package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

    private Cores cores;

    public PecaXadrez(Tabuleiro tabuleiro, Cores cores) {
        super(tabuleiro);
        this.cores = cores;
    }

    public Cores getCores() {
        return cores;
    }
    public PosicaoXadrez getPosicaoXadrez() {
        return PosicaoXadrez.fromPosicao(posicao);
    }

    protected boolean haPecaAdversaria(Posicao posicao){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p != null && p.getCores() != cores;
    }
}
