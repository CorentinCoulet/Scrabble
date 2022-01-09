public class MainScrabble {
    public static void main(String[] args){

        Ut.afficher("Nombre de joeurs (2-4): ");
        int nbJ=Ut.saisirEntier();
        while(nbJ<2 || nbJ>4){
            nbJ=Ut.saisirEntier();
        }

        String[] listeJoueurs = new String[nbJ];
        for(int i = 0; i < nbJ; i++){
            Ut.afficher("Nom Joueur" + (i+1) + " :");
            listeJoueurs[i]=Ut.saisirChaine();
        }

        Scrabble jeuScrabble = new Scrabble(listeJoueurs);
        jeuScrabble.partie();
        }
    }
