package aplicacao;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class JogoXadrez {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();
        List<PecaXadrez> capturada = new ArrayList<>();

        while (!partidaXadrez.getCheckMate()){
            try {
                UI.clearScreen();
                UI.imprimirPartida(partidaXadrez, capturada);
                System.out.println();
                System.out.print("Origem: ");
                PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

                boolean[][] possibleMoves = partidaXadrez.possibleMoves(origem);
                UI.clearScreen();
                UI.printTabuleiro(partidaXadrez.getPecas(), possibleMoves);
                System.out.println();
                System.out.print("Destino: ");
                PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

                PecaXadrez pecaCapturada = partidaXadrez.realizarPartida(origem, destino);

                if(pecaCapturada != null){
                    capturada.add(pecaCapturada);
                }
            }
            catch (XadrezException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.imprimirPartida(partidaXadrez, capturada);
    }
}
