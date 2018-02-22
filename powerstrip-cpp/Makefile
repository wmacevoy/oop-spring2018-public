all : tests

# https://github.com/google/googletest
GTEST_DIR=${HOME}/projects/googletest/googletest
CXXFLAGS= -std=c++11 -isystem ${GTEST_DIR}/include -pthread -Iinclude
LDFLAGS=-Llib -lgtest

# build gtest library
lib/libgtest.a : ${GTEST_DIR}/src/gtest-all.cc
	$(CXX) -c $(CXXFLAGS) -o $@ -I${GTEST_DIR} $<

# main suitable for all tests
tmp/testMain.o : src/testMain.cpp
	$(CXX) -c $(CXXFLAGS) -o $@ $<

tmp/PowerStrip.o : src/PowerStrip.cpp include/PowerStrip.h include/Mode.h include/OutletState.h
	$(CXX) -c $(CXXFLAGS) -o $@ $<

bin/testHello : src/testHello.cpp lib/libgtest.a
	$(CXX) $(CXXFLAGS) -o $@ src/testHello.cpp $(LDFLAGS)

bin/testPowerStrip : src/testPowerStrip.cpp tmp/PowerStrip.o tmp/testMain.o lib/libgtest.a
	$(CXX) $(CXXFLAGS) -o $@ $^ src/testPowerStrip.cpp tmp/PowerStrip.o tmp/testMain.o $(LDFLAGS)

tests : bin/testPowerStrip
