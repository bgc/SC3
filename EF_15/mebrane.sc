(
	c = (1..10).choose;
	c = 1;
	//f = (1..12).choose;
	f = Scale.enigmatic.degrees.choose;

("Root note:" + f).postln;

c.do{
		//f = rrand(10,50);
		//f.postln;
		//f = 250;

	d = Scale.enigmatic.degrees.choose;

	("degree: "+ d).postln;
	("final note: " + (d+f)).postln;

	m = (f + d).midicps;

	Synth(
			\membraneHex, [
			\freq, m, //exprand(10,40),
				\amp, 1/c,
				\cf, exprand(2, 50)*m,
				\rq, exprand(0.01, 0.5),
				\atk, rrand(0.001, 3),
				\rel, exprand(0.1, 5),
				\pan, rrand(-1.0, 1.0),
				\loss, exprand(0.999, 0.99999),
				\tension, exprand(0.01, 0.1)
			]
		);
	};

)



(
SynthDef(\membraneHex, {
	arg pan = 0, out = 0,freq = 4, tension = 0.5, loss = 0.9999999,atk=2, sus=0, rel=3, c1=1, c2=(-1);
	var sig, env;
	env = EnvGen.kr(
		Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:c2), doneAction: 2);
	sig = MembraneHexagon.ar(env*PinkNoise.ar(freq), tension, loss);
	sig = HPF.ar(sig,80);
	sig = sig * env;
	sig = Limiter.ar(sig, 0.92, 0.01);
	sig = LeakDC.ar(sig, 0.995);
	sig = Pan2.ar(sig, pan);
	Out.ar(out, sig);
}

).add;
)



/*
(
{ var excitation = EnvGen.kr(Env.perc,
                            MouseButton.kr(0, 1, 0),
                             timeScale: 0.1, doneAction: 0
                            ) * PinkNoise.ar(0.4);
  var tension = MouseX.kr(0.01, 0.1);
  var loss = MouseY.kr(0.999999, 0.999, 1);
  MembraneHexagon.ar(excitation, tension, loss);
}.play;
)
*/
