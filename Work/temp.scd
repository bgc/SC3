(s.boot;)//Startup

(s.reboot;)//restart

(s.makeGui;)//show Server Window

(s.scope;)//Show Scope

/*
SynthDef(\name, {
		arg out = 0, //Output
		ppS = 1, //Pulses Per Second
		amp = 0.5; //Amplitude
		var dusty; //Dust2 Generator
	}
).add;
*/

SynthDef("help-Dust2", {
	arg out=0;
	Out.ar(out,
		Dust2.ar(3, 0.5)
	)
}).add;

~snd4 = Synth("help-Dust2")
~snd4.free();


(
SynthDef("reson", {
	arg note = 220, dust0 = 2, dust1 = 5, dust2 = 1;
	(
		Out.ar(0,
		Resonz.ar(
				Dust2.ar(dust0),//in
				note,//freq
				0.0001,//bwr (0.001 tem ataque mais suave, 0.01 tem ataque imediato, 0.1 fica alto o ataque e fa clicks...
				500 //VOLUME???,
			)+
		Resonz.ar(Dust2.ar(dust1), note*2, 0.0001, 500)+
		Resonz.ar(Dust2.ar(dust2), note*4, 0.0001, 500))
	)* 3.reciprocal;

		(
		Out.ar(1,
		Resonz.ar(
				Dust2.ar(dust0),//in
				note,//freq
				0.0001,//bwr (0.001 tem ataque mais suave, 0.01 tem ataque imediato, 0.1 fica alto o ataque e faz clicks... (descobri que o 0.001 as vezes tem muitos clicks.. depende do dust2
				500 //VOLUME???,
			)+
		Resonz.ar(Dust2.ar(dust1), note*2, 0.0001, 500)+
		Resonz.ar(Dust2.ar(dust2), note*4, 0.0001, 500))
	)* 3.reciprocal;
}).add;
)
/*
calcular uma perfect fourth onde cent.value = 500 cents
Erg.value=Freq.value*Math.pow((Math.pow(2,1/1200)),Cent.value);

Podemos criar um dictionary com os cents para os intervalos e os nomes...

*/

~snd3 = Synth("reson",[\note, 330, \dust0, 1,\dust1, 2, \dust2, 1 ]);

~snd3.play();
~snd3.free();

~snd3.set(\note, ~notesFreq.at("D4"));

(
SynthDef("pulsestream", {
	arg whichOut = 0, //define Pan2 position
			amp = 0.5;		//define current pulse amplitude

	OffsetOut.ar(0,
		Pan2.ar(
			Impulse.ar(0),whichOut, amp)
	);
	FreeSelf.kr(Impulse.kr(0));
}).add;
)

(
~pls = fork {
	(1 / (1..100).scramble).do { |dt|
		Synth.grain("pulsestream",
			[ "whichOut": rrand(-1.0, 1.0),
				"amp": rrand(0.1, 1.0)
			]
		);
		dt.wait
	}
};
)

(
//TENAR PREENCHER AS OUTRAS NOTAS... A-1 e A9 A10....
~notesFreq = Dictionary[
	//Note to Frequency:
	"A0"	 ->	27.5000,
	"A#0"	 ->	29.1352,
	"B0"	->	30.8677,
	"C1"	->	32.7032,
	"C#1"	 ->	34.6478,
	"D1"	->	36.7081,
	"D#1"	 ->	38.8909,
	"E1"	->	41.2034,
	"F1"	->	43.6535,
	"F#1"	 ->	46.2493,
	"G1"	->	48.9994,
	"G#1"	 ->	51.9131,
	"A1"	->	55.0000,
	"A#1"	 ->	58.2705,
	"B1"	->	61.7354,
	"C2"	->	65.4064,
	"C#2"	 ->	69.2957,
	"D2"	->	73.4162,
	"D#2"	 ->	77.7817,
	"E2"	->	82.4069,
	"F2"	->	87.3071,
	"F#2"	 ->	92.4986,
	"G2"	->	97.9989,
	"G#2"	 ->	103.8262,
	"A2"	->	110.0000,
	"A#2"	 ->	116.5409,
	"B2"	->	123.4708,
	"C3"	->	130.8128,
	"C#3"	 ->	138.5913,
	"D3"	->	146.8324,
	"D#3"	 ->	155.5635,
	"E3"	->	164.8138,
	"F3"	->	174.6141,
	"F#3"	 ->	184.9972,
	"G3"	->	195.9977,
	"G#3"	 ->	207.6523,
	"A3"	->	220.0000,
	"A#3"	 ->	233.0819,
	"B3"	->	246.9417,
	"C4"	->	261.6256,
	"C#4"	 ->	277.1826,
	"D4"	->	293.6648,
	"D#4"	 ->	311.1270,
	"E4"	->	329.6276,
	"F4"	->	349.2282,
	"F#4"	 ->	369.9944,
	"G4"	->	391.9954,
	"G#4"	 ->	415.3047,
	"A4"	->	440.0000,
	"A#4"	 ->	466.1638,
	"B4"	->	493.8833,
	"C5"	->	523.2511,
	"C#5"	 ->	554.3653,
	"D5" ->	587.3295,
	"D#5"	 ->	622.2540,
	"E5" ->	659.2551,
	"F5" ->	698.4565,
	"F#5"	 ->	739.9888,
	"G5" ->	783.9909,
	"G#5"	 ->	830.6094,
	"A5" ->	880.0000,
	"A#5"	 ->	932.3275,
	"B5" ->	987.7666,
	"C6" ->	1046.5023,
	"C#6"	 ->	1108.7305,
	"D6" ->	1174.6591,
	"D#6"	 ->	1244.5079,
	"E6" ->	1318.5102,
	"F6" ->	1396.9129,
	"F#6"	 ->	1479.9777,
	"G6" ->	1567.9817,
	"G#6"	 ->	1661.2188,
	"A6" ->	1760.0000,
	"A#6"	 ->	1864.6550,
	"B6" ->	1975.5332,
	"C7" ->	2093.0045,
	"C#7"	 ->	2217.4610,
	"D7" ->	2349.3181,
	"D#7"	 ->	2489.0159,
	"E7" ->	2637.0205,
	"F7" ->	2793.8259,
	"F#7"	 ->	2959.9554,
	"G7" ->	3135.9635,
	"G#7"	 ->	3322.4376,
	"A7" ->	3520.0000,
	"A#7"	 ->	3729.3101,
	"B7" ->	3951.0664,
	"C8" ->	4186.0090
];

)

~notesFreq.at("A#7")





/**
SynthDef(\bru,{
	arg note = 64,
	amp1 = 1,
	amp2 = 1,
	amp3 = 1,
	bwr1 = 0.010,
	bwr2 = 0.010,
	bwr3 = 0.010;
	var res1,
	res2,
	res3,
	mixer;
	res1 = Resonz.ar(
		in: WhiteNoise.ar(0.5),
		freq: note.midicps,
		bwr: bwr1,
		mul: amp1
	);
	res2 = Resonz.ar(
		in: WhiteNoise.ar(0.5),
		freq: (note+12).midicps,
		bwr: bwr2,
		mul: amp2
	);
	res3 = Resonz.ar(
		in: WhiteNoise.ar(0.5),
		freq: (note+24).midicps,
		bwr: bwr3,
		mul: amp3
	);
	mixer = Mix.new([res1, res2, res3]);
	Out.ar(0, mixer);
}).add;
p = Synth(\bru);
p.free;
 */
