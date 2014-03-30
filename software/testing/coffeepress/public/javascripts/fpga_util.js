if (typeof String.prototype.startsWith != 'function') {
  // see below for better implementation!
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}


var zedboard;

zedboard = {
  capacity: {
    luts: 53200,
    registers: 106400,
    brams: 140,
    dsps: 220
  },
  components: {
    sine: {
      luts: 2258,
      registers: 1767,
      brams: 1,
      dsps: 0
    },
    noise: {
      luts: 567,
      registers: 319,
      brams: 1,
      dsps: 0
    },
    saw: {
      luts: 739,
      registers: 447,
      brams: 1,
      dsps: 0
    },
    triangle: {
      luts: 781,
      registers: 448,
      brams: 1,
      dsps: 0
    },
    square: {
      luts: 1083,
      registers: 1321,
      brams: 1,
      dsps: 0
    },
    add: {
      luts: 843,
      registers: 565,
      brams: 1,
      dsps: 0
    },
    sub: {
      luts: 843,
      registers: 565,
      brams: 1,
      dsps: 0
    },
    mul: {
      luts: 883,
      registers: 533,
      brams: 1,
      dsps: 4
    },
    delay: {
      luts: 100,
      registers: 100,
      brams: 100,
      dsps: 100
    },
    adsr: {
      luts: 1301,
      registers: 1358,
      brams: 1,
      dsps: 1
    },
    ramp: {
      luts: 1265,
      registers: 1384,
      brams: 1,
      dsps: 1
    },
    amp: {
      luts: 7900,
      registers: 4551,
      brams: 1,
      dsps: 6
    },
    triangle: {
      luts: 739,
      registers: 447,
      brams: 1,
      dsps: 0
    },
    fir: {
      luts: 1762,
      registers: 1925,
      brams: 1,
      dsps: 28
    },
    pwm: {
      luts: 822,
      registers: 433,
      brams: 1,
      dsps: 0
    }
  }
};
