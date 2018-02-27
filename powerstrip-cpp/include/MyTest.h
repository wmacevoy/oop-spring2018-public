#pragma once

#include <sstream>
#include <iostream>
#include <string>

#define DEBUG 0

#if DEBUG > 0

class AssertionError {
 public: std::string message;
 public: AssertionError(const std::string &_message) : message(_message) {}
};

template <typename T> void __assertEquals__(const char *file, int line,
                                            const char *exStr,
                                            const T &expect,
                                            const char *actStr,
                                            const T &actual) {
  if ((expect) != (actual)) {
    std::stringstream msg;
    msg << "unexpected result on line " << line << " of file " << file
        << ": expected " << expect
        << ", but got " << actual << " for " << actStr;
#if DEBUG > 10
    std::cerr << msg.str() << std::endl;
#endif
    throw AssertionError(msg.str());
  } else {
#if DEBUG > 1000
    std::cerr << "passed test on line " << line << "of file :"
              << " got " << actual << " for " << actStr << " as expected." << std::endl;
#endif
  }
}

#define assertEquals(expect,result) __assertEquals__(__FILE__,__LINE__,#expect,expect,#result,result)

#else
#define assertEquals(expect,result) 
#endif





