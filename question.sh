#!/usr/bin/env bash

echo "Would you like to see these in color? Y/N (really any key other than y or yes defaults to no trippy color)"
read answer
clear
[ "${answer,,}" == "y" ] || [ "${answer,,}" == "yes" ] && bash codechooser.sh | bash lolcat1960.sh 175 185 || bash codechooser.sh