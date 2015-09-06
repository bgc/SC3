Server.default = Server.local;

s = Server.default;

(s.boot;)

(s.makeGui;)

(s.reboot;)

(s.scope;)

(s.freqscope;)


s.sync;





(
SynthDef('spirM', {
	arg out = 0,

	freq0  = 131,
	freq1  = 141,
	freq2  = 151,
	freq3  = 241,
	freq4  = 272,
	freq5  = 282,
	freq6  = 292,
	freq7  = 302,
	freq8  = 415,
	freq9  = 433,
	freq10 = 515,
	freq11 = 653,
	freq12 = 701,



	vol0 = 0.5,
	vol1 = 0.5,
	vol2 = 0.5,
	vol3 = 0.5,
	vol4 = 0.5,
	vol5 = 0.5,
	vol6 = 0.5,
	vol7 = 0.5,
	vol8 = 0.5,
	vol9 = 0.5,
	vol10 = 0.5,
	vol11 = 0.5,
	vol12 = 0.5;

	var spirMSynth;

	spirMSynth = (
		SinOsc.ar(freq0, 0, vol0, 0) +
		SinOsc.ar(freq1, 0, vol1, 0) +
		SinOsc.ar(freq2, 0, vol2, 0) +
		SinOsc.ar(freq3, 0, vol3, 0) +
		SinOsc.ar(freq4, 0, vol4, 0) +
		SinOsc.ar(freq5, 0, vol5, 0) +
		SinOsc.ar(freq6, 0, vol6, 0) +
		SinOsc.ar(freq7, 0, vol7, 0) +
		SinOsc.ar(freq8, 0, vol8, 0) +
		SinOsc.ar(freq9, 0, vol9, 0) +
		SinOsc.ar(freq10, 0, vol10, 0) +
		SinOsc.ar(freq11, 0, vol11, 0) +
		SinOsc.ar(freq12, 0, vol12, 0)
	)* 13.reciprocal;


	Out.ar(out,
		spirMSynth
	);

}).add;

)



~snd7 = Synth(\spirM);

~snd7.set(\freq12, 380);

~snd7.set(\vol12, 0.1);

~snd7.free;
