(
{
	var sin1, //Sine Oscillator
			sin2, //Sine Oscillator
			sin3, //Sine Oscillator
			sin4, //Sine Oscillator
			sin1Freq,
			sin2Freq,
			sin3Freq,
			sin4Freq,
			sin1FDiBrown,
			sin2FDiBrown,
			sin3FDiBrown,
			sin4FDiBrown
;

	sin1 = SinOsc.ar();
	sin2 = SinOsc.ar();
	sin3 = SinOsc.ar();
	sin4 = SinOsc.ar();

}.play;
)



































s.makeGui;
b = Buffer.read(s,Platform.resourceDir +/+ "sounds/a11wlk01-44_1.aiff");
b.plot;
b.numFrames;


(
SynthDef("help-LoopBuf",{
	arg numChannels = 1,
		bufnum = 0,
		rate = 1,
		gate = 1,
		startPos = 0,
		startLoop = 0,
		endLoop = 1000,
		interpolation = 4;

	var signal;
	signal = LoopBuf.ar(1,bufnum, BufRateScale.kr(bufnum) * rate, gate, startPos, startLoop, endLoop, interpolation);
	Out.ar(0,signal);

}).add;

)

s.sendMsg("/s_new", "help-LoopBuf", 3000, 1, 0, \bufnum, b.bufnum, \startLoop, 5000, \endLoop, 1000);
s.sendMsg("/n_set", 3000, \startLoop, 20000, \endLoop, 33000) // change loop points

s.sendMsg("/n_set", 3000, \interpolation, 4);

