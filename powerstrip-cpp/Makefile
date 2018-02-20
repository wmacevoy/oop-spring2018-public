
# https://github.com/google/googletest

GTEST_DIR=${HOME}/projects/googletest/googletest
CXXFLAGS= -std=c++11 -isystem ${GTEST_DIR}/include -pthread -Iinclude
LDFLAGS=-Llib -lgtest

lib/libgtest.a : ${GTEST_DIR}/src/gtest-all.cc
	$(CXX) -c $(CXXFLAGS) -o $@ -I${GTEST_DIR} $<

bin/testHello : src/testHello.cpp
	$(CXX) $(CXXFLAGS) -o $@ $< $(LDFLAGS)

tmp/testMain.o : src/testMain.cpp
	$(CXX) -c $(CXXFLAGS) -o $@ $<

tmp/PowerStrip.o : src/PowerStrip.cpp include/PowerStrip.h include/Mode.h include/OutletState.h
	$(CXX) -c $(CXXFLAGS) -o $@ $<

bin/testPowerStrip : src/testPowerStrip.cpp tmp/PowerStrip.o tmp/testMain.o
	$(CXX) $(CXXFLAGS) -o $@ $^ $(LDFLAGS)

