/*
 * SuperCollider class for BCR2000 controller
 * created by: bgc
 * based on the NanoKontrol.sc by jesusgollonet (http://github.com/jesusgollonet/NanoKontrol.sc/)
 */


BCR2000 {

	var topButtons1,
			topButtons2,
			knobButtons,
			topKnobs,
			knobsRow1,
			knobsRow2,
			knobsRow3,
			fourButtons;

	var <controllers;

	*new{
		^super.new.initBCR2000;
	}

	initBCR2000{

		//arg port = 1;
		//CHANGED FROM CONNECTALL TO A SPECIFIC PORT (default 1)
		//MIDIIn.connect(port, MIDIClient.sources.at(port));
		//Removed specific port to connectAll... was getting an error

		MIDIIn.connectAll;

		topButtons1	= IdentityDictionary[
			\topButton1 -> BCR2000Button(\topButton1, 65),
			\topButton2 -> BCR2000Button(\topButton2, 66),
			\topButton3 -> BCR2000Button(\topButton3, 67),
			\topButton4 -> BCR2000Button(\topButton4, 68),
			\topButton5 -> BCR2000Button(\topButton5, 69),
			\topButton6 -> BCR2000Button(\topButton6, 70),
			\topButton7 -> BCR2000Button(\topButton7, 71),
			\topButton8 -> BCR2000Button(\topButton8, 72)
		];

		topButtons2	= IdentityDictionary[
			\topButton21 -> BCR2000Button(\topButton21, 73),
			\topButton22 -> BCR2000Button(\topButton22, 74),
			\topButton23 -> BCR2000Button(\topButton23, 75),
			\topButton24 -> BCR2000Button(\topButton24, 76),
			\topButton25 -> BCR2000Button(\topButton25, 77),
			\topButton26 -> BCR2000Button(\topButton26, 78),
			\topButton27 -> BCR2000Button(\topButton27, 79),
			\topButton28 -> BCR2000Button(\topButton28, 80)
		];

		knobButtons	= IdentityDictionary[
			\nButton1 -> BCR2000Button(\nButton1, 33),
			\nButton2 -> BCR2000Button(\nButton2, 34),
			\nButton3 -> BCR2000Button(\nButton3, 35),
			\nButton4 -> BCR2000Button(\nButton4, 36),
			\nButton5 -> BCR2000Button(\nButton5, 37),
			\nButton6 -> BCR2000Button(\nButton6, 38),
			\nButton7 -> BCR2000Button(\nButton7, 39),
			\nButton8 -> BCR2000Button(\nButton8, 40)
		];

		topKnobs		= IdentityDictionary[
			\topKnob1 -> BCR2000Knob(\topKnob1, 1),
			\topKnob2 -> BCR2000Knob(\topKnob2, 2),
			\topKnob3 -> BCR2000Knob(\topKnob3, 3),
			\topKnob4 -> BCR2000Knob(\topKnob4, 4),
			\topKnob5 -> BCR2000Knob(\topKnob5, 5),
			\topKnob6 -> BCR2000Knob(\topKnob6, 6),
			\topKnob7 -> BCR2000Knob(\topKnob7, 7),
			\topKnob8 -> BCR2000Knob(\topKnob8, 8)
		];

		knobsRow1		= IdentityDictionary[
			\knobRow11 -> BCR2000Knob(\knobRow11, 81),
			\knobRow12 -> BCR2000Knob(\knobRow12, 82),
			\knobRow13 -> BCR2000Knob(\knobRow13, 83),
			\knobRow14 -> BCR2000Knob(\knobRow14, 84),
			\knobRow15 -> BCR2000Knob(\knobRow15, 85),
			\knobRow16 -> BCR2000Knob(\knobRow16, 86),
			\knobRow17 -> BCR2000Knob(\knobRow17, 87),
			\knobRow18 -> BCR2000Knob(\knobRow18, 88)
		];

		knobsRow2		= IdentityDictionary[
			\knobRow21 -> BCR2000Knob(\knobRow21, 89),
			\knobRow22 -> BCR2000Knob(\knobRow22, 90),
			\knobRow23 -> BCR2000Knob(\knobRow23, 91),
			\knobRow24 -> BCR2000Knob(\knobRow24, 92),
			\knobRow25 -> BCR2000Knob(\knobRow25, 93),
			\knobRow26 -> BCR2000Knob(\knobRow26, 94),
			\knobRow27 -> BCR2000Knob(\knobRow27, 95),
			\knobRow28 -> BCR2000Knob(\knobRow28, 96)
		];

		knobsRow3		= IdentityDictionary[
			\knobRow31 -> BCR2000Knob(\knobRow31, 97),
			\knobRow32 -> BCR2000Knob(\knobRow32, 98),
			\knobRow33 -> BCR2000Knob(\knobRow33, 99),
			\knobRow34 -> BCR2000Knob(\knobRow34, 100),
			\knobRow35 -> BCR2000Knob(\knobRow35, 101),
			\knobRow36 -> BCR2000Knob(\knobRow36, 102),
			\knobRow37 -> BCR2000Knob(\knobRow37, 103),
			\knobRow38 -> BCR2000Knob(\knobRow38, 104)
		];

		fourButtons = IdentityDictionary[
			\fButton1 -> BCR2000Button(\fButton1, 105),
			\fButton2 -> BCR2000Button(\fButton2, 106),
			\fButton3 -> BCR2000Button(\fButton3, 107),
			\fButton4 -> BCR2000Button(\fButton4, 108)
		];

		controllers = IdentityDictionary.new;

		controllers.putAll(
			topButtons1,
			topButtons2,
			knobButtons,
			topKnobs,
			knobsRow1,
			knobsRow2,
			knobsRow3,
			fourButtons);
	}

//TODO: the align and variable creation for easier mapping access
/*

faders{
		var align = fadergroup;
		align.order;
		^align.atAll(align.order);
	}

*/

	removeAll{
		controllers.do{|cDict|
			cDict.do{|c| c.free}
		}
	}

	doesNotUnderstand {|selector ... args|
		var controller = controllers[selector];
		^controller ?? {
			super.doesNotUnderstand(selector, args)
		};
	}

}


