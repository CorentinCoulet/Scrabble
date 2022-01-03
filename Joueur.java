public class Joueur {

    private String nom;
    private MEE chevalet;
    private int score;

    public Joueur(String unNom) {

        this.nom = unNom;
        this.chevalet = new MEE(26);
        this.score = 0;

    }

    public String toString() {

        return ("Joueur: " + this.nom + '\n' + "Score: " + this.score + '\n');
    }

    public int getScore() {

        return this.score;
    }

    public void ajouteScore(int nb) {

        this.score += nb;
    }


    /*
      pré-requis : nbPointsJeton, indique le nombre de points rapportés par chaque jeton/lettre
      résultat : le nombre de points total sur le chevalet de ce joueur
      suggestion : bien relire la classe MEE !
    */

    public int nbPointsChevalet(int[] nbPointsJeton) {

        int scoreChevalet;

        if (this.chevalet.estVide()) {

            scoreChevalet = 0;
        } 
        
        else {

            scoreChevalet = this.chevalet.sommeValeurs(nbPointsJeton);
        }

        return (scoreChevalet);
    }


    /*
      pré-requis : les éléments de s sont inférieurs à 26
      action : simule la prise de nbJetons jetons par this dans le sac s,
      dans la limite de son contenu.
    */

    public void prendJetons(MEE s, int nbJetons) {

      s.transfereAleat(this.chevalet, nbJetons);
    }

    /*
      pré-requis : les éléments de s sont inférieurs à 26 et nbPointsJet.length >= 26
      action : simule le coup de this : this choisit de passer son tour,
      d’échanger des jetons ou de placer un mot
      résultat : -1 si this a passé son tour, 1 si son chevalet est vide, et 0 sinon
     */

    public int joue(Plateau p, MEE s, int[] nbPointsJet) {

        int soluce=-4; //  on met une valeur par défaut qui ne changera rien au programme

        Ut.afficher(this.printChevalet());
        System.out.println("N pour passer, E pour échanger, P pour placer : ");
        char userInput = Ut.saisirCaractere();

        switch (userInput) {

            case 'N':
                // Revenir dessus pour la structure du tour passé
                soluce = -1;
                break;

            case 'E':
                this.echangeJetons(s);
                soluce = 0;
                break;

            case 'P':

                if(joueMot(p, s, nbPointsJet)){

                    if (this.chevalet.estVide()) {

                        soluce = 1;
                    } 
                    
                    else {

                        soluce = 0;
                    }
                }

                else {
                    Ut.afficher("Placement invalide du Mot");

                    joueMot(p, s, nbPointsJet);
                }

                break;

            default:

                throw new IllegalStateException("Choix incorrect" + userInput);
        
        }

        return soluce;
    }


    /*
      pré-requis : les éléments de s sont inférieurs à 26
      et nbPointsJet.length >= 26
      action : simule le placement d’un mot de this :
      a) le mot, sa position sur le plateau et sa direction, sont saisis
      au clavier
      b) vérifie si le mot est valide
      c) si le coup est valide, le mot est placé sur le plateau
      résultat : vrai ssi ce coup est valide, c’est-à-dire accepté par
      CapeloDico et satisfaisant les règles détaillées plus haut
      stratégie : utilise la méthode joueMotAux
    */

    public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet) {

        boolean soluce;
        Ut.afficher("Mot à placer :");

        Mot motIn = new Mot();
        motIn.ask(); // CAPELODico au boulot!
        String mot = motIn.getMot();

        System.out.println("Veuillez l'emplacement de ligne souhaité : ");
        int numLigne = Ut.saisirEntier() - 1;

        System.out.println("Maintenant, l'emplacement de colonne souhaité : ");
        int numColumn = Ut.saisirEntier() - 1;

        System.out.println("Et enfin, le sens de placement, v pour vertical, h pour horizontal");
        char sens = Ut.saisirCaractere();

        if (p.placementValide(mot, numLigne, numColumn, sens, this.chevalet)) {

            joueMotAux(p, s, nbPointsJet, mot, numLigne, numColumn, sens);
            soluce = true;
        } 
        
        else {

            soluce = false;
        }

        return soluce;
    }


    /*
      pré-requis : cf. joueMot et le placement de mot à partir de la case (numLig, numCol) 
      dans le sens donné par sens est valide action : simule le placement d’un mot de this
      Les jetons sont placés sur le plateau, leurs exemplaires retirés du chevalet
      Le score est calculé en multipliant score lettre*code couleur
    */

    public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, int numLigne, int numColumn, char sens) {

        p.place(mot, numLigne, numColumn, sens, this.chevalet);

        if (this.chevalet.getCardinal() < 7) {

            this.prendJetons(s, 7 - this.chevalet.getCardinal());
        }

        this.ajouteScore(p.nbPointsPlacement(mot, numLigne, numColumn, sens, nbPointsJet));

        if (mot.length() == 7) {

            this.ajouteScore(50);
        }
    }


    /*
      pré-requis : sac peut contenir des entiers de 0 à 25
      action : simule l’échange de jetons de ce joueur :
      - saisie de la suite de lettres du chevalet à échanger en vérifiant que la suite soit correcte
      - échange de jetons entre le chevalet du joueur et le sac
      stratégie : appelle les méthodes estCorrectPourEchange et echangeJetonsAux
    */

    public void echangeJetons(MEE sac) {

        Ut.afficher("Saisissez les lettres à échanger :");

        String mot = Ut.saisirChaine();

        if (this.estCorrectPourEchange(mot)) {

            echangeJetonsAux(sac, mot);
        }
    }


    /*
      résultat : vrai ssi les caractères de mot correspondent tous à des
      lettres majuscules et l’ensemble de ces caractères est un
      sous-ensemble des jetons du chevalet de this
    */

    public boolean estCorrectPourEchange(String mot) {

        boolean soluce = true;
        int i = 0;

        while (soluce && i < mot.length() && Ut.estUneMajuscule(mot.charAt(i))) {

            if (this.chevalet.contientChar(mot.charAt(i))) {
                i++;
            } 
            
            else {

                soluce = false;
            }
        }

        return soluce;
    }


    /*
      pré-requis : sac peut contenir des entiers de 0 à 25 et
      ensJetons est un ensemble de jetons correct pour l’échange
      action : simule l’échange de jetons de ensJetons avec des
      jetons du sac tirés aléatoirement.
    */

    public void echangeJetonsAux(MEE sac, String ensJetons) {

        for (int i = 0; i < ensJetons.length(); i++) {

            int alea = Ut.majToIndex(ensJetons.charAt(i));
            // transfert aléatoire d'un jeton vers le chevalet

            sac.transfereAleat(this.chevalet, 1);
            // transfert spécifique d'un jeton du chevalet vers le sac
            // Cet ordre a été choisi pour ne pas retirer du sac la lettre
            // qu'on souhaite avoir échangé pour une autre

            this.chevalet.transfere(sac, alea);
        }

    }
    public String printChevalet(){

        String cadre ="| ";
        int[] obs = this.chevalet.getTabFreq();

        for(int i=0;i<obs.length;i++){

            if(obs[i]!=0){

                for(int j=1;j==obs[i];j++){

                    cadre+=Ut.indexToMaj(i)+" | ";
                }
            }
        }

        cadre+='\n';
        return(cadre);
    }

}