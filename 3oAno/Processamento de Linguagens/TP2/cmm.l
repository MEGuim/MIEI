%{


#include "y.tab.h"
%}
letra       [a-zA-Z]
digito      [0-9]
indesejado  \t|\n

%option yylineno
%%

[\{\}\(\)\[\]\=\<\>\,\;\"\+\-\*\/\%\&\!\|]      {return yytext[0];}

"BEGINCMM"				                        {return BEGINCMM;}
"ENDCMM"				                        {return ENDCMM;}
"VARBEGIN"				                        {return VARBEGIN;}
"VAREND"				                        {return VAREND;}
"FUNBEGIN"				                        {return FUNBEGIN;}
"FUNEND"				                        {return FUNEND;}
"FUNC"					                        {return FUNC;}

"IF"					                        {return IF;}
"ELSE"					                        {return ELSE;}
"WHILE"					                        {return WHILE;}
"FOR"					                        {return FOR;}
"AND"											{return AND;}
"OR"											{return OR;}

"READ"					                        {return READ;}
"WRITE"					                        {return WRITE;}


{letra}({letra}|{digito})*                      {yylval.pal = strdup(yytext); return variavel;}
-?{digito}+ 				                    {yylval.val = atoi(yytext); return NUM;}
\"[^\"]+\"										{yylval.pal = strdup(yytext); return string;}

{indesejado}                                    {;}

%%

int yywrap() {
   return(1);
}
