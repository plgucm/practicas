package alex.manual;

/*
DEFINICION DE CLASES LÉXICAS
*/
public enum ClaseLexica {
	/*CLs de comparadores != == > >= < <=*/
	NEQ, EQ, GT, EGT, LT, ELT,
	/*CL Indentificador*/
	IDEN,
	/*CL Entero*/
	ENT,
	/*CLs de Parentesis de Apertura y Cierre*/
	PAP, PCIERRE, 
	/*CL Punto  y Coma ;*/
	PUNTOCOMA, 
	/*CLs de Operaciones + - * / */
	MAS, MENOS, POR, DIV, 
	/*CL de asignación = */
	ASIG, 
	/*CL Separador de Secciones && */
	SEPSEC, 
	/*CLs de Operaciones Booleanas*/
	AND, OR, NOT, 
	/*CLs de Nombres de Clases*/
	BOOL, INT,
	/*CLs de Valores Booleanos*/
	TRUE, FALSE,
	/*CL Fin de Fichero*/
	EOF 
}
