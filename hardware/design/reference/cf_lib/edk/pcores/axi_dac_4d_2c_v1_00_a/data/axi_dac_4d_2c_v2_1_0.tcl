
proc run_coregen {mhsinst} {  
  set core_dir [file join [xget_hw_pcore_dir $mhsinst] ..]
  set adi_common_dir [file join $core_dir .. adi_common_v1_00_a]
  set adi_common_dir [file normalize $adi_common_dir]
  source [file join $adi_common_dir data generate_ngc_and_v.tcl]
  generate_ngc_and_v $core_dir "cf_ddsv_intp4x_1"
  generate_ngc_and_v $core_dir "cf_ddsx_1"
}  
