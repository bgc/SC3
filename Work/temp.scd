(
Server.default = Server.local;
s = Server.default;
s.boot;
)

(s.boot;)//Startup

(s.reboot;)//restart


(s.makeGui;)//show Server Window

(s.scope;)//Show Scope
(s.plotTree;)
(s.freqscope)
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
				0.0001,//bwr (0.001 tem ataque mais suave, 0.01 tem ataque imediato, 0.1 fica alto o ataque e faz clicks...
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


(

SynthDef(\wnoise, {
	arg freq = 110,
	rq = 0.05,
	vl = 1, amp = 0.5,
	pan = 0;
	Out.ar(0,Pan2.ar(
		BPF.ar(WhiteNoise.ar(VarLag.kr(amp, vl, 7), 0),VarLag.kr(freq, vl, 5), VarLag.kr(rq, vl, 5),1,0), VarLag.kr(pan, vl, 7), 1)
	)
}).add;

)

~snd7 = Synth(\wnoise);

~snd7.set(\freq, 380);

~snd7.set(\vl, 0.1);

~snd7.set(\rq, 0.003);

~snd7.set(\pan, 0);

~snd7.set(\amp, 1);





s.boot;
(
SynthDef("help-Synth-get", { arg freq = 440;
    Out.ar(0, SinOsc.ar(freq, 0, 0.1));
}).add;
)
x = Synth("help-Synth-get");
x.set(\freq, 220 + 440.rand);

({
	var a;
	x.get(\freq, { arg value; ("freq is now:" + value + "Hz").postln; a = value; a.value().postln; a.value().postln; a.postln; });

}.value)
	x.free;




/*
calcular uma perfect fourth onde cent.value = 500 cents
Erg.value=Freq.value*Math.pow((Math.pow(2,1/1200)),Cent.value);

Podemos criar um dictionary com os cents para os intervalos e os nomes...

*/

~snd3 = Synth("reson",[\note, 330, \dust0, 1,\dust1, 2, \dust2, 1 ]);

~snd3.play();
~snd3.free();

~snd3.set(\note, ~notesFreq.at("C4"));


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

/*

TEM BUG!

(
SynthDef("test", {
	var sig = Impulse.ar(1);
	var count = 0;
	Select.kr(sig > 0, [{"fostes".postln;count = count+1; count.postln;}, {0}]);

//	if(sig > 0, { count = count+1; count.postln}, { false; });

	Out.ar(0,
		Impulse.ar(1);
	);
}).add;
)

~snd5 = Synth("test");

~snd5.free();
*/


/*
500.ratiomidi;
(4/3).ratiomidi.ceil;
*/

(
var f = {
	arg freq = 0, octave = 1;
	if(freq > 0 ,
		{
			freq*(2.pow(octave));
		},
		{
			0;
		}
	);
};

f.value(440,2);
)


(
SynthDef(\twoPulses, {

	arg pan = 0.0,
			amp = 1.0,
			mix = 0.75,
			room = 0.95,
			damp = 0.25,
			release_dur = 5.0,
			gate = 1;
	var env, sound;
	env = EnvGen.kr(Env.asr(0.01, amp, release_dur), gate, doneAction:2);
	sound = FreeVerb.ar(
		Pan2.ar(
			Impulse.ar(0), pan, amp
		),
		mix,
		room,
		damp
	);

	Out.ar(0,
		sound * env
	);



}).add;

)

(

t = Task({
			var b, a;
		b =  Synth.new(\twoPulses,
			[\release_dur, 0.5,
			\amp, 1,
			\gate, 1,
			\mix, 0,
			\pan, rrand(-1.0, 1.0)]
		);
		5.wait;
		b.set(\gate, 0);
		a =  Synth.new(\twoPulses,
			[\release_dur, 5,
			\amp, 1,
			\gate, 1, \pan, rrand(-1.0, 1.0)]);
		3.wait;
		a.set(\gate, 0);
	});

)


	t.start;




// The Dbrown and Dibrown Ugens are good for random walk (drunken walk)
(
{
	var a, freq, trig;
	a = Dbrown(-1.0, 1.0, 0.1, inf);
	trig = Impulse.kr(MouseX.kr(1, 40, 1));
	freq = Demand.kr(trig, 0, a) * 30 + 340;
	Poll.kr(trig, a, "Dibrown");
	SinOsc.ar(freq) * 0.1
}.play;
)








/**
 *
 * Este código não é meu.
 * Michael Dzjaparidze
 *
 */
