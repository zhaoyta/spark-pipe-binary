#!/bin/sh

echo "Running shell script"
while read LINE; do
   echo test2:${LINE}
done