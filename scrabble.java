public class scrabble {

    private Joueur[] joueurs;

    private int numJoueur;  // joueur courant (entre 0 et joueurs.length-1)
    
    private Plateau plateau;

    private MEE sac;

    private static int[] nbPointsJeton = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10};


    public scrabble(String[] listeJoueurs) {

        int[] sacStandard = { 9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1 };
        
        this.plateau = new Plateau();
        this.sac = new MEE(sacStandard);
        this.joueurs = new Joueur[listeJoueurs.length];
        
        for (int i = 0; i < listeJoueurs.length; i++) {
            
            this.joueurs[i] = new Joueur(listeJoueurs[i]);
            this.numJoueur = i;
        }
    }


    /*
      1. la distribution initiale des jetons aux joueurs,
      2. des itérations sur les différents tours de jeu jusqu’à la fin de la
      partie,
      // 3. le calcul des scores finaux,
      4. l’affichage du ou des gagnants.
    */

    public void partie() {

        boolean arret = false;
        int changeJoueur = 0;
        
        this.numJoueur = Ut.randomMinMax(0, joueurs.length - 1);
        // Distribution initiale des jetons aux joueurs
        
        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i].prendJetons(this.sac, 7);
        }
        // Début de la partie + itération des tours

        while (!arret) {
            Ut.afficher(this.toString());
            int passeTour = joueurs[this.numJoueur].joue(this.plateau, this.sac, this.nbPointsJet);

            if (passeTour == -1) {

                changeJoueur++;

                if (changeJoueur == joueurs.length) {
                    arret = true;
                    // fin de tour des joueurs, retrait des points des jetons restants sur le terrain

                    for (int y = 0; y < joueurs.length; y++) {
                        int ptsRestant = joueurs[y].nbPointsChevalet(nbPointsJet);
                        joueurs[y].ajouteScore(-(ptsRestant));
                    }
                }
            }

            if (passeTour == 1) {
                
                /*
                 remplissage impossible du terrain, sac vide, le joueur actuel gagne 
                 et récupère les points des jetons restants des autres joueurs sur le terrain
                */

                arret = true;

                int ptsRestantTest = 0;

                for (int i = 0; i < joueurs.length; i++) {

                    // on vérifie si le terrain est bien vide, renvoi 0 en score

                    ptsRestantTest += joueurs[i].nbPointsChevalet(nbPointsJet);
                }
                joueurs[this.numJoueur].ajouteScore(ptsRestantTest);

            }
            if (this.numJoueur == joueurs.length - 1 && !arret) {
                // Je réinitialise le joueur courant au premier joueur de la liste pour passer
                // au tour suivant.
                changeJoueur = 0;
                this.numJoueur = 0;
            } 
            
            else {

                if (!arret) {
                    this.numJoueur++;
                }
            }
        }

        System.out.println(this.vainqueur());

    }


    /*
      Méthode d'affichage du/des vainqueur(s) (en cas d'ex-aequo)
      retourne (nom et score)
    */

    public String vainqueur() {

        int[] winner = new int[joueurs.length];
        int idWinner = 0;
        String answer = "";

        for (int i = 1; i < (joueurs.length); i++) {

            if (joueurs[idWinner].getScore() == joueurs[i].getScore()) {

                winner[i] = i;
            } 
            
            else {

                if (joueurs[idWinner].getScore() < joueurs[i].getScore()) {

                    idWinner = i;
                }
            }
        }

        if (idWinner == winner[1] && winner[1] != 0) { 
            // égalité à la première place de 2 ou plus de joueurs

            answer += "Ex-aequo entre :" + '\n' + joueurs[idWinner].toString() + " et ";
            for (int i = 1; i < winner.length; i++) {
                if (winner[i] != 0) {
                    answer += joueurs[winner[i]].toString() + ", ";
                }
            }
        } else {
            answer += "Le vainqueur est :" +'\n'+ joueurs[idWinner].toString();
        }
        return answer;
    }
    // toString pour afficher, à chaque tour de jeu, l'état du plateau et le joueur qui a la main

    public String toString() {
        
        return (this.plateau.toString() + '\n' + joueurs[this.numJoueur].toString());
    }

}