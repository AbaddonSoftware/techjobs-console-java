#!/usr/bin/env bash
if [ "${1,,}" = "clean" ]; then # $1 is just the first commandline                                        # argument ,, = tolowercase
  echo "cleaning"
  rm -r out/repl.it
  rmdir out                     # this will error if directory is not
                                # empty. This is intentional to avoid deleting
                                # intellij outfiles when testing this script
                                # locally

  exit 1
fi

echo "This file compiles all source files even the launchcode"
echo "ones which are not included in chooser. running this"
echo "with 'bash compile.sh clean' will delete the compiled"
echo "classes if needed."
echo ""
echo "Would you like to compile these now? Y/N"
read RESPONSE
if [ "${RESPONSE,,}" = "y" ] || [ "${RESPONSE,,}" = "yes" ]; then
  echo "compiling sources to out/repl.it/"
  mkdir -p out/repl.it/{lib,resources}
  cp lib/* out/repl.it/lib
  cp resources/job_data.csv out/repl.it/resources/
  find -name "*.java" > sources
  javac -cp out/repl.it/lib/commons-csv-1.4.jar -d out/repl.it @sources
  echo "this directory and subdirectories may be deleted as it's only useful when running code on repl.it" > out/repl.it/youcandelete.me
  rm sources

  echo "Press any key to continue"
  read -n 1 -s -r
  clear

else
  clear
  echo -e "\e[31m!!!Sources have not been compiled!!!"
  echo -e "\e[31m   rerun if this was a mistake."
  echo -e "\e[39m"
fi


