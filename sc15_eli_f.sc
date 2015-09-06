s.boot;
s.plotTree;
s.meter;
s.makeGui;
s.scope;




/*
HairCell
*GravityGrid*
*FitzHughNagumo*
*SpruceBudworm*
Sieve1
*/




(

{
	0.1*GravityGrid.ar(
		Impulse.kr(
			LFNoise0.kr([0.025,0.0231],90,100)),
		[100.2,10.5],
		LFNoise0.kr(0.10,0.8),
		LFNoise0.kr(0.10,0.8)
	)
}.play

)


/*
(
{Pan2.ar(0.5*GravityGrid.ar(
	Impulse.kr(1),
	SinOsc.kr(0.5,0.0,0.8,1.0),
	LFSaw.kr(50),
	LFNoise0.kr(10,0.8)),0.0)}.play
)
*/








/*

reset	If > 0.0, restart with new initial conditions sampled from initx, inity
rate	update rate for a sample step
k1	equation constant
k2	equation constant
alpha	equation constant
beta	equation constant
mu	equation constant
rho	equation constant
initx	reset value for x
inity	reset value for y

*/






































/*
Env.perc(attackTime:0.05, releaseTime:1, level:1, curve:-4).test.plot;

Env.perc(0.001, 1, 1, -8).test.plot;
*/














/*

"http://sccode.org/1-16"

(
play{
	Limiter.ar(
		GVerb.ar(
			sin(
				Ringz.ar(
					Impulse.ar(
						2**LFNoise0.ar(1!2).range(1,4).round),
					[40,200,234,889],
					0.7
				).sum*2
			),
			5,
			2,
			0.7)
	)
}
)

(
play{Limiter.ar(GVerb.ar(sin(Ringz.ar(Impulse.ar(2**LFNoise0.ar(1!3).range(1,5).round/2),[40,200,234,889,1320,150],0.7).sum),5,2,0.7)/2)}
)
*/





/*
"http://sccode.org/1-508"

(
{
	var carrier = SinOsc.ar(MouseX.kr(100, 1000, 1));
	var modulator = SinOsc.ar(MouseY.kr(100, 1000, 1)) * 0.5;
	carrier.abs + modulator * carrier.sign * 0.1!2;
}.scope;
)

// adding DC offset and LFO "wobbles"

(
{
	var carrier = SinOsc.ar(MouseX.kr(100, 1000, 1)) + LFNoise2.kr(4, 0.3) + 0.1;
	var modulator = SinOsc.ar(MouseY.kr(100, 1000, 1)) + LFNoise2.kr(7, 0.5) - 0.3;
	carrier.abs + modulator * carrier.sign * 0.1!2;
}.scope;
)

*/




/*
"http://sccode.org/1-4UU"

// Ressonance
(
SynthDef(\Synth3,
	{arg ress = 0, choose = 0;
		var klank, env;
		klank = Klank.ar(`[choose !12, {Rand(0.128,0.700)}!12],BrownNoise.ar(0.7));
		klank = klank;
		env = EnvGen.kr(Env.perc(0.07, ress), doneAction:2);
		Out.ar(0, klank*env.dup*0.000128);
}).add;
)
//Attack
(
SynthDef(\Synth4,
	{arg ress = 0, choose = 0;
		var klank, env;
		klank = Klank.ar(`[choose !12, {Rand(0.128,0.700)}!12],BrownNoise.ar(0.7));
		klank = klank;
		env = EnvGen.kr(Env.perc(0, 1), doneAction:2);
		Out.ar(0, klank*env.dup*0.00128);
}).add;
)
(
{21.do{x = [70,90,120].choose; y = rrand(0.1,7); Synth(\Synth3, [\ress, y, \choose, x]);Synth(\Synth4, [\choose, x]); y.wait;}}.fork;
)

*/
