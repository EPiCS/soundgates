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

/* This file is designed for use with ISim build 0x61e1bd6e */

#define XSI_HIDE_SYMBOL_SPEC true
#include "xsi.h"
#include <memory.h>
#ifdef __GNUC__
#include <stdlib.h>
#else
#include <malloc.h>
#define alloca _alloca
#endif
static const char *ng0 = "/home/hendrik/Soundgates/Cordic/sin_generator.vhd";
extern char *IEEE_P_2592010699;
extern char *IEEE_P_0774719531;
extern char *IEEE_P_3564397177;
extern char *STD_TEXTIO;

unsigned char ieee_p_0774719531_sub_1306455576395559435_774719531(char *, char *, char *, char *, char *);
unsigned char ieee_p_0774719531_sub_1306455576397931277_774719531(char *, char *, char *, char *, char *);
char *ieee_p_0774719531_sub_1496620905533649268_774719531(char *, char *, char *, char *, char *, char *);
char *ieee_p_0774719531_sub_1496620905533721142_774719531(char *, char *, char *, char *, char *, char *);
unsigned char ieee_p_2592010699_sub_2763492388968962707_2592010699(char *, char *, unsigned int , unsigned int );
unsigned char ieee_p_2592010699_sub_374109322130769762_2592010699(char *, unsigned char );
void ieee_p_3564397177_sub_2250825304603680424_3564397177(char *, char *, char *, char *, char *, unsigned char , int );


static void work_a_1132065030_3212880686_p_0(char *t0)
{
    char *t1;
    char *t2;
    char *t3;
    char *t4;
    char *t5;
    char *t6;
    char *t7;

LAB0:    xsi_set_current_line(89, ng0);

LAB3:    t1 = (t0 + 2792U);
    t2 = *((char **)t1);
    t1 = (t0 + 5864);
    t3 = (t1 + 56U);
    t4 = *((char **)t3);
    t5 = (t4 + 56U);
    t6 = *((char **)t5);
    memcpy(t6, t2, 16U);
    xsi_driver_first_trans_fast(t1);

LAB2:    t7 = (t0 + 5704);
    *((int *)t7) = 1;

LAB1:    return;
LAB4:    goto LAB2;

}

static void work_a_1132065030_3212880686_p_1(char *t0)
{
    char *t1;
    char *t2;
    unsigned char t3;
    unsigned char t4;
    char *t5;
    char *t6;
    char *t7;
    char *t8;
    char *t9;
    char *t10;
    char *t11;
    char *t12;
    char *t13;
    char *t14;
    char *t15;
    char *t16;

LAB0:    xsi_set_current_line(90, ng0);
    t1 = (t0 + 1032U);
    t2 = *((char **)t1);
    t3 = *((unsigned char *)t2);
    t4 = (t3 == (unsigned char)3);
    if (t4 != 0)
        goto LAB3;

LAB4:
LAB5:    t10 = (t0 + 1992U);
    t11 = *((char **)t10);
    t10 = (t0 + 5928);
    t12 = (t10 + 56U);
    t13 = *((char **)t12);
    t14 = (t13 + 56U);
    t15 = *((char **)t14);
    memcpy(t15, t11, 16U);
    xsi_driver_first_trans_fast_port(t10);

LAB2:    t16 = (t0 + 5720);
    *((int *)t16) = 1;

LAB1:    return;
LAB3:    t1 = (t0 + 1832U);
    t5 = *((char **)t1);
    t1 = (t0 + 5928);
    t6 = (t1 + 56U);
    t7 = *((char **)t6);
    t8 = (t7 + 56U);
    t9 = *((char **)t8);
    memcpy(t9, t5, 16U);
    xsi_driver_first_trans_fast_port(t1);
    goto LAB2;

LAB6:    goto LAB2;

}

