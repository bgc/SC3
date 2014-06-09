//bgc

BGCPerf {
	//<> get set
	//< get
	//> set

	var <>window, //the window
			<>s //the server;

	*new {
		^super.new.init;
	}

	init {
		arg s = Servel.local;
		this.s = s;

		{
			this.setSynthDefs;
			this.s.sync;
		}.fork;


		//synthdefs allready started;
		this.initGui();
	}

	initGui {
		window = Window("BGCPerf", Rect(20,20, 250, 400)).front;
		window.onClose_{
			this.clearSynths();
			this.free;
		}
	}

	sendOutPulseStream {
		//TODO teste if var pls = fork... is necessary
		fork({
			(1 / (1..100).scramble).do { |dt|
				Synth.grain("pulsestream",
					[
						\whichOut: rrand(-1.0, 1.0),
						\amp: rrand(0.1, 1.0),
						\freq: rrand(130.813, 10548.082)
					]
				);
				dt.wait
			}
		});
	}

	sendOutTwoPulses {
		//t = Task({ ou fork???
		fork ({
			var b, a;
			b =  Synth.new(\twoPulses,
				[
					\release_dur, 0.5,
					\amp, 1,
					\gate, 1,
					\mix, 0,
					\pan, rrand(-1.0, 1.0),
					\freq, rrand(130.813, 10548.082)
				]
			);
			5.wait;
			b.set(\gate, 0);
			a =  Synth.new(\twoPulses,
				[\release_dur, 5,
					\amp, 1,
					\gate, 1,
					\pan, rrand(-1.0, 1.0),
					\freq, rrand(130.813, 10548.082)
				]
			);
			3.wait;
			a.set(\gate, 0);
		});
	}

	setSynthDefs {
		SynthDef("pulsestream", {
			arg whichOut = 0, //define Pan2 position
					amp = 0.5, //define current pulse amplitude
					freq = 440;
			OffsetOut.ar(0,
				Pan2.ar(
					BPF.ar(
						Impulse.ar(0)
						freq,
						0.3
					),
					whichOut,
					amp
				),
			)
			FreeSelf.kr(Impulse.kr(0));
		}).add;

		SynthDef(\twoPulses, {
			arg pan = 0.0,
					amp = 1.0,
					mix = 0.75,
					room = 0.95,
					damp = 0.25,
					release_dur = 5.0,
					gate = 1,
					freq = 440;
			var env,
					sound;
			env = EnvGen.kr(
				Env.asr(0.01, amp, release_dur),
				gate, doneAction:2
			);
			sound = FreeVerb.ar(
				Pan2.ar(
					BPF.ar(
						Impulse.ar(0),
						freq,
						0.3
					),
					pan,
					amp
				),
				mix,
				room,
				damp
			);
			Out.ar(0,
				sound * env
			);
		}).add;

	clearSynths {

	}

	}

}