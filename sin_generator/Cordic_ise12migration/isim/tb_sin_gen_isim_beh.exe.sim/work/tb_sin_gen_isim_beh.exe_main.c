/**********************************************************************/
/*   ____  ____                                                       */
/*  /   /\/   /                                                       */
/* /___/  \  /                                                        */
/* \   \   \/                                                       */
/*  \   \        Copyright (c) 2003-2009 Xilinx, Inc.                */
/*  /   /          All Right Reserved.                                 */
/* /---/   /\                                                         */
/* \   \  /  \                                                      */
/*  \___\/\___\                                                    */
/***********************************************************************/

#include "xsi.h"

struct XSI_INFO xsi_info;

char *IEEE_P_0774719531;
char *IEEE_P_1242562249;
char *IEEE_P_2592010699;
char *STD_STANDARD;
char *IEEE_P_3564397177;
char *STD_TEXTIO;
char *IEEE_P_3499444699;
char *VL_P_2533777724;


int main(int argc, char **argv)
{
    xsi_init_design(argc, argv);
    xsi_register_info(&xsi_info);

    xsi_register_min_prec_unit(-12);
    ieee_p_2592010699_init();
    ieee_p_3499444699_init();
    ieee_p_0774719531_init();
    ieee_p_1242562249_init();
    std_textio_init();
    ieee_p_3564397177_init();
    vl_p_2533777724_init();
    work_m_11491833963429943931_2073120511_init();
    unisims_ver_m_05425408596767641966_1593237687_init();
    unisims_ver_m_05411624385900261925_1759035934_init();
    unisims_ver_m_16560396281061723760_3367321443_init();
    unisims_ver_m_00370795044214117770_0257217429_init();
    unisims_ver_m_00370795044214117770_0374334164_init();
    unisims_ver_m_00370795044214117770_2336946039_init();
    unisims_ver_m_04211380916625289904_3841093685_init();
    unisims_ver_m_02598168256336594463_0679316750_init();
    unisims_ver_m_00445221965264662842_2449448540_init();
    unisims_ver_m_18219952033656923898_3852734344_init();
    unisims_ver_m_04745495493673810507_2091797430_init();
    unisims_ver_m_11630753370473123952_2467748173_init();
    unisims_ver_m_11630753370473123952_3102277666_init();
    unisims_ver_m_11630753370473123952_4132015673_init();
    unisims_ver_m_11630753370473123952_2639295204_init();
    unisims_ver_m_11630753370473123952_0861236213_init();
    unisims_ver_m_05231626732346724408_3459089569_init();
    unisims_ver_m_06675532837520590383_3378826912_init();
    unisims_ver_m_06675532837520590383_1925941802_init();
    unisims_ver_m_08352317005144616203_2771340377_init();
    unisims_ver_m_01238863293647809410_1058825862_init();
    work_m_01985310321742546741_0483295857_init();
    work_a_1132065030_3212880686_init();
    work_a_2921116039_2372691052_init();


    xsi_register_tops("work_a_2921116039_2372691052");
    xsi_register_tops("work_m_11491833963429943931_2073120511");

    IEEE_P_0774719531 = xsi_get_engine_memory("ieee_p_0774719531");
    IEEE_P_1242562249 = xsi_get_engine_memory("ieee_p_1242562249");
    IEEE_P_2592010699 = xsi_get_engine_memory("ieee_p_2592010699");
    xsi_register_ieee_std_logic_1164(IEEE_P_2592010699);
    STD_STANDARD = xsi_get_engine_memory("std_standard");
    IEEE_P_3564397177 = xsi_get_engine_memory("ieee_p_3564397177");
    STD_TEXTIO = xsi_get_engine_memory("std_textio");
    IEEE_P_3499444699 = xsi_get_engine_memory("ieee_p_3499444699");
    VL_P_2533777724 = xsi_get_engine_memory("vl_p_2533777724");

    return xsi_run_simulation(argc, argv);

}
