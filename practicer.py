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
################################################################################
import time
import os
import random
import tkinter

random.seed()
startTime = 0
prevTime = 0
currentTime = 0
timing = False
dir_path = os.path.dirname(os.path.realpath(__file__))

DEBUG = True



def getNextProb():
  global dir_path
  os.chdir(dir_path + "\\problems")

  for traversals in range(0,2):
    subdirs = os.listdir()
    randNum = random.randint(0, len(subdirs) - 1)
    os.chdir(os.getcwd() + "\\" + subdirs[randNum])
    if DEBUG:
      print(os.getcwd())

  os.startfile(os.getcwd() + "\\" + subdirs[randNum][3:] + ".pdf")


# TIMER MENU
# Should include the following buttons:
#   Start
#   Next Problem
#   Done
# As well as:
#   Log of submissions (incl. each timestamp; will be available at the end)
#   The actual timer

def getTimePassed():
  global currentTime
  global startTime
  print(str(int((currentTime - startTime) % 86400 / 3660)) + ":" +
        str(int((currentTime - startTime) % 3600 / 60)) + ":" +
        str(int((currentTime - startTime) % 60)))


def startTimer():
  global startTime, prevTime, currentTime

  startTime = prevTime = currentTime = time.time()
  timing = True
  print("timing = " + str(timing))


def updateTimer():
  global currentTime, prevTime, timing

  while timing:
    time.sleep(0.1)
    currentTime = time.time()
    if (currentTime - prevTime) >= 1:
      prevTime = currentTime
      getTimePassed()


def stopTimer():
  timing = False
  print("timing = " + str(timing))


def startGUI():
  global timing
  gui = tkinter.Tk()

  start = tkinter.Button(gui, text = "Start", command = startTimer())
  start.pack()
  stop = tkinter.Button(gui, text = "Stop", command = stopTimer())
  stop.pack()

  while True:
    gui.update_idletasks()
    gui.update()
    if timing:
      start.destroy()
      print("destroyed")


def main():
  startGUI()


main()
