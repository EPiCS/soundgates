// Generate coefficients for different cutoff frequencies
// ------------------------------------------------------------

Fs              = 44100;     // sample rate
Cmax            = 8000;
FirOrder        = 29;
FixPtScale      = 30;   // Q1.30
FilterType      = 'bp';
OutputFilename  = ['fir_coeff_', FilterType, '_.cpp']
// ------------------------------------------------------------
cd('/home/lukas/uni/master/pg-soundgates/software/zynq/SoundComponents/src/fir');

fid = mopen(char(OutputFilename), 'wt');

mfprintf(fid, '#include ""fir_coeff.hpp""\n\n');
mfprintf(fid, 'int32_t coeff_%s[][%i] = { \n',FilterType, FirOrder);

for i=0:Cmax
    
    nc= i / Fs;
    mfprintf(fid, '{');

    // Calculate coefficients
    [h, hm, fr] = wfir (FilterType, FirOrder, [nc 0], 'hm', [0 0]);
    if sum(h) > 0 then
        h = h / sum(h);
    end
    
    
    // Scale up
    h = round(h* 2^FixPtScale);
    
    
    
    for j=1:length(h)-1;
        mfprintf(fid, '%i,', h(j));

    end
    mfprintf(fid, '%i', h(length(h)));

    
    if i < Cmax then
        mfprintf(fid, '},\n');

    else
        mfprintf(fid, '}\n');
    end
end

mfprintf(fid, '}; \n');

mclose(fid);
