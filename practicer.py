################################################################################
# Sources:
# http://stackoverflow.com/questions/8220108/how-do-i-check-the-operating-system-in-python
# http://stackoverflow.com/questions/5137497/find-current-directory-and-files-directory
# http://stackoverflow.com/questions/1810743/how-to-set-the-current-working-directory-in-python
# http://stackoverflow.com/questions/973473/getting-a-list-of-all-subdirectories-in-the-current-directory
# https://www.blog.pythonlibrary.org/2010/09/04/python-101-how-to-open-a-file-or-program/
#
################################################################################
from sys import platform # For OS check, if needed
import os
import random
random.seed()

DEBUG = True

# Figure out where we are
dir_path = os.path.dirname(os.path.realpath(__file__))
os.chdir(dir_path + "\\problems")

# Randomly select a problem
for traversals in range(0,2):
  subdirs = os.listdir()
  randNum = random.randint(0, len(subdirs) - 1)
  os.chdir(os.getcwd() + "\\" + subdirs[randNum])
  if DEBUG:
    print(os.getcwd())
os.startfile(os.getcwd() + "\\" + subdirs[randNum][3:] + ".pdf")

# Need menu for timer
