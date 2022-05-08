package xadrez;

import pecasXadrez.Bispo;
import pecasXadrez.Peao;
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
    private boolean checkMate;

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

    public boolean getCheckMate() {
        return checkMate;
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

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        }
        else{
            nextTurn();
        }
        return (PecaXadrez) pecaCapturada;
    }

    public Peca mover(Posicao origem, Posicao destino){

        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
        p.aumentarMovimentos();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);

        if (pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }
    private void desfazMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {

        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
        p.diminuirMovimentos();
        tabuleiro.colocarPeca(p, origem);

        if (pecaCapturada != null) {
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
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
    private boolean testCheckMate(Cores cores) {
        if (!testCheck(cores)) {
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCores() == cores).collect(Collectors.toList());
        for (Peca p : list) {
            boolean[][] mat = p.possiveisMovimentos();
            for (int i=0; i<tabuleiro.getLinhas(); i++) {
                for (int j=0; j<tabuleiro.getColunas(); j++) {
                    if (mat[i][j]) {
                        Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosicao();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = mover(origem, destino);
                        boolean testCheck = testCheck(cores);
                        desfazMovimento(origem, destino, pecaCapturada);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    private void colocaNovaPeca(char coluna, int linha, PecaXadrez pecaXadrez){
        tabuleiro.colocarPeca(pecaXadrez, new PosicaoXadrez(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(pecaXadrez);
    }

    private void initialSetup(){
        colocaNovaPeca('a', 1, new Torre(tabuleiro, Cores.WHITE));
        colocaNovaPeca('c', 1, new Bispo(tabuleiro, Cores.WHITE));
        colocaNovaPeca('e', 1, new Rei(tabuleiro, Cores.WHITE));
        colocaNovaPeca('f', 1, new Bispo(tabuleiro, Cores.WHITE));
        colocaNovaPeca('h', 1, new Torre(tabuleiro, Cores.WHITE));

        colocaNovaPeca('a', 2, new Peao(tabuleiro, Cores.WHITE));
        colocaNovaPeca('b', 2, new Peao(tabuleiro, Cores.WHITE));
        colocaNovaPeca('c', 2, new Peao(tabuleiro, Cores.WHITE));
        colocaNovaPeca('d', 2, new Peao(tabuleiro, Cores.WHITE));
        colocaNovaPeca('e', 2, new Peao(tabuleiro, Cores.WHITE));
        colocaNovaPeca('f', 2, new Peao(tabuleiro, Cores.WHITE));
        colocaNovaPeca('g', 2, new Peao(tabuleiro, Cores.WHITE));
        colocaNovaPeca('h', 2, new Peao(tabuleiro, Cores.WHITE));

        //------------------------------------------------------------------
        colocaNovaPeca('a', 8, new Torre(tabuleiro, Cores.BLACK));
        colocaNovaPeca('c', 8, new Bispo(tabuleiro, Cores.BLACK));
        colocaNovaPeca('e', 8, new Rei(tabuleiro, Cores.BLACK));
        colocaNovaPeca('f', 8, new Bispo(tabuleiro, Cores.BLACK));
        colocaNovaPeca('h', 8, new Torre(tabuleiro, Cores.BLACK));

        colocaNovaPeca('a', 7, new Peao(tabuleiro, Cores.BLACK));
        colocaNovaPeca('b', 7, new Peao(tabuleiro, Cores.BLACK));
        colocaNovaPeca('c', 7, new Peao(tabuleiro, Cores.BLACK));
        colocaNovaPeca('d', 7, new Peao(tabuleiro, Cores.BLACK));
        colocaNovaPeca('e', 7, new Peao(tabuleiro, Cores.BLACK));
        colocaNovaPeca('f', 7, new Peao(tabuleiro, Cores.BLACK));
        colocaNovaPeca('g', 7, new Peao(tabuleiro, Cores.BLACK));
        colocaNovaPeca('h', 7, new Peao(tabuleiro, Cores.BLACK));


    }
}
