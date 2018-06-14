#!/bin/sh

echo "Running shell script"
while read LINE; do
   echo test3:${LINE}
done