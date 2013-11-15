// Sine component
// source_addr : std_logic_vector(C_FIFO_WIDTH - 1 downto 0); -- memory address of the data source buffer
//        src_len : std_logic_vector(C_FIFO_WIDTH - 1 downto 0); -- data length of the source buffer
//        dest_addr : std_logic_vector(C_FIFO_WIDTH - 1 downto 0); -- memory address destination buffer
//        opt_arg_addr : std_logic_vector(C_FIFO_WIDTH - 1 downto 0); -- memory address of optional arguments
//        opt_arg_len : std_logic_vector(C_FIFO_WIDTH - 1 downto 0); -- number of optional arguments

typedef struct {
    int src_addr;
    int src_len;
    int dest_addr;
    int opt_arg_addr;
    int opt_arg_len;
} sHeader;

//typedef struct {
//    sBase base;
//    int *offset;
//    int *increment;
//} sHeaderSine;
