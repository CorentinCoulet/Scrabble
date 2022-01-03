public class Mot {
    private String enter;

    public void ask(){
        String input= Ut.saisirChaine();
        int i=0;
        boolean correct = true;
        while(correct && i<input.length()){
            correct= Ut.estUneMajuscule(input.charAt(i));
            i++;
        }

        if(correct){
            System.out.println("Mot enregistré avec succès!");
            this.enter = input;
        }

        else{
            System.out.println("Veuillez resaissir le mot en toutes lettres majuscules: ");
            ask();
        }
    }

  public String getMot() {
    return this.enter;
  }

}
