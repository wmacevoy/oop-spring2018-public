#
# To build
# 
#   cd tmp
#   cmake ..
#   make
#

# Ubuntu 16.04 provides version 3.5, CLion 2017.3 uses version 3.9, Tyler Jones suggest 3.2
# is a safe minimum version...
cmake_minimum_required(VERSION 3.5)

# The name of the folder 
project(powerstrip-cpp)

# CXX STANDARD 11, 14, 17
set(CMAKE_CXX_STANDARD 14)

# set output directories
set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/lib)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/lib)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/bin)

# look for includes in include directory
include_directories(include)

file(GLOB_RECURSE ALL_SOURCES RELATIVE "${CMAKE_CURRENT_SOURCE_DIR}" ${PROJECT_SOURCE_DIR}/src/*.cpp)

# Categorize sources (skipping ones created as backups by OS X and Emacs),
# This builds SOURCES, MAINS, and TESTS
# >3.6 versions of CMake can so this much easier, like with
# But Ubuntu 16.04 ships with CMake 3.5... sigh
#
#   list(FILTER SOURCES EXCLUDE REGEX "(^|/)[.#]" )
#   list(FILTER SOURCES EXCLUDE REGEX "(^|/)main" )
#   list(FILTER SOURCES EXCLUDE REGEX "(^|/)test" )

FOREACH ( item ${ALL_SOURCES} )
  STRING ( REGEX MATCH "(^|/)[.#]" skip ${item} )
  IF (NOT skip STREQUAL "" )
     continue()
  ENDIF (NOT skip STREQUAL "" )

  STRING(REGEX MATCH "(^|/)main" main ${item})
  STRING(REGEX MATCH "(^|/)test" test ${item}) 

  IF(main)
    LIST(APPEND MAINS ${item})
  ELSEIF(test)
    LIST(APPEND TESTS ${item})
  ELSE(main)
    LIST(APPEND SOURCES ${item})  
  ENDIF(main)
ENDFOREACH(item ${ALL_SOURCES})

#
# adjust this to where you have cloned google test.  I keep my projects
# in the $HOME/projects folder, so:
#
#   cd ~/projects
#   git clone git@github.com:google/googletest.git
#
set(GTEST_DIR $ENV{HOME}/projects/googletest)

#
# these should be set relative to the GTEST_DIR folder
#
set(GTEST_TEST_DIR ${GTEST_DIR}/googletest)
set(GTEST_MOCK_DIR ${GTEST_DIR}/googlemock)

#
# googletest uses threads, even if you don't
#
find_package(Threads REQUIRED)

#
# need both directories to build the gtest library
#
include_directories(SYSTEM
  ${GTEST_TEST_DIR}/include
  ${GTEST_TEST_DIR}
)

#
# build the static gtest library (goes in lib folder)
#
add_library(gtest STATIC ${GTEST_TEST_DIR}/src/gtest-all.cc include/RetractableSolarPanelPowerStrip.h)

#
# build the static sources library (all regular source files)
#
add_library(sources STATIC ${SOURCES})

target_link_libraries(gtest ${CMAKE_THREAD_LIBS_INIT})

#
# need both directories to build the gtest library
#
add_executable(testPowerStrip src/testPowerStrip.cpp src/testMain.cpp)
target_link_libraries(testPowerStrip ${CMAKE_THREAD_LIBS_INIT} gtest sources)

add_executable(testRetractablePowerStrip src/testRetractablePowerStrip.cpp src/testMain.cpp)
target_link_libraries(testRetractablePowerStrip ${CMAKE_THREAD_LIBS_INIT} gtest sources)

add_executable(testRetractableSolarPanelPowerStrip src/testRetractableSolarPanelPowerStrip.cpp src/testMain.cpp)
target_link_libraries(testRetractableSolarPanelPowerStrip ${CMAKE_THREAD_LIBS_INIT} gtest)
