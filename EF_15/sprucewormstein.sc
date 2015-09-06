(
	c = (1..10).choose;
	//c = 1;

f = Scale.bartok.degrees.choose;

("Root note:" + f).postln;


	c.do{
		//f = rrand(10,50);
		//f.postln;
	d = Scale.bartok.degrees.choose;

	("degree: "+ d).postln;
	("final note: " + (d+f)).postln;

	m = (f + d).midicps;
	n = exprand(0.01, 3);

		//f = 250;
		Synth(
		"SpruceWormstein",[
			\cf, exprand(2, 50)*m,
			\rateu, exprand(0.001, 1.0),
			\ratew, exprand(0.001, 1.0),
			\atk, rrand(0.001, 3.0),
			\rel, exprand(0.9, 3.0),
			\c1, rrand(-7, -1),
			\c2, rrand(-7, -1),
			\pan, rrand(-1.0, 1.0),
			\mdlt, n,
			\dlt, exprand(0.01, n)
		]
		);
	};
)




(
SynthDef("SpruceWormstein",{
	arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
		freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0,
	rateu = 0.1,ratew=0.1;

	var sig, env;
env = EnvGen.kr(
			Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
		doneAction: 2);
	sig = (0.05*
		SpruceBudworm.ar(
			Impulse.kr(freq),//reset
			0.01,//rate
					rateu,//k1
					ratew,//k2
					initx:1.2
				)[0] //spruce
			);
	sig = Ball.ar(sig, 0.98, ratew);
sig = sig * env;
	sig = Limiter.ar(sig, 0.2, 0.01);
	sig = LeakDC.ar(sig, 0.995);
	Out.ar(out, Pan2.ar(sig, pan));
}).add;
)
