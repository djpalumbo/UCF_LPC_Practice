from tkinter import *
import time
import os
import random

random.seed()
dir_path = os.path.dirname(os.path.realpath(__file__))

DEBUG = True


# Need functions/buttons:
#   Skip
#   Done
#   Log of submissions (incl. each timestamp; will be available at the end)


class Session(Frame):
  def __init__(self, parent=None, **kw):
    Frame.__init__(self, parent, kw)
    self._problems = {}
    self._current = ""

  """ Add problem to list. """

  """ Remove problem from list. """


""" Randomly selects the next problem. """
def getNextProb():
  global dir_path
  os.chdir(dir_path + "\\problems")
  for traversals in range(0,2):
    subdirs = os.listdir()
    randNum = random.randint(0, len(subdirs) - 1)
    os.chdir(os.getcwd() + "\\" + subdirs[randNum])
  os.startfile(os.getcwd() + "\\" + subdirs[randNum][3:] + ".pdf")

  # Must ignore previous picks
  # Must ignore non-numbered folders


class StopWatch(Frame):
  def __init__(self, parent=None, **kw):
    Frame.__init__(self, parent, kw)
    self._start = 0.0
    self._elapsedtime = 0.0
    self._running = 0
    self.timestr = StringVar()
    self.makeWidgets()

  """ Make the time label. """
  def makeWidgets(self):
    l = Label(self, textvariable=self.timestr)
    self._setTime(self._elapsedtime)
    l.pack(fill=X, expand=NO, pady=2, padx=2)

  """ Update the label with elapsed time. """
  def _update(self):
    self._elapsedtime = time.time() - self._start
    self._setTime(self._elapsedtime)
    self._timer = self.after(50, self._update)

  """ Set the time string to Minutes:Seconds:Hundreths """
  def _setTime(self, elap):
    minutes = int(elap/60)
    seconds = int(elap - minutes*60.0)
    hseconds = int((elap - minutes*60.0 - seconds)*100)
    self.timestr.set('%02d:%02d:%02d' % (minutes, seconds, hseconds))

  """ Start the stopwatch & give first problem, ignore if running. """
  def Start(self):
    if not self._running:
      self._start = time.time() - self._elapsedtime
      self._update()
      self._running = 1
      getNextProb()

  """ Go to next problem if running. """
  def Next(self):
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
