//Feedback test
(
{
	var input, //Initial Input
			crAmp, //Input Amplitude
			fbIn, //LocaL Signal In
			fbInAmt, //Local Signal In Amplitude
			entry, //Local Signal Entry after LeakDC Removed & Amplitude applied

			processing, //Processing Chain
			delayed, //Delayed Signal
			delAmt, //Delay Mix Amount
			reverbed, //Reverberated Signal
			revAmt, //Reverb Mix Amount

			limiterAmt, //Limiter amount
			fVolAmt,//Volume of Output
			fbOut; //Local Out

	crAmp = 0.5;
	input = Crackle.ar(1.5, crAmp);

	fbIn = LocalIn.ar(1);
	fbInAmt = 1;

	entry = LeakDc.ar(fbIn) * fbInAmt;

	//Start Processing
	processing = input + entry;

	/*
	possible EQ

	SynthDef(\eq, {|in, lofreq = 80, midfreq = 800, hifreq = 2000, band1 = -45, band2 = -45, out, band3 = -45, mix = -1|
		var dry, wet;
		dry = In.ar(in, 2);
		wet = BLowShelf.ar(dry, lofreq, 1, band1); //check arguments above, wasnt BLowShelf but BPeakEQ
		wet = BPeakEQ.ar(wet, midfreq, 1, band2);
		wet = BHiShelf.ar(wet, hifreq, 1, band3);//check arguments above, wasnt BHiShelf but BPeakEQ
		Out.ar(out, XFade2.ar(dry, wet, mix));
	}).add;


	TODO: check out DFM1
	DFM1 is a digitally modelled analog filter.1 It provides low-pass and high-pass filtering. The filter can be overdriven and will self-oscillate at high resonances.

	//There's also the ddwEQ quark.

	q = MultiEQ(2);  // stereo
	q.edit(targ, b);  // target and bus
	q.edit(nil, 0);  // default group, bus 0
	//Then, when you close the window, it prints the specs to rebuild the EQ any time:
	//MultiEQ.new(2, \loshelf, 56.662, -2.556.dbamp, 1, \eq, 344.517, -2.256.dbamp, 1, \hishelf, 7834.203, 1.654.dbamp, 1)
	*/

	/* TREMOLO ?
		tremFreq 0.001 - 6hz
		depth = trem Amount
		(
			var input, tremWave, modulator, depth=0.25;

			a = {
				tremWave = SinOsc.ar(3, 0, depth);
				modulator = (1.0 - depth) + tremWave;
				input = SinOsc.ar(20, 0, 1.0);
				input * modulator;
			}.plot(2.0);
)
	*/
	/*
	DiodeRingMod?
	*/
	/*
	Decimator?
	*/


	//Delay
	/*
	MultiTap?
	SwitchDelay?
		feedback delay line implementing switch-and-ramp buffer jumping
		No need for XFade2??? but still needs delAmt to change levels od dry & wet
	*/
	delayed = processing;
	//delAmt varies between -1 (dry) & 1(wet);
	processing = XFade2.ar(processing, delayed, delAmt, 1);
	//Reverb
	/*
	JPverb?
	FreeVerb?
	*/
	reverbed = processing;
	//revAmt varies between -1 (dry) & 1(wet);
	processing = XFade2.ar(processing, reverbed, revAmt, 1);
	limiterAmt = 0.8;

	//Limiter
	processing = Limiter.ar(processing, limiterAmt);
	//Final Volume
	processing = processing * fVolAmt;
	//Write to fbOut
	fbOut = LocalOut.ar(processing);

	Out.ar([0,1], processing);
}.play;
)