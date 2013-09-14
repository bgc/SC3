BGCDroneMachine {
	var <>synthOscs, //synth oscilattors
	<>s,//the Server
	<>window, //the window...
	<>note,
	<>octave;

	*new {
		^super.new.init;
	}
	init {
		arg s = Server.local;
		this.s = s;
		this.synthOscs = List.new();
		this.setupSynthOscs;
		this.setupGui;
	}

	setupGui {
		this.window = Window("droneMachine", Rect(20,20, 250, 400)).front;
		this.window.onClose_{
					this.clearBuffers();
					this.free;
		};

		this.note = PopUpMenu(this.window,Rect(10,10,80,20));
		this.note.items = ["C","C#","D","D#","E","F","F#","G", "G#", "A", "A#", "B"];
		this.note.action = { //arg menu;
			//[menu.value, menu.item].postln;
			this.calculateMidiNote;
};

		this.octave = PopUpMenu(this.window,Rect(100,10,80,20));
		this.octave.items = ["-5","-4","-3","-2","-1","0","1","2", "3", "4", "5"];
		this.octave.action = { //arg menu;
			//[menu.value, menu.item].postln;
			this.calculateMidiNote;
};
	}

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

	setOSCResponders {}

	setSynthDefs {
		SynthDef(\droneMachine,
			//var note = 0, oscillator = 0, trmRate = 0, tremOsc = 0, tremRangeMin = 0, tremRangeMax = 1, pannerRate = 0, pannerOsc = 0, pannerRangeMin = -1, pannerRangeMax = 0, amp = 0;

			).add;
	}
	calculateMidiNote{
		this.note.value.postln;
		this.octave.value.postln;
		(this.note.value + (this.octave.value*12)).postln;
	}

	clearBuffers { //cleanups on quit
		//this.player.free;
		this.synthOscs.do{ arg buf, index;
			buf.free;
			synthOscs.at(index).free;
		};
		synthOscs.clear;
	}

}