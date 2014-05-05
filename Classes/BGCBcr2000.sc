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
}
