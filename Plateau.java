public class Plateau {

    private Case[][] g = new Case[15][15];


    /*
      Initialisateur du plateau, à partir de la matrice standard selon les règles
      du Scrabble
     */
    public Plateau() {
        int[][] plateau = { { 5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5 },
                            { 1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1 },
                            { 1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1 },
                            { 2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2 },
                            { 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1 },
                            { 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1 },
                            { 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1 },
                            { 5, 1, 1, 2, 1, 1, 1, 4, 1, 1, 1, 2, 1, 1, 5 },
                            { 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1 },
                            { 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1 },
                            { 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1 },
                            { 2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2 },
                            { 1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1 },
                            { 1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1 },
                            { 5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5 } 
        };

        for (int i = 0; i < plateau.length; i++) {

            for (int y = 0; y < plateau[0].length; y++) {

                this.g[i][y] = new Case(plateau[i][y]);
            }
        }
    }


    /*
      Constructeur de plateau à partir d'une grille passée en paramètre
    */

    public Plateau(Case[][] plateau) {

        this.g = plateau;
    }


    public String toString() {

        String plateauCurrent = "   |01 |02 |03 |04 |05 |06 |07 |08 |09 |10 |11 |12 |13 |14 |15 |" + '\n' + "-".repeat(64) + "\n";
        char colonne = 'A';

        for (int i = 0; i < g.length; i++) {

            plateauCurrent += (" " + colonne + " |");

            for (int y = 0; y < g[0].length; y++) {

                if (g[i][j].getCouleur() == 1 && !g[i][y].estRecouverte()) {

                    plateauCurrent += "   |";
                } 
                
                else {

                    plateauCurrent += " " + g[i][y].toString() + " |";
                }
            }

            plateauCurrent += '\n' + "-".repeat(64) + '\n';
            colonne++;
        }

        return (plateauCurrent);
    }


    public boolean dansChevalet(String mot, MEE e) {

        int[] terrain = e.getTabFreq();
        boolean soluce = true;
        int pos = 0;

        while (soluce && pos < mot.length()) {
            // Si la lettre du mot saisi a au moins 1 exemplaire

            if (terrain[Ut.majToIndex(mot.charAt(pos))] != 0) {

                pos++;
            } 
            
            else {

                soluce = false;
            }
        }

        return soluce;
    }
    

    /*
      Méthode de Classe permettant de placer un mot sur le plateau 
      @return Vrai ssi le placement de mot sur this à partir de la case(numLig,numCol)
      dans le sens à partir des jetons de e est valide.
    */

    public boolean placementValide(String mot, int numLigne, int numColumn, char sens, MEE e) {

        // conversion Entier Naturel Relatif aux coordonnées d'un plan.
        boolean soluce = false;
        int endZone; // Cordonnée X ou Y 

        Case casePreZone;
        Case caseAfterZone;

        boolean condCaseVide = false;
        int nbCaseVide = 0;

        boolean condCaseRemplie = false;
        int nbcaseRemplie = 0;

        boolean contrainteIntegrite = false;
        int indexLettreObservee = 0;

        boolean condCaseCentrale = false;
        int caseCentralePresente = 0;

        switch (sens) {

            case 'v':

                endZone = numLigne + mot.length() - 1;

                if (endZone + 1 > 14) {

                    caseAfterZone = null;
                } 
                
                else {

                    caseAfterZone = g[endZone + 1][numColumn];
                }

                if (endZone - 1 < 0) {

                    casePreZone = null;
                } 
                
                else {

                    casePreZone = g[endZone - 1][numColumn];
                }

                if (endZone <= 14) {

                    for (int i = numLigne; i <= endZone; i++) {

                        if (g[i][numColumn].estRecouverte() && g[i][numColumn].getLettre() == mot.charAt(indexLettreObservee)) {
                            
                            nbcaseRemplie++;
                            indexLettreObservee++;
                        } 
                        else {

                            nbCaseVide++;
                        }

                        if (g[i][numColumn] == g[7][7]) {
                            
                            caseCentralePresente++;
                        }
                    }
                }
                
                else {

                    System.out.println("---Débordement de plateau---");
                } 
                break;

            // Sur un mot vertical, les coordonées en X (numColumn) de ses lettres sont identiques.

            case 'h':

                endZone = numColumn + mot.length() - 1;

                if (endZone + 1 > 14) {

                    caseAfterZone = null;
                } 
                
                else {
                   
                    caseAfterZone = g[numLigne][numColumn + 1];
                }

                if (endZone - 1 < 0) {
                    
                    casePreZone = null;
                } 
                
                else {
                    
                    casePreZone = g[numLigne][numColumn - 1];
                }

                if (endZone <= 14) {
                    for (int j = numColumn; j <= endZone; j++) {
                        if (g[numLigne][j].estRecouverte() && g[numLigne][j].getLettre() == mot.charAt(indexLettreObservee)) { // contrainteIntegrite
                            
                            nbcaseRemplie++;
                            indexLettreObservee++;
                        }

                        else {

                            nbCaseVide++;
                        }

                        if (g[numLigne][j] == g[7][7]) {
                            
                            caseCentralePresente++;
                        }
                    }

                } 
                
                else {

                    System.out.println("---Débordement de plateau---");
                }
                break;

            default:

                throw new IllegalStateException("Sens incorrect" + sens);
        }

        condCaseCentrale = (caseCentralePresente > 0);
        condCaseRemplie = (nbcaseRemplie > 0);
        contrainteIntegrite = condCaseRemplie;
        condCaseVide = (nbCaseVide > 0);

        // Premier placement
        if (!this.g[7][7].estRecouverte() && mot.length() >= 2 && dansChevalet(mot, e) && condCaseCentrale) {
            
            soluce = true;
        } 
        
        else {
            // Placement du reste du jeu
            if (this.g[7][7].estRecouverte() && soluce == false && endZone <= 14 && endZone >= 0
                    && (casePreZone == null || !casePreZone.estRecouverte())
                    && (caseAfterZone == null || !caseAfterZone.estRecouverte())
                    && dansChevalet(mot, e) && contrainteIntegrite && condCaseVide && condCaseRemplie) {

                soluce = true;
            } 
            
            else {

                soluce = false;
            }
        }

        return soluce;
    }


    /*
      pré-requis : le placement de mot sur this à partir de la case
      (numLig, numCol) dans le sens donné par sens est valide
      résultat : retourne le nombre de points rapportés par ce placement, le
      nombre de points de chaque jeton étant donné par le tableau nbPointsJet.
    */

    public int nbPointsPlacement(String mot, int numLigne, int numColumn, char sens, int[] nbPointsJeton) {

        int totalPoints = 0;
        int multiMot = 1;

        switch (sens) {

            case 'v':

                for (int i = 0; i < mot.length(); i++) {

                    int indexPointsJet = Ut.majToIndex(mot.charAt(i));

                    if(g[numColumn][numLigne].getCouleur()==4 || g[numColumn][numLigne].getCouleur()==5) { 
                        //Mot compte Double Triple
                        
                        switch (g[numColumn][numLigne].getCouleur()){
                            
                            case 4:

                            totalPoints += nbPointsJeton[indexPointsJet] * 1;
                            multiMot = multiMot * 2;
                            break;

                            case 5:

                            totalPoints += nbPointsJeton[indexPointsJet] * 1;
                            multiMot = multiMot * 3;
                            break;

                        }

                    }

                    else {

                    totalPoints += nbPointsJeton[indexPointsJet] * g[numColumn][numLigne].getCouleur();
                    // valeur du score de la lettre * couleur sous la lettre
                    } 

                    numLigne++;
                }
                break;

            case 'h':

                for (int i = 0; i < mot.length(); i++) {

                    int indexPointsJet = Ut.majToIndex(mot.charAt(i));

                    if(g[numColumn][numLigne].getCouleur()==4 || g[numColumn][numLigne].getCouleur()==5) {

                        switch (g[numColumn][numLigne].getCouleur()){

                            case 4:

                            totalPoints += nbPointsJeton[indexPointsJet] * 1;
                            multiMot = multiMot * 2;
                            break;

                            case 5:

                            totalPoints += nbPointsJeton[indexPointsJet] * 1;
                            multiMot = multiMot * 3;
                            break;

                        }

                    }

                    else {

                    totalPoints += nbPointsJeton[indexPointsJet] * g[numColumn][numLigne].getCouleur();}
                    numColumn++;

                }
                break;
        }

        totalPoints = totalPoints * multiMot;
        return totalPoints;
    }


    /*
      pré-requis : le placement de mot sur this à partir de la case
      (numLigne, numColumn) dans le sens donné par sens à l’aide des jetons de e est valide.
      action/résultat : effectue ce placement et retourne le nombre de jetons retirés de e.
    */

    public int place(String mot, int numLigne, int numColumn, char sens, MEE e) {

        boolean condPlaceValide = placeValide(mot, numLigne, numColumn, sens, e);
        int soluce = 0;

        if (condPlaceValide) {

            switch (sens) {

                case 'v':

                    for (int i = 0; i < mot.length(); i++) {

                        g[numLigne][numColumn].setLettre(mot.charAt(i));

                        if (e.retire(Ut.majToIndex(mot.charAt(i)))) {

                            soluce++;
                        }

                        numLigne++;
                    }
                    break;

                case 'h':

                    for (int y = 0; y < mot.length(); y++) {

                        g[numLigne][numColumn].setLettre(mot.charAt(y));

                        if (e.retire(Ut.majToIndex(mot.charAt(y)))) {

                            soluce++;
                        }

                        numColumn++;
                    }
                    break;
            }
        }
        
        return soluce;
    }

}
