BGCPerfMachine {

	var <>ctrlIP,
	<>ctrlPort,
	<>s, //Server




	//AUDIO FILES RELATED
	<>thePath, //parent directory of sound files
	<>audioFilesBufferList, //list of buffers with the soundfiles
	<>fileList, //the Gui List with filenames
	<>thePath, //parent directory of sound files

	<>player; //the Synth;

	*new {
		arg ctrlIP, ctrlPort;

		ctrlIP.postln;
		ctrlPort.postln;

		^super.newCopyArgs(ctrlIP, ctrlPort).init;
	}



	init {
		arg s = Server.local;
		this.s = s;
		("Will connect to IP: " + this.ctrlIP).postln;
		("On Port : " + this.ctrlPort).postln;
		this.audioFilesBufferList = List.new();
/*
		//loadup synthdefs
		{
			this.setSynthDefs;
			this.s.sync;
		}.fork;


		//Ask for files to be used

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
		//Questionavel este free ser aqui...
		this.free;
		*/
	}


	loadFiles {
		arg paths;
		var f;
		/*
		b = NetAddr.new("127.0.0.1", 7771);    // create the NetAddr
		b.sendMsg("/hello", "there");    // send the application the message "hello" with the parameter "there"
		*/
	}

	setSynthDefs {

	}

}
