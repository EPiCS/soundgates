#!/bin/bash

find . -print | while read fn;
do
   filename=$(basename "$fn")
   extension="${filename##*.}"
   filename="${filename%.*}"
#   echo $extension
#   echo $filename
#   echo $fn

   if [[ $extension == "svg" ]]
   then
      replace="$(echo -n "$fn" | tr '[\001-\040]' '[_*]')";
      replace="$(echo "$replace" |
      sed -e 's-ä-ae-g' -e 's-ö-oe-g' -e 's-ü-ue-g' \
          -e 's-Ä-Ae-g' -e 's-Ö-Oe-g' -e 's-Ü-Ue-g' \
          -e 's-ß-ss-g')"
      if [[ $fn != $replace ]]
      then
         echo gebe Datei "$fn neuen Namen $replace";
         mv -u "$fn" "$replace";
      fi
      replacePDF="$(echo $replace | sed -e 's@.svg@.pdf@g')";
      echo export pdf nach "$replacePDF"
#      inkscape -z -A $replacePDF $replace
#      inkscape -z --export-latex -A $replacePDF $replace
       echo removing watermark
       xsltproc -o $replace --novalid removeWaterMark.xsl $replace
       echo export
       cairosvg $replace -o $replacePDF
       echo done with $replacePDF
   else
      echo nichts getan mit $fn
   fi
done
