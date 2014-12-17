#! /bin/bash

echo checking out website
git clone git@github.com:fitnesse-eclipse/fitnesse-eclipse.github.io.git
cd fitnesse-eclipse.github.io
git config user.email "fitnesseclipse@a1dutch.co.uk"
git config user.name "fitnesseclipse"

echo clearing nightly folder
rm -rf nightly/*

echo updating nightly folder
cp -r ~/fitnesse-eclipse/fitnesseclipse.site/target/repository/* nightly/

echo commiting changes
git add --all
git commit -m "Update Nightly"

echo pushing to remote
git push origin master