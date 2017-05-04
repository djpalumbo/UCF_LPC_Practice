################################################################################
# Sources:
# http://stackoverflow.com/questions/8220108/how-do-i-check-the-operating-system-in-python
# http://stackoverflow.com/questions/5137497/find-current-directory-and-files-directory
# http://stackoverflow.com/questions/1810743/how-to-set-the-current-working-directory-in-python
# http://stackoverflow.com/questions/973473/getting-a-list-of-all-subdirectories-in-the-current-directory
# https://www.blog.pythonlibrary.org/2010/09/04/python-101-how-to-open-a-file-or-program/
# https://www.tutorialspoint.com/python/python_gui_programming.htm
# http://stackoverflow.com/questions/27112614/tkinter-gui-stopwatch-timer
#
# Timer:
# http://code.activestate.com/recipes/124894-stopwatch-in-tkinter/
#
################################################################################
from tkinter import *
import time
import os
import random

random.seed()
dir_path = os.path.dirname(os.path.realpath(__file__))

DEBUG = True


# Timer menu needs:
#   Next
#   Done
#   Log of submissions (incl. each timestamp; will be available at the end)


def getNextProb():
  global dir_path
  os.chdir(dir_path + "\\problems")
  for traversals in range(0,2):
    subdirs = os.listdir()
    randNum = random.randint(0, len(subdirs) - 1)
    os.chdir(os.getcwd() + "\\" + subdirs[randNum])
  os.startfile(os.getcwd() + "\\" + subdirs[randNum][3:] + ".pdf")


class StopWatch(Frame):
  """ Implements a stop watch frame widget. """
  def __init__(self, parent=None, **kw):
    Frame.__init__(self, parent, kw)
    self._start = 0.0
    self._elapsedtime = 0.0
    self._running = 0
    self.timestr = StringVar()
    self.makeWidgets()

  def makeWidgets(self):
    """ Make the time label. """
    l = Label(self, textvariable=self.timestr)
    self._setTime(self._elapsedtime)
    l.pack(fill=X, expand=NO, pady=2, padx=2)

  def _update(self):
    """ Update the label with elapsed time. """
    self._elapsedtime = time.time() - self._start
    self._setTime(self._elapsedtime)
    self._timer = self.after(50, self._update)

  def _setTime(self, elap):
    """ Set the time string to Minutes:Seconds:Hundreths """
    minutes = int(elap/60)
    seconds = int(elap - minutes*60.0)
    hseconds = int((elap - minutes*60.0 - seconds)*100)
    self.timestr.set('%02d:%02d:%02d' % (minutes, seconds, hseconds))

  def Start(self):
    """ Start the stopwatch & give first problem, ignore if running. """
    if not self._running:
      self._start = time.time() - self._elapsedtime
      self._update()
      self._running = 1
      getNextProb()

  def Next(self):
    """ Go to next problem if running. """
    if self._running:
      getNextProb()


def main():
  root = Tk()
  sw = StopWatch(root)
  sw.pack(side=TOP)

  Button(root, text='Start', command=sw.Start).pack(side=LEFT)
  Button(root, text='Next', command=sw.Next).pack(side=LEFT)
  Button(root, text='Quit', command=root.quit).pack(side=RIGHT)

  root.mainloop()

if __name__ == '__main__':
  main()
