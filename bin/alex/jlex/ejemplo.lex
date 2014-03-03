package alex.jlex;

%%
%line
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode

%{
  private ALexOperations ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digito = [0-9]
parteEntera = {digito}+
separador = [ \t\r\b\n]
eq = \=\=
neq = \!\=
gt = \>
egt = \>\=
lt = \<
elt = \<\=
and = and
or = or
not = not
bool = bool
int = int
true = true
false = false
sepsec = \&\&
identificador = {letra}({letra}|{digito}|_)*
numeroEntero = [\+,\-]?{parteEntera}
operadorMas = \+
operadorMenos = \-
operadorPor = \*
operadorDivision = /
parentesisApertura = \(
parentesisCierre = \)
asig = \=
puntocoma  = \;
%%
{separador}               {}
{eq}                      {return ops.unidadEQ();}
{neq}                     {return ops.unidadNEQ();}
{gt}                      {return ops.unidadGT();}
{egt}                     {return ops.unidadEGT();}
{lt}                      {return ops.unidadLT();}
{elt}                     {return ops.unidadELT();}
{and}                     {return ops.unidadAnd();}
{or}                     {return ops.unidadOr();}
{not}                     {return ops.unidadNot();}
{bool}                     {return ops.unidadBool();}
{int}                     {return ops.unidadInt();}
{true}                     {return ops.unidadTrue();}
{false}                     {return ops.unidadFalse();}
{sepsec}                  {return ops.unidadSepSec();}
{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{operadorMas}             {return ops.unidadMas();}
{operadorMenos}           {return ops.unidadMenos();}
{operadorPor}  			  {return ops.unidadPor();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{asig}                    {return ops.unidadAsig();} 
{puntocoma}               {return ops.unidadPuntoComa();}
[^]                       {ops.error();}  