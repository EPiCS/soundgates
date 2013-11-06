// Sine component

struct sBase {
    int sample_count;
    int *target_addr;
}

struct sComponent_sine {
    struct sBase base;
    int *offset;
}
