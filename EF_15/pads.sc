s.boot;



(

SynthDef(\pad1, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, sig2, env;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
    sig = Saw.ar(freq+LFNoise0.ar(2)) + Saw.ar(freq + LFNoise0.ar(4));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)

(

SynthDef(\pad2, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env, sig2;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
    sig = Saw.ar(freq+LFNoise0.ar(2)) + Saw.ar(freq + LFNoise0.ar(4)) + Klank.ar(`[[cf*0.5, cf, cf*2], nil, [1*rq,1,1*rq]], PinkNoise.ar(0.007));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)








(

SynthDef(\pad3, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env, sig2;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
    sig = SinOsc.ar(freq+LFNoise0.ar(2)) + Blip.ar(freq + LFNoise0.ar(4), 100);
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)



(

SynthDef(\pad4, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env, sig2;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
    sig = SinOsc.ar(freq+LFNoise0.ar(2)) + Blip.ar(freq + LFNoise0.ar(4), 100) + Klank.ar(`[[cf*0.5, cf, cf*2], nil, [1*rq,1,1*rq]], PinkNoise.ar(0.007));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)

(

SynthDef(\pad5, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env, sig2;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
    sig = SinOsc.ar(freq+LFNoise0.ar(2)) + SinOsc.ar(freq + LFNoise0.ar(4)) + Klank.ar(`[[cf*0.5, cf, cf*2], nil, [1*rq,1,1*rq]], PinkNoise.ar(0.007));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)

(

SynthDef(\pad6, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, env, sig2;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
    sig = SinOsc.ar(freq+LFNoise0.ar(2)) + SinOsc.ar(freq + LFNoise0.ar(4));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);
    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)




(

SynthDef(\pad7, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, sig2, env;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
	sig = SinOsc.ar(freq+LFNoise0.ar(2)) + SinOsc.ar((freq*2)+LFNoise0.ar(2)) + SinOsc.ar((freq) + LFNoise0.ar(4))  + SinOsc.ar((freq*0.5) + LFNoise0.ar(4)) + Klank.ar(`[[cf*0.5, cf, cf*2], nil, [1*rq,1,1*rq]], PinkNoise.ar(0.007));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig.tanh.tanh * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);

    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)


(

SynthDef(\pad8, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, sig2, env;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
	sig = PulseDPW.ar(freq+LFNoise0.ar(2)) + PulseDPW.ar((freq*2)+LFNoise0.ar(2)) + LFTri.ar((freq) + LFNoise0.ar(4))  + LFTri.ar((freq*0.5) + LFNoise0.ar(4)) + Klank.ar(`[[cf*0.5, cf, cf*2], nil, [1*rq,1,1*rq]], PinkNoise.ar(0.007));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig.tanh.tanh * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);

    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)




(

SynthDef(\pad9, {
	  arg atk=2, sus=0, rel=3, c1=1, c2=(-1),
    freq=500, cf=1500, rq=0.2, amp=1,pan = 0, out=0;

    var sig, sig2, env;
    env = EnvGen.kr(
      //Env.perc(attackTime:atk, releaseTime:rel, level:1, curve:-4),
      Env([0,1,1,0], [atk,sus, rel], [c1, 0, c2]),
    doneAction: 0);
	sig = Formant.ar(freq+LFNoise0.ar(2),freq*2, freq* 0.5) + Formant.ar((freq*2)+LFNoise0.ar(2), freq*4, freq) + LFTri.ar((freq) + LFNoise0.ar(4))  + LFTri.ar((freq*0.5) + LFNoise0.ar(4)) + Klank.ar(`[[cf*0.5, cf, cf*2], nil, [1*rq,1,1*rq]], PinkNoise.ar(0.007));
    //sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf + (freq*env), rq);
    //sig = HPF.ar(sig, 80);
		sig = FreeVerb.ar(sig, 0.75, 0.6);
    sig = sig.tanh.tanh * env * amp;
		sig2 = DelayL.ar(sig * -1, 0.2, 0.01);
		sig = Limiter.ar(sig + sig2, 0.2, 0.01);

    sig = Pan2.ar(sig, pan);
		DetectSilence.ar(sig, doneAction: 2);
    Out.ar(out, sig);
}).add;

)

(
	c = (1..10).choose;
	//c = 1;
	//f = (1..12).choose;
	f = Scale.major.semitones.choose + 44;

("Root note:" + f).postln;

c.do{
		//f = rrand(10,50);
		//f.postln;
		//f = 250;
	d = Scale.major.semitones.choose;
	("degree: "+ d).postln;
	("final note: " + (d+f)).postln;
	m = (f + d).midicps;
	Synth(
			\pad9, [
			\freq, m, //exprand(10,40),
				\amp, 1/c,
				\cf, exprand(2, 50)*m,
				\rq, exprand(0.01, 0.35),
				\atk, rrand(1.0, 5.0),
				\rel, exprand(1.0, 5.0),
				\pan, rrand(-1.0, 1.0),
			]
		);
	};

)