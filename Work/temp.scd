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

SynthDef("help-Dust2", { arg out=0;
    Out.ar(out,
        Dust2.ar(3, 0.5)
    )
}).add;

~snd4 = Synth("help-Dust2")
~snd4.free();


(
SynthDef("reson", {
	(
		Out.ar(0,
		Resonz.ar(
				Dust2.ar(2),//in
				220,//freq
				0.001,//bwr (0.001 tem ataque mais suave, 0.01 tem ataque imediato, 0.1 fica alto o ataque e fa clicks...
				500 //VOLUME???
			)+
		Resonz.ar(Dust2.ar(5), 440, 0.001, 500)+
		Resonz.ar(Dust2.ar(1), 880, 0.001, 500)
	))* 3.reciprocal;
}).add;
)
~snd3 = Synth("reson");

~snd3.play();
//~snd3.free(8);
~snd3.free();



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
