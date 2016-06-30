  /* Kommentieren Sie bitte vor jedem Test, was der Test
  *  tun soll und weshalb Sie ihn geschrieben haben. 
  *  Das beiliegende Testskript kompiliert die Tests und fuehrt sie aus. 
  * 
  * Tipp: Vergleichen Sie mit assertEquals keine DNA Objekte. Das koennen Sie
  * noch nicht. Sie koennen aber Strings vergleichen, also auch den
  * Stringanteil eines DNA Objekts. 
  */

import static org.junit.Assert.*;
import org.junit.*;

public class DNATest{

	/* Prüfen ob A zu T wird und umgekehrt (da sie jeweils das Komplement von einander sind)*/
	@Test
	public void AzuTundTzuA(){
	  DNA D=new DNA("TATA");
	  DNA C=D.complementWC();
	  assertEquals("ATAT",C.dna);
	}
	/* Prüfen ob G zu C wird und umgekehrt (da sie jeweils das Komplement von einander sind)*/
	@Test
	public void GzuCundCzuG(){
	  DNA D=new DNA("GCGC");
	  DNA C=D.complementWC();
	  assertEquals("CGCG",C.dna);
	}
	/* Prüfen ob beides gleichzeitig funktioniert*/
	@Test
	public void AllesZumJeweiligenKomplement(){
	  DNA D=new DNA("ATGC");
	  DNA C=D.complementWC();
	  assertEquals("TACG",C.dna);
	}
	/* Prüfen bei leerer DNA*/
	@Test
	public void LeerZuLeer(){
	  DNA D=new DNA("");
	  DNA C=D.complementWC();
	  assertEquals("",C.dna);
	}
	/*Prüfen bei falscher DNA*/
	@Test
	public void FalschZuLeer(){
	  DNA D=new DNA("ZZZ");
	  DNA C=D.complementWC();
	  assertEquals("",C.dna);
	}
	/*PalindromWC true*/
	@Test
	public void PWCtrue(){
	  DNA D=new DNA("GCATATGC");
	  assertEquals(true,D.palindromeWC());
	}
	/*PalindromWC false*/
	@Test
	public void PWCfalse(){
	  DNA D=new DNA("ATGCATGC");
	  assertEquals(false,D.palindromeWC());
	}
	// TEST PRÜFEN FUNKTIONEN MIT ERGEBNIS/FUNKTIONALITÄT WIE IN DER BESCHREIBUNG VON A2
}
