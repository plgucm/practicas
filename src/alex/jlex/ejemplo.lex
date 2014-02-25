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
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {digitoPositivo}{digito}*
separador = [ \t\r\b\n]
comentario = #[^\n]* 
eq = \=\=
neq = \!\=
gt = \>
egt = \>\=
lt = \<
elt = \<\=
sepsec = \&\&
identificador = {letra}({letra}|{digito})*
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
{comentario}              {}
{eq}                      {return ops.unidadEQ();}
{neq}                     {return ops.unidadNEQ();}
{gt}                      {return ops.unidadGT();}
{egt}                     {return ops.unidadEGT();}
{lt}                      {return ops.unidadLT();}
{elt}                     {return ops.unidadELT();}
{sepsec}                     {return ops.unidadSepSec();}
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