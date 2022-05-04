package aplicacao;

import xadrez.PartidaXadrez;

public class JogoXadrez {
    public static void main(String[] args){

        PartidaXadrez partidaXadrez = new PartidaXadrez();
        UI.printTabuleiro(partidaXadrez.getPecas());
    }
}
