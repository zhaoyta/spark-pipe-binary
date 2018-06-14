#!/bin/sh

echo "Running shell script"
while read LINE; do
   echo test1:${LINE}
done