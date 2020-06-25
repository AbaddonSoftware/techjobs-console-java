#!/usr/bin/env bash
# this was just a dumb idea that I had while playing with terminal colors for an error message
# and is very loosely based on the original lolcat (more hippy than rainbow here though). Someone 
# will probably want to organize the colors to make this prettier/easier to read. The real 
# much better lolcat can be found at https://github.com/busyloop/lolcat and installed with 
# apt or other package manager

beginColor=$(( $1 & 255 ))
endColor=$(( $2 & 255 ))
[ -z "$beginColor" ] && beginColor=19
[ -z "$endColor" ] && endColor=200
fgColorNumber=$beginColor
while read -r line; do
    if [[ $line =~ '[2J' ]]; then
      clear
    fi
    line=$(echo "$line" | sed -e 's/\\e\[[0-9;]*m//g' -e 's/\x1B\[[0-9;]*[a-zA-Z]//g' )
    for (( index=0; index<${#line}; index++ )); do
      echo -n -e "\e[38;5;${fgColorNumber}m${line:$index:1}"
      ((fgColorNumber=fgColorNumber+1))

     if [ "$fgColorNumber" -ge $endColor ]; then
       let fgColorNumber=$beginColor
     fi
   done
   echo
done

