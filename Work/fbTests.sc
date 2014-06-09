//Feedback test
(
{
	var input, //Initial Input
			crAmp, //Input Amplitude
			fbIn, //LocaL Signal In
			fbInAmt, //Local Signal In Amplitude
			entry, //Local Signal Entry after LeakDC Removed & Amplitude applied

			processing, //Processing Chain

			eq, //equalizer container, used for eq Select
			eqLFreq, //equalizer lows frequency (80)
			eqMLFreq, //equalizer low mids frequency (400)??
			eqMHFreq, //equalizer high mids frequency (800)??
			eqHFreq, //equalizer highs frequency (2000)??

			eqLRq, //equalizer lows reciprocal of Q (1) //check min && max
			eqMLRq, //equalizer low mids reciprocal of Q (1) //check min && max
			eqMHRq, //equalizer high mids reciprocal of Q (1) //check min && max
			eqHRq, //equalizer highs reciprocal of Q (1) //check min && max

			eqLBC, //equalizer lows Boost/Cut (0) //check min && max
			eqMLBC, //equalizer low mids Boost/Cut (0) //check min && max
			eqMHBC, //equalizer high mids Boost/Cut (0) //check min && max
			eqHBC, //equalizer highs Boost/Cut (0) //check min && max

			eqActive, //whether eq is on or off

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


	//Apply eq to signal
	/*
	TODO check if i want eq or not... one switch or 4 switches?
	start with only 1 global switch... no need to complicate things at start
	remenber to map eqActive to int from 0 to 1, 0 being default
	*/

	//Low Shelf
	eq = BLowShelf.ar(processing, eqLFreq, eqLRq, eqLBC);
	//Low Mids
	eq = BPeakEQ.ar(eq, eqMLFreq,eqMLRq ,eqMLBC);
	//High Mids
	eq = BPeakEQ.ar(eq, eqMHFreq,eqMHRq ,eqMHBC);
	//High Shelf
	eq = BHiShelf.ar(eq, eqHFreq,eqHRq ,eqHBC);

	processing = Select.ar(eqActive,
		[
			processing,
			eq
		]
	);


	/*
	Arguments for BLOWSHELF, BPEAKEQ && BHHIGHShelf
	in	input signal to be processed.
	freq	center frequency.
	rs	the reciprocal of S. Shell boost/cut slope. When S = 1, the shelf slope is as steep as it can be and remain monotonically increasing or decreasing gain with frequency. The shelf slope, in dB/octave, remains proportional to S for all other values for a fixed freq/SampleRate.ir and db.
	db	gain. boost/cut the center frequency in dBs.

	*/

/*
	possible EQ
	SynthDef(\eq, {|in, lofreq = 80, midfreq = 800, hifreq = 2000, band1 = -45, band2 = -45, out, band3 = -45, mix = -1|
		var dry, wet;
		dry = In.ar(in, 2);
		wet = BLowShelf.ar(dry, lofreq, 1, band1);
		wet = BPeakEQ.ar(wet, midfreq, 1, band2);
		wet = BHiShelf.ar(wet, hifreq, 1, band3);
		Out.ar(out, XFade2.ar(dry, wet, mix));
	}).add;
	//There's also the ddwEQ quark.
	Will not be using this...
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