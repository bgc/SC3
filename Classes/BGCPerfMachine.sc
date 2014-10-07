BGCPerfMachine {

	var <>ctrlIP,
	<>ctrlPort,
	<>s, //Server




	//AUDIO FILES RELATED

	<>audioFilesBufferList, //list of buffers with the soundfiles
	<>fileList, //the Gui List with filenames
	<>filePath, //the path of file

	<>arraySize,
	<>minFreq,
	<>maxFreq,
	<>freqsArray,

	<>player; //the Synth;

	*new {
		arg ctrlIP, ctrlPort;


		^super.newCopyArgs(ctrlIP, ctrlPort).init;
	}



	init {

		arg s = Server.local;

		this.s = s;

		("Will connect to IP: " + this.ctrlIP).postln;
		("On Port : " + this.ctrlPort).postln;

		this.audioFilesBufferList = List.new();

		this.fileList = Array.new();
		this.filePath = Array.new();

		this.arraySize = 24;

		this.minFreq = 55;
		this.maxFreq = 13000;

		this.freqsArray = Array.fill(this.arraySize+1, {
			arg i;
			(1/this.arraySize)*i
		});

		this.freqsArray.sort;
		this.freqsArray.removeAt(0);

		/*
		//loadup synthdefs
		{
			this.setSynthDefs;
			this.s.sync;
		}.fork;
		*/

		//Ask for files to be used
		this.selectFolder;

		/*
		//Questionavel este free ser aqui...
		this.free;
		*/





	}


	loadFiles {
		arg paths;

		var setupLMenu;

		this.clearBuffers;

		this.fileList = Array.new(paths.size);
		this.filePath = Array.new(paths.size);


		paths[0].postln;
		//this.thePath = paths[0].dirname;


		paths.do{
			arg path;

			var numchnls;

			["Trying to load file: " ++ path.basename].postln;
			if( this.isAudioFile(path.basename),
				{
					[\loaded, path.basename].postln;

					this.fileList.add(path.basename);
					this.filePath.add(path.dirname);

					numchnls = SoundFile(path).numChannels;
					if(numchnls == 2,{
						this.audioFilesBufferList.add(Buffer.read(this.s,path, channels: [0]));
					});
					if( numchnls == 1, {
						this.audioFilesBufferList.add(Buffer.readChannel(this.s,path))
					});
				},
				{
					["Failed to load file: " ++ path.basename].postln;
				}
			);
		};



		if(this.fileList.size > 0,
			{
				setupLMenu = NetAddr.new(this.ctrlIP, this.ctrlPort);
				setupLMenu.sendMsg("/Menu/populateMenu", *this.fileList);
			},{
				"You don't know what you are doing!".postln;
			}
		);



	}

	setSynthDefs {

	}

/*
/Menu/selection, 1
*/

	clearBuffers { //cleanups on quit
		this.player.free;

		this.audioFilesBufferList.do{ arg buf, index;
			buf.free;
			audioFilesBufferList.at(index).free;
		};

		this.audioFilesBufferList.clear;
		this.fileList = Array.new();

	}


	selectFolder {

		QDialog.openPanel(
			okFunc: {
				arg selection;
				this.loadFiles(selection)
			},
			cancelFunc: {
				"cancelled".postln;
				this.free;
			},
			multipleSelection: true
		);

	}

	isAudioFile {//check is viable audiofile...
		arg path;
		^(
			path.endsWith("wav") ||
			path.endsWith("wave") ||
			path.endsWith("aif") ||
			path.endsWith("aiff") ||
			path.endsWith("WAV") &&
			not(path.endsWith("asd"))
		)
	}

}
