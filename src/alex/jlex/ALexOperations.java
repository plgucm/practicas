package alex.jlex;

public class ALexOperations {
	private AnalizadorLexicoTiny alex;
	public ALexOperations(AnalizadorLexicoTiny alex) {
		this.alex = alex;   
	}
	public UnidadLexica unidadId() {
		return new UnidadLexicaMultivaluada(alex.fila(),ClaseLexica.IDEN,
				alex.lexema()); 
	} 
	public UnidadLexica unidadEnt() {
		return new UnidadLexicaMultivaluada(alex.fila(),ClaseLexica.ENT,alex.lexema()); 
	}
	public UnidadLexica unidadMas() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.MAS); 
	} 
	public UnidadLexica unidadMenos() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.MENOS); 
	} 
	public UnidadLexica unidadPor() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.POR); 
	} 
	public UnidadLexica unidadDiv() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.DIV); 
	} 
	public UnidadLexica unidadPAp() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.PAP); 
	} 
	public UnidadLexica unidadPCierre() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.PCIERRE); 
	} 
	public UnidadLexica unidadAsig() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.ASIG); 
	} 
	public UnidadLexica unidadPuntoComa() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.PUNTOCOMA); 
	} 
	public UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.EOF); 
	}    
	public UnidadLexica unidadEQ() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.NEQ);     
	}    
	public UnidadLexica unidadNEQ() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.EQ);     
	}    
	public UnidadLexica unidadGT() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.GT);     
	}    
	public UnidadLexica unidadEGT() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.EGT);     
	}    
	public UnidadLexica unidadLT() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.LT);     
	}    
	public UnidadLexica unidadELT() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.ELT);     
	}    
	public UnidadLexica unidadBool() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.BOOL);     
	}   
	public UnidadLexica unidadInt() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.INT);     
	}     
	public UnidadLexica unidadAnd() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.AND);     
	}     
	public UnidadLexica unidadOr() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.OR);     
	}    
	public UnidadLexica unidadNot() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.NOT);     
	}  
	public UnidadLexica unidadTrue() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.TRUE);     
	}   
	public UnidadLexica unidadFalse() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.FALSE);     
	}  
	public UnidadLexica unidadSepSec() {
		return new UnidadLexicaUnivaluada(alex.fila(),ClaseLexica.SEPSEC);     
	}
	public void error() {
		System.err.println("***"+alex.fila()+" Caracter inexperado: "+alex.lexema());
	}
}