(
SynthDef.new("vinylCrackling", { arg out = 10;
	var pop, imp, modImp, midNoise, hiNoise, loNoise, loopTime, numChan,w, x, y;

	loopTime = 8;
	numChan = 2;

	//Harder pops
	pop = TDuty.ar(Dseq({ 1.0.bilinrand.abs } ! loopTime, inf));
	pop = Resonz.ar(Decay.ar(pop, TRand.ar(0.001, 0.1, pop)), Demand.ar(pop, 0, Dseq({ 7000.bilinrand.abs + 40 } ! 		loopTime, inf)), mul: Demand.ar(pop, 0, Dseq({ exprand(0.1, 0.87) } ! loopTime, inf)));
	pop = LeakDC.ar(pop, TRand.ar(0.9, 0.9995, pop));
	#w, x, y = PanB2.ar(pop, Demand.ar(pop, 0, Dseq({ 1.0.rand2 } ! loopTime, inf)));

	//Softer, highpass filtered impulses
	imp = { arg i; HPF.ar(LPF.ar(Dust2.ar(8), 8000.0), 6000.0, WhiteNoise.ar(0.7)) } ! numChan;

	//Faster, modulated lowpass filtered impulses
	modImp = LPF.ar(Dust.ar(LFDNoise3.ar(1).exprange(100, 500), LFDNoise3.ar(10).exprange(0.01, 0.07)), 9000.0) ! numChan;

	//Mid frequency modulated noise background
	midNoise = LPF.ar(GrayNoise.ar(LFDNoise3.ar(60)) * WhiteNoise.ar(0.009), 12000.0) ! numChan;

	//High frequency modulated noise background
	hiNoise = HPF.ar(LPF.ar(GrayNoise.ar(LFDNoise3.ar(60)) * WhiteNoise.ar(0.01), 14000.0), 8000.0) ! numChan;

	//Low frequency modulated noise background
	loNoise = LPF.ar(BrownNoise.ar(PinkNoise.ar.range(0.001, 0.02)), 1200) ! numChan;

	Out.ar(out, DecodeB2.ar(numChan, w, x, y) + imp + modImp + midNoise + hiNoise + loNoise);
	}).send(s);
)

s.sendMsg("/s_new", "vinylCrackling", 1001, 0, 0, \out, 0);
s.sendMsg("/n_free", 1001);







/*

(
SynthDef(\sines, {arg out = 0, release_dur, gate =1, amp = 0.2;
		var sines, env;
		env = EnvGen.kr(Env.asr(0.01, amp, release_dur), gate, doneAction:2);
		sines = SinOsc.ar(852, 0, 2.2)+SinOsc.ar(1633, 0, 2.2);
		Out.ar(out, sines * env);
}).add

)

(

t = Task({
		var a;
		a =  Synth.new(\sines, [\release_dur, 5, \out, 0, \amp, 0.2, \gate, 1]);
		1.wait;
		a.set(\gate, 0);
	 });

)


	 t.start;

*/





(
{
	var source, local;

	//source = Decay.ar(Impulse.ar(0.3), 0.1) * WhiteNoise.ar(0.2);
	source = Streson.ar(
		SinOscFB.ar(120, MouseX.kr(-1, 1)),
		//	MouseY.kr(0.001, 0.9)
			0.9
	);
	local = LocalIn.ar(2) + [source, 0]; // read feedback, add to source
	local = Rotate2.ar(local[0], local[1], 0.6);
	//local = DelayN.ar(local, 0.1, 0.1); // delay sound

	// reverse channels to give ping pong effect, apply decay factor
	LocalOut.ar(local * MouseY.kr(0.0001, 1));
	//LocalOut.ar(local);
	Out.ar(0, local);
}.play;
)







({
	Out.ar(0,Streson.ar(SinOscFB.ar(
		//MouseX.kr(0, 2000)
		110,
		//MouseY.kr(0.0001, 1)
		Streson.ar(Impulse.ar(12),LinExp.kr(LFCub.kr(0.1, 0.5*pi), -1, 1, 280, 377).reciprocal
		)
	),0.5))
}.play
)


({
	Out.ar(0,
		Streson.ar(
			SinOscFB.ar(97, MouseX.kr(-1, 1)),
		//	MouseY.kr(0.001, 0.9)
			0.9
	))
}.play
)





//TODO: check out DynKlank + DynKlang








//Simple Feedback
(
{
	// below we declare our variables with 'var'
	var input, fBLoopIn, fBLoopOut, processing;
	// input is our sound source, a little noise
	input =Crackle.ar(1.5,0.5);
	// fBLoopIn is our feedback loop insertion point.
	fBLoopIn =LocalIn.ar(1);
	// in processing, we mix the input with the feedback loop
	// here MouseX is being used to control the gain of the feedback signal
	// LeakDC removes DC offset
	processing = input +LeakDC.ar((fBLoopIn *MouseX.kr(0,1.05)));
	// fBLoopOut is our feedback loop output point
	fBLoopOut=LocalOut.ar(processing);
	// Out routes the audio to the speakers. the square brackets send the output to both left
	// and right channels.
	Out.ar([0,1], processing)
	}.play
)

