(
{
	var sin1, //Sine Oscillator
			sin2, //Sine Oscillator
			sin3, //Sine Oscillator
			sin4, //Sine Oscillator
			freqArray,
			sin1FreqA, //Sine Oscillator1 Frequencys Array
			sin2FreqA, //Sine Oscillator2 Frequencys Array
			sin3FreqA, //Sine Oscillator3 Frequencys Array
			sin4FreqA, //Sine Oscillator4 Frequencys Array
			sin1FDiBrown,
			sin2FDiBrown,
			sin3FDiBrown,
			sin4FDiBrown,
			trig1,
			trig2,
			trig3,
			trig4,
			freq1,
			freq2,
			freq3,
			freq4
;

	freqArray = [
		261.6256,
		277.1826,
		293.6648,
		311.1270,
		329.6276,
		349.2282,
		369.9944,
		391.9954,
		415.3047,
		440.0000,
		466.1638,
		493.8833];

	sin1FreqA = freqArray.scramble;
	sin2FreqA = freqArray.scramble;
	sin3FreqA = freqArray.scramble;
	sin4FreqA = freqArray.scramble;



	trig1 = Impulse.kr(rrand(0.1, 5.0));
	trig2 = Impulse.kr(rrand(0.1, 5.0));
	trig3 = Impulse.kr(rrand(0.1, 5.0));
	trig4 = Impulse.kr(rrand(0.1, 5.0));





/*
	sin1 = SinOsc.ar();
	sin2 = SinOsc.ar();
	sin3 = SinOsc.ar();
	sin4 = SinOsc.ar();
*/

}.play;
)




(
{
	var a, freq, trig;
	a = Dbrown(-1.0, 1.0, 0.1, inf);
	trig = Impulse.kr(MouseX.kr(1, 40, 1));
	freq = Demand.kr(trig, 0, a) * 30 + 340;
	Poll.kr(trig, a, "Dibrown");
	SinOsc.ar(freq) * 0.1
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

