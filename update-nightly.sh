#! /bin/bash

branch=$(git branch | awk '/^\*/{print $2}')

if [ "$branch" != "master" ]; then 
  echo Skipping update of nightly as on branch $branch
  exit
fi 

echo Cloning website
git clone git@github.com:fitnesse-eclipse/fitnesse-eclipse.github.io.git
cd fitnesse-eclipse.github.io
git config user.email "fitnesseclipse@a1dutch.co.uk"
git config user.name "fitnesseclipse"

echo Clearing nightly folder
rm -rf nightly/*

echo Updating nightly folder
cp -r ~/fitnesse-eclipse/fitnesseclipse.site/target/repository/* nightly/

echo Commiting changes
git add --all
git commit -m "Update Nightly"

echo Pushing to remote
git push origin master