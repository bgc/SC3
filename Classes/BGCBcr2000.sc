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
			knobsRow3;

	var <controllers;

	*new{
		^super.new.initBCR2000;
	}

	initBCR2000{
		arg port = 1;
		//CHANGED FROM CONNECTALL TO A SPECIFIC PORT (default 1)
		MIDIIn.connect(port, MIDIClient.sources.at(port));

		topButtons1	= IdentityDictionary[
      \topButton1 -> BCR2000Button(\topButton1, 0)
    ];
		topButtons2	= IdentityDictionary[];
		knobButtons	= IdentityDictionary[];
		topKnobs		= IdentityDictionary[];
		knobsRow1		= IdentityDictionary[];
		knobsRow2		= IdentityDictionary[];
		knobsRow3		= IdentityDictionary[];

		controllers = IdentityDictionary.new;

		controllers.putAll(
			topButtons1,
			topButtons2,
			knobButtons,
			topKnobs,
			knobsRow1,
			knobsRow2,
			knobsRow3);
	}


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
