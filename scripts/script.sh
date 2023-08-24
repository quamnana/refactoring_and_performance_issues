#!/usr/bin/env bash

# find commits from Apache SVN
cd code/tmp/pdfbox || exit
git show $(git log --grep=git-svn-id:.*@1900429 --pretty=format:%H)
