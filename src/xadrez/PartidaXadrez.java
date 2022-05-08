package xadrez;

import pecasXadrez.Rei;
import pecasXadrez.Torre;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;
    private Cores currentPlayer;
    private int turn;
    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();
    private boolean check;

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8,8);
        turn = 1;
        currentPlayer = Cores.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Cores getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean getCheck() {
        return check;
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

    public boolean[][] possibleMoves(PosicaoXadrez posicaoDeOrigem){
        Posicao posicao = posicaoDeOrigem.toPosicao();
        validatePosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).possiveisMovimentos();
    }

    public PecaXadrez realizarPartida(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){
        Posicao origem = posicaoOrigem.toPosicao();
        Posicao destino = posicaoDestino.toPosicao();

        validatePosicaoOrigem(origem);
        validatePosicaoDestino(origem, destino);
        Peca pecaCapturada = mover(origem,destino);

        if (testCheck(currentPlayer)) {
            desfazMovimento(origem, destino, pecaCapturada);
            throw new XadrezException("Você não pode se colocar em cheque");
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;
        nextTurn();
        return (PecaXadrez) pecaCapturada;
    }
    private void desfazMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        Peca p = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, origem);

        if (pecaCapturada != null) {
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
    }

    public Peca mover(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);

        if (pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }

    public void validatePosicaoOrigem(Posicao posicao){
        if(!tabuleiro.haUmaPeca(posicao)){
            throw new XadrezException("Nao existe peca na posicao de origem");
        }
        if(currentPlayer != ((PecaXadrez)tabuleiro.peca(posicao)).getCores()){
            throw new XadrezException("A peça escolhida não é sua.");
        }
        if(!tabuleiro.peca(posicao).HaPossiveisMovimentos()){
            throw new XadrezException("Não existe movimentos escolhidos para a peca especifica");
        }
    }
    public void validatePosicaoDestino(Posicao origem, Posicao destino){
        if(!tabuleiro.peca(origem).possiveisMovimentos(destino)){
            throw new XadrezException("A peça escolhida não pode se mover pra posição de destino.");
        }
    }
    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Cores.WHITE) ? Cores.BLACK : Cores.WHITE;
    }

    private Cores opponent(Cores cores) {
        return (cores == Cores.WHITE) ? Cores.BLACK : Cores.WHITE;
    }

    private PecaXadrez rei(Cores cores) {
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCores() == cores).collect(Collectors.toList());
        for (Peca p : list) {
            if (p instanceof Rei) {
                return (PecaXadrez)p;
            }
        }
        throw new IllegalStateException("Nao ha rei" + cores + " no tabuleiro");
    }

    private boolean testCheck(Cores cores) {
        Posicao posicaoRei = rei(cores).getPosicaoXadrez().toPosicao();
        List<Peca> opponentPieces = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCores() == opponent(cores)).collect(Collectors.toList());
        for (Peca p : opponentPieces) {
            boolean[][] mat = p.possiveisMovimentos();
            if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }
    private void colocaNovaPeca(char coluna, int linha, PecaXadrez pecaXadrez){
        tabuleiro.colocarPeca(pecaXadrez, new PosicaoXadrez(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(pecaXadrez);
    }

    private void initialSetup(){
        colocaNovaPeca('c', 1, new Torre(tabuleiro, Cores.WHITE));
        colocaNovaPeca('c', 2, new Torre(tabuleiro, Cores.WHITE));
        colocaNovaPeca('d', 2, new Torre(tabuleiro, Cores.WHITE));
        colocaNovaPeca('e', 2, new Torre(tabuleiro, Cores.WHITE));
        colocaNovaPeca('e', 1, new Torre(tabuleiro, Cores.WHITE));
        colocaNovaPeca('d', 1, new Rei(tabuleiro, Cores.WHITE));

        colocaNovaPeca('c', 7, new Torre(tabuleiro, Cores.BLACK));
        colocaNovaPeca('c', 8, new Torre(tabuleiro, Cores.BLACK));
        colocaNovaPeca('d', 7, new Torre(tabuleiro, Cores.BLACK));
        colocaNovaPeca('e', 7, new Torre(tabuleiro, Cores.BLACK));
        colocaNovaPeca('e', 8, new Torre(tabuleiro, Cores.BLACK));
        colocaNovaPeca('d', 8, new Rei(tabuleiro, Cores.BLACK));

    }
}