static void work_a_1132065030_3212880686_p_2(char *t0)
{
    char t7[16];
    char t21[16];
    char *t1;
    unsigned char t2;
    char *t3;
    char *t4;
    unsigned char t5;
    unsigned char t6;
    char *t8;
    char *t9;
    char *t10;
    char *t11;
    char *t12;
    char *t13;
    char *t14;
    char *t15;
    char *t16;
    unsigned char t17;
    unsigned char t18;
    char *t19;
    char *t20;
    int t22;
    unsigned int t23;

LAB0:    xsi_set_current_line(102, ng0);
    t1 = (t0 + 2432U);
    t2 = ieee_p_2592010699_sub_2763492388968962707_2592010699(IEEE_P_2592010699, t1, 0U, 0U);
    if (t2 != 0)
        goto LAB2;

LAB4:
LAB3:    t1 = (t0 + 5736);
    *((int *)t1) = 1;

LAB1:    return;
LAB2:    xsi_set_current_line(103, ng0);
    t3 = (t0 + 2952U);
    t4 = *((char **)t3);
    t5 = *((unsigned char *)t4);
    t6 = (t5 == (unsigned char)2);
    if (t6 != 0)
        goto LAB5;

LAB7:    xsi_set_current_line(106, ng0);
    t1 = (t0 + 2792U);
    t3 = *((char **)t1);
    t1 = (t0 + 9216U);
    t4 = (t0 + 1352U);
    t8 = *((char **)t4);
    t4 = (t0 + 9120U);
    t9 = ieee_p_0774719531_sub_1496620905533721142_774719531(IEEE_P_0774719531, t7, t3, t1, t8, t4);
    t10 = (t0 + 5992);
    t11 = (t10 + 56U);
    t12 = *((char **)t11);
    t13 = (t12 + 56U);
    t14 = *((char **)t13);
    memcpy(t14, t9, 16U);
    xsi_driver_first_trans_fast(t10);

LAB6:    xsi_set_current_line(109, ng0);
    t1 = (t0 + 2792U);
    t3 = *((char **)t1);
    t1 = (t0 + 9216U);
    t4 = (t0 + 3248U);
    t8 = *((char **)t4);
    t4 = (t0 + 9184U);
    t5 = ieee_p_0774719531_sub_1306455576397931277_774719531(IEEE_P_0774719531, t3, t1, t8, t4);
    if (t5 == 1)
        goto LAB11;

LAB12:    t9 = (t0 + 2792U);
    t10 = *((char **)t9);
    t9 = (t0 + 9216U);
    t11 = (t0 + 3368U);
    t12 = *((char **)t11);
    t11 = (t0 + 9200U);
    t6 = ieee_p_0774719531_sub_1306455576395559435_774719531(IEEE_P_0774719531, t10, t9, t12, t11);
    t2 = t6;

LAB13:    if (t2 != 0)
        goto LAB8;

LAB10:
LAB9:    xsi_set_current_line(113, ng0);
    t1 = (t0 + 4944);
    t3 = (t0 + 3992U);
    t4 = (t0 + 2792U);
    t8 = *((char **)t4);
    memcpy(t7, t8, 16U);
    t4 = (t0 + 9216U);
    ieee_p_3564397177_sub_2250825304603680424_3564397177(IEEE_P_3564397177, t1, t3, t7, t4, (unsigned char)0, 0);
    xsi_set_current_line(114, ng0);
    t1 = (t0 + 4944);
    t3 = (t0 + 3992U);
    t4 = (t0 + 9488);
    t9 = (t21 + 0U);
    t10 = (t9 + 0U);
    *((int *)t10) = 1;
    t10 = (t9 + 4U);
    *((int *)t10) = 1;
    t10 = (t9 + 8U);
    *((int *)t10) = 1;
    t22 = (1 - 1);
    t23 = (t22 * 1);
    t23 = (t23 + 1);
    t10 = (t9 + 12U);
    *((unsigned int *)t10) = t23;
    std_textio_write7(STD_TEXTIO, t1, t3, t4, t21, (unsigned char)0, 0);
    xsi_set_current_line(115, ng0);
    t1 = (t0 + 4944);
    t3 = (t0 + 3992U);
    t4 = (t0 + 1832U);
    t8 = *((char **)t4);
    memcpy(t21, t8, 16U);
    t4 = (t0 + 9152U);
    ieee_p_3564397177_sub_2250825304603680424_3564397177(IEEE_P_3564397177, t1, t3, t21, t4, (unsigned char)0, 0);
    xsi_set_current_line(117, ng0);
    t1 = (t0 + 4944);
    t3 = (t0 + 3816U);
    t4 = (t0 + 3992U);
    std_textio_writeline(STD_TEXTIO, t1, t3, t4);
    goto LAB3;

LAB5:    xsi_set_current_line(104, ng0);
    t3 = (t0 + 2792U);
    t8 = *((char **)t3);
    t3 = (t0 + 9216U);
    t9 = (t0 + 1352U);
    t10 = *((char **)t9);
    t9 = (t0 + 9120U);
    t11 = ieee_p_0774719531_sub_1496620905533649268_774719531(IEEE_P_0774719531, t7, t8, t3, t10, t9);
    t12 = (t0 + 5992);
    t13 = (t12 + 56U);
    t14 = *((char **)t13);
    t15 = (t14 + 56U);
    t16 = *((char **)t15);
    memcpy(t16, t11, 16U);
    xsi_driver_first_trans_fast(t12);
    goto LAB6;

LAB8:    xsi_set_current_line(110, ng0);
    t13 = (t0 + 2952U);
    t14 = *((char **)t13);
    t17 = *((unsigned char *)t14);
    t18 = ieee_p_2592010699_sub_374109322130769762_2592010699(IEEE_P_2592010699, t17);
    t13 = (t0 + 6056);
    t15 = (t13 + 56U);
    t16 = *((char **)t15);
    t19 = (t16 + 56U);
    t20 = *((char **)t19);
    *((unsigned char *)t20) = t18;
    xsi_driver_first_trans_fast(t13);
    goto LAB9;

LAB11:    t2 = (unsigned char)1;
    goto LAB13;

}

