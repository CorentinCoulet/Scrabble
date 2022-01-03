public class MEE {

    private int[] tabFreq; //tabFreq[i] est le nombre d’exemplaires
                           // (fréquence) de l’élément i
    private int nbTotEx; // nombre total d’exemplaires 
    
    public int[] getTabFreq(){

        return this.tabFreq;
    }
    
    public int getCardinal(){

        return this.nbTotEx;
    }

   
    /*
      pré-requis : max >= 0
      action : crée un multi-ensemble vide dont les éléments seront
      inférieurs à max
    */

    public MEE(int max) {

        this.tabFreq = new int[max];
        this.nbTotEx = 0;
    }


    /*
      pré-requis : les éléments de tab sont positifs ou nuls
      action : crée un multi-ensemble dont le tableau de fréquences est une copie de tab 
      Notes : Je parcours le tableau
      en copiant les valeurs et verifie la frequence d'un element pour obtenir un
      cardinal pour l'ensemble copié
    */

    public MEE(int[] tab) {

        this(tab.length);
        for (int i = 0; i < tab.length; i++) {
            this.tabFreq[i] = tab[i];
            if (tab[i] != 0) {
                this.nbTotEx += tab[i];
            }
        }
    }


    //  La recopie du tableau entraine la copie du cardinal car il est intégré dans le constructeur

    public MEE(MEE e) {

        this(e.tabFreq);
    }


    // résultat : vrai si et seuleument si (ssi) cet ensemble est vide

    public boolean estVide() {

        return this.nbTotEx == 0;
    }


    /*
      pré-requis : 0 <= i < tabFreq.length
      action : ajoute un exemplaire de i à this
      cela entraine l'augmentation du total de 1 également
    */

    public void ajoute(int i) {

        this.tabFreq[i] += 1;
        this.nbTotEx++;
    }


    /*
      pré-requis : 0 <= i < tabFreq.length
      action/résultat : retire un exemplaire de i de this s’il en existe et diminue donc le total,
      et retourne vrai ssi cette action a pu être effectuée.
    */

    public boolean retire(int i) {

        boolean soluce;

        if (this.tabFreq[i] > 0) {

            this.tabFreq[i]--;
            this.nbTotEx--;
            soluce = true;

        } 
        
        else {

            soluce = false;
            
        }

        return (soluce);
    }


    /*
     pré-requis : this est non vide
     action/résultat : retire de this un exemplaire choisi aléatoirement
     et le retourne
    */

    public int retireAleat () {

        int exemplaire;

        do {
            exemplaire = Ut.randomMinMax(0,this.tabFreq.length-1);
        } while (this.tabFreq[exemplaire]==0);
        
        this.retire(exemplaire);
        return(exemplaire);
    }
    

    /*
     pré-requis : 0 <= i < tabFreq.length
     action/résultat : transfère un exemplaire de i de this vers e s’il
     en existe, et retourne vrai ssi cette action a pu être effectuée
    */

    public boolean transfere(MEE e, int i) {
           
        boolean soluce;

        if(this.tabFreq[i] > 0){

            this.retire(i);
            e.ajoute(i);  
            soluce = true;
               
        }
        
        else {
            soluce = false;
        }

        return(soluce);
    }


    /*
      pré-requis : k >= 0
      action : tranfère k exemplaires choisis aléatoirement de this vers e
      dans la limite du contenu de this
      résultat : le nombre d’exemplaires effectivement transférés
    */

    public int transfereAleat(MEE e, int k) {

        int nb=1;
        int exemplaire;
        int soluce=0;

        while(nb<=k){
            exemplaire=Ut.randomMinMax(0,(this.tabFreq.length-1));
            if(this.transfere(e, exemplaire)){
                soluce++;
            }
            nb++;
        }

        return(soluce);
    }


    /*
      pré-requis : tabFreq.length <= v.length
      résultat : retourne la somme des valeurs des exemplaires des
      éléments de this, la valeur d’un exemplaire d’un élément i
      de this étant égale à v[i]
    */

    public int sommeValeurs(int[] v) {

        int sommeVal = 0;

        for (int i = 0; i<this.tabFreq.length; i++){
            sommeVal += (tabFreq[i]*v[i]);
        }

        return sommeVal;
    }


    /* Pré-requis:
     c est une Letrre Majuscule
     @param c
     @return vrai ssi l'ensemble contient au moins 1 fois le charactère passé en paramètre
    */

    public boolean contientChar(char c){

        int i= Ut.majToIndex(c);
        boolean soluce;

        if(this.tabFreq[i]>0){
            
            soluce=true;
        }
        
        else {
            
            soluce=false;
        }

        return soluce;
    }

}
