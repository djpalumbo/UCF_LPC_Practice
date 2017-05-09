from tkinter import *
import time
import os
import random

random.seed()
dir_path = os.path.dirname(os.path.realpath(__file__))
probList = []

DEBUG = True


# Need functions/buttons:


class Session(Frame):
  def __init__(self, parent=None, **kw):
    Frame.__init__(self, parent, kw)
    self._problems = {}
    self._current = ""

  """ Remove problem from list. """


""" Randomly selects the next problem. """
def getNextProb():
  foundNext = False
  traversals = 0
  global dir_path
  global probList # Temp global variable until implemented into Session

  while not foundNext:
    os.chdir(dir_path + "\\problems")

    # Locate random subdirectory (where there may be a programming problem)
    while traversals < 2:
      subdirs = os.listdir()
      randNum = random.randint(0, len(subdirs) - 1)
      # Ignore non-numbered directories & try to traverse
      if subdirs[randNum][:2].isdigit():
        try:
          os.chdir(os.getcwd() + "\\" + subdirs[randNum])
        except NotADirectoryError:
          traversals -= 1
      else:
        traversals -= 1
      traversals += 1
    traversals = 0

    # If problem not already chosen, do it. Otherwise, start over
    if os.getcwd() not in probList:
      foundNext = True
      probList.append(os.getcwd())
      os.startfile(os.getcwd() + "\\" + subdirs[randNum][3:] + ".pdf")


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

  """ Start the stopwatch & give first problem; ignore if running. """
  def Start(self):
    if not self._running:
      self._start = time.time() - self._elapsedtime
      self._update()
      self._running = 1
      if self._elapsedtime == 0:
        getNextProb()

  def Stop(self):
    """ Stop the stopwatch, ignore if stopped. """
    if self._running:
      self.after_cancel(self._timer)
      self._elapsedtime = time.time() - self._start
      self._setTime(self._elapsedtime)
      self._running = 0

  """ Go to next problem if running. """
  def Next(self):
    if self._running:
      getNextProb()


def main():
  root = Tk()
  sw = StopWatch(root)
  sw.pack(side=TOP)

  Button(root, text='Start', command=sw.Start).pack(side=LEFT)
  Button(root, text='Next', command=sw.Next).pack(side=LEFT) # i.e. submit?
  #  Button(root, text='Skip', command=
  #  Button(root, text='Done', command=
  #  Button(root, text='Log', command=
  Button(root, text='Stop', command=sw.Stop).pack(side=LEFT)

  #  Button(root, text='Quit', command=root.quit).pack(side=RIGHT)

  root.mainloop()

if __name__ == '__main__':
  main()
