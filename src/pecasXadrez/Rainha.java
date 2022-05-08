package pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

import javax.swing.text.Position;

public class Rainha extends PecaXadrez {

    public Rainha(Tabuleiro tabuleiro, Cores cores) {
        super(tabuleiro, cores);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // above
        p.setValues(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // left
        p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }


        // right
        p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }


        // below
        p.setValues(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }


        // nw
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // ne
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }


        // se
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }


        // sw
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }


        return mat;
    }
}