//Same as before + Delay

(
	{
		// below we declare our variables with 'var'
		var input, fBLoopIn, fBLoopOut, processing;
		// input is our sound source, a little noise
		input =Crackle.ar(1.5,0.5);
		// fBLoopIn is our feedback loop insertion point.fBLoopIn =LocalIn.ar(1);
		// in processing, we mix the input with the feedback loop
		// here MouseX is being used to control the gain of the feedback signal
		processing = input +LeakDC.ar((DelayN.ar(fBLoopIn,0.5,0.5, *MouseX.kr(0,1.05))));
		// fBLoopOut is our feedback loop output point
		fBLoopOut =LocalOut.ar(processing);
		processing =Limiter.ar(processing,0.8);
		// Out routes the audio to the speakers. the square brackets send the output to both left
		// and right channels.
		Out.ar([0,1], processing);
		}.play

)












(
//TENAR PREENCHER AS OUTRAS NOTAS... A-1 e A9 A10....
~notesFreq = Dictionary[
	//Note to Frequency:
	"A0"	 ->	27.5000,
	"A#0"	 ->	29.1352,
	"B0"	 ->	30.8677,
	"C1"	 ->	32.7032,
	"C#1"	 ->	34.6478,
	"D1"	 ->	36.7081,
	"D#1"	 ->	38.8909,
	"E1"	 ->	41.2034,
	"F1"	 ->	43.6535,
	"F#1"	 ->	46.2493,
	"G1"	 ->	48.9994,
	"G#1"	 ->	51.9131,
	"A1"	 ->	55.0000,
	"A#1"	 ->	58.2705,
	"B1"	 ->	61.7354,
	"C2"	 ->	65.4064,
	"C#2"	 ->	69.2957,
	"D2"	 ->	73.4162,
	"D#2"	 ->	77.7817,
	"E2"	 ->	82.4069,
	"F2"	 ->	87.3071,
	"F#2"	 ->	92.4986,
	"G2"	 ->	97.9989,
	"G#2"	 ->	103.8262,
	"A2"	 ->	110.0000,
	"A#2"	 ->	116.5409,
	"B2"	 ->	123.4708,
	"C3"	 ->	130.8128,
	"C#3"	 ->	138.5913,
	"D3"	 ->	146.8324,
	"D#3"	 ->	155.5635,
	"E3"	 ->	164.8138,
	"F3"	 ->	174.6141,
	"F#3"	 ->	184.9972,
	"G3"	 ->	195.9977,
	"G#3"	 ->	207.6523,
	"A3"	 ->	220.0000,
	"A#3"	 ->	233.0819,
	"B3"	 ->	246.9417,
	"C4"	 ->	261.6256,
	"C#4"	 ->	277.1826,
	"D4"	 ->	293.6648,
	"D#4"	 ->	311.1270,
	"E4"	 ->	329.6276,
	"F4"	 ->	349.2282,
	"F#4"	 ->	369.9944,
	"G4"	 ->	391.9954,
	"G#4"	 ->	415.3047,
	"A4"	 ->	440.0000,
	"A#4"	 ->	466.1638,
	"B4"	 ->	493.8833,
	"C5"	 ->	523.2511,
	"C#5"	 ->	554.3653,
	"D5"	 ->	587.3295,
	"D#5"	 ->	622.2540,
	"E5"	 ->	659.2551,
	"F5"	 ->	698.4565,
	"F#5"	 ->	739.9888,
	"G5"	 ->	783.9909,
	"G#5"	 ->	830.6094,
	"A5"	 ->	880.0000,
	"A#5"	 ->	932.3275,
	"B5"	 ->	987.7666,
	"C6"	 ->	1046.5023,
	"C#6"	 ->	1108.7305,
	"D6"	 ->	1174.6591,
	"D#6"	 ->	1244.5079,
	"E6"	 ->	1318.5102,
	"F6"	 ->	1396.9129,
	"F#6"	 ->	1479.9777,
	"G6"	 ->	1567.9817,
	"G#6"	 ->	1661.2188,
	"A6"	 ->	1760.0000,
	"A#6"	 ->	1864.6550,
	"B6"	 ->	1975.5332,
	"C7"	 ->	2093.0045,
	"C#7"	 ->	2217.4610,
	"D7"	 ->	2349.3181,
	"D#7"	 ->	2489.0159,
	"E7"	 ->	2637.0205,
	"F7"	 ->	2793.8259,
	"F#7"	 ->	2959.9554,
	"G7"	 ->	3135.9635,
	"G#7"	 ->	3322.4376,
	"A7"	 ->	3520.0000,
	"A#7"	 ->	3729.3101,
	"B7"	 ->	3951.0664,
	"C8"	 ->	4186.0090
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
