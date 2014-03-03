package alex.manual;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class AnalizadorLexicoTiny {

	private Reader input;
	private StringBuffer lex;
	private int sigCar;
	private int filaInicio;
	private int columnaInicio;
	private int filaActual;
	private int columnaActual;
	private Map<String, ClaseLexica> tokens;
	private static final String NL = System.getProperty("line.separator");

	private static enum Estado {
		INICIO, REC_ID, REC_MAS, REC_MENOS, REC_ENT, REC_PUNTOCOMA, REC_PAP, REC_PCIERR,
		REC_ASIG, REC_DIV, REC_MUL, REC_EOF, 
		REC_HALFNEQ, REC_NEQ, REC_HALF_SEP_SEC, REC_SEP_SEC,
		REC_GT, REC_EGT, REC_LT, REC_ELT, REC_EQ
	}

	private Estado estado;

	public AnalizadorLexicoTiny(Reader input) throws IOException {
		this.input = input;
		lex = new StringBuffer();
		sigCar = input.read();
		filaActual=1;
		columnaActual=1;
		this.tokens = new HashMap<String, ClaseLexica>();
		this.tokens.put("and", ClaseLexica.AND);
		this.tokens.put("or", ClaseLexica.OR);
		this.tokens.put("not", ClaseLexica.NOT);
		this.tokens.put("bool", ClaseLexica.BOOL);
		this.tokens.put("int", ClaseLexica.INT);
		this.tokens.put("true", ClaseLexica.TRUE);
		this.tokens.put("false", ClaseLexica.FALSE);
	}

	public UnidadLexica sigToken() throws IOException {
		estado = Estado.INICIO;
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		lex.delete(0,lex.length());
		while(true) {
			switch(estado) {
			case INICIO: 
				if(hayLetra())  transita(Estado.REC_ID);
				else if (hayDigitoPos()) transita(Estado.REC_ENT);
				else if (haySuma()) transita(Estado.REC_MAS);
				else if (hayResta()) transita(Estado.REC_MENOS);
				else if (hayMul()) transita(Estado.REC_MUL);
				else if (hayDiv()) transita(Estado.REC_DIV);
				else if (hayPAp()) transita(Estado.REC_PAP);
				else if (hayPCierre()) transita(Estado.REC_PCIERR);
				else if (hayAsig()) transita(Estado.REC_ASIG);
				else if (hayPuntoComa()) transita(Estado.REC_PUNTOCOMA);
				else if (haySepTabNL()) transitaIgnorando(Estado.INICIO);
				else if (hayEOF()) transita(Estado.REC_EOF);
				else if (hayHalfneq()) transita(Estado.REC_HALFNEQ);
				else if (hayHalfSepSec()) transita(Estado.REC_HALF_SEP_SEC);
				else if (hayGT()) transita(Estado.REC_GT);
				else if (hayLT()) transita(Estado.REC_LT);
				else error();
				break;
			case REC_ID: 
				if (hayLetra() || hayDigito()) transita(Estado.REC_ID);
				else {
					ClaseLexica clase = this.tokens.get(lex.toString());
					if(clase != null)
						return unidadToken(clase);
					else
						return unidadId();               
				}
				break;
			case REC_ENT:
				if (hayDigito()) transita(Estado.REC_ENT);
				else return unidadEnt();
				break;
			case REC_MAS:
				if (hayDigitoPos()) transita(Estado.REC_ENT);
				else return unidadMas();
				break;
			case REC_MENOS: 
				if (hayDigitoPos()) transita(Estado.REC_ENT);
				else return unidadMenos();
				break;
			case REC_MUL: return unidadPor();
			case REC_DIV: return unidadDiv();              
			case REC_PAP: return unidadPAp();
			case REC_PCIERR: return unidadPCierre();
			case REC_ASIG: 
				if(haySecondHalfEQ()) transita(Estado.REC_EQ);
				else return unidadAsig();
			case REC_EQ:
				return unidadEQ();	
			case REC_PUNTOCOMA: 
				return unidadPuntoComa();
			case REC_EOF: return unidadEof();
			case REC_HALFNEQ:
				if(haySecondhalfneq()) transita(Estado.REC_NEQ);
				break;
			case REC_HALF_SEP_SEC: 
				if(haySecondHalfSepSec()) transita(Estado.REC_SEP_SEC);
				break;
			case REC_NEQ:
				return unidadNEQ();
			case REC_SEP_SEC:
				return unidadSepSec();
			case REC_GT:
				if(haySecondHalfEGT()) transita(Estado.REC_EGT);
				else return unidadGT();
			case REC_EGT:
				return unidadEGT();
			case REC_LT:
				if(haySecondHalfLT()) transita(Estado.REC_ELT);
				else return unidadLT();
			case REC_ELT:
				return unidadELT();		
			}
		}    
	}
	private void transita(Estado sig) throws IOException {
		lex.append((char)sigCar);
		sigCar();         
		estado = sig;
	}
	private void transitaIgnorando(Estado sig) throws IOException {
		sigCar();         
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		estado = sig;
	}
	private void sigCar() throws IOException {
		sigCar = input.read();
		if (sigCar == NL.charAt(0)) saltaFinDeLinea();
		if (sigCar == '\n') {
			filaActual++;
			columnaActual=0;
		}
		else {
			columnaActual++;  
		}
	}
	private void saltaFinDeLinea() throws IOException {
		for (int i=1; i < NL.length(); i++) {
			sigCar = input.read();
			if (sigCar != NL.charAt(i)) error();
		}
		sigCar = '\n';
	}

	private boolean hayLetra() {return sigCar >= 'a' && sigCar <= 'z' ||
			sigCar >= 'A' && sigCar <= 'Z';}
	private boolean hayDigitoPos() {return sigCar >= '1' && sigCar <= '9';}
	private boolean hayCero() {return sigCar == '0';}
	private boolean hayDigito() {return hayDigitoPos() || hayCero();}
	private boolean haySuma() {return sigCar == '+';}
	private boolean hayResta() {return sigCar == '-';}
	private boolean hayMul() {return sigCar == '*';}
	private boolean hayDiv() {return sigCar == '/';}
	private boolean hayPAp() {return sigCar == '(';}
	private boolean hayPCierre() {return sigCar == ')';}
	private boolean hayAsig() {return sigCar == '=';}
	private boolean hayHalfneq() {return sigCar == '!';}
	private boolean haySecondhalfneq() {return sigCar == '=';}
	private boolean hayGT() {return sigCar == '>';}
	private boolean hayLT() {return sigCar == '<';}
	private boolean hayPuntoComa() {return sigCar == ';';}
	private boolean hayHalfSepSec() {return sigCar == '&';}
	private boolean haySecondHalfEQ() {return sigCar == '=';}
	private boolean haySecondHalfSepSec() {return sigCar == '&';}
	private boolean haySecondHalfEGT() {return sigCar == '=';}
	private boolean haySecondHalfLT() {return sigCar == '=';}
	private boolean haySepTabNL() {return sigCar == ' ' || sigCar == '\t' || sigCar=='\n';}
	private boolean hayEOF() {return sigCar == -1;}

	private UnidadLexica unidadId() {
		switch(lex.toString()) {
		case "=":  
			return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ASIG);
		default:    
			return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.IDEN,lex.toString());     
		}
	}  
	private UnidadLexica unidadToken(ClaseLexica clase){
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, clase);
	}
	private UnidadLexica unidadEnt() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.ENT,lex.toString());     
	}    
	private UnidadLexica unidadMas() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAS);     
	}    
	private UnidadLexica unidadMenos() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOS);     
	}    
	private UnidadLexica unidadPor() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.POR);     
	}    
	private UnidadLexica unidadDiv() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DIV);     
	}    
	private UnidadLexica unidadPAp() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PAP);     
	}    
	private UnidadLexica unidadPCierre() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PCIERRE);     
	}    
	private UnidadLexica unidadNEQ() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.NEQ);     
	}    
	private UnidadLexica unidadAsig() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ASIG);     
	}    
	private UnidadLexica unidadPuntoComa() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PUNTOCOMA);     
	}    
	private UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EOF);     
	}    
	private UnidadLexica unidadGT() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.GT);     
	}     
	private UnidadLexica unidadEQ() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EQ);     
	}   
	private UnidadLexica unidadSepSec() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.SEPSEC);     
	}     
	private UnidadLexica unidadEGT() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EGT);     
	}     
	private UnidadLexica unidadLT() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LT);     
	}     
	private UnidadLexica unidadELT() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ELT);     
	}     
	private void error() {
		System.err.println("("+filaActual+','+columnaActual+"):Caracter inexperado");  
		System.exit(1);
	}

	public static void main(String arg[]) throws IOException {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
		UnidadLexica unidad;
		do {
			unidad = al.sigToken();
			System.out.println(unidad);
		}
		while (unidad.clase() != ClaseLexica.EOF);
	} 
}