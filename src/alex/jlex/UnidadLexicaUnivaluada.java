package alex.jlex;

public class UnidadLexicaUnivaluada extends UnidadLexica {
   public String lexema() {throw new UnsupportedOperationException();}   
   public UnidadLexicaUnivaluada(int fila, ClaseLexica clase) {
     super(fila,clase);  
   }
  public String toString() {
    return "[clase:"+clase()+",fila:"+fila()+",col:"+columna()+"]";  
  }   
}
