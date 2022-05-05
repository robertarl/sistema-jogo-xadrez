package pecasXadrez;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

    public Torre(Tabuleiro tabuleiro, Cores cores) {
        super(tabuleiro, cores);
    }
    @Override
    public String toString(){
        return "T";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        return mat;
    }
}
