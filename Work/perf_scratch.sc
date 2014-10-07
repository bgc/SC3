Help.gui;

Server.local = s = Server.default;

s.boot;

s.makeGui;

StageLimiter.activate;


(
//criar o array com os valores de divisão do espectro e as suas frequencias
l = 32;
m = 13000;
n = 55;

a = Array.fill(l+1, {arg i; (1/l)*i});
a.sort;
a.removeAt(0);
Post << a;

b = Array.fill(l,{0});


a.do({arg item, i; b.put(i,item.lincurve(0, 1, n, m, curve: 4))});

b.removeAt(b.size-1);
Post << b;
)

z = Buffer.alloc(s,2048,1);

/*

You can use f = { |freq| 2 * freq / Server.default.sampleRate } to
determine the cut off frequency for your brick wall. Negative is a low
pass filter, positive is a high pass filter, so if you wanted, say, a
band from 8000Hz to 10000Hz you could use f.value(10000).neg and then
f.value(8000) with two PV_BrickWalls in series.



How about:

Server.default = s = Server.internal.boot;
f = FreqScope.new;
(SynthDef(\bandpass_brickwall, { arg out=0,bufnum=0;
				var in, chain, valor, pct;
				in = WhiteNoise.ar(0.2);
				chain = FFT(LocalBuf(2048), in);
				valor = MouseX.kr(0, 22050);
				chain = PV_BrickWall(chain,	SampleDur.ir * (valor * 2));
				chain = PV_BrickWall(chain, (SampleDur.ir * ((valor + 2000) * 2)) - 1);
				Out.ar(out, 0.5 * IFFT(chain));
}).play(s,[\out,0]);
)




*/

e = Buffer.alloc(s,2048,1);
f = Buffer.alloc(s, 2048,1);
g = Buffer.alloc(s,2048,1);
h = Buffer.alloc(s, 2048,1);

(
SynthDef("help-brick", { arg out=0, bufnum=0,bufnum2=1, bufnum3 = 2;
	var in, chain, chain2, chain3, output;
	in = {WhiteNoise.ar(0.2)};
	chain = FFT(bufnum, in);
	chain2 = PV_Copy(bufnum, bufnum2);
	chain3 = PV_Copy(bufnum, bufnum3);


	//chain = PV_BrickWall(chain, SampleDur.ir * (116.58162013552 * 2)-1);
	chain = PV_BrickWall(chain, SampleDur.ir * (458.48598039825 * 2));
	chain = PV_BrickWall(chain, SampleDur.ir * (705.28927216187 * 2)-1);

	chain2 = PV_BrickWall(chain2, SampleDur.ir * (705.28927216187 * 2));
	chain2 = PV_BrickWall(chain2, SampleDur.ir * (1022.1909717086 * 2)-1);

	chain3 = PV_BrickWall(chain3, SampleDur.ir * (6010.2429142225 * 2));
	chain3 = PV_BrickWall(chain3, SampleDur.ir * (7833.8862824646 * 2)-1);

	Out.ar(out, Pan2.ar(IFFT(chain),1,1) + Pan2.ar(IFFT(chain2),-1,1) + Pan2.ar(IFFT(chain3), 0, 1));
}).play(s,[\out, 0, \bufnum, e.bufnum, \bufnum2, f.bufnum, \bufnum3, g.bufnum]);
)



o = BGCPerfMachine.new("127.0.0.1", 333);
o.ctrlPort;




a = ["there", "is", "no", "Escape", "put", "down"];
a = Array.new();
a = a + ["there", "is", "no", "Escape"];
b = NetAddr.new("192.168.1.34", 8000);
b.sendMsg("/Menu/populateMenu", *a);
// send the application the message "hello" with the parameter "there"
a.asCompileString
a.join(",")
*a
a.asString.interpret
a.streamContents;
a.asCompileString.asOSCArgBundle
~player;
~player.free;








(
f = { |action|
	var myAddr = Ref.new;
	var before = NetAddr.broadcastFlag;
	NetAddr.broadcastFlag = true;
	OSCresponder(nil, '/getMyIP', { |t,r,msg,addr|
		action.(addr);
		NetAddr.broadcastFlag = before;
	}).add;

	NetAddr("255.255.255.255", NetAddr.langPort).sendMsg('/getMyIP');
};

)

f.({ |addr| addr.postln });


OSCFunc.trace(true);
// Turn posting on
OSCFunc.trace(false);


(
OSCdef.new(
	\menu, {
		arg msg, time, address, port;
		"comming from: " + msg[0].postln;
		msg[1].postln;

	},
	'Container/Menu/selection'
);
)



x = BGCPerfMachine.new("192.168.1.34", 8000);
x.free();
x.selectFolder;
x;
