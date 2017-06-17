#include "instrumentos.h"

int mapChords(char *p){

  /** Piano Chords **/

  if(strcmp("acoustic grand",p) == 0) return 0;
  if(strcmp("bright acoustic",p) == 0) return 1;
  if(strcmp("electric grand",p) == 0) return 2;
  if(strcmp("honky-tonk",p) == 0) return 3;
  if(strcmp("eletric piano 1",p) == 0) return 4;
  if(strcmp("eletric piano 2",p) == 0) return 5;
  if(strcmp("harpsichord",p) == 0) return 6;
  if(strcmp("clavinet",p) == 0) return 7;

  /** Chromatic Percussion **/

  if(strcmp("celesta",p) == 0) return 8;
  if(strcmp("glockenspiel",p) == 0) return 9;
  if(strcmp("music box",p) == 0) return 10;
  if(strcmp("vibraphone",p) == 0) return 11;
  if(strcmp("marimba",p) == 0) return 12;
  if(strcmp("xylophone",p) == 0) return 13;
  if(strcmp("tubular bells",p) == 0) return 14;
  if(strcmp("dulcimer",p) == 0) return 15;

  /** Organ **/

  if(strcmp("drawbar organ",p) == 0) return 16;
  if(strcmp("percussive organ",p) == 0) return 17;
  if(strcmp("rock organ",p) == 0) return 18;
  if(strcmp("church organ",p) == 0) return 19;
  if(strcmp("reed organ",p) == 0) return 20;
  if(strcmp("accordion",p) == 0) return 21;
  if(strcmp("harmonica",p) == 0) return 22;
  if(strcmp("tango accordion",p) == 0) return 23;

  /** Guitar **/

  if(strcmp("nylon string guitar",p) == 0) return 24;
  if(strcmp("steel string guitar",p) == 0) return 25;
  if(strcmp("electric jazz guitar",p) == 0) return 26;
  if(strcmp("electric clean guitar",p) == 0) return 27;
  if(strcmp("electric muted guitar",p) == 0) return 28;
  if(strcmp("overdriven guitar",p) == 0) return 29;
  if(strcmp("distortion guitar",p) == 0) return 30;
  if(strcmp("guitar harmonics",p) == 0) return 31;

  /** Bass **/

  if(strcmp("acoustic bass",p) == 0) return 32;
  if(strcmp("electric bass(finger)",p) == 0) return 33;
  if(strcmp("electric bass(pick)",p) == 0) return 34;
  if(strcmp("fretless bass",p) == 0) return 35;
  if(strcmp("slap bass 1",p) == 0) return 36;
  if(strcmp("slap bass 2",p) == 0) return 37;
  if(strcmp("synth bass 1",p) == 0) return 38;
  if(strcmp("synth bass 2",p) == 0) return 39;

  /** Solo Strings **/

  if(strcmp("violin",p) == 0) return 40;
  if(strcmp("viola",p) == 0) return 41;
  if(strcmp("cello",p) == 0) return 42;
  if(strcmp("contrabass",p) == 0) return 43;
  if(strcmp("tremolo strings",p) == 0) return 44;
  if(strcmp("pizzicato strings",p) == 0) return 45;
  if(strcmp("orchestral strings",p) == 0) return 46;
  if(strcmp("timpani",p) == 0) return 47;

  /** Ensemble **/

  if(strcmp("string ensemble 1",p) == 0) return 48;
  if(strcmp("string ensemble 2",p) == 0) return 49;
  if(strcmp("synthstrings 1",p) == 0) return 50;
  if(strcmp("synthstrings 2",p) == 0) return 51;
  if(strcmp("choir aahs",p) == 0) return 52;
  if(strcmp("voice oohs",p) == 0) return 53;
  if(strcmp("synth voice",p) == 0) return 54;
  if(strcmp("orchestra hit",p) == 0) return 55;

  /** Brass **/

  if(strcmp("trumpet",p) == 0) return 56;
  if(strcmp("trombone",p) == 0) return 57;
  if(strcmp("tuba",p) == 0) return 58;
  if(strcmp("muted trumpet",p) == 0) return 59;
  if(strcmp("french horn",p) == 0) return 60;
  if(strcmp("brass section",p) == 0) return 61;
  if(strcmp("synthbrass 1",p) == 0) return 62;
  if(strcmp("synthbrass 2",p) == 0) return 63;

  /** Reed **/

  if(strcmp("soprano sax",p) == 0) return 64;
  if(strcmp("alto sax",p) == 0) return 65;
  if(strcmp("tenor sax",p) == 0) return 66;
  if(strcmp("baritone sax",p) == 0) return 67;
  if(strcmp("oboe",p) == 0) return 68;
  if(strcmp("english horn",p) == 0) return 69;
  if(strcmp("bassoon",p) == 0) return 70;
  if(strcmp("clarinet",p) == 0) return 71;

  /** Pipe **/

  if(strcmp("piccolo",p) == 0) return 72;
  if(strcmp("flute",p) == 0) return 73;
  if(strcmp("recorder",p) == 0) return 74;
  if(strcmp("pan flute",p) == 0) return 75;
  if(strcmp("blown bottle",p) == 0) return 76;
  if(strcmp("skakuhachi",p) == 0) return 77;
  if(strcmp("whistle",p) == 0) return 78;
  if(strcmp("ocarina",p) == 0) return 79;

  /** Synth Lead **/

  if(strcmp("lead 1 (square)",p) == 0) return 80;
  if(strcmp("lead 2 (sawtooth)",p) == 0) return 81;
  if(strcmp("lead 3 (calliope)",p) == 0) return 82;
  if(strcmp("lead 4 (chiff)",p) == 0)  return 83;
  if(strcmp("lead 5 (charang)",p) == 0) return 84;
  if(strcmp("lead 6 (voice)",p) == 0) return 85;
  if(strcmp("lead 7 (fifths)",p) == 0) return 86;
  if(strcmp("lead 8 (bass+lead)",p) == 0) return 87;

  /** Synth Pad **/

  if(strcmp("pad 1 (new age)",p) == 0) return 88;
  if(strcmp("pad 2 (warm)",p) == 0) return 89;
  if(strcmp("pad 3 (polysynth)",p) == 0) return 90;
  if(strcmp("pad 4 (choir)",p) == 0) return 91;
  if(strcmp("pad 5 (bowed)",p) == 0) return 92;
  if(strcmp("pad 6 (metallic)",p) == 0) return 93;
  if(strcmp("pad 7 (halo)",p) == 0) return 94;
  if(strcmp("pad 8 (sweep)",p) == 0) return 95;

  /** Synth Effects **/

  if(strcmp("fx 1 (rain)",p) == 0) return 96;
  if(strcmp("fx 2 (soundtrack)",p) == 0) return 97;
  if(strcmp("fx 3 (crystal)",p) == 0) return 98;
  if(strcmp("fx 4 (atmosphere)",p) == 0) return 99;
  if(strcmp("fx 5 (brightness)",p) == 0) return 100;
  if(strcmp("fx 6 (goblins)",p) == 0) return 101;
  if(strcmp("fx 7 (echoes)",p) == 0) return 102;
  if(strcmp("fx 8 (sci-fi)",p) == 0) return 103;

  /** Ethnic **/

  if(strcmp("sitar",p) == 0) return 104;
  if(strcmp("banjo",p) == 0) return 105;
  if(strcmp("samisen",p) == 0) return 106;
  if(strcmp("koto",p) == 0) return 107;
  if(strcmp("kalimba",p) == 0) return 108;
  if(strcmp("bagpipe",p) == 0) return 109;
  if(strcmp("fiddle",p) == 0) return 110;
  if(strcmp("shanai",p) == 0) return 111;

  /** Percussive **/

  if(strcmp("tinkle bell",p) == 0) return 112;
  if(strcmp("agogo",p) == 0) return 113;
  if(strcmp("steel drums",p) == 0) return 114;
  if(strcmp("woodblock",p) == 0) return 115;
  if(strcmp("taiko drum",p) == 0) return 116;
  if(strcmp("melodic drum",p) == 0) return 117;
  if(strcmp("synth drum",p) == 0) return 118;
  if(strcmp("reverse cymbal",p) == 0) return 119;

  /** Sounds Effects **/

  if(strcmp("guitar fret noise",p) == 0) return 120;
  if(strcmp("breath noise",p) == 0) return 121;
  if(strcmp("seashore",p) == 0) return 122;
  if(strcmp("bird tweet",p) == 0) return 123;
  if(strcmp("telephone ring",p) == 0) return 124;
  if(strcmp("helicopter",p) == 0) return 125;
  if(strcmp("applause",p) == 0) return 126;
  if(strcmp("gunshot",p) == 0) return 127;


  return -1;

}


