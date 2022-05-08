package pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;


public class Cavalo extends PecaXadrez {

    public Cavalo(Tabuleiro tabuleiro, Cores cores) {
        super(tabuleiro, cores);
    }

    @Override
    public String toString() {
        return "C";
    }

    private boolean mover(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p == null || p.getCores() != getCores();
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExiste(p) && mover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && mover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(posicao.getLinha() - 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && mover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExiste(p) && mover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExiste(p) && mover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(posicao.getLinha() + 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && mover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && mover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExiste(p) && mover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
