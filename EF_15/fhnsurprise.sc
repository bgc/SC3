//controlled surprise
(
SynthDef("FHN-surprise2",
	{
		arg rateu = 0.01, ratew = 0.01,
		atk=2, sus=0, rel=3,c1=1, c2=(-1), pan = 0.0, cf = 1000,
		mdlt=0.1,dlt=0.1,dcyt=10;

		var sig, env;

		env = EnvGen.kr(
			//Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
			Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
		doneAction: 2);

		sig = CombN.ar(
						RLPF.ar(
							RHPF.ar(
								FitzHughNagumo.ar(
									reset: 0,
									rateu: rateu,
									ratew: ratew,
									b0: 1,
									b1: 1,
									initu: 0,
									initw: 0
								),
							cf,0.01), //RHPF
						13000,0.01), //RLPF
					mdlt,dlt,dcyt); //COMBN

		//sig = Ball.ar(sig,0.98, 0.3);
		//* env;
		//sig = LeakDC(sig, 0.95);
		sig = sig * env;
		sig = Limiter.ar(sig, 0.2, 0.01);

		Out.ar(0,
			Pan2.ar(
				sig,
			pan)
		)
	}).add
)




//controlled surprise
(
SynthDef("FHN-surprise",
	{
		arg rateu = 0.01, ratew = 0.01,
		atk=2, sus=0, rel=3,c1=1, c2=(-1), pan = 0.0, cf = 1000,
		mdlt=0.1,dlt=0.1,dcyt=10;

		var sig, env;

		env = EnvGen.kr(
			//Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
			Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
		doneAction: 2);

		sig = CombN.ar(
						RLPF.ar(
							RHPF.ar(
								FitzHughNagumo.ar(
									reset: 0,
									rateu: rateu,
									ratew: ratew,
									b0: 1,
									b1: 1,
									initu: 0,
									initw: 0
								),
							cf,0.01), //RHPF
						13000,0.01), //RLPF
					mdlt,dlt,dcyt); //COMBN

		sig = sig * env;
		sig = Limiter.ar(sig, 0.2, 0.01);

		Out.ar(0,
			Pan2.ar(
				sig,
			pan)
		)
	}).add
)





(
	c = (1..10).choose;
	c = 1;

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
		"FHN-surprise2",[
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
