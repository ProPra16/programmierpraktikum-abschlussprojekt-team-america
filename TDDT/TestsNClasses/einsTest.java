import static org.junit.Assert.*;
import org.junit.*;

public class HochdreiTest{

	@Test
	public void testgimmeHochdrei(){
	int n=8;
	int m=Hochdrei.gimmeHochdrei(2);
	assertEquals(n,m);
	}
	@Test
	public void tegimmeHochdrei(){
	int n=7;
	int m=Hochdrei.gimmeHochdrei(2);
	assertEquals(n,m);
	}
}