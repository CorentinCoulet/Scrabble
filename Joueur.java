public class Joueur {
    private String nom;
    private MEE chevalet;
    private int score;

    public Joueur(String unNom) {
        this.chevalet = new MEE(26);
        this.score=0;
        this.nom = unNom;
    }

    public String toString() {
        return ("Joueur: " + this.nom + '\n' + "Score: " + this.score + '\n');
    }

    public int getScore() {
        return this.score;
    }

    public void ajouteScore(int nb) {
        this.score = this.score + nb;
    }

    /**
     * pré-requis : nbPointsJet indique le nombre de points rapportés par
     * chaque jeton/lettre
     * résultat : le nombre de points total sur le chevalet de ce joueur
     * suggestion : bien relire la classe MEE !
     */

    public int nbPointsChevalet(int[] nbPointsJet) {
        int scoreChev;
        if (this.chevalet.estVide()) {
            scoreChev = 0;
        } else {
            scoreChev = this.chevalet.sommeValeurs(nbPointsJet);
        }
        return (scoreChev);
    }

    /**
     * pré-requis : les éléments de s sont inférieurs à 26
     * action : simule la prise de nbJetons jetons par this dans le sac s,
     * dans la limite de son contenu.
     */

    public void prendJetons(MEE s, int nbJetons) {
      s.transfereAleat(this.chevalet, nbJetons);
    }

    /**
     * pré-requis : les éléments de s sont inférieurs à 26
     * et nbPointsJet.length >= 26
     * action : simule le coup de this : this choisit de passer son tour,
     * d’échanger des jetons ou de placer un mot
     * résultat : -1 si this a passé son tour, 1 si son chevalet est vide,
     * et 0 sinon
     */

    public int joue(Plateau p, MEE s, int[] nbPointsJet) {
        Ut.afficher(this.printChevalet());
        System.out.println("S pour passer / X pour échanger / P pour placer : ");
        int result=-2;
        char ui = Ut.saisirCaractere();
        switch (ui) {
            case 'S':
                result = -1;
                break;
            case 'X':
                this.echangeJetons(s);
                result = 0;
                break;
            case 'P':
                if(joueMot(p, s, nbPointsJet)){
                  if (this.chevalet.estVide()) {
                    result = 1;
                  }
                  else {
                    result = 0;
                  }
                }
                else{Ut.afficher("Impossible de placer le mot");
                  joueMot(p, s, nbPointsJet);
                }
                break;
                default:
                throw new IllegalStateException("Choix incorrect" + ui);

        }
        return result;
    }

    /**
     * pré-requis : les éléments de s sont inférieurs à 26
     * et nbPointsJet.length >= 26
     * action : simule le placement d’un mot de this :
     * a) le mot, sa position sur le plateau et sa direction, sont saisis
     * au clavier
     * b) vérifie si le mot est valide
     * c) si le coup est valide, le mot est placé sur le plateau
     * résultat : vrai ssi ce coup est valide, c’est-à-dire accepté par
     * CapeloDico et satisfaisant les règles détaillées plus haut
     * stratégie : utilise la méthode joueMotAux
     */

    public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet) {
        boolean result;
        Ut.afficher("Mot à placer :");
        Mot motIn = new Mot();
        motIn.ask();
        String mot = motIn.getMot();
        System.out.println("Saisissez le numéro de ligne de placement souhaité: ");
        int numLig = Ut.saisirEntier() - 1;
        System.out.println("Maintenant, Saissisez le numéro de Colonne de placement souhaité: ");
        int numCol = Ut.saisirEntier() - 1;
        System.out.println("Et enfin, le sens de placement, V pour vertical, H pour horizontal");
        char sens = Ut.saisirCaractere();
        if (p.placementValide(mot, numLig, numCol, sens, this.chevalet)) {
            joueMotAux(p, s, nbPointsJet, mot, numLig, numCol, sens);
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * pré-requis : cf. joueMot et le placement de mot à partir de la case
     * (numLig, numCol) dans le sens donné par sens est valide
     * action : simule le placement d’un mot de this
     *
     * //Les jetons sont placés sur le plateau, leurs exemplaires retirés du
     * chevalet
     * //Le score est calculé en multipliant score lettre*code couleur
     */

    public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, int numLig, int numCol, char sens) {
        p.place(mot, numLig, numCol, sens, this.chevalet);
        if (this.chevalet.getCardinal() < 7) {
            this.prendJetons(s, 7 - this.chevalet.getCardinal());
        }
        this.ajouteScore(p.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet));
        if (mot.length() == 7) {
            this.ajouteScore(50);
        }
    }

    /**
     * pré-requis : sac peut contenir des entiers de 0 à 25
     * action : simule l’échange de jetons de ce joueur :
     * - saisie de la suite de lettres du chevalet à échanger
     * en vérifiant que la suite soit correcte
     * - échange de jetons entre le chevalet du joueur et le sac
     * stratégie : appelle les méthodes estCorrectPourEchange et echangeJetonsAux
     */

    public void echangeJetons(MEE sac) {
        Ut.afficher("Quelles lettres souhaités vous échanger :");
        String mot = Ut.saisirChaine();
        if (this.estCorrectPourEchange(mot)) {
            echangeJetonsAux(sac, mot);
        }
    }

    /**
     * résultat : vrai ssi les caractères de mot correspondent tous à des
     * lettres majuscules et l’ensemble de ces caractères est un
     * sous-ensemble des jetons du chevalet de this
     */

    public boolean estCorrectPourEchange(String mot) {
        boolean result = true;
        int m = 0;
        while (result && m < mot.length() && Ut.estUneMajuscule(mot.charAt(m))) {
            if (this.chevalet.contientChar(mot.charAt(m))){
                m++;
            }
            else{
                result = false;
            }
        }
        return result;
    }

    /**
     * pré-requis : sac peut contenir des entiers de 0 à 25 et
     * ensJetons est un ensemble de jetons correct pour l’échange
     * action : simule l’échange de jetons de ensJetons avec des
     * jetons du sac tirés aléatoirement.
     */

    public void echangeJetonsAux(MEE sac, String ensJetons) {
        for (int l = 0; l < ensJetons.length(); l++) {
            int index = Ut.majToIndex(ensJetons.charAt(l));
            sac.transfereAleat(this.chevalet, 1);
            this.chevalet.transfere(sac, index);
        }

    }

}
