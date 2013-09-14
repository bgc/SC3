SynthDef(\bru,{
	arg note = 64,
	amp1 = 1,
	amp2 = 1,
	amp3 = 1,
	bwr1 = 0.010,
	bwr2 = 0.010,
	bwr3 = 0.010;

	var res1,
	res2,
	res3,
	mixer;

	res1 = Resonz.ar(
		in: WhiteNoise.ar(0.5),
		freq: note.midicps,
		bwr: bwr1,
		mul: amp1
	);

	res2 = Resonz.ar(
		in: WhiteNoise.ar(0.5),
		freq: (note+12).midicps,
		bwr: bwr2,
		mul: amp2
	);

	res3 = Resonz.ar(
		in: WhiteNoise.ar(0.5),
		freq: (note+24).midicps,
		bwr: bwr3,
		mul: amp3
	);

	mixer = Mix.new([res1, res2, res3]);

	Out.ar(0, mixer);
}).add;