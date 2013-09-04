//bgc
BGCSamplePlayer {

	var <>window, //the window...
	<>position, //wassuposed to be a int, but is not needed... BGC used instead
	<>list, //the Gui List with filenames
	<>plotter, //soundfile view
	<>thePath, //parent directory of sound files
	<>bufferList, //list of buffers with the soundfiles
	<>player, //the synth
	<>slider, //rate slider
	<>volSlider,
	<>slideCtl, //ControlSpec for slider
	<>speedText, //number box to display the slider values (after ControlSpec map)
	<>s,
	<>meter, //Volume meter
	<> oscr;
	*new {
		^super.new.init;
	}

	init { |position = "BGC", s = Server.local|
		this.position = position;
		this.s = s;
		this.bufferList = List.new();
		{
			this.setSynthDefs;
			this.s.sync;
		}.fork;

		QDialog.openPanel(
			okFunc: { |selection| this.loadFiles(selection)},
			cancelFunc: {
					"cancelled".postln;
					this.free;
			},
			multipleSelection: true
		);
		this.free;
	}

	initGui{ //create all the gui items & actions
		var slideLayout,
		graphLayout;
		window = Window("Sample Player "++ this.position, Rect(20,20, 250, 400)).front;
		window.onClose_{
					this.clearBuffers();
					this.free;
		};

		this.slideCtl = ControlSpec(-2.0, 2.0, \lin, 0.01, 1, "Speed");

		this.plotter = QSoundFileView(this.window, Rect(20,20, 300, 60));

		this.speedText = TextField(this.window, Rect(10,10,50, 20)).value_(1.00);
		this.speedText.background_(Color.grey);
		this.speedText.stringColor_(Color.white);
		this.speedText.align_(\center);

		this.list = QListView( selectionMode: \single );
		this.list.action_{ arg me;
			var f = SoundFile.new;
			f.openRead(this.thePath++"/"++list.items[me.value]);

			this.plotter.soundfile = f;
			this.plotter.read(0, f.numFrames);
			this.plotter.refresh;

			this.bufferList.at(me.value).bufnum.postln;
			this.player.set(\bufnum, this.bufferList.at(me.value).bufnum);
		};
		this.meter = LevelIndicator(window, Rect(10, 10, 20, 160));
		this.meter.warning = 0.6;
		this.meter.critical = 0.9;
		this.meter.value = 0.7;
		//Setup OSCResponder
		this.setOSCResponders;
		this.volSlider = Slider(window, Rect(10, 30, 50, 160))
			.value_(1)
			.orientation_(\vertical)
			.action_({
				this.player.set(\vol,this.volSlider.value);
			});

		this.slider = Slider(window, Rect(20, 60, 150, 20))
			.value_(this.slideCtl.unmap(1))
			.action_({
				this.speedText.value_(this.slideCtl.map(this.slider.value));
				this.player.set(\rate,this.slideCtl.map(this.slider.value));
			});
		this.window.layout = HLayout();
		graphLayout = VLayout();

		graphLayout.add(list);
		graphLayout.add(plotter.maxHeight_(80));

		slideLayout = HLayout();
		slideLayout.add(this.speedText.maxHeight_(30).maxWidth_(50));
		slideLayout.add(slider.maxHeight_(30));
		graphLayout.add(slideLayout);
		this.window.layout.add(graphLayout);
		this.window.layout.add(meter.maxWidth_(10).minWidth_(10));
		this.window.layout.add(volSlider.maxWidth_(15).minWidth_(15));
		this.window.front;
	}

	loadFiles { |paths| //initialize the GUI, load selected files
		var f;
		this.initGui();
		paths[0].postln;
		this.thePath = paths[0].dirname;

		paths.do{ |path|
			var numchnls;
			["Trying to load file: " ++ path.basename].postln;
			if( this.isAudioFile(path.basename),
				{
					[\loaded, path.basename].postln;
					this.list.items_(this.list.items.add(path.basename));
					numchnls = SoundFile(path).numChannels;
					if(numchnls == 2,{
						this.bufferList.add(Buffer.read(this.s,path));
					});
					if( numchnls == 1, {
						this.bufferList.add(Buffer.readChannel(this.s,path,channels:[0,0]))
					});
				},
				{
					["Failed to load file: " ++ path.basename].postln;
				}
			);
		};

		f = SoundFile.new;
		f.openRead(this.thePath++"/"++list.items[0]);
		this.player = Synth(\playBufStereo, [\out, 0, \bufnum, 0, \rate, 1]);
		this.plotter.soundfile = f;
		this.plotter.read(0, f.numFrames);
		this.plotter.refresh;
	}

	isAudioFile {//check is viable audiofile...
		arg path;
		^(
			path.contains("wav") ||
			path.contains("wave") ||
			path.contains("aif") ||
			path.contains("aiff") ||
			path.contains("WAV") &&
			not(path.contains("asd"))
		)
	}

	clearBuffers { //cleanups on quit
		this.player.free;
		this.bufferList.do{ arg buf, index;
			buf.free;
			bufferList.at(index).free;
		};
		bufferList.clear;
	}



//Method to setup OSCResponder.
	setOSCResponders {

		this.oscr = OSCresponder(this.s.addr,'/tr',{ |time,responder,msg|
		//	OSC id lookup cheat sheet
		//	0	Input Levels
		//	1	Beat One
		//	2	Crotchets
		//	3	Quavers
		//	4	Onsets Frames
		//	5	SemiQuavers
		//	6	Onset loop Level
		//	7	Onset LED

		switch (msg[2])
			{0}	{
					{
						this.meter.value = msg[3].ampdb.linlin(-40, 0, 0, 1);
						this.meter.peakLevel = msg[4].ampdb.linlin(-40, 0, 0, 1);
					}.defer
				}
		/*	{1}	{ this.beatOneOSCAction }
			{2}	{ this.crotchetOSCAction }
			{3}	{ this.quaverOSCAction }
			{4}	{
					if(nowAnalysing) {
						onsetFrames.add(msg[3])
					};
				}
			{5}	{ this.semiquaverOSCAction }
			{6}	{
					{
						onsetMeter.value = msg[3].ampdb.linlin(-40, 0, 0, 1);
						onsetMeter.peakLevel = msg[4].ampdb.linlin(-40, 0, 0, 1);
					}.defer
				}
			{7}	{
					{
						{onsetLED.value = msg[3]/msg[3]}.defer;
						0.1.wait;
						{onsetLED.value = 0}.defer ;
					}.fork
				};
*/
		}).add;

	}

setSynthDefs {
	SynthDef(\playBufStereo, {| out = 0, bufnum = 0, rate = 1 , vol = 1|
		var scaledRate, player, imp, delimp;
		scaledRate = BufRateScale.kr(bufnum);
		player = PlayBuf.ar(1, bufnum, scaledRate * rate, loop: 1, doneAction:0);
		imp = Impulse.kr(10);
		delimp = Delay1.kr(imp);
		SendReply.kr(imp, '/tr', [Amplitude.kr(player*vol), K2A.ar(Peak.ar(player*vol, delimp).lag(0, 3))], 0);
		Out.ar(out, [player*vol, player*vol])
		}).add;
	}



}