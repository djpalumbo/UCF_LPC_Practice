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

# Need to know location of practice problems -- Assume we're in that directory
# Randomly select a problem (choose from what's available)

os.startfile('C:/Users/Dave/Google Drive/UCF/Programming Practice/' +
        'UCF Local Programming Contest/2011/11_blackcactus/' +
        'blackcactus.pdf') # (This is an example)

# Need menu for timer
