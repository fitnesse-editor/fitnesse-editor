#! /bin/bash
rm -rf fitnesseclipse.website/nightly/*
cp -r fitnesseclipse.site/target/repository/* fitnesseclipse.website/nightly/
git commit --all -m "Update Nightly"
