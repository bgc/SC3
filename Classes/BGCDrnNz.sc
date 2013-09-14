BGCDrnNz {
//	var <>synthOscs, //synth oscilattors
	var <>s,//the Server
	<>window, //the window...
	<>note,
	<>octave,
	<>machine,
	<>mainVolSlider,
	<>amp1Slider,
	<>amp2Slider,
	<>amp3Slider;


	*new {
		^super.new.init;
	}

	init {
		arg s = Server.local;
		this.s = s;
//		this.synthOscs = List.new();
//		this.setupSynthOscs;
		Task({
			this.setSynthDefs;
			this.s.sync;
			this.machine = Synth(\drnnz, [\note, 0, \amp1, 0, \amp2, 0, \amp3, 0]);
		}).play;
		this.setupGui;
	}

	setupGui {
		var nLayout,//note layout;
		nView,
		layout1,
		layout2;

		this.window = Window("DrnNz", Rect(20,20, 250, 400)).front;
		this.window.onClose_{
			this.cleanup();
			this.free;
		};

		this.note = PopUpMenu(this.window);
		this.note.items = ["C","C#","D","D#","E","F","F#","G", "G#", "A", "A#", "B"];
		this.note.action = {
			this.calculateMidiNote;
		};


		this.octave = PopUpMenu(this.window);
		this.octave.items = ["-5","-4","-3","-2","-1","0","1","2", "3", "4", "5"];
		this.octave.action = {
			this.calculateMidiNote;
		};

		this.window.layout = HLayout();
		nView = View(window).fixedWidth_(330).fixedHeight_(80);
		nLayout = HLayout();
		layout1 = VLayout();
		layout2 = VLayout();

		layout1.add(
			TextView().string_("Note")
			.editable_(false)
			.maxWidth_(60)
			.maxHeight_(25)
			.hasVerticalScroller_(false)
			.hasHorizontalScroller_(false),
			stretch: 1, align:\topLeft
		);
		layout1.add(this.note, stretch: 1, align:\topLeft);

		layout2.add(
			TextView()
			.string_("Octave")
			.editable_(false)
			.maxWidth_(60)
			.maxHeight_(25)
			.hasVerticalScroller_(false)
			.hasHorizontalScroller_(false),
			stretch: 1, align:\topLeft
		);
		layout2.add(this.octave, stretch: 1, align:\topLeft);

		nLayout.add(layout1);
		nLayout.add(layout2);

		nView.layout = nLayout;
		this.window.layout.add(nView, stretch: 1, align:\topLeft);

		this.mainVolSlider = Slider(window, Rect(10, 30, 50, 160))
			.value_(1)
			.orientation_(\vertical)
			.action_({
				this.machine.set(\famp, this.mainVolSlider.value);
			});

			this.amp1Slider = Slider(window, Rect(10, 30, 50, 160))
			.value_(1)
			.orientation_(\vertical)
			.action_({
				this.machine.set(\amp1, this.amp1Slider.value);
			});

			this.amp2Slider = Slider(window, Rect(10, 30, 50, 160))
			.value_(1)
			.orientation_(\vertical)
			.action_({
				this.machine.set(\amp2, this.amp2Slider.value);
			});

			this.amp3Slider = Slider(window, Rect(10, 30, 50, 160))
			.value_(1)
			.orientation_(\vertical)
			.action_({
				this.machine.set(\amp3, this.amp3Slider.value);
			});
	}

/*
	setupSynthOscs{
		3.do{ arg i;
			var theBuf;

			switch (i)
				{0}{
					this.synthOscs.add(
						Buffer.alloc(this.s, 512, 1)
						.sendCollection(Wavetable.sineFill(256, 1.0/[1]))
					);
				}
				{1}{
					this.synthOscs.add(
						Buffer.alloc(this.s, 512, 1)
						.sendCollection(Wavetable.sineFill(256, 1.0/[1,2]))
					);
				}
				{2}{
					this.synthOscs.add(Buffer.alloc(this.s, 512, 1)
						.sendCollection(Wavetable.sineFill(256, [1, 0.25, 0.5,0.75]))
						);
				}
		};
	}



	setupFXOscs{}
*/


	setOSCResponders {}

	setSynthDefs {

		SynthDef(\drnnz,{

			arg note = 64,
			xamp = 0.5,
			amp1 = 1,
			amp2 = 1,
			amp3 = 1,
			famp = 1,
			bwr1 = 0.010,
			bwr2 = 0.010,
			bwr3 = 0.010;

			var res1,
			res2,
			res3,
			mixer,
			exciter;

			exciter = WhiteNoise.ar(xamp);

			res1 = Resonz.ar(
				in: exciter,
				freq: VarLag.kr(note.midicps, 0.01),
				bwr: bwr1,
				mul: VarLag.kr(amp1, 0.01)
			);

			res2 = Resonz.ar(
				in: exciter,
				freq: VarLag.kr((note+12).midicps, 0.01),
				bwr: bwr2,
				mul: VarLag.kr(amp2, 0.01)
			);

			res3 = Resonz.ar(
				in: exciter,
				freq: VarLag.kr((note+24).midicps, 0.01),
				bwr: bwr3,
				mul: VarLag.kr(amp3, 0.01)
			);

			mixer = Mix.new([res1, res2, res3]);

			Out.ar(0, mixer*VarLag.kr(famp,0.01));
		}).add;
	}

	calculateMidiNote {
		var pitch;
		this.note.value.postln;
		this.octave.value.postln;
		pitch = (this.note.value + (this.octave.value*12));
		this.machine.set(\note, pitch)
	}

	cleanup {
		this.machine.free;
	}

/*
	clearBuffers { //cleanups on quit
		//this.player.free;
		this.synthOscs.do{ arg buf, index;
			buf.free;
			synthOscs.at(index).free;
		};
		synthOscs.clear;
	}
*/
}