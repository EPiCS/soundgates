// Generate coefficients for different cutoff frequencies
// ------------------------------------------------------------

Fs              = 44100;     // sample rate
Cmax            = 8000;
FirOrder        = 29;
FixPtScale      = 14;
FilterType      = 'hp';
OutputFilename  = ['fir_coeff_', FilterType, '_.hpp']
// ------------------------------------------------------------

fid = mopen(char(OutputFilename), 'wt');

mfprintf(fid, '#include <stdint.h>\n\n');
mfprintf(fid, 'int32_t coeff_%s[][%i] = { \n',FilterType, FirOrder);

for i=0:Cmax
    
    nc= i / Fs;
    mfprintf(fid, '{');

    // Calculate coefficients
    [h, hm, fr] = wfir (FilterType, FirOrder, [nc 0], 'hm', [0 0]);
    
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
