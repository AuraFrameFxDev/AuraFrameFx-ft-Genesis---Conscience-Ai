# CMakeLists.txt for Collab Canvas Module
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name
project("collabcanvas")

# Set C++ standard
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Set compiler flags for optimization and debugging
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++20 -fPIC -O3")

# Debug build specific flags
if(CMAKE_BUILD_TYPE STREQUAL "Debug")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g -DDEBUG -DCOLLAB_CANVAS_DEBUG_BUILD")
else()
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -DNDEBUG")
endif()

# Check if COLLAB_CANVAS_BUILD is defined (passed from Gradle)
if(COLLAB_CANVAS_BUILD)
    add_definitions(-DCOLLAB_CANVAS_BUILD=1)
endif()

# Creates and names a library, sets it as SHARED
# and provides the relative paths to its source code.
add_library(${CMAKE_PROJECT_NAME} SHARED
    # List C/C++ source files with relative paths to this CMakeLists.txt.
    collab_canvas_native.cpp
)

# Set target properties
set_target_properties(${CMAKE_PROJECT_NAME} PROPERTIES
    OUTPUT_NAME "collab_canvas_native"
    LIBRARY_OUTPUT_NAME "collab_canvas_native"
)

# Specifies libraries CMake should link to your target library.
target_link_libraries(${CMAKE_PROJECT_NAME}
    # List libraries link to the target library
    android
    log
)