public class Case {
        private char lettre;
        private boolean recouverte;
        private int couleur;

        /*
        pré-requis : uneCouleur est un entier entre 1 et 5
        action : constructeur de Case
        */
    public Case(int Couleur){
      this.lettre=0;
      this.recouverte=false;
      this.couleur=Couleur;
    }

    /* résultat : la couleur de this, un nombre entre 1 et 5 */
    public int getCouleur(){
        return(this.couleur);}

    /* pré-requis : cette case est recouverte */
    public char getLettre(){
        return this.lettre;}

    /* pré-requis : let est une lettre majuscule */
    public void setLettre(char letter){
        this.lettre=letter;
        this.recouverte=true;
    }

    /* résultat : vrai ssi la case est recouverte par une lettre */
    public boolean estRecouverte(){
        boolean result;
        if(this.recouverte && this.lettre != 0){
            result=true;
        }
        else{result=false;
        }
        return(result);
    }
    
    public String toString(){
        String result="";
        if(this.estRecouverte()){
           result = result + this.getLettre();
        }
        else{result = result + this.getCouleur();
        }
        return(result);
    }
}
