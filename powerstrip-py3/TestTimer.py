#!/usr/bin/env python3
import unittest
import datetime
from Timer import Timer

class TestTimer(unittest.TestCase):
    def testSetTimeOn(self):
        timer = Timer()
        t = datetime.time(hour=22,minute=30,second=15,microsecond=250)
        timer.setTimeOn(t)
        s = timer.getTimeOn()
        self.assertEqual(t,s)

    def testSetTimeOff(self):
        timer = Timer()
        t = datetime.time(hour=22,minute=30,second=15,microsecond=250)
        timer.setTimeOff(t)
        s = timer.getTimeOff()
        self.assertEqual(t,s)

    def testTimerTime(self):
        timer = Timer()
        jan2010_4pm = datetime.datetime(
            year=2010,month=1,day=1,hour=16,minute=0,second=0)
        jan2010_mid = datetime.datetime(
            year=2010,month=1,day=1,hour=0,minute=0,second=0)
        timer.setDatetime(jan2010_4pm)
        now=timer.getDatetime()
        delta=(now-jan2010_4pm).total_seconds()
        self.assertLess(abs(delta),1)

        today =timer.getToday()
        delta=(today-jan2010_mid).total_seconds()
        self.assertLess(abs(delta),1)

    def testTimerAlwaysOn(self):        
        timer = Timer()
        timer.setTimeOn(datetime.time.min)
        timer.setTimeOff(datetime.time.max)
        jan2010_mid = datetime.datetime(
            year=2010,month=1,day=1,hour=0,minute=0,second=0)
        for m in range(24*60+1):
            timer.setDatetime(jan2010_mid + datetime.timedelta(seconds=60*m))
            self.assertTrue(timer.isActive())

    def testTimerAlwaysOff(self):
        timer = Timer()
        timer.setTimeOn(datetime.time.min)
        timer.setTimeOff(datetime.time.min)
        jan2010_mid = datetime.datetime(
            year=2010,month=1,day=1,hour=0,minute=0,second=0)
        for m in range(24*60+1):
            timer.setDatetime(jan2010_mid + datetime.timedelta(seconds=60*m))
            self.assertFalse(timer.isActive())
            
    def testTimer10pmTil1am(self):
        timer = Timer()
        jan2010midnight = datetime.datetime(
            year=2010,month=1,day=1,hour=0,minute=0,second=0)
        timer.setDatetime(jan2010midnight)
        tenPm = datetime.time(hour=22,minute=0,second=0,microsecond=0)
        timer.setTimeOn(tenPm)
        oneAm = datetime.time(hour=1,minute=0,second=0,microsecond=0)
        timer.setTimeOff(oneAm)
        self.assertTrue(timer.isActive())
        
        jan2010_2am = datetime.datetime(2010,1,1,2,0,0)        
        timer.setDatetime(jan2010_2am)
        self.assertFalse(timer.isActive())

        dec2009_9pm = datetime.datetime(2009,12,31,21,0,0)        
        timer.setDatetime(dec2009_9pm)        
        self.assertFalse(timer.isActive())

    def testTimer10amTil1pm(self):
        timer = Timer()
        timer.debug = True
        jan2010midnight = datetime.datetime(2010,1,1,0,0,0)
        timer.setDatetime(jan2010midnight)
        tenAm = datetime.time(10,0,0,0)
        timer.setTimeOn(tenAm)
        onePm = datetime.time(13,0,0,0)
        timer.setTimeOff(onePm)
        self.assertFalse(timer.isActive())
        
        jan2010_2am = datetime.datetime(2010,1,1,2,0,0)        
        timer.setDatetime(jan2010_2am)
        self.assertFalse(timer.isActive())

        dec2009_11am = datetime.datetime(2009,12,31,11,0,0)        
        timer.setDatetime(dec2009_11am)
        self.assertTrue(timer.isActive())

if __name__ == '__main__':
    unittest.main()