int mapDrums(char *p){
  if(strcmp("acoustic bass drum",p) == 0) return 35;
  if(strcmp("bass drum",p) == 0) return 36;
  if(strcmp("side stick",p) == 0) return 37;
  if(strcmp("acoustic snare",p) == 0) return 38;
  if(strcmp("hand clap",p) == 0) return 39;
  if(strcmp("electric snare",p) == 0) return 40;
  if(strcmp("low floor tom",p) == 0) return 41;
  if(strcmp("closed hi hat",p) == 0) return 42;
  if(strcmp("high floor tom",p) == 0) return 43;
  if(strcmp("pedal hi-hat",p) == 0) return 44;
  if(strcmp("low tom",p) == 0) return 45;
  if(strcmp("open hi-hat",p) == 0) return 46;
  if(strcmp("low-mid tom",p) == 0) return 47;
  if(strcmp("hi mid tom",p) == 0) return 48;
  if(strcmp("crash cymbal 1",p) == 0) return 49;
  if(strcmp("high tom",p) == 0) return 50;
  if(strcmp("ride cymbal 1",p) == 0) return 51;
  if(strcmp("chinese cymbal",p) == 0) return 52;
  if(strcmp("ride bell",p) == 0) return 53;
  if(strcmp("tambourine",p) == 0) return 54;
  if(strcmp("splash cymbal",p) == 0) return 55;
  if(strcmp("cowbell",p) == 0) return 56;
  if(strcmp("crash cymbal 2",p) == 0) return 57;
  if(strcmp("vibraslap",p) == 0) return 58;
  if(strcmp("ride cymbal 2",p) == 0) return 59;
  if(strcmp("hi bongo",p) == 0) return 60;
  if(strcmp("low bongo",p) == 0) return 61;
  if(strcmp("mute hi conga",p) == 0) return 62;
  if(strcmp("open hi conga",p) == 0) return 63;
  if(strcmp("low conga",p) == 0) return 64;
  if(strcmp("high timbale",p) == 0) return 65;
  if(strcmp("low timbale",p) == 0) return 66;
  if(strcmp("high agogo",p) == 0) return 67;
  if(strcmp("low agogo",p) == 0) return 68;
  if(strcmp("cabasa",p) == 0) return 69;
  if(strcmp("maracas",p) == 0) return 70;
  if(strcmp("short whistle",p) == 0) return 71;
  if(strcmp("long whistle",p) == 0) return 72;
  if(strcmp("short guiro",p) == 0) return 73;
  if(strcmp("long guiro",p) == 0) return 74;
  if(strcmp("claves",p) == 0) return 75;
  if(strcmp("hi wood block",p) == 0) return 76;
  if(strcmp("low wood block",p) == 0) return 77;
  if(strcmp("mute cuica",p) == 0) return 78;
  if(strcmp("open cuica",p) == 0) return 79;
  if(strcmp("mute triangle",p) == 0) return 80;
  if(strcmp("open triangle",p) == 0) return 81;

  return -1;
}
