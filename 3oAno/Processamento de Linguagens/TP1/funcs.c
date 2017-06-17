#include "funcs.h"



char* processaNotes(char* notes, int tempo) {
	char aux[200];
	char *res1, *res2;
	char nota[10];
	char notaAnt[10];
	int nr_notas=0;
	int t_notas=0;
	int i, rep_nota=1;

	char *p,*next;
	strcpy(aux,notes);
	strtok(aux, "|\0");

	p=strtok(aux," \t");
	strcpy(nota,p);
	strcpy(notaAnt,nota);
	nr_notas++;

	asprintf(&res1,"\"%s\"",nota);

	while(p){
		p=strtok(NULL," ");
		if(p!=NULL){
			strcpy(nota,p);
			nr_notas++;
			if(strcmp(nota,".")==0){
				strcpy(nota, notaAnt);
			}
			else(strcpy(notaAnt,nota));
			asprintf(&res1, "%s \"%s\"", res1,nota);
		}
	}
	t_notas=tempo/nr_notas;

	strcpy(aux,res1);
	p=strtok(aux, " \t");
	asprintf(&res2, "%sz",p);
	for(i=1;i<=nr_notas;i++){
		next = strtok(NULL, " \t");
		if(next==NULL){
			if(rep_nota>1 || (t_notas>1)){
				asprintf(&res2, "%s%d", res2,(rep_nota*t_notas));
			}
		}
		else{
			if(strcmp(p,next)==0){
				rep_nota++;
			}
			else{
				if(rep_nota>1 || t_notas>1){
					asprintf(&res2, "%s%d %sz", res2,(rep_nota*t_notas), next);
					rep_nota = 1;
				}
				else asprintf(&res2, "%s %sz", res2,next);
				p=next;
			}
		}

	}
	return res2;
}

char* processaChords(char* chords) {
	int inst;
	char* res;

	inst = mapChords(chords);

	if (inst != -1) {
		asprintf(&res, "%%%%MIDI chordprog %d\t\t\t%% instrumento: %s\n", inst, chords);
	}

	return res;
}

char* processaDrums(char* drums) {
	int inst;
	char* res;

	inst = mapDrums(drums);

	if (inst != -1) {
		asprintf(&res, "%%%%MIDI drum %d\t\t\t%% instrumento de percurssão: %s\n", inst, drums);
	}

	return res;
}

char* processaGChord(char* gchord) {
	char* res;

	asprintf(&res, "%%%%MIDI gchord\t%s\t\t\t%% modo de acompanhamento\n", gchord);

	return res;
}
