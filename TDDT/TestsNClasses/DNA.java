/* 
 * Gegeben ist die aus der Uebung bekannte Klasse und die zu ergaenzenden 
 * Funktionen. Es sind bereits Rueckgabewerte gegeben, damit die Tests
 * auch dann kompilieren, wenn Sie diese Aufgabe noch nicht geloest haben.
 * Sie koennen diese Rueckgabewerte natuerlich aendern.
 * 
 */
 
public class DNA{

    public String validDna = "ACTG";
    public String dna;

    public DNA complementWC() {
        char[] b=dna.toCharArray();
	for(int i=0;i<dna.length();i++){
	  if(dna.charAt(i)=='A'){b[i]='T';}
	  else if(dna.charAt(i)=='T'){b[i]='A';}
	  else if(dna.charAt(i)=='G'){b[i]='C';}
	  else {b[i]='G';}
	}
	String a=new String(b);
	return new DNA(a);
    }

    public boolean palindromeWC() {
	char[] b=dna.toCharArray();
	for(int i=0;i<dna.length();i++){
	  b[i]=dna.charAt(dna.length()-1-i);
	}
	String a=new String(b);
	for(int j=0;j<dna.length();j++){
	  if(a.charAt(j)!=this.complementWC().dna.charAt(j)){   //a==this.compWC().dna hat
	    return false;                                       // nicht funktioniert
	  }
	}
	return true;
    }
public static void main(String[] args){
	DNA kek=new DNA("GCATATGC");
	System.out.println(kek.palindromeWC());
}
/* Ab hier nicht mehr aendern! */

    public boolean check(String dna) {
        for(int i = 0; i < dna.length(); i++) {
            if ( validDna.indexOf(dna.charAt(i)) == -1 ){
                return false;
            }
        }
        return true;
    }

    public DNA(String dna){
        if (check(dna)) this.dna = dna;
        else this.dna = "";
    }
} 