BCR2000Controller {

	var <key, <num;
	var responder;

	*new{|... args|
		^super.newCopyArgs(*args);
	}

	onChanged_{|action|
		responder= MIDIdef.cc(key, {|val| action.value(val) }, num);
	}

	free{
		responder.free;
	}
}


BCR2000ControllerNote {

	var <key, <num;
	var responder;

	*new{|... args|
		^super.newCopyArgs(*args);
	}

	onChanged_{|action|
		responder= MIDIdef.noteOn(key, {|val| action.value(val) }, num);
	}

	free{
		responder.free;
	}
}


//the R8 buttons send cc events
BCR2000Button : BCR2000Controller {

	var responder;

	onChanged_{|action|
		responder= MIDIdef.cc(
			(key).asSymbol, {
				|val|
				action.value(val)
			},
			num);
		}

/*  onRelease_{|action|
		releaseresp= MIDIdef.noteOff((key++"release").asSymbol, {|val| if(val==0, { action.value(val) }) }, num);
	}
*/

	free{
		responder.free;
	}
}


//the R8 buttons send cc events
BCR2000Knob : BCR2000Controller {

	var responder;

	onChanged_{|action|
		responder= MIDIdef.cc((key).asSymbol, {|val| action.value(val)  }, num);
	}

/*  onRelease_{|action|
		releaseresp= MIDIdef.noteOff((key++"release").asSymbol, {|val| if(val==0, { action.value(val) }) }, num);
	}
*/
	free{
		responder.free;
	}
}



/*


MIDIIn.connectAll;
MIDIIn.control = {| port , chan, num, val| [chan,num,val].postln};

n = BCR2000.new;


n.topButton1.onChanged = {|val| 
		"fader 1 changed".postln;
		val.postln;
};

n.topKnob1.onChanged = {
	|val|
	val.postln;
};


n.topButtons1.do{|knob, i| 
		knob.onChanged= {|val| ("knob"+(i+1)).postln; val.postln }
};


n.removeAll;
n.free;


MIDIClient.sources;



*/