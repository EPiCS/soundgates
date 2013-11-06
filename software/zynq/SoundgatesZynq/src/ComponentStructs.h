// Sine component

typedef struct {
    int sample_count;
    int *target_addr;
} sBase;

typedef struct {
    sBase base;
    int *offset;
    int *increment;
} sHeaderSine;
