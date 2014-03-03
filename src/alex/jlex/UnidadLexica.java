package alex.jlex;

public abstract class UnidadLexica {
   private ClaseLexica clase;
   private int fila;
   public UnidadLexica(int fila, ClaseLexica clase) {
     this.fila = fila;
     this.clase = clase;
   }
   public ClaseLexica clase () {return clase;}
   public abstract String lexema();
   public int fila() {return fila;}
}
