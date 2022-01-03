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

        //TO DO LIST :
        //Revoir pourquoi il n'y pas 7 jetons dans le sac à tout moment!!!
        //A faire :
        // Créer CapeloDico
        // Empecher de placer un mot vide
        // Empecher de choisir numLig ou numCol invalide au placement du mot.
        // Comment changer de choix si on s'est trompé (passer de placer à Echanger par exemple)

        //


        }






    }