static void work_a_1132065030_3212880686_p_3(char *t0)
{
    char *t1;
    unsigned char t2;
    char *t3;
    char *t4;
    int t5;
    char *t6;
    int t7;
    unsigned char t8;
    char *t9;
    char *t10;
    char *t11;
    char *t12;

LAB0:    xsi_set_current_line(125, ng0);
    t1 = (t0 + 1472U);
    t2 = ieee_p_2592010699_sub_2763492388968962707_2592010699(IEEE_P_2592010699, t1, 0U, 0U);
    if (t2 != 0)
        goto LAB2;

LAB4:
LAB3:    t1 = (t0 + 5784);
    *((int *)t1) = 1;

LAB1:    return;
LAB2:    xsi_set_current_line(126, ng0);
    t3 = (t0 + 2632U);
    t4 = *((char **)t3);
    t5 = *((int *)t4);
    t3 = (t0 + 3488U);
    t6 = *((char **)t3);
    t7 = *((int *)t6);
    t8 = (t5 == t7);
    if (t8 != 0)
        goto LAB5;

LAB7:    xsi_set_current_line(130, ng0);
    t1 = (t0 + 2632U);
    t3 = *((char **)t1);
    t5 = *((int *)t3);
    t7 = (t5 + 1);
    t1 = (t0 + 6120);
    t4 = (t1 + 56U);
    t6 = *((char **)t4);
    t9 = (t6 + 56U);
    t10 = *((char **)t9);
    *((int *)t10) = t7;
    xsi_driver_first_trans_fast(t1);

LAB6:    goto LAB3;

LAB5:    xsi_set_current_line(127, ng0);
    t3 = (t0 + 6120);
    t9 = (t3 + 56U);
    t10 = *((char **)t9);
    t11 = (t10 + 56U);
    t12 = *((char **)t11);
    *((int *)t12) = 0;
    xsi_driver_first_trans_fast(t3);
    xsi_set_current_line(128, ng0);
    t1 = (t0 + 2472U);
    t3 = *((char **)t1);
    t2 = *((unsigned char *)t3);
    t8 = ieee_p_2592010699_sub_374109322130769762_2592010699(IEEE_P_2592010699, t2);
    t1 = (t0 + 6184);
    t4 = (t1 + 56U);
    t6 = *((char **)t4);
    t9 = (t6 + 56U);
    t10 = *((char **)t9);
    *((unsigned char *)t10) = t8;
    xsi_driver_first_trans_fast(t1);
    goto LAB6;

}


extern void work_a_1132065030_3212880686_init()
{
	static char *pe[] = {(void *)work_a_1132065030_3212880686_p_0,(void *)work_a_1132065030_3212880686_p_1,(void *)work_a_1132065030_3212880686_p_2,(void *)work_a_1132065030_3212880686_p_3};
	xsi_register_didat("work_a_1132065030_3212880686", "isim/tb_sin_gen_isim_beh.exe.sim/work/a_1132065030_3212880686.didat");
	xsi_register_executes(pe);
}
