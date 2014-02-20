
//clear plot
clf()

Fs = 44100;     // sample rate
fc = 100;      // cutoff
fc_1 = 2000
nc = fc / Fs;   // normalized cutoff
nc_1 = fc_1 / Fs;
f1 = 1000;      // 1000 Hz
f2 = 4000;      // 4000 Hz

scale = 15;

fir_order = 29;

t = 0:1/Fs:1;

n = length(t);

fv = linspace(0,Fs,length(t)); // frequency vectors

[h, hm, fr] = wfir ('bp', fir_order, [nc nc_1], 'hm', [0 0]); 

h = h/ sum(h);

x1 = sin(2*%pi*f1*t);

x2 = sin(2*%pi*f2*t);

x = x1 + x2;

// Apply filter
//x = 0.8 * squarewave(f1*t);

z = poly(0, 'z');
hz = syslin('d',poly(h,'z','c')./z**33);

yhz = flts(x, hz);
[hzm,fr]=frmag(hz, 1024);
fr2 = fr.*Fs;

X = fft(x)./(length(x)/2);
Y = fft(yhz)./(length(x)/2);

subplot(411)
plot(fv(1:n/2),abs(X(1:n/2)),fr2,hzm);

subplot(412)
plot(t(1:1000), x(1:1000));

subplot(413)
plot(fv(1:n/2),abs(Y(1:n/2)),fr2,hzm);

subplot(414)
plot(t(1:1000), yhz(1:1000));
disp(h);
for i = 1:fir_order
    mprintf('to_signed(%i, 16),\n', round(h(i) * 2^scale) );
end
