#! /bin/bash
git clone git@github.com:fitnesse-eclipse/fitnesse-eclipse.github.io.git

cd fitnesse-eclipse.github.io
git config user.email "fitnesseclipse@a1dutch.co.uk"
git config user.name "fitnesseclipse"

rm -rf nightly/*
cp -r ~/fitnesse-eclipse/fitnesseclipse.site/target/repository/* nightly/
git commit -a -u -m "Update Nightly"
git push origin master