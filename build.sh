#!/bin/bash

rm -rf ./target
mkdir ./target
javac -d ./target ./hu/oe/Main.java
cd ./target
jar cfe ./Main.jar hu.oe.Main ./hu/oe/Main.class
cd ..