
//DetectSilence.ar(sig, doneAction:doneAction);
(



  SynthDef(\bpfsaw10, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 2);
    sig = Klank.ar(`[[cf*0.5, cf, cf*2], nil, [1*rq,1,1*rq]], PinkNoise.ar(0.007));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    sig = HPF.ar(sig, 80);
    sig = sig * env * amp;
    sig = Limiter.ar(sig, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;







  SynthDef(\bpfsaw9, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]), doneAction: 2);
    sig = Saw.ar(freq);
  sig = BPF.ar(sig, cf+(freq*env), rq);
    sig = sig * env * amp;
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;






  SynthDef(\bpfsaw8, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 2);
    sig = Klank.ar(`[[cf*0.5, cf, cf*2], nil, [1*rq,1,1*rq]], PinkNoise.ar(0.007));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf, rq);
    sig = HPF.ar(sig, 80);
    sig = sig * env * amp;
    sig = Limiter.ar(sig, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;






  SynthDef(\bpfsaw7, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]), doneAction: 2);
    sig = Formant.ar(Saw.ar(freq)*freq, cf,cf*rq);
    sig = sig * env * amp;
    sig = HPF.ar(sig, 80);
    sig = Limiter.ar(sig, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;





  SynthDef(\bpfsaw6, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0, numharm = 150;

    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]), doneAction: 2);
    sig = Blip.ar(freq, numharm);
    sig = Resonz.ar(sig, cf, rq);
    sig = sig * env * amp;
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;






  SynthDef(\bpfsaw5, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]), doneAction: 2);

    sig = Spring.ar(Saw.ar(freq), 0.98, 0.06);
    sig = Spring.ar(sig, 0.5, 0.6);
    sig = Spring.ar(sig, 0.2, 0.01);

    sig = Resonz.ar(sig, cf, rq);
    sig = sig * env * amp;
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;








  SynthDef(\bpfsaw4, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]), doneAction: 2);
    sig = Spring.ar(Saw.ar(freq), 0.98, 0.06);
    sig = Resonz.ar(sig, cf, rq);
    sig = sig * env * amp;
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;





  SynthDef(\bpfsaw3, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]), doneAction: 2);
    sig = Ball.ar(Saw.ar(freq), 0.98, 0.6);
    sig = Resonz.ar(sig, cf, rq);
    sig = sig * env * amp;
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;




  SynthDef(\bpfsaw2, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]), doneAction: 2);
    sig = Saw.ar(freq) + SinOsc.ar(freq);
    sig = Resonz.ar(sig, cf, rq);
    sig = sig * env * amp;
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;




  SynthDef(\bpfsaw, {
    arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]), doneAction: 2);
    sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf, rq);
    sig = sig * env * amp;
    sig = Pan2.ar(sig, pan);
    Out.ar(out, sig);
  }).add;

)


(
	c = (1..10).choose;
	//c = 1;
	//f = (1..12).choose;
	f = Scale.enigmatic.semitones.choose;

("Root note:" + f).postln;

c.do{
		//f = rrand(10,50);
		//f.postln;
		//f = 250;

	d = Scale.enigmatic.semitones.choose;

	("degree: "+ d).postln;
	("final note: " + (d+f)).postln;

	m = (f + d).midicps;

	Synth(
			\bpfsaw9, [
			\freq, m, //exprand(10,40),
				\amp, 1/c,
				\cf, exprand(2, 50)*m,
				\rq, exprand(0.01, 0.5),
				\atk, rrand(0.001, 3),
				\rel, exprand(0.3, 3),
				\pan, rrand(-1.0, 1.0),
			]
		);
	};

)
