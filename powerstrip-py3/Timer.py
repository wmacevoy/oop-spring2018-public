import datetime

class Timer:
    def __init__(self):
        self._delta = datetime.timedelta()
        self._on = Timer.timeToTimedelta(datetime.time.min)
        self._off = Timer.timeToTimedelta(datetime.time.max)

    def setDatetime(self, now):
        if not isinstance(now,datetime.datetime):
            raise ValueError("now must be a datetime")
        self._delta = now - datetime.datetime.utcnow()

    def getDatetime(self):
        return datetime.datetime.utcnow() + self._delta

    def timeToTimedelta(time):
        if not isinstance(time,datetime.time):
            raise ValueError("time must be a time")
        return datetime.timedelta(
            days=0,
            seconds=time.second,
            microseconds=time.microsecond,
            milliseconds=0,
            minutes=time.minute,
            hours=time.hour)


    def timedeltaToTime(timedelta):
        if not isinstance(timedelta,datetime.timedelta):
            raise ValueError("timedelta must be a timedelta")
        tdHour=timedelta.days*24+timedelta.seconds//(60*60)
        tdMinute=(timedelta.seconds//60) % 60
        tdSecond=timedelta.seconds % 60
        tdMicros=timedelta.microseconds
        return datetime.time(
            hour=tdHour,
            minute=tdMinute,
            second=tdSecond,
            microsecond=tdMicros)

    def setTimeOff(self, off):
        self._off = Timer.timeToTimedelta(off)

    def getTimeOff(self):
        return Timer.timedeltaToTime(self._off)

    def setTimeOn(self, on):
        self._on = Timer.timeToTimedelta(on)

    def getTimeOn(self):
        return Timer.timedeltaToTime(self._on)

    def getToday(self,value=None):
        if value != None and not isinstance(value,datetime.datetime):
            raise ValueError("value must be None or a datetime")
        if value == None: value = self.getDatetime()
        return datetime.datetime(
            year=value.year,
            month=value.month,
            day=value.day,
            hour=0,
            minute=0,
            second=0,
            microsecond=0,
            tzinfo=value.tzinfo)

    def isActive(self):
        now = self.getDatetime()
        today = self.getToday(now)
        todayOn = today + self._on
        todayOff = today + self._off
        yesterday = today - datetime.timedelta(days=1)
        yesterdayOn = yesterday + self._on
        yesterdayOff = yesterday + self._off

        if yesterdayOn > yesterdayOff and todayOff > now: return True
        if todayOn <= now and todayOff > now: return True
        return False
