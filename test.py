#!/usr/bin/python

import sys
import os
import avro.schema
import avro.io
import io


data =  sys.stdin.read()
#print len(data)
#type(data)
schema = avro.schema.parse(open("src/main/resources/stringpair.avsc", "rb").read())
bytes_reader = io.BytesIO(data)
decoder = avro.io.BinaryDecoder(bytes_reader)
reader = avro.io.DatumReader(schema)
sp = reader.read(decoder)


writer = avro.io.DatumWriter(schema)

bytes_writer = io.BytesIO()
encoder = avro.io.BinaryEncoder(bytes_writer)
writer.write({"left": "Alyssa", "right": "abc"}, encoder)
writer.write({"left": "Alyssa", "right": "abc"}, encoder)

writer.write({"left": "Alyssa", "right": "abc"}, encoder)

raw_bytes = bytes_writer.getvalue()
sys.stdout.write(bytes_writer.getvalue())
#sys.stdin.read()
#d = os.path.dirname(__file__)
#print os.path.abspath(d)